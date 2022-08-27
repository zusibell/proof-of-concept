package com.example.bff.dao.impl;

import com.example.bff.domain.ApplicationStatus;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.helpers.HttpHeaderHelper;
import org.apache.cxf.jaxrs.client.JAXRSClientFactoryBean;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.RefreshableKeycloakSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import javax.annotation.PostConstruct;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.*;
import java.util.*;
import java.util.function.Supplier;

@Slf4j
public abstract class AbstractRestDao {

    private static final String BASE_URL_PATTERN = "client.http.%s.base-url";
    private static final String DEFAULT_NAME = "defaul";

    @Autowired
    private Environment environment;

    private WebClient template;
    private final String daoName;
    private boolean sendBearerToken;

    public AbstractRestDao(String daoName) {
        this.daoName = daoName;
    }

    @PostConstruct
    public void init() {
        String baseUrl = getProperty(BASE_URL_PATTERN, String.class, null);
        if (baseUrl == null) {
            throw new IllegalArgumentException("Service base URL for DAO " + daoName + " must not be null");
        }

        JAXRSClientFactoryBean factory = new JAXRSClientFactoryBean();
        factory.setAddress(baseUrl);

        configureClientFactory(factory);
        template = factory.createWebClient();

        HTTPConduit conduit = WebClient.getConfig(template).getHttpConduit();
        conduit.getClient().setAllowChunking(false);
        configureClientTemplate(template);
    }

    public final void setSendBearerToken(boolean send){
        this.sendBearerToken = send;
    }

    private <T> T getProperty(String propertyPattern, Class<T> returnType, T defaultValue) {
        T value = environment.getProperty(String.format(propertyPattern, daoName), returnType);
        if (value == null) {
            value = environment.getProperty(String.format(propertyPattern, DEFAULT_NAME), returnType, defaultValue);
        }
        return value;
    }

    protected WebClient createClient() {
        WebClient client = WebClient.fromClient(template, true);
        if (sendBearerToken){
            KeycloakPrincipal authentication = (KeycloakPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            var token = authentication.getKeycloakSecurityContext().getTokenString();
            log.debug(token);

            if (token != null) {
                log.debug(token);
                client.header(HttpHeaderHelper.AUTHORIZATION, "Bearer "+token);

            }
        }
        return client;
    }

    protected void configureClientFactory(JAXRSClientFactoryBean factory) {
        // empty default implementation
    }

    protected void configureClientTemplate(WebClient client) {
        // empty default implementation
    }

    /**
     * Retrieve a resource from a REST service.
     *
     * @param <T>        the generic type
     * @param targetType type of the returned object
     * @param path       remote path (may include placeholders)
     * @param pathArgs   arguments used to fill in placeholders in <em>path</em>
     * @return remote object
     */
    protected <T> T findSingleResource(Class<T> targetType, String path, Object... pathArgs) throws Exception {
        WebClient client = createClient().path(path, pathArgs);
        return invoke(client, () -> client.get(targetType));
    }

    /**
     * Retrieve a collection resource from a REST service.
     *
     * @param <T>        the generic type
     * @param targetType type of the returned object
     * @param path       remote path (may include placeholders)
     * @param pathArgs   arguments used to fill in placeholders in <em>path</em>
     * @return list of remote objects
     */
    protected <T> Collection<T> findCollectionResource(Class<T> targetType, String path, Object... pathArgs) throws Exception {
        WebClient client = createClient().path(path, pathArgs);
        Collection<T> result = invoke(client, () -> Collections.unmodifiableCollection(client.getCollection(targetType)));
        if (result == null) {
            result = Collections.emptyList();
        }
        return result;
    }

    /**
     * Post a resource to a REST service and receive the response.
     *
     * @param <T>        the generic return type
     * @param request    the object to post
     * @param targetType type of the returned object
     * @param path       remote path (may include placeholders)
     * @param pathArgs   arguments used to fill in placeholders in <em>path</em>
     * @return remote objects
     */
    protected <T> T postSingleResource(Object request, Class<T> targetType, String path, Object... pathArgs) throws Exception {
        WebClient client = createClient().path(path, pathArgs);
        return invoke(client, () -> client.post(request, targetType));
    }


    /**
     * Put a resource to a REST service and receive the response.
     *
     * @param <T>        the generic return type
     * @param request    the object to post
     * @param targetType type of the returned object
     * @param path       remote path (may include placeholders)
     * @param pathArgs   arguments used to fill in placeholders in <em>path</em>
     * @return remote objects
     */
    protected <T> T putSingleResource(Object request, Class<T> targetType, String path, Object... pathArgs) throws Exception {
        WebClient client = createClient().path(path, pathArgs);
        return invoke(client, () -> client.put(request, targetType));
    }

    /**
     * Invoke an operation on the web client. Exceptions resulting from the service call will be
     * handled (all errors will be rethrown as DAO exceptions.)
     *
     * @param client   web client
     * @param function operation to call
     * @param <T>      return type
     * @return service call result
     */
    protected <T> T invoke(WebClient client, Supplier<T> function) throws Exception {
        try {
            return function.get();
        } catch (WebApplicationException ex) {
            var status = constructApplicationExceptionFromStatusCode(ex);
            // wrap these exceptions so that the exception mappers can see them
            throw new Exception("Error calling " + client.getCurrentURI().toString() + "\nStatus: " + status + "\nException: " + ex);
        }
    }

    private ApplicationStatus constructApplicationExceptionFromStatusCode(WebApplicationException ex) {
        Response response = ex.getResponse();
        ApplicationStatus status = ApplicationStatus.UNSPECIFIED_ERROR;
        switch (response.getStatus()) {
            case 400:
                status = ApplicationStatus.INVALID_REQUEST;
                break;
            case 403:
                status = ApplicationStatus.FORBIDDEN;
                break;
            case 404:
                status = ApplicationStatus.RESOURCE_DOES_NOT_EXIST;
                break;
        }
        return status;
    }



}
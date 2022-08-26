package com.example.bff.dao.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import org.apache.cxf.jaxrs.client.JAXRSClientFactoryBean;
import org.apache.cxf.jaxrs.client.WebClient;

import javax.ws.rs.core.MediaType;

public abstract class AbstractJsonRestDao extends AbstractRestDao {
    public AbstractJsonRestDao(String daoName) {
        super(daoName);
    }

    @Override
    protected void configureClientTemplate(WebClient client) {
        client.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON);
    }

    @Override
    protected void configureClientFactory(JAXRSClientFactoryBean factory) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.registerModule(new Jdk8Module());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true);
        mapper.configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
        factory.setProvider(new JacksonJsonProvider(mapper));
    }
}

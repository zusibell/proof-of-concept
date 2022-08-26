package com.example.bff.domain;

import javax.ws.rs.core.Response;

public enum ApplicationStatus {
    /**
     * The resource does not exist.
     */
    RESOURCE_DOES_NOT_EXIST("Requested resource does not exist", "NO_RESOURCE", Response.Status.NOT_FOUND),
    /**
     * Forbidden.
     */
    FORBIDDEN("Current user is not allowed to perform the requested action", "FORBIDDEN", Response.Status.FORBIDDEN),
    CLAIMING_NOT_ALLOWED("The task is already claimed", "CLAIMING_NOT_ALLOWED", Response.Status.FORBIDDEN),
    SERVICE_UNAVAILABLE("The requested service is currently unavailable", "SERVICE_UNAVAILABLE", Response.Status.SERVICE_UNAVAILABLE),
    /**
     * The invalid request.
     */
    INVALID_REQUEST("Request is not valid", "INVALID_REQUEST", Response.Status.BAD_REQUEST),
    /**
     * The unspecified error.
     */
    UNSPECIFIED_ERROR("General error", "UNKNOWN", Response.Status.INTERNAL_SERVER_ERROR),
    /**
     * A request timed out.
     */
    REQUEST_TIMEOUT("Request timed out", "REQUEST_TIMEOUT", Response.Status.REQUEST_TIMEOUT),
    RATING_NOT_AVAILABLE("No rating is available for this partner", "NO_RATING", Response.Status.BAD_REQUEST),
    TOO_MANY_RESULTS("Too many query results", "TOO_MANY_RESULTS", Response.Status.BAD_REQUEST),
    INVALID_PARTNER("Partner does not match filter criteria", "INVALID_PARTNER", Response.Status.BAD_REQUEST),
    NO_APPLICATION_ROLES("No users with requested roles found", "NO_AUTHORIZED_USERS", Response.Status.NOT_FOUND),
    LENGTH_REQUIRED("Length required", "LENGTH_REQUIRED", Response.Status.LENGTH_REQUIRED),
    REQUEST_ENTITY_TOO_LARGE("Request entity too large", "REQUEST_ENTITY_TOO_LARGE", Response.Status.REQUEST_ENTITY_TOO_LARGE),
    INVALID_USER("User not found", "INVALID_USER", Response.Status.NOT_FOUND);
    /**
     * The message.
     */
    private String message;
    /**
     * The code.
     */
    private String code;
    /**
     * The return code.
     */
    private Response.Status returnCode;

    /**
     * Create a new status.
     *
     * @param message    the error message
     * @param code       the error code
     * @param returnCode the HTTP response code
     */
    ApplicationStatus(String message, String code, Response.Status returnCode) {
        this.message = message;
        this.code = code;
        this.returnCode = returnCode;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Response.Status getReturnCode() {
        return returnCode;
    }

}

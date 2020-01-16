package com.cogent.loginservice.constants;

/**
 * This class includes the name and API end points of other microservices that we need to communicate.
 *
 */
public class MicroServiceConstants {

    public static final String BASE_API = "/api/user";

    public interface DbMicroServiceConstants {
        String BASE = "db-producer";
        String SEARCH_USER = "/get/{username}";
        String UPDATE_USER_LOGIN_ATTEMPT = "/updateLoginAttempt/{username}/{loginAttempt}";
    }
}

package com.fitness.centrale.centralefitness;

/**
 * Created by remy on 16/03/17.
 */

public class Constants {


    /**
     * SERVER INFO
     */
    public static String HOST = "192.168.0.103";
    public static String PORT  = "8080";
    public static String SERVER = "http://" + HOST + ":" + PORT;


    /*
     * SERVER PATHS
     */
    public static String REGISTER = "/registration";
    public static String AUTHCREDS = "/authentication";
    public static String AUTHTOKEN = "/authentication/token";
    public static String INSTANTWATT = "/user/watt/instant";
    public static String GETPROFILE = "/user/get/profile";
    public static String UPDATEPROFILE = "/user/update/profile";
    public static String UPDATEPROFILEPICTURE = "/user/update/picture";
    public static String GETPROFILEPICTURE = "/user/get/picture";
    public static String CHANGEPASSWORD = "/user/update/password";
    public static String USER_PAIR_START = "/user/pair/start";
    public static String GET_INSTANT_PRODUCTION = "/user/get/instantproduction";
    public static String USER_PAIR_STOP = "/user/pair/stop";
    public static String GET_EVENTS_IDS = "/get/events";


    /*
     *  REQUESTS VARIABLES
     */
    public static String LOGIN = "login";
    public static String PASSWORD = "password";
    public static String NEW_PASSWORD = "new password";
    public static String CONFIRM_PASSWORD = "confirmation";
    public static String TOKEN  ="token";
    public static String EMAIL = "email address";
    public static String PHONE = "phone number";
    public static String FIRST_NAME = "first name";
    public static String LAST_NAME="last name";
    public static String PICTURE = "picture";
    public static String SESSIONID= "session id";
    public static String START="start";
    public static String END="end";


}

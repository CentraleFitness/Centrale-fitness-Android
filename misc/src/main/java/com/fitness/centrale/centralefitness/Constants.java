package com.fitness.centrale.centralefitness;

/**
 * Created by remy on 16/03/17.
 */

public class Constants {


    /**
     * SERVER INFO
     */
    public static String HOST = "82.197.180.99";
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



    /*
     *  REQUESTS VARIABLES
     */
    public static String LOGIN = "login";
    public static String PASSWORD = "password";
    public static String TOKEN  ="token";
    public static String EMAIL = "email address";
    public static String PHONE = "phone number";
    public static String FIRST_NAME = "first name";
    public static String LAST_NAME="last name";
    public static String PICTURE = "picture";


}

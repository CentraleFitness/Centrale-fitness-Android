package com.fitness.centrale.centralefitness;

/**
 * Created by remy on 16/03/17.
 */

public class Constants {


    /**
     * SERVER INFO
     */
    public static String HOST = "192.168.1.102";
    public static String PORT  = "8080";
    public static String SERVER = "http://" + HOST + ":" + PORT;


    /*
     * SERVER PATHS
     */
    public static String REGISTER = "/registration";
    public static String AUTHCREDS = "/authentication";
    public static String AUTHTOKEN = "/authentication/token";


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

}

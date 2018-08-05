package com.fitness.centrale.misc;

/**
 * Created by remy on 16/03/17.
 */

public class Constants {


    /**
     * SERVER INFO
     */
    public static String HOST = "192.168.1.27";
    //public static String HOST = "163.5.84.201";
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
    public static String GET_EVENTS_PREVIEW = "/get/eventpreview";
    public static String GET_AFFILIATION = "/get/affiliation";
    public static String AFFILIATE="/affiliate";
    public static String REGISTER_EVENT="/event/registration";
    public static String GET_POSTS="/get/posts";
    public static String GET_POST_CONTENT="/get/postcontent";
    public static String GET_STATS_SESSIONS="/get/sportsessions";
    public static String GET_STATS_SESSION="/get/sportsession";
    public static String GET_STATS_DETAILS="/get/sportsessionstats";



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
    public static String EVENTID="eventid";
    public static String SPORTCENTERID="sport center id";
    public static String AFFILIATIONTOKEN="affiliation token";
    public static String ISREG="isreg";
    public static String TARGETID="target id";
    public static String POSTID="post id";
    public static String POSTTYPE="post type";
    public static String POSTICON="post icon";
    public static String POSTDATE="post date";
    public static String POSTCONTENT="post content";


}

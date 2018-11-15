package com.fitness.centrale.misc;

/**
 * Created by remy on 16/03/17.
 */

public class Constants {


    /**
     * Misc values
     */
    public static boolean DEMO_AVAILABLE = true;

    /**
     * SERVER INFO
     */
    public static String HOST = "91.121.155.83";
    //public static String HOST = "163.5.84.201";
    public static String PORT  = "8081";
    public static String SERVER = "http://" + HOST + ":" + PORT;
    public static String VERSION = "1.0.1BETA";


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
    public static String GET_CHALLENGES = "/challenge/get";
    public static String GET_EVENTS_PREVIEW = "/get/eventpreview";
    public static String GET_AFFILIATION = "/get/affiliation";
    public static String AFFILIATE="/affiliate";
    public static String REGISTER_EVENT="/event/registration";
    public static String GET_POSTS="/get/posts";
    public static String GET_POST_CONTENT="/get/postcontent";
    public static String GET_STATS_SESSIONS="/get/sportsessions";
    public static String GET_STATS_SESSION="/get/sportsession";
    public static String GET_STATS_DETAILS="/get/sportsessionstats";
    public static String GET_CUSTOM_PROGRAMS="/customProgram-get-range";
    public static String GET_CUSTOM_PROGRAM_PREVIEW ="/customProgram-get-preview";
    public static String GET_CUSTOM_PROGRAM_STEPS= "/customProgram-get-steps";
    public static String LIKE_POST = "/post-like";
    public static String GET_POST_LIKES = "/post-get-likes";
    public static String CREATE_POST = "/post-create";
    public static String GET_POST_COMMENTS = "/post-comment-get-range";
    public static String CREATE_COMMENT = "/post-comment-create";
    public static String UNAFFILIATE = "/unaffiliate";

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
    public static String GYMID="sport center id";
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
    public static String CUSTOMPROGRAMID="custom program id";


}

package com.fitness.centrale.misc.store;

/**
 * Created by Psyycker on 08/02/2018.
 */

public class Store {


    private static Store store;

    public static Store getStore(){
        if (store == null){
            store = new Store();
        }
        return store;
    }



    /*
     * USER
     */
    private UserObject userObject;


    public void setUser(final String firstName, final String lastName, final String email, final String login, final String phone){
        userObject = new UserObject(firstName, lastName, email, login, phone);

    }

    public void removeUser(){
        userObject = null;
    }

    public void updateUser(final UserObject object){
        userObject = object;
    }

    public UserObject getUserObject(){
        if (userObject == null) {
            userObject = new UserObject();
        }
        return userObject;
    }
    /*
    END USER
     */

    /*
     * LAST SESSION
     */
    private LastSessionObject sessionObject;


    public void setSession(final int sessionAverage){
        this.sessionObject = new LastSessionObject(sessionAverage);
    }

    public LastSessionObject getLastSessionObject(){
        if (sessionObject == null) {
            sessionObject = new LastSessionObject();
        }
        return sessionObject;
    }
    /*
    END LAST SESSION
     */




}

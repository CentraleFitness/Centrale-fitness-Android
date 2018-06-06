package com.fitness.centrale.centralefitness.store;

/**
 * Created by Psyycker on 08/02/2018.
 */

public class UserObject {

    public String firstName = "" ;
    public String lastName ="";
    public String email ="";
    public String login ="";
    public String phone ="";
    public String gymId = "";

    public UserObject(){

    }


    public UserObject(final String firstName, final String lastName, final String email, final String login, final String phone){

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.login = login;
        this.phone = phone;

    }


}

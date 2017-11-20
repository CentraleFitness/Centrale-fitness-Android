package com.fitness.centrale.centralefitness;

/**
 * Created by remy on 16/03/17.
 */

public enum Paths {

    AUTH_WITH_CREDS(Constants.AUTHCREDS),
    AUTH_WITH_TOKEN(Constants.AUTHTOKEN),
    REGISTER(Constants.REGISTER),
    INSTANTWATT(Constants.INSTANTWATT),
    GETPROFILE(Constants.GETPROFILE),
    UPDATEPROFILE(Constants.UPDATEPROFILE),
    UPDATEPROFILEPICTURE(Constants.UPDATEPROFILEPICTURE);



    String value;

    Paths(String str) {
        value = str;
    }

    @Override
    public String toString() {
        return value;
    }
}

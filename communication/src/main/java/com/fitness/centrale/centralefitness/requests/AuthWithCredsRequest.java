package com.fitness.centrale.centralefitness.requests;

import com.fitness.centrale.centralefitness.Communication;
import com.fitness.centrale.centralefitness.Constants;
import com.fitness.centrale.centralefitness.Paths;
import com.fitness.centrale.centralefitness.RequestObject;

/**
 * Created by remy on 16/03/17.
 */

public class AuthWithCredsRequest extends Communication {


    public AuthWithCredsRequest(String username, String password){
        requestObject = new RequestObject();
        requestObject.put(Constants.LOGIN, username);
        requestObject.put(Constants.PASSWORD, password);
        path = Paths.AUTH_WITH_CREDS;
    }


    public static void main(String[] args) {
        new AuthWithCredsRequest("toto", "tata").prepareRequest();
    }


}

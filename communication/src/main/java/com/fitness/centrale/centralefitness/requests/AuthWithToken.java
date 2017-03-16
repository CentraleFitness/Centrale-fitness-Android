package com.fitness.centrale.centralefitness.requests;

import com.fitness.centrale.centralefitness.Communication;
import com.fitness.centrale.centralefitness.Constants;
import com.fitness.centrale.centralefitness.Paths;
import com.fitness.centrale.centralefitness.RequestObject;

/**
 * Created by remy on 16/03/17.
 */

public class AuthWithToken extends Communication {

    public AuthWithToken(String token) {
        path = Paths.AUTH_WITH_TOKEN;
        requestObject = new RequestObject();
        requestObject.put(Constants.TOKEN, token);
    }
}

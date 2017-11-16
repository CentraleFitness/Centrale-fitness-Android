package com.fitness.centrale.centralefitness.requests;

import com.fitness.centrale.centralefitness.Communication;
import com.fitness.centrale.centralefitness.Constants;
import com.fitness.centrale.centralefitness.Paths;
import com.fitness.centrale.centralefitness.RequestObject;
import com.fitness.centrale.centralefitness.ResponseObject;

/**
 * Created by remy on 16/03/17.
 */

public class GetProfileRequest extends Communication {


    public GetProfileRequest(String token){
        requestObject = new RequestObject();
        requestObject.put(Constants.TOKEN, token);
        path = Paths.GETPROFILE;
    }


    public static void main(String[] args) {
        ResponseObject obj = new GetProfileRequest("eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJwc3l5Y2tlciJ9.3s5ir8bm4fg5dIzb7SsAv2QG5J13VksSb92v9apn01g").prepareRequest();
        System.out.println();
    }


}

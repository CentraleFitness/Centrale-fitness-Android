package com.fitness.centrale.centralefitness.requests;

import com.fitness.centrale.centralefitness.Communication;
import com.fitness.centrale.centralefitness.Constants;
import com.fitness.centrale.centralefitness.Paths;
import com.fitness.centrale.centralefitness.RequestObject;
import com.fitness.centrale.centralefitness.ResponseObject;

/**
 * Created by remy on 16/03/17.
 */

public class RegisterRequest extends Communication {


    public RegisterRequest(String username, String password, String email, String name, String firstname, String phone){
        requestObject = new RequestObject();
        requestObject.put(Constants.LOGIN, username);
        requestObject.put(Constants.PASSWORD, password);
        requestObject.put(Constants.EMAIL, email);
        requestObject.put(Constants.FIRST_NAME, firstname);
        requestObject.put(Constants.LAST_NAME, name);
        requestObject.put(Constants.PHONE, phone);
        path = Paths.REGISTER;
    }


    public static void main(String[] args) {
        System.out.println(new RegisterRequest("toto", "tata", "toto@tata.fr", "tata", "toto", "0656643212").prepareRequest().get("code"));
    }


}

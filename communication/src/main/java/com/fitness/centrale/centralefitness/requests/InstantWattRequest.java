package com.fitness.centrale.centralefitness.requests;

import com.fitness.centrale.centralefitness.Communication;
import com.fitness.centrale.centralefitness.Constants;
import com.fitness.centrale.centralefitness.Paths;
import com.fitness.centrale.centralefitness.Prefs;
import com.fitness.centrale.centralefitness.RequestObject;

/**
 * Created by remy on 22/03/17.
 */

public class InstantWattRequest extends Communication {


    public InstantWattRequest(){
        requestObject = new RequestObject();
        requestObject.put(Constants.TOKEN, Prefs.getToken());
        path = Paths.INSTANTWATT;
    }

}

package com.fitness.centrale.centralefitness;

/**
 * Created by remy on 16/03/17.
 */

public class Communication {


    protected RequestObject requestObject;
    protected Paths path;


    protected ResponseObject prepareRequest(){
        SendRequestObject obj = new SendRequestObject();
        obj.messageToSend = requestObject;
        new RequestLauncher(obj, path).start();
        boolean canRun = true;
        while (canRun) {
            synchronized (obj) {
                canRun = obj.isRunning;
            }
            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return obj.response;
    }



}

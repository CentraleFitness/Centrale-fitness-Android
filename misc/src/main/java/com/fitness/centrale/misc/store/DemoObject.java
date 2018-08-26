package com.fitness.centrale.misc.store;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Psyycker on 08/02/2018.
 */

public class DemoObject {


    public static class Session{
        public LinkedList<Float> values;
        public String date;
        public String machineType = "bike";
    }

    public boolean demo = false ;
    public boolean enterInDemo = true;
    public boolean enterInSessionPage = true;
    public boolean enterInSocial = true;
    public List<Session> sessionList = new LinkedList<>();

    public DemoObject(){

    }


    public DemoObject(final boolean demo){

        this.demo = demo;

    }


}

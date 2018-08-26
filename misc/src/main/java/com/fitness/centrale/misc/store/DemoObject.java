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
    }

    public boolean demo = false ;
    public boolean enterInDemo = false;
    public boolean enterInSessionPage = false;
    public boolean enterInSocial = false;
    public List<Session> sessionList;

    public DemoObject(){

    }


    public DemoObject(final boolean demo){

        this.demo = demo;

    }


}

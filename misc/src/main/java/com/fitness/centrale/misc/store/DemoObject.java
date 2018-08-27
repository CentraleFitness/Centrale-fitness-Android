package com.fitness.centrale.misc.store;

import java.util.Date;
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
    public static class Event{
        public Date startDate;
        public Date endDate;
        public boolean registered = false;
        public String description;
        public String name;
        public String picture;
        public String id;
    }

    public static class Post{
        public Date postDate = new Date();
        public String content;
        public String poster;
    }

    public boolean demo = false ;
    public boolean enterInDemo = true;
    public boolean enterInSessionPage = true;
    public boolean enterInSocial = true;
    public List<Session> sessionList = new LinkedList<>();
    public List<Event> eventList = new LinkedList<>();
    public List<Post> postsList = new LinkedList<>();

    public DemoObject(){

        Event event1 = new Event();
        event1.name = "Evénement 1";
        event1.startDate = new Date(1534978800000L);
        event1.endDate = new Date(1566514800000L);
        event1.registered = false;
        event1.description = "Voici la description de l'événement 1.";
        event1.id = "0";

        Event event2 = new Event();
        event2.name = "Evénement 2";
        event2.startDate = new Date(1534978800000L);
        event2.endDate = new Date(1566514800000L);
        event2.registered = false;
        event2.description = "Voici la description de l'événement 2.";
        event2.id = "1";


        eventList.add(event1);
        eventList.add(event2);


        Post post1 = new Post();
        post1.content = "Coucou";
        post1.poster = "Centrale Fitness";

        postsList.add(post1);

    }

    public Event getEventById(final String id){
        for (Event event : eventList){
            if (event.id.equals(id))
                return event;
        }
        return null;
    }

    public List<Event> getRegisteredEvents(){
        List<Event> registered = new LinkedList<>();
        for (Event event : eventList){
            if (event.registered)
                registered.add(event);
        }
        return registered;
    }

    public DemoObject(final boolean demo){

        this.demo = demo;

    }


}

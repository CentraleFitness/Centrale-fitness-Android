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

    public static class Challenge {
        public String machineType;
        public String title;
        public String desc;
        public String pointsNeeded;
        public String steps = "[40, 80, 120, 160]";
        public int userPoints = 0;
    }

    public static class Post{
        public Date postDate = new Date();
        public String content;
        public String poster;
    }

    public boolean demo = false ;
    public boolean enterInDemo = false;
    public boolean enterInSessionPage = false;
    public boolean enterInSocial = false;
    public List<Session> sessionList = new LinkedList<>();
    public List<Event> eventList = new LinkedList<>();
    public List<Post> postsList = new LinkedList<>();
    public List<Challenge> challengesList = new LinkedList<>();

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

        Event event3 = new Event();
        event3.name = "Evénement 3";
        event3.startDate = new Date(1534978800000L);
        event3.endDate = new Date(1566514800000L);
        event3.registered = false;
        event3.description = "Voici la description de l'événement 3.";
        event3.id = "2";

        Event event4 = new Event();
        event4.name = "Evénement 4";
        event4.startDate = new Date(1534978800000L);
        event4.endDate = new Date(1566514800000L);
        event4.registered = true;
        event4.description = "Voici la description de l'événement 4.";
        event4.id = "3";

        Event event5 = new Event();
        event5.name = "Evénement 5";
        event5.startDate = new Date(1534978800000L);
        event5.endDate = new Date(1566514800000L);
        event5.registered = false;
        event5.description = "Voici la description de l'événement 5.";
        event5.id = "4";

        Event event6 = new Event();
        event6.name = "Evénement 6";
        event6.startDate = new Date(1534978800000L);
        event6.endDate = new Date(1566514800000L);
        event6.registered = true;
        event6.description = "Voici la description de l'événement 6.";
        event6.id = "5";


        eventList.add(event1);
        eventList.add(event2);
        eventList.add(event3);
        eventList.add(event4);
        eventList.add(event5);
        eventList.add(event6);


        Challenge challenge1 = new Challenge();
        challenge1.title = "Dépensez vous en vélo !";
        challenge1.desc = "Pédalez le plus possible et gagner le platine !";
        challenge1.machineType = "bike";
        challenge1.pointsNeeded = "250";
        challenge1.userPoints = 47;

        Challenge challenge2 = new Challenge();
        challenge2.title = "Dépensez vous en vélo !";
        challenge2.desc = "Pédalez le plus possible et gagner le platine !";
        challenge2.machineType = "heliptic";
        challenge2.pointsNeeded = "250";
        challenge2.userPoints = 130;


        Challenge challenge3 = new Challenge();
        challenge3.title = "Dépensez vous en Tapis de course !";
        challenge3.desc = "Pédalez le plus possible et gagner le platine !";
        challenge3.machineType = "trake";
        challenge3.pointsNeeded = "250";
        challenge3.userPoints = 170;



        challengesList.add(challenge1);
        challengesList.add(challenge2);
        challengesList.add(challenge3);

        Post post1 = new Post();
        post1.content = "Eclatez vous dans votre salle de sport aujourd'hui !";
        post1.poster = "Centrale Fitness";

        Post post2 = new Post();
        post2.content = "Centrale fitness c'est génial ! !";
        post2.poster = "Un sportif";

        Post post3 = new Post();
        post3.content = "Et une session de plus aujourd'hui !";
        post3.poster = "Un accroc au sport";

        Post post4 = new Post();
        post4.content = "Aujourd'hui je soulève 250Kg";
        post4.poster = "Jean Claude Gros Biceps";

        Post post5 = new Post();
        post5.content = "Help ! Je suis coincé sous une barre !";
        post5.poster = "Alfred Corps de Lache";

        Post post6 = new Post();
        post6.content = "La semaine prochaine, petite surprise dans votre salle !";
        post6.poster = "Le gérant sympa";

        Post post7 = new Post();
        post7.content = "J'ai fait 3 pompes d'affilées !";
        post7.poster = "Alfred Corps de Lache";

        Post post8 = new Post();
        post8.content = "Du monde en salle aujourd'hui ?";
        post8.poster = "Pierrot Tire au Flanc";


        postsList.add(post1);
        postsList.add(post2);
        postsList.add(post3);
        postsList.add(post4);
        postsList.add(post5);
        postsList.add(post6);
        postsList.add(post7);
        postsList.add(post8);


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

package utils;


import entities.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;

public class Generate {

    public static void main(String[] args) throws ParseException {

        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        User user = new User("user", "test123");
        User admin = new User("admin", "test123");
        User both = new User("user_admin", "test123");

        Role userRole = new Role("user");
        Role adminRole = new Role("admin");

        user.addRole(userRole);
        admin.addRole(adminRole);
        both.addRole(userRole);
        both.addRole(adminRole);



        Guest guest1 = new Guest("Kurt Larsen","29726382","kurt@gmail.com","aktiv");
        Guest guest2 = new Guest("Svend Knudsen","71926518","svend@knudmail.com","aktiv");
        Guest guest3 = new Guest("Rasmus Jensen","93517283","rasmus@hotmail.com","aktiv");
        Guest guest4 = new Guest("Paul Hansen","47192735","pauk@gmail.com","inaktiv");
        Guest guest5 = new Guest("Bilal Madsen","27192832","bilal@newmail.com","inaktiv");
        Guest guest6 = new Guest("Kim Rasmussen","37109265","Kim@swanmail.com","aktiv");

        ShowEntity show1 = new ShowEntity("Trylleshowet",15,"Scene 5","15-06-2022","13:15");
        ShowEntity show2 = new ShowEntity("Rasmus Seebach",30,"Store Scene","15-06-2022","19:00");
        ShowEntity show3 = new ShowEntity("Anders And",60,"Scene 1","16-06-2022","15:30");
        ShowEntity show4 = new ShowEntity("Kim Larsen",20,"Scene 2","16-06-2022","17:30");
        ShowEntity show5 = new ShowEntity("Knud Madsen",15,"scene 2","17-06-2022","14:30");

        Festival festival1 = new Festival("Festival1","City1","13-06-2022",7);
        Festival festival2 = new Festival("Festival2","City2","10-06-2022",7);


        guest1.addShow(show1);
        guest1.addShow(show2);
        guest2.addShow(show2);
        guest2.addShow(show3);
        guest3.addShow(show1);
        guest4.addShow(show4);
        guest4.addShow(show5);
        guest5.addShow(show5);
        guest6.addShow(show4);
        guest6.addShow(show5);

        guest1.setFestival(festival1);
        guest2.setFestival(festival1);
        guest3.setFestival(festival1);
        guest4.setFestival(festival2);
        guest5.setFestival(festival2);
        guest6.setFestival(festival2);


        em.getTransaction().begin();

        em.persist(userRole);
        em.persist(adminRole);
        em.persist(user);
        em.persist(admin);
        em.persist(both);

        em.persist(guest1);
        em.persist(guest2);
        em.persist(guest3);
        em.persist(guest4);
        em.persist(guest5);
        em.persist(guest6);

        em.persist(show1);
        em.persist(show2);
        em.persist(show3);
        em.persist(show4);
        em.persist(show5);

        em.persist(festival1);
        em.persist(festival2);

        em.getTransaction().commit();
    }

}

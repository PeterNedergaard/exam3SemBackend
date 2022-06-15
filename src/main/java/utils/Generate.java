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



        Guest guest1 = new Guest("Guest1","Phone1","Email1","Status1");
        Guest guest2 = new Guest("Guest2","Phone2","Email2","Status2");
        Guest guest3 = new Guest("Guest3","Phone3","Email3","Status3");

        ShowEntity show1 = new ShowEntity("Show1",160,"Location1","15-06-2022","13:15");
        ShowEntity show2 = new ShowEntity("Show2",90,"Location2","16-06-2022","10:00");
        ShowEntity show3 = new ShowEntity("Show3",30,"Location3","17-06-2022","15:30");


        Festival festival1 = new Festival("Festival1","City1","13-06-2022",7);
        Festival festival2 = new Festival("Festival2","City2","10-06-2022",7);



        guest1.addShow(show1);
        guest2.addShow(show2);
        guest3.addShow(show3);

        guest1.setFestival(festival1);
        guest2.setFestival(festival1);
        guest3.setFestival(festival2);



        em.getTransaction().begin();

        em.persist(userRole);
        em.persist(adminRole);
        em.persist(user);
        em.persist(admin);
        em.persist(both);

        em.persist(guest1);
        em.persist(guest2);
        em.persist(guest3);

        em.persist(show1);
        em.persist(show2);
        em.persist(show3);

        em.persist(festival1);
        em.persist(festival2);

        em.getTransaction().commit();
    }

}

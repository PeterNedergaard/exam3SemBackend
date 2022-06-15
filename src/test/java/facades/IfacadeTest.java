package facades;

import entities.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import static org.junit.jupiter.api.Assertions.*;

class IfacadeTest {

    Facade facade = Facade.getFacade(EMF_Creator.createEntityManagerFactoryForTest());
    static EntityManagerFactory emf;
    static EntityManager em;

    @BeforeEach
    void setUp() {

        emf = EMF_Creator.createEntityManagerFactoryForTest();
        em = emf.createEntityManager();


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
        guest1.addShow(show2);
        guest2.addShow(show2);
        guest3.addShow(show3);

        guest1.setFestival(festival1);
        guest2.setFestival(festival1);
        guest3.setFestival(festival2);


        try {
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
        }finally {
            em.close();
        }

    }

    @AfterEach
    void tearDown() {
        emf.close();
    }






    @Test
    void getAllShows() {
        System.out.println("Test for getting all shows");

        int expected = 3;
        int actual = facade.getAllShows().size();

        assertEquals(expected,actual);
    }

    @Test
    void getAllGuests() {
        System.out.println("Test for getting all guests");

        int expected = 3;
        int actual = facade.getAllGuests().size();

        assertEquals(expected,actual);
    }

    @Test
    void getGuestByEmail() {
        System.out.println("Test for getting specific guest by email");

        String expected = "Guest1";
        String actual = facade.getGuestByEmail("Email1").getName();

        assertEquals(expected,actual);
    }

    @Test
    void getShowsByGuest() {
        System.out.println("Test for getting a specific users shows");

        int expected = 2;
        int actual = facade.getShowsByGuest(facade.getGuestByEmail("Email1")).size();
    }

    @Test
    void addGuestToShow() {
    }

    @Test
    void createFestival() {
    }

    @Test
    void createShow() {
    }

    @Test
    void createGuest() {
    }

    @Test
    void updateFestival() {
    }

    @Test
    void updateGuest() {
    }

    @Test
    void updateShow() {
    }
}
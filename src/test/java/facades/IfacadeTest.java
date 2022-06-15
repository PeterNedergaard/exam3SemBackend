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


    private static Guest guest1, guest2, guest3;
    private static ShowEntity show1, show2, show3;
    private static Festival festival1, festival2;


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



        guest1 = new Guest("Guest1","Phone1","Email1","Status1");
        guest2 = new Guest("Guest2","Phone2","Email2","Status2");
        guest3 = new Guest("Guest3","Phone3","Email3","Status3");

        show1 = new ShowEntity("Show1",160,"Location1","15-06-2022","13:15");
        show2 = new ShowEntity("Show2",90,"Location2","16-06-2022","10:00");
        show3 = new ShowEntity("Show3",30,"Location3","17-06-2022","15:30");


        festival1 = new Festival("Festival1","City1","13-06-2022",7);
        festival2 = new Festival("Festival2","City2","10-06-2022",7);



        guest1.addShow(show1);
        guest1.addShow(show2);
        guest2.addShow(show2);
        guest3.addShow(show3);

        guest1.setFestival(festival1);
        guest2.setFestival(festival1);
        guest3.setFestival(festival2);


        try {
            em.getTransaction().begin();

            em.createQuery("delete from Guest").executeUpdate();
            em.createQuery("delete from ShowEntity").executeUpdate();
            em.createQuery("delete from Festival").executeUpdate();
            em.createQuery("delete from Role").executeUpdate();
            em.createQuery("delete from User").executeUpdate();

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

        Guest expected = guest1;
        Guest actual = facade.getGuestByEmail("Email1");

        assertEquals(expected,actual);
    }

    @Test
    void getShowsByGuest() {
        System.out.println("Test for getting a specific users shows");

        int expected = 2;
        int actual = facade.getShowsByGuest(facade.getGuestByEmail("Email1")).size();

        assertEquals(expected,actual);
    }

    @Test
    void addGuestToShow() {
        System.out.println("Test for adding a guest to a show");

        Guest expected = guest1;
        Guest actual = facade.addGuestToShow(guest1,show3);

        assertEquals(expected,actual);
    }

    @Test
    void createFestival() {
        System.out.println("Test for creating a festival");

        Festival expected = new Festival("NewName","NewCity","NewStartDate",1);
        Festival actual = facade.createFestival(expected);

        assertEquals(expected,actual);
    }

    @Test
    void createShow() {
        System.out.println("Test for creating a show");

        ShowEntity expected = new ShowEntity("NewName",1,"NewLocation","NewStartDate","NewStartTime");
        ShowEntity actual = facade.createShow(expected);

        assertEquals(expected,actual);
    }

    @Test
    void createGuest() {
        System.out.println("Test for creating a guest");

        Guest expected = new Guest("NewName","NewPhone","NewEmail","NewStatus");
        Guest actual = facade.createGuest(expected);

        assertEquals(expected,actual);
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
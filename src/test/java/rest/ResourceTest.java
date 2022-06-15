package rest;

import dtos.FestivalDTO;
import dtos.GuestDTO;
import dtos.ShowDTO;
import entities.*;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.parsing.Parser;
import java.net.URI;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
//Uncomment the line below, to temporarily disable this test
//@Disabled

public class ResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;
    private static EntityManager em;


    private static Guest guest1, guest2, guest3;
    private static GuestDTO guest1DTO, guest2DTO, guest3DTO;

    private static ShowEntity show1, show2, show3;
    private static ShowDTO show1DTO, show2DTO, show3DTO;

    private static Festival festival1, festival2;
    private static FestivalDTO festival1DTO, festival2DTO;


    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {

        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }

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


        guest1DTO = new GuestDTO(guest1);
        guest2DTO = new GuestDTO(guest2);
        guest3DTO = new GuestDTO(guest3);

        show1DTO = new ShowDTO(show1);
        show2DTO = new ShowDTO(show2);
        show3DTO = new ShowDTO(show3);

        festival1DTO = new FestivalDTO(festival1);
        festival2DTO = new FestivalDTO(festival2);

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
    public void testServerIsUp() {
        given().when().get("/info").then().statusCode(200);
    }


    @Test
    public void getAllShows() {
        System.out.println("Testing to get all shows");

        List<ShowDTO> actualShowList = given()
                .contentType("application/json")
                .when()
                .get("/info/allshows")
                .then()
                .extract().body().jsonPath().getList("",ShowDTO.class);

        assertThat(actualShowList, containsInAnyOrder(show1DTO,show2DTO,show3DTO));
    }

    @Test
    public void getShowsByGuest() {
        System.out.println("Testing to get a specific guests shows");

        List<ShowDTO> actualShowList = given()
                .contentType("application/json")
                .when()
                .get("/info/showsbyguest/" + guest1.getEmail())
                .then()
                .extract().body().jsonPath().getList("",ShowDTO.class);

        assertThat(actualShowList, containsInAnyOrder(show1DTO,show2DTO));
    }


}

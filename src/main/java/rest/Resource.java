package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dtos.FestivalDTO;
import dtos.GuestDTO;
import dtos.ShowDTO;
import entities.Festival;
import entities.Guest;
import entities.ShowEntity;
import entities.User;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import errorhandling.API_Exception;
import facades.Facade;
import utils.EMF_Creator;

/**
 * @author lam@cphbusiness.dk
 */
@Path("info")
public class Resource {
    
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final EntityManager em = EMF.createEntityManager();
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    Facade facade = Facade.getFacade(EMF_Creator.createEntityManagerFactory());

    @Context
    private UriInfo context;

    @Context
    SecurityContext securityContext;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getInfoForAll() {
        return "{\"msg\":\"Hello anonymous\"}";
    }

    //Just to verify if the database is setup
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("all")
    public String allUsers() {

        EntityManager em = EMF.createEntityManager();
        try {
            TypedQuery<User> query = em.createQuery ("select u from User u",entities.User.class);
            List<User> users = query.getResultList();
            return "[" + users.size() + "]";
        } finally {
            em.close();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("user")
    @RolesAllowed("user")
    public String getFromUser() {
        String thisuser = securityContext.getUserPrincipal().getName();
        return "{\"msg\": \"Hello to User: " + thisuser + "\"}";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("admin")
    @RolesAllowed("admin")
    public String getFromAdmin() {
        String thisuser = securityContext.getUserPrincipal().getName();
        return "{\"msg\": \"Hello to (admin) User: " + thisuser + "\"}";
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("allshows")
    public Response getAllShows() {

        List<ShowDTO> showDTOList = ShowDTO.getShowDTOs(facade.getAllShows());

        return Response
                .ok()
                .entity(gson.toJson(showDTOList))
                .build();
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("allfestivals")
    public Response getAllFestivals() {

        List<FestivalDTO> festivalDTOList = FestivalDTO.getFestivalDTOs(facade.getAllFestivals());

        return Response
                .ok()
                .entity(gson.toJson(festivalDTOList))
                .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("allguests")
    public Response getAllGuests() {

        List<GuestDTO> guestDTOList = GuestDTO.getGuestDTOs(facade.getAllGuests());

        return Response
                .ok()
                .entity(gson.toJson(guestDTOList))
                .build();
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("showsbyguest/{id}")
    public Response getShowsByGuest(@PathParam("id") Long id) {

//        List<ShowDTO> showDTOList = ShowDTO.getShowDTOs(facade.getShowsByGuest(facade.getGuestByEmail(email)));
        Guest guest = em.find(Guest.class, id);

        List<ShowDTO> showDTOList = ShowDTO.getShowDTOs(facade.getShowsByGuest(guest));

        return Response
                .ok()
                .entity(gson.toJson(showDTOList))
                .build();
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("addguesttoshow")
    public Response addGuestToShow(String jsonString) throws API_Exception {


        Guest guest;
        ShowEntity show;

        Long guestId;
        Long showId;

        try{
            JsonObject json = JsonParser.parseString(jsonString).getAsJsonObject();

            guestId = json.get("guestId").getAsLong();
            showId = json.get("showId").getAsLong();

        } catch (Exception e) {
            throw new API_Exception("Malformed JSON Suplied", 400, e);
        }

        guest = em.find(Guest.class,guestId);
        show = em.find(ShowEntity.class,showId);

        GuestDTO guestDTO = new GuestDTO(facade.addGuestToShow(guest,show));

        return Response
                .ok()
                .entity(guestDTO)
                .build();
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("createfestival")
    public Response createFestival(String jsonString) throws API_Exception {

        Festival festival;
        String name;
        String city;
        String startDate;
        int duration;

        try{
            JsonObject json = JsonParser.parseString(jsonString).getAsJsonObject();
            name = json.get("name").getAsString();
            city = json.get("city").getAsString();
            startDate = json.get("startDate").getAsString();
            duration = json.get("duration").getAsInt();

        } catch (Exception e) {
            throw new API_Exception("Malformed JSON Suplied", 400, e);
        }

        festival = new Festival(name,city,startDate,duration);

        return Response
                .ok()
                .entity(new FestivalDTO(facade.createFestival(festival)))
                .build();
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("createshow")
    public Response createShow(String jsonString) throws API_Exception {

        ShowEntity show;
        String name;
        int duration;
        String location;
        String startDate;
        String startTime;

        try{
            JsonObject json = JsonParser.parseString(jsonString).getAsJsonObject();
            name = json.get("name").getAsString();
            duration = json.get("duration").getAsInt();
            location = json.get("location").getAsString();
            startDate = json.get("startDate").getAsString();
            startTime = json.get("startTime").getAsString();

        } catch (Exception e) {
            throw new API_Exception("Malformed JSON Suplied", 400, e);
        }

        show = new ShowEntity(name,duration,location,startDate,startTime);

        return Response
                .ok()
                .entity(new ShowDTO(facade.createShow(show)))
                .build();
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("createguest")
    public Response createGuest(String jsonString) throws API_Exception {

        Guest guest;
        String name;
        String phone;
        String email;
        String status;

        try{
            JsonObject json = JsonParser.parseString(jsonString).getAsJsonObject();
            name = json.get("name").getAsString();
            phone = json.get("phone").getAsString();
            email = json.get("email").getAsString();
            status = json.get("status").getAsString();;

        } catch (Exception e) {
            throw new API_Exception("Malformed JSON Suplied", 400, e);
        }

        guest = new Guest(name,phone,email,status);

        return Response
                .ok()
                .entity(new GuestDTO(facade.createGuest(guest)))
                .build();
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("updatefestival")
    public Response updateFestival(String jsonString) throws API_Exception {

        Festival festivalToUpdate;
        Long festivalId;

        Festival updatedFestival;
        String updatedName;
        String updatedCity;
        String updatedStartDate;
        int updatedDuration;

        try{
            JsonObject json = JsonParser.parseString(jsonString).getAsJsonObject();
            festivalId = json.get("festivalId").getAsLong();
            updatedName = json.get("updatedName").getAsString();
            updatedCity = json.get("updatedCity").getAsString();
            updatedStartDate = json.get("updatedStartDate").getAsString();
            updatedDuration = json.get("updatedDuration").getAsInt();

        } catch (Exception e) {
            throw new API_Exception("Malformed JSON Suplied", 400, e);
        }

        festivalToUpdate = em.find(Festival.class,festivalId);
        updatedFestival = new Festival(updatedName,updatedCity,updatedStartDate,updatedDuration);

        return Response
                .ok()
                .entity(new FestivalDTO(facade.updateFestival(festivalToUpdate,updatedFestival)))
                .build();
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("updateguest")
    public Response updateGuest(String jsonString) throws API_Exception {

        Guest guestToUpdate;
        Long guestId;

        Guest updatedGuest;
        String updatedName;
        String updatedPhone;
        String updatedEmail;
        String updatedStatus;

        try{
            JsonObject json = JsonParser.parseString(jsonString).getAsJsonObject();
            guestId = json.get("guestId").getAsLong();
            updatedName = json.get("updatedName").getAsString();
            updatedPhone = json.get("updatedPhone").getAsString();
            updatedEmail = json.get("updatedEmail").getAsString();
            updatedStatus = json.get("updatedStatus").getAsString();

        } catch (Exception e) {
            throw new API_Exception("Malformed JSON Suplied", 400, e);
        }

        guestToUpdate = em.find(Guest.class,guestId);
        updatedGuest = new Guest(updatedName,updatedPhone,updatedEmail,updatedStatus);

        return Response
                .ok()
                .entity(new GuestDTO(facade.updateGuest(guestToUpdate,updatedGuest)))
                .build();
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("updateshow")
    public Response updateShow(String jsonString) throws API_Exception {

        ShowEntity showToUpdate;
        Long showId;

        ShowEntity updatedShow;
        String updatedName;
        int updatedDuration;
        String updatedLocation;
        String updatedStartDate;
        String updatedStartTime;

        try{
            JsonObject json = JsonParser.parseString(jsonString).getAsJsonObject();
            showId = json.get("showId").getAsLong();
            updatedName = json.get("updatedName").getAsString();
            updatedDuration = json.get("updatedDuration").getAsInt();
            updatedLocation = json.get("updatedLocation").getAsString();
            updatedStartDate = json.get("updatedStartDate").getAsString();
            updatedStartTime = json.get("updatedStartTime").getAsString();

        } catch (Exception e) {
            throw new API_Exception("Malformed JSON Suplied", 400, e);
        }

        showToUpdate = em.find(ShowEntity.class,showId);
        updatedShow = new ShowEntity(updatedName,updatedDuration,updatedLocation,updatedStartDate,updatedStartTime);

        return Response
                .ok()
                .entity(new ShowDTO(facade.updateShow(showToUpdate,updatedShow)))
                .build();
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("deleteshow")
    public void deleteShow(String jsonString) throws API_Exception {

        ShowEntity show;
        Long showId;

        try{
            JsonObject json = JsonParser.parseString(jsonString).getAsJsonObject();

            showId = json.get("showId").getAsLong();

        } catch (Exception e) {
            throw new API_Exception("Malformed JSON Suplied", 400, e);
        }

        show = em.find(ShowEntity.class,showId);

        facade.deleteShow(show);
    }
}
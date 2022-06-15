package facades;

import entities.Festival;
import entities.Guest;
import entities.ShowEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;

public class Facade implements Ifacade{

    private static EntityManagerFactory emf;
    private static EntityManager em;
    private static Facade instance;

    public Facade() {
    }

    public static Facade getFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            em = emf.createEntityManager();
            instance = new Facade();
        }
        return instance;
    }





    @Override
    public List<ShowEntity> getAllShows() {
        return em.createQuery("SELECT s FROM ShowEntity s",ShowEntity.class).getResultList();
    }

    @Override
    public List<ShowEntity> getShowsByGuest(Guest guest) {
        return guest.getShowList();
    }

    @Override
    public Guest addGuestToShow(Guest guest, ShowEntity show) {
        guest.addShow(show);

        em.getTransaction().begin();
        em.merge(guest);
        em.merge(show);
        em.getTransaction().commit();

        if(show.getGuestList().contains(guest)){
            return guest;
        }

        return null;
    }

    @Override
    public Festival createFestival(Festival festival) {

        em.getTransaction().begin();
        em.persist(festival);
        em.getTransaction().commit();

        return em.find(Festival.class,festival.getId());
    }

    @Override
    public ShowEntity createShow(ShowEntity show) {

        em.getTransaction().begin();
        em.persist(show);
        em.getTransaction().commit();

        return em.find(ShowEntity.class,show.getId());
    }

    @Override
    public Guest createGuest(Guest guest) {

        em.getTransaction().begin();
        em.persist(guest);
        em.getTransaction().commit();

        return em.find(Guest.class,guest.getId());
    }

    @Override
    public Festival updateFestival(Festival festivalToUpdate, Festival updatedFestival) {

        festivalToUpdate.setName(updatedFestival.getName());
        festivalToUpdate.setCity(updatedFestival.getCity());
        festivalToUpdate.setStartDate(updatedFestival.getStartDate());
        festivalToUpdate.setDuration(updatedFestival.getDuration());

        em.getTransaction().begin();
        em.merge(festivalToUpdate);
        em.getTransaction().commit();

        return em.find(Festival.class,getFestivalByName(updatedFestival.getName()).getId());
    }

    @Override
    public Guest updateGuest(Guest guestToUpdate, Guest updatedGuest) {

        guestToUpdate.setName(updatedGuest.getName());
        guestToUpdate.setPhone(updatedGuest.getPhone());
        guestToUpdate.setEmail(updatedGuest.getEmail());
        guestToUpdate.setStatus(updatedGuest.getStatus());

        em.getTransaction().begin();
        em.merge(guestToUpdate);
        em.getTransaction().commit();

        return em.find(Guest.class,getGuestByEmail(updatedGuest.getEmail()).getId());
    }

    @Override
    public ShowEntity updateShow(ShowEntity showToUpdate, ShowEntity updatedShow) {

        showToUpdate.setName(updatedShow.getName());
        showToUpdate.setDuration(updatedShow.getDuration());
        showToUpdate.setLocation(updatedShow.getLocation());
        showToUpdate.setStartDate(updatedShow.getStartDate());
        showToUpdate.setStartTime(updatedShow.getStartTime());

        em.getTransaction().begin();
        em.merge(showToUpdate);
        em.getTransaction().commit();

        return showToUpdate;
    }



    @Override
    public List<Guest> getAllGuests() {
        return em.createQuery("SELECT g FROM Guest g",Guest.class).getResultList();
    }

    @Override
    public Guest getGuestByEmail(String email) {

        for (Guest g : getAllGuests()) {
            if (g.getEmail().equals(email)){
                return g;
            }
        }

        return null;
    }

    @Override
    public ShowEntity getShowByName(String showName) {

        for (ShowEntity s : getAllShows()) {
            if (s.getName().equals(showName)){
                return s;
            }
        }

        return null;
    }

    @Override
    public List<Festival> getAllFestivals() {
        return em.createQuery("SELECT f FROM Festival f",Festival.class).getResultList();
    }

    @Override
    public Festival getFestivalByName(String festivalName) {

        for (Festival f : getAllFestivals()) {
            if (f.getName().equals(festivalName)){
                return f;
            }
        }

        return null;
    }

}

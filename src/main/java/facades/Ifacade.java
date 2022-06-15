package facades;

import entities.Festival;
import entities.Guest;
import entities.ShowEntity;

import java.util.List;

public interface Ifacade {

    List<ShowEntity> getAllShows();

    List<ShowEntity> getShowsByGuest(Guest guest);

    Guest addGuestToShow(Guest guest, ShowEntity show);

    Festival createFestival(Festival festival);

    ShowEntity createShow(ShowEntity show);

    Guest createGuest(Guest guest);

    Festival updateFestival(Festival festivalToUpdate, Festival updatedFestival);

    Guest updateGuest(Guest guestToUpdate, Guest updatedGuest);

    ShowEntity updateShow(ShowEntity showToUpdate, ShowEntity updatedShow);

    Guest getGuestByEmail(String email);

    List<Guest> getAllGuests();

}

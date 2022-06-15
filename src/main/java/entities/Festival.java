package entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
public class Festival {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;
    private String city;
    private String startDate;
    private int duration;

    @OneToMany(mappedBy = "festival")
    private List<Guest> guestList = new ArrayList<>();


    public Festival(String name, String city, String startDate, int duration) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.startDate = startDate;
        this.duration = duration;
    }





    public Festival() {
    }

    public void addGuest(Guest guest){
        this.guestList.add(guest);
        if(!guest.getFestival().equals(this)){
            guest.setFestival(this);
        }
    }

    public List<Guest> getGuestList() {
        return guestList;
    }

    public void setGuestList(List<Guest> guestList) {
        this.guestList = guestList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Festival festival = (Festival) o;
        return duration == festival.duration && Objects.equals(name, festival.name) && Objects.equals(city, festival.city) && Objects.equals(startDate, festival.startDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, city, startDate, duration);
    }
}

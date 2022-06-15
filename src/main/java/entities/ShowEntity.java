package entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class ShowEntity { //For some reason, I get errors when the entity is simply called 'Show'. It might be a reserved keyword
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;
    private int duration;
    private String location;
    private String startDate;
    private String startTime;

    @ManyToMany(mappedBy = "showList")
    private List<Guest> guestList = new ArrayList<>();


    public ShowEntity(String name, int duration, String location, String startDate, String startTime) {
        this.name = name;
        this.duration = duration;
        this.location = location;
        this.startDate = startDate;
        this.startTime = startTime;
    }





    public ShowEntity() {
    }

    public void addGuest(Guest guest){
        this.guestList.add(guest);
        if(!guest.getShowList().contains(this)){
            guest.getShowList().add(this);
        }
    }

    public List<Guest> getGuestList() {
        return guestList;
    }

    public void setGuestList(List<Guest> guestList) {
        this.guestList = guestList;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        ShowEntity that = (ShowEntity) o;
        return duration == that.duration && Objects.equals(name, that.name) && Objects.equals(location, that.location) && Objects.equals(startDate, that.startDate) && Objects.equals(startTime, that.startTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, duration, location, startDate, startTime);
    }
}

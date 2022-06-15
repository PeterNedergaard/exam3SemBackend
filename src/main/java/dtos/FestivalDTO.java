package dtos;

import entities.Festival;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class FestivalDTO {

    private Long id;
    private String name;
    private String city;
    private String startDate;
    private int duration;


    public FestivalDTO(String name, String city, String startDate, int duration) {
        this.name = name;
        this.city = city;
        this.startDate = startDate;
        this.duration = duration;
    }

    public FestivalDTO(Festival festival) {
        this.id = festival.getId();
        this.name = festival.getName();
        this.city = festival.getCity();
        this.startDate = festival.getStartDate();
        this.duration = festival.getDuration();
    }

    public static List<FestivalDTO> getFestivalDTOs(List<Festival> festivalList) {
        List<FestivalDTO> festivalDTOList = new ArrayList<>();

        for (Festival f : festivalList) {
            festivalDTOList.add(new FestivalDTO(f));
        }

        return festivalDTOList;
    }




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FestivalDTO that = (FestivalDTO) o;
        return duration == that.duration && Objects.equals(name, that.name) && Objects.equals(city, that.city) && Objects.equals(startDate, that.startDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, city, startDate, duration);
    }
}

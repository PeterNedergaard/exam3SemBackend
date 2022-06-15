package dtos;

import entities.ShowEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ShowDTO {

    private Long id;
    private String name;
    private int duration;
    private String location;
    private String startDate;
    private String startTime;


    public ShowDTO(Long id, String name, int duration, String location, String startDate, String startTime) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.location = location;
        this.startDate = startDate;
        this.startTime = startTime;
    }


    public ShowDTO(ShowEntity show) {
        this.id = show.getId();
        this.name = show.getName();
        this.duration = show.getDuration();
        this.location = show.getLocation();
        this.startDate = show.getStartDate();
        this.startTime = show.getStartTime();
    }


    public List<ShowDTO> getShowDTOs(List<ShowEntity> showList) {
        List<ShowDTO> showDTOList = new ArrayList<>();

        for (ShowEntity s : showList) {
            showDTOList.add(new ShowDTO(s));
        }

        return showDTOList;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShowDTO showDTO = (ShowDTO) o;
        return duration == showDTO.duration && Objects.equals(name, showDTO.name) && Objects.equals(location, showDTO.location) && Objects.equals(startDate, showDTO.startDate) && Objects.equals(startTime, showDTO.startTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, duration, location, startDate, startTime);
    }
}

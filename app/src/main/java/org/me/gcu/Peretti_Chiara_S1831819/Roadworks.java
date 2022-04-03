package org.me.gcu.Peretti_Chiara_S1831819;

import java.time.LocalDate;

public class Roadworks {

    private String title;
    private String description;
    private String georss;
    private LocalDate startDate;

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    private LocalDate endDate;


    public Roadworks(String title) {
        this.title = title;
    }

    public Roadworks() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGeorss() {
        return georss;
    }

    public void setGeorss(String georss) {
        this.georss = georss;
    }

    @Override
    public String toString() {
        return "Roadworks " +
                "title='" + title;
    }
}

package org.me.gcu.Peretti_Chiara_S1831819;

public class Roadworks {

    private String title;
    private String description;
    private String georss;

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
        return "Roadworks{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", georss='" + georss + '\'' +
                '}';
    }
}

package ru.ulstu.is.sbapp.news.model;

import jakarta.persistence.*;
import ru.ulstu.is.sbapp.FavouriteTiding.FavouriteTiding;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Tiding {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    String Heading;

    String Content;



    @OneToMany(mappedBy = "tiding",fetch = FetchType.EAGER)
    private List<FavouriteTiding> favourites;

    public Tiding(){}

    public Tiding(String Heading, String Content)
    {
        this.Heading = Heading;
        this.Content = Content;
    }
    public Long getId()
    {
        return id;
    }
    public String getHeading()
    {
        return Heading;
    }
    public String getContent()
    {
        return Content;
    }
    public void setHeading(String Heading){
        this.Heading =Heading;
    }
    public void setContent(String Content)
    {
        this.Content = Content;
    }
    public List<FavouriteTiding> getFavourites() {
        if (favourites == null) {
            favourites = new ArrayList<>();
        }
        return favourites;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tiding tiding = (Tiding) o;
        return Objects.equals(id, tiding.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Technique{" +
                "id=" + id +
                ", Heading='" + Heading + '\'' +
                ", Content ='" + Content + '\'' +
                '}';
    }
}

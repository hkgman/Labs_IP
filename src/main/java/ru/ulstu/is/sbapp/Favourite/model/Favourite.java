package ru.ulstu.is.sbapp.Favourite.model;

import jakarta.persistence.*;
import ru.ulstu.is.sbapp.FavouriteTiding.FavouriteTiding;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
public class Favourite {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    Date DateOfAdd;

    @OneToMany(mappedBy = "favourite",fetch = FetchType.EAGER)
    private List<FavouriteTiding> tidings;


    public Favourite()
    {

    }

    public Favourite(Date DateOfAdd)
    {
        this.DateOfAdd=DateOfAdd;
    }
    public List<FavouriteTiding> getTidings() {
        if (tidings == null) {
            tidings = new ArrayList<>();
        }
        return tidings;
    }
    public Long getId()
    {
        return id;
    }
    public Date getDateOfAdd()
    {
        return  DateOfAdd;
    }
    public void setDateOfAdd(Date DateOfAdd)
    {
        this.DateOfAdd=DateOfAdd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Favourite favourite = (Favourite) o;
        return Objects.equals(id, favourite.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Favourite{" +
                "id=" + id +
                ", DateOfAdd='" + DateOfAdd + '\'' +
                '}';
    }

}

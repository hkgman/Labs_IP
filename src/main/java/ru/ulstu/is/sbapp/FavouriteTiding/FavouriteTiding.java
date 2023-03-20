package ru.ulstu.is.sbapp.FavouriteTiding;

import jakarta.persistence.*;
import ru.ulstu.is.sbapp.Favourite.model.Favourite;
import ru.ulstu.is.sbapp.news.model.Tiding;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name="purchase_technique")
@IdClass(FavouriteTidingId.class)
public class FavouriteTiding implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn(name="favourite_id",referencedColumnName = "id")
    private Favourite favourite;
    @Id
    @ManyToOne
    @JoinColumn(name = "tiding_id",referencedColumnName = "id")
    private Tiding tiding;


    public FavouriteTiding() {
    }

    public FavouriteTiding(Favourite favourite, Tiding tiding) {
        this.favourite = favourite;
        this.tiding = tiding;
    }

    // Properties

    public void setFavourite(Favourite favourite) {
        this.favourite = favourite;
    }

    public Favourite getFavourite() {
        return favourite;
    }

    public void setTiding(Tiding tiding) {
        this.tiding = tiding;
    }

    public Tiding getTiding() {
        return tiding;
    }

    // ![Properties]

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FavouriteTiding favouriteTiding = (FavouriteTiding) o;
        return Objects.equals(favourite.getId(), favouriteTiding.favourite.getId()) &&
                Objects.equals(tiding.getId(), favouriteTiding.tiding.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(favourite.getId(), tiding.getId());
    }

    @Override
    public String toString() {
        return "FavouriteTiding{" +
                "favouriteId=" + favourite.getId() +
                ", tidingId=" + tiding.getId() +
                '}';
    }
}

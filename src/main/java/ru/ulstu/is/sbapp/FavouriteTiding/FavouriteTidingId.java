package ru.ulstu.is.sbapp.FavouriteTiding;

import java.io.Serializable;
import java.util.Objects;

public class FavouriteTidingId implements Serializable {
    private Long favourite;
    private Long tiding;

    public FavouriteTidingId()
    {

    }
    public FavouriteTidingId(Long favouriteId, Long tidingId)
    {
        this.favourite=favouriteId;
        this.tiding=tidingId;
    }
    public void setFavourite(Long favourite) {
        this.favourite = favourite;
    }

    public Long getFavourite() {
        return favourite;
    }

    public void setTiding(Long tiding) {
        this.tiding = tiding;
    }

    public Long getTiding() {
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
        FavouriteTidingId favouriteTidingId = (FavouriteTidingId) o;
        return Objects.equals(favourite, favouriteTidingId.favourite) &&
                Objects.equals(tiding, favouriteTidingId.tiding);
    }

    @Override
    public int hashCode() {
        return Objects.hash(favourite, tiding);
    }

    @Override
    public String toString() {
        return "FavouriteTidingId{" +
                "favourite=" + favourite +
                ", tiding=" + tiding +
                '}';
    }
}

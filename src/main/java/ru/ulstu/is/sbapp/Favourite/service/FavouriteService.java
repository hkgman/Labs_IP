package ru.ulstu.is.sbapp.Favourite.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.ulstu.is.sbapp.FavouriteTiding.FavouriteTiding;
import ru.ulstu.is.sbapp.FavouriteTiding.FavouriteTidingId;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import ru.ulstu.is.sbapp.Favourite.model.Favourite;
import ru.ulstu.is.sbapp.client.model.Client;
import ru.ulstu.is.sbapp.news.model.Tiding;

import java.util.Date;
import java.util.List;

@Service
public class FavouriteService {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Favourite addFavourite(Date DateOfAdd) {
        if(DateOfAdd==null)
        {
            throw new IllegalArgumentException("Date is null or empty");
        }
        final Favourite favourite = new Favourite(DateOfAdd);
        em.persist(favourite);
        return favourite;
    }

    @Transactional(readOnly = true)
    public Favourite findFavourite(Long id) {
        final Favourite favourite = em.find(Favourite.class, id);
        if (favourite == null) {
            throw new EntityNotFoundException(String.format("Favourite with id [%s] is not found", id));
        }
        return favourite;
    }

    @Transactional(readOnly = true)
    public List<Favourite> findAllFavourites() {
        return em.createQuery("select f from Favourite f", Favourite.class)
                .getResultList();
    }
    @Transactional
    public Favourite updateFavourite(Long id, Date DateOfAdd) {
        if(DateOfAdd==null)
        {
            throw new IllegalArgumentException("Date is null or empty");
        }
        final Favourite currentFavourite = findFavourite(id);
        currentFavourite.setDateOfAdd(DateOfAdd);
        return em.merge(currentFavourite);
    }
    @Transactional
    public Favourite deleteFavourite(Long id) {
        final Favourite currentFavourite = findFavourite(id);
        em.createQuery("update Client set favourite = null where favourite.id = " + id).executeUpdate();
        em.createQuery("delete from FavouriteTiding ft where ft.favourite.id = " + id).executeUpdate();
        em.remove(currentFavourite);
        return currentFavourite;
    }

    @Transactional
    public void deleteAllFavourites() {
        em.createQuery("update Client set favourite = null").executeUpdate();
        em.createQuery("delete from FavouriteTiding").executeUpdate();
        em.createQuery("delete from Favourite").executeUpdate();
    }
    @Transactional
    public void addTechniqueInPurchase(Long id, Tiding tiding) {
        final Favourite favourite = findFavourite(id);
        FavouriteTiding favouriteTiding = em.find(FavouriteTiding.class, new FavouriteTidingId(favourite.getId(), tiding.getId()));
        if (favouriteTiding == null) {
            favouriteTiding = new FavouriteTiding(favourite, tiding);
        }
        em.merge(favouriteTiding);
    }

    @Transactional
    public void removeTechniqueInPurchase(Long id, Tiding tiding, int count) {
        final Favourite favourite = findFavourite(id);
        FavouriteTiding favouriteTiding = em.find(FavouriteTiding.class, new FavouriteTidingId(favourite.getId(), tiding.getId()));
        if (favouriteTiding == null) {
            return;
        }
        em.remove(favouriteTiding);
    }
}
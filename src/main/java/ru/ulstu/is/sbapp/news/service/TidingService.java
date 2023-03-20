package ru.ulstu.is.sbapp.news.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.ulstu.is.sbapp.news.model.Tiding;

import java.util.List;

@Service
public class TidingService {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Tiding addTiding(String Heading, String Content) {
        if (!StringUtils.hasText(Heading) || !StringUtils.hasText(Content) ) {
            throw new IllegalArgumentException("Tiding info is null or empty");
        }
        final Tiding tiding = new Tiding(Heading,Content);
        em.persist(tiding);
        return tiding;
    }

    @Transactional(readOnly = true)
    public Tiding findTiding(Long id) {
        final Tiding tiding = em.find(Tiding.class, id);
        if (tiding == null) {
            throw new EntityNotFoundException(String.format("Tiding with id [%s] is not found", id));
        }
        return tiding;
    }

    @Transactional(readOnly = true)
    public List<Tiding> findAllTidings() {
        return em.createQuery("select t from Tiding T", Tiding.class)
                .getResultList();
    }

    @Transactional
    public Tiding updateTiding(Long id, String Heading, String Content) {
        if (!StringUtils.hasText(Heading) || !StringUtils.hasText(Content) ) {
            throw new IllegalArgumentException("Tiding info is null or empty");
        }
        final Tiding currentTiding = findTiding(id);
        currentTiding.setHeading(Heading);
        currentTiding.setContent(Content);
        return em.merge(currentTiding);
    }

    @Transactional
    public Tiding deleteTiding(Long id) {
        final Tiding currentTiding = findTiding(id);
        em.createQuery("delete from FavouriteTiding ft where ft.tiding.id = " + id).executeUpdate();
        em.remove(currentTiding);
        return currentTiding;
    }

    @Transactional
    public void deleteAllTidings() {
        em.createQuery("delete from FavouriteTiding");
        em.createQuery("delete from Tiding").executeUpdate();
    }
}

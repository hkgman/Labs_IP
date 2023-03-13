package ru.ulstu.is.sbapp.technique.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.ulstu.is.sbapp.client.model.Client;
import ru.ulstu.is.sbapp.technique.model.Technique;

import java.util.List;

@Service
public class TechniqueService {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Technique addTechnique(String Type, double TechPrice) {
        if (!StringUtils.hasText(Type)) {
            throw new IllegalArgumentException("Technique type is null or empty");
        }
        if(TechPrice == 0)
        {
            throw new IllegalArgumentException("Technique TechPrice is null or empty");
        }
        final Technique technique = new Technique(Type, TechPrice);
        em.persist(technique);
        return technique;
    }

    @Transactional(readOnly = true)
    public Technique findTechnique(Long id) {
        final Technique technique = em.find(Technique.class, id);
        if (technique == null) {
            throw new EntityNotFoundException(String.format("Technique with id [%s] is not found", id));
        }
        return technique;
    }

    @Transactional(readOnly = true)
    public List<Technique> findAllTechniques() {
        return em.createQuery("select t from Technique T", Technique.class)
                .getResultList();
    }

    @Transactional
    public Technique updateTechnique(Long id, String Type, Float TechPrice) {
        if (!StringUtils.hasText(Type)){
            throw new IllegalArgumentException("Technique type is null or empty");
        }
        if(TechPrice == null)
        {
            throw new IllegalArgumentException("Technique TechPrice is null or empty");
        }
        final Technique currentTechnique = findTechnique(id);
        currentTechnique.setType(Type);
        currentTechnique.setTechPrice(TechPrice);
        return em.merge(currentTechnique);
    }

    @Transactional
    public Technique deleteTechnique(Long id) {
        final Technique currentTechnique = findTechnique(id);
        em.remove(currentTechnique);
        return currentTechnique;
    }

    @Transactional
    public void deleteAllTechniques() {
        em.createQuery("delete from Technique").executeUpdate();
    }
}

package ru.ulstu.is.sbapp.purchase.service;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.ulstu.is.sbapp.client.model.Client;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import ru.ulstu.is.sbapp.purchase.model.Purchase;

import java.util.Date;
import java.util.List;

@Service
public class PurchaseService {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Purchase addPurchase(Date DateOfPurchase, double Price) {
        if (DateOfPurchase==null) {
            throw new IllegalArgumentException("Date of purchase is null or empty");
        }
        if(Price==0){
            throw new IllegalArgumentException("Price is null or empty");
        }
        final Purchase purchase = new Purchase(DateOfPurchase, Price);
        em.persist(purchase);
        return purchase;
    }

    @Transactional(readOnly = true)
    public Purchase findPurchase(Long id) {
        final Purchase purchase = em.find(Purchase.class, id);
        if (purchase == null) {
            throw new EntityNotFoundException(String.format("Purchase with id [%s] is not found", id));
        }
        return purchase;
    }

    @Transactional(readOnly = true)
    public List<Purchase> findAllPurchases() {
        return em.createQuery("select p from Purchase p", Purchase.class)
                .getResultList();
    }

    @Transactional
    public Purchase updatePurchase(Long id, Date DateOfPurchase, Float Price) {
        if (DateOfPurchase==null) {
            throw new IllegalArgumentException("Date of purchase is null or empty");
        }
        if(Price==null){
            throw new IllegalArgumentException("Price is null or empty");
        }
        final Purchase currentPurchase = findPurchase(id);
        currentPurchase.setDateOfPurchase(DateOfPurchase);
        currentPurchase.setPrice(Price);
        return em.merge(currentPurchase);
    }

    @Transactional
    public Purchase deletePurchase(Long id) {
        final Purchase currentPurchase = findPurchase(id);
        em.remove(currentPurchase);
        return currentPurchase;
    }

    @Transactional
    public void deleteAllPurchases() {
        em.createQuery("delete from Purchase").executeUpdate();
    }
}
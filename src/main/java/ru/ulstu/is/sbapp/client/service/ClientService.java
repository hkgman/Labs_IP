package ru.ulstu.is.sbapp.client.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.ulstu.is.sbapp.Favourite.model.Favourite;
import ru.ulstu.is.sbapp.client.model.Client;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import ru.ulstu.is.sbapp.Favourite.service.FavouriteService;

import java.util.List;

@Service
public class ClientService {
    @PersistenceContext
    private EntityManager em;

    @Autowired
    FavouriteService favouriteService;

    @Transactional
    public Client addClient(String firstName, String lastName,String email) {
        if (!StringUtils.hasText(firstName) || !StringUtils.hasText(lastName) ||!StringUtils.hasText(email)) {
            throw new IllegalArgumentException("Client info is null or empty");
        }
        final Client client = new Client(firstName, lastName,email);
        em.persist(client);
        return client;
    }

    @Transactional(readOnly = true)
    public Client findClient(Long id) {
        final Client client = em.find(Client.class, id);
        if (client == null) {
            throw new EntityNotFoundException(String.format("Client with id [%s] is not found", id));
        }
        return client;
    }

    @Transactional(readOnly = true)
    public List<Client> findAllClients() {
        return em.createQuery("select c from Client c", Client.class)
                .getResultList();
    }

    @Transactional
    public Client updateClient(Long id, String firstName, String lastName,String email) {
        if (!StringUtils.hasText(firstName) || !StringUtils.hasText(lastName) ||!StringUtils.hasText(email)) {
            throw new IllegalArgumentException("Client info is null or empty");
        }
        final Client currentClient = findClient(id);
        currentClient.setFirstName(firstName);
        currentClient.setLastName(lastName);
        currentClient.setEmail(email);
        return em.merge(currentClient);
    }

    @Transactional
    public Client deleteClient(Long id) {
        final Client currentClient = findClient(id);
        em.remove(currentClient);
        return currentClient;
    }

    @Transactional
    public void deleteAllClients() {
        em.createQuery("delete from Client").executeUpdate();
    }
    @Transactional
    public Client setFavourite(Long id, Favourite favourite)
    {
        final Client client=findClient(id);
        client.setFavourite(favourite);
        em.merge(client);
        return client;
    }
}

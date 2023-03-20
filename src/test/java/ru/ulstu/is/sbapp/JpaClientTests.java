package ru.ulstu.is.sbapp;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.ulstu.is.sbapp.Favourite.model.Favourite;
import ru.ulstu.is.sbapp.Favourite.service.FavouriteService;
import ru.ulstu.is.sbapp.client.model.Client;
import ru.ulstu.is.sbapp.client.service.ClientService;
import ru.ulstu.is.sbapp.news.service.TidingService;

import java.util.Date;
import java.util.List;

@SpringBootTest
public class JpaClientTests {
    private static final Logger log = LoggerFactory.getLogger(JpaFavouriteTests.class);
    @Autowired
    private ClientService clientService;
    @Autowired
    private FavouriteService favouriteService;

    @Test
    void testClientCreate() {
        clientService.deleteAllClients();
        final Client client = clientService.addClient("Pasha","Sorokin","sorokin.zxcv@gmail.com");
        log.info("testClientCreate: " + client.toString());
        Assertions.assertNotNull(client.getId());

        clientService.deleteAllClients();
    }
    @Test
    void testReadClient()
    {
        clientService.deleteAllClients();
        final Client client = clientService.addClient("Pasha","Sorokin","sorokin.zxcv@gmail.com");
        log.info("testClientRead[0]: " + client.toString());
        final  Client curclient=clientService.findClient(client.getId());
        log.info("testClientRead[1]: " + curclient.toString());
        Assertions.assertEquals(client, curclient);
        clientService.deleteAllClients();
    }
    @Test
    void testClientReadNotFound() {
        clientService.deleteAllClients();
        Assertions.assertThrows(EntityNotFoundException.class, () -> clientService.findClient(-1L));
    }
    @Test
    void testClientReadAll() {
        clientService.deleteAllClients();
        clientService.addClient("Pupa","Lupa","sasdfdsf@gmail.com");
        clientService.addClient("Pasha","Sorokin","sorokin.zxcv@gmail.com");
        final List<Client> clients = clientService.findAllClients();
        log.info("testClientReadAll: " + clients.toString());
        Assertions.assertEquals(clients.size(), 2);

        clientService.deleteAllClients();
    }
    @Test
    void testClientReadAllEmpty() {
        clientService.deleteAllClients();
        final List<Client> clients = clientService.findAllClients();
        log.info("testClientReadAllEmpty: " + clients.toString());
        Assertions.assertEquals(clients.size(), 0);
    }
    @Test
    void testSetFavourite() {
        clientService.deleteAllClients();
        Client client = clientService.addClient("Pasha","Sorokin","sorokin.zxcv@gmail.com");
        final Date dateofadd2= new Date(121212121121L);
        final Favourite favourite = favouriteService.addFavourite(dateofadd2);
        clientService.setFavourite(client.getId(), favourite);
        client = clientService.findClient(client.getId());
        Assertions.assertEquals(favourite, client.getFavourite());

        clientService.deleteAllClients();
        favouriteService.deleteAllFavourites();
    }
}

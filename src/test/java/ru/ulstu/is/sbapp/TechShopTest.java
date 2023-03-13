package ru.ulstu.is.sbapp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.ulstu.is.sbapp.client.model.Client;
import ru.ulstu.is.sbapp.client.service.ClientService;
import ru.ulstu.is.sbapp.purchase.model.Purchase;
import ru.ulstu.is.sbapp.purchase.service.PurchaseService;
import ru.ulstu.is.sbapp.technique.service.TechniqueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class TechShopTest {
    private static final Logger log = LoggerFactory.getLogger(TechShopTest.class);
    @Autowired
    ClientService clientService;

    @Autowired
    TechniqueService techniqueService;

    @Autowired
    PurchaseService purchaseService;

    @Test
    void testClientCreate(){
        clientService.deleteAllClients();
        final Client client = clientService.addClient("Вася","Пупкин");
        log.info(client.toString());
        Assertions.assertNotNull(client.getId());
    }

    @Test
    void testPurchaseCreate()
    {
        purchaseService.deleteAllPurchases();
        clientService.deleteAllClients();
        final Client client = clientService.addClient("Вася","Пупкин");
        Date date = new Date(12121212L);
        clientService.addPurchase(client.getId(),date, 3000.0);
        log.info(client.getPurchases().toString());
    }

    @Test
    void testTecnhiques(){}

    @Test
    void testPurchases(){}
}

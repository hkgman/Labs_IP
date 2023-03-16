package ru.ulstu.is.sbapp;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.ulstu.is.sbapp.client.model.Client;
import ru.ulstu.is.sbapp.client.service.ClientService;
import ru.ulstu.is.sbapp.purchase.model.Purchase;
import ru.ulstu.is.sbapp.purchase.service.PurchaseService;
import ru.ulstu.is.sbapp.technique.model.Technique;
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
        techniqueService.deleteAllTechniques();
        final Technique tech1 = techniqueService.addTechnique("Computer",200000.0);
        final Technique tech2 = techniqueService.addTechnique("Mobile phone",10000.0);
        final Technique tech3 = techniqueService.addTechnique("Microwave",45000.0);
        ArrayList<Technique> techniques=new ArrayList<>();
        techniques.add(tech1);
        techniques.add(tech2);
        techniques.add(tech3);
        Date date = new Date(1212121212121L);
        final Purchase  purchase= purchaseService.addPurchase(date,1,techniques);
        log.info(purchase.toString());
        final Purchase purchase1 = purchaseService.findPurchase(purchase.getId());
        Assertions.assertEquals(true,purchase1.getTechnique().contains(tech1));
        Assertions.assertEquals(purchase,purchase1);
        log.info(purchase.getTechnique().toString());

        Assertions.assertEquals(purchase1.getTechnique().size(), 3);


        log.info("Техник = " + purchaseService.findPurchase(purchase.getId()).getTechnique().size());
        techniqueService.deleteAllTechniques();
        log.info("Техник после удаления = " + purchaseService.findPurchase(purchase.getId()).getTechnique().size());

    }

    /*TODO*/
    @Test
    void testPurchases(){}
}

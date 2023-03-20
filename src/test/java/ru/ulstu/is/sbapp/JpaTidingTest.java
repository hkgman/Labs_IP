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
import ru.ulstu.is.sbapp.news.model.Tiding;
import ru.ulstu.is.sbapp.news.service.TidingService;

import java.util.Date;
import java.util.List;

@SpringBootTest
public class JpaTidingTest {
    private static final Logger log = LoggerFactory.getLogger(JpaFavouriteTests.class);
    @Autowired
    private TidingService tidingService;

    @Test
    void testTidingCreate()
    {
        tidingService.deleteAllTidings();
        final Tiding tiding=tidingService.addTiding("Da","Net");
        log.info("testTidingCreate: " + tiding.toString());
        Assertions.assertNotNull(tiding.getId());
        tidingService.deleteAllTidings();
    }
    @Test
    void testTidingRead()
    {
        tidingService.deleteAllTidings();
        final Tiding tiding=tidingService.addTiding("Da","Net");
        log.info("testTidingRead[0]: " + tiding.toString());
        final Tiding findTiding=tidingService.findTiding(tiding.getId());
        log.info("testTidingRead[1]: " + findTiding.toString());
        Assertions.assertEquals(tiding, findTiding);

        tidingService.deleteAllTidings();
    }
    @Test
    void testTidingReadNotFound() {
        tidingService.deleteAllTidings();
        Assertions.assertThrows(EntityNotFoundException.class, () -> tidingService.findTiding(-1L));
    }
    @Test
    void testAllTidingRead()
    {
        tidingService.deleteAllTidings();
        tidingService.addTiding("Da","Net");
        tidingService.addTiding("Aga","yep");
        final List<Tiding> tidings = tidingService.findAllTidings();
        log.info("testAllTidingRead: " + tidings.toString());
        Assertions.assertEquals(tidings.size(), 2);

        tidingService.deleteAllTidings();
    }
    @Test
    void testTidingReadAllEmpty() {
        tidingService.deleteAllTidings();
        final List<Tiding> tidings = tidingService.findAllTidings();
        log.info("testTidingReadAllEmpty: " + tidings.toString());
        Assertions.assertEquals(tidings.size(), 0);
    }
    @Test
    void testUpdateTiding()
    {
        tidingService.deleteAllTidings();
        final Tiding tiding=tidingService.addTiding("Da","Net");
        log.info("testUpdateTiding: " + tiding.toString());
        tidingService.updateTiding(tiding.getId(),"Net","Ladno");
        final  Tiding tiding1=tidingService.findTiding(tiding.getId());
        log.info("testUpdateTiding: " + tiding1.toString());
        Assertions.assertEquals(tiding1.getHeading(), "Net");
        Assertions.assertEquals(tiding1.getContent(), "Ladno");
        tidingService.deleteAllTidings();
    }
}

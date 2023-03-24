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
public class JpaFavouriteTests {
    private static final Logger log = LoggerFactory.getLogger(JpaFavouriteTests.class);
    @Autowired
    private FavouriteService favouriteService;
    @Autowired
    private TidingService tidingService;

    @Test
    void testFavouriteCreate() {
        favouriteService.deleteAllFavourites();
        final Date dateofadd= new Date(1212121212121L);
        final Favourite favourite = favouriteService.addFavourite(dateofadd);
        log.info("testFavouriteCreate: " + favourite.toString());
        Assertions.assertNotNull(favourite.getId());

        favouriteService.deleteAllFavourites();
    }

    @Test
    void testFavouriteRead() {
        favouriteService.deleteAllFavourites();
        final Date dateofadd= new Date(1212121212121L);
        final Favourite favourite = favouriteService.addFavourite(dateofadd);
        log.info("testFavouriteRead[0]: " + favourite.toString());
        final Favourite findFavourite = favouriteService.findFavourite(favourite.getId());
        log.info("testFavouriteRead[1]: " + findFavourite.toString());
        Assertions.assertEquals(favourite, findFavourite);

        favouriteService.deleteAllFavourites();
    }

    @Test
    void testFavouriteReadNotFound() {
        favouriteService.deleteAllFavourites();
        Assertions.assertThrows(EntityNotFoundException.class, () -> favouriteService.findFavourite(-1L));
    }

    @Test
    void testFavouriteReadAll() {
        favouriteService.deleteAllFavourites();
        final Date dateofadd1= new Date(1212121212121L);
        final Date dateofadd2= new Date(121212121121L);
        favouriteService.addFavourite(dateofadd1);
        favouriteService.addFavourite(dateofadd2);
        final List<Favourite> favourites = favouriteService.findAllFavourites();
        log.info("testFavouriteReadAll: " + favourites.toString());
        Assertions.assertEquals(favourites.size(), 2);

        favouriteService.deleteAllFavourites();
    }

    @Test
    void testFavouriteReadAllEmpty() {
        favouriteService.deleteAllFavourites();
        final List<Favourite> favourites = favouriteService.findAllFavourites();
        log.info("testFavouriteReadAllEmpty: " + favourites.toString());
        Assertions.assertEquals(favourites.size(), 0);
    }

    @Test
    void testAddTidingInFavourite() {
        favouriteService.deleteAllFavourites();
        final Date dateofadd1= new Date(1212121212121L);
        Favourite favourite = favouriteService.addFavourite(dateofadd1);
        final Tiding tidingOne = tidingService.addTiding("Tiding 1", "abcd");
        final Tiding tidingTwo = tidingService.addTiding("Tiding 2", "efgh");
        favouriteService.addTidingInFavourite(favourite.getId(), tidingOne);
        favouriteService.addTidingInFavourite(favourite.getId(), tidingTwo);
        favourite = favouriteService.findFavourite(favourite.getId());
        log.info("testAddTidingInFavourite: " + favourite.getTidings().toString());
        Assertions.assertEquals(favourite.getTidings().size(), 2);

        favouriteService.deleteAllFavourites();
        tidingService.deleteAllTidings();
    }


    @Test
    void testRemoveTidingFromFavourite() {
        favouriteService.deleteAllFavourites();
        final Date dateofadd1= new Date(1212121212121L);
        Favourite favourite = favouriteService.addFavourite(dateofadd1);
        final Tiding tidingOne = tidingService.addTiding("Tiding 1", "abcd");
        final Tiding tidingTwo = tidingService.addTiding("Tiding 2", "efgh");
        favouriteService.addTidingInFavourite(favourite.getId(), tidingOne);
        favouriteService.addTidingInFavourite(favourite.getId(), tidingTwo);
        favouriteService.removeTidingInFavourite(favourite.getId(), tidingOne);
        favourite = favouriteService.findFavourite(favourite.getId());
        log.info("testRemoveTidingFromFavourite: " + favourite.getTidings());
        Assertions.assertEquals(favourite.getTidings().size(), 1);

        favouriteService.deleteAllFavourites();
        tidingService.deleteAllTidings();
    }

}

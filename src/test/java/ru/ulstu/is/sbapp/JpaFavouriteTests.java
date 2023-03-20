package ru.ulstu.is.sbapp;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.ulstu.is.sbapp.Favourite.model.Favourite;
import ru.ulstu.is.sbapp.Favourite.service.FavouriteService;
import ru.ulstu.is.sbapp.news.model.Tiding;
import ru.ulstu.is.sbapp.news.service.TidingService;


@SpringBootTest
public class JpaFavouriteTests {
    private static final Logger log = LoggerFactory.getLogger(JpaFavouriteTests.class);
    @Autowired
    private FavouriteService favouriteService;
    @Autowired
    private TidingService tidingService;

    @Test
    void Test()
    {

    }
}

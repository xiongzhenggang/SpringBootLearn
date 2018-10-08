package com.xzg.zuul.server.initialize;

import com.xzg.zuul.server.FeignService.RestaurantClient;
import com.xzg.zuul.server.entity.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


/**
 * 预先数据的加载
 */
@Component
public class FeignSample implements CommandLineRunner {

    @Autowired
    private RestaurantClient restaurantClient;

    @Override
    public void run(String... strings) throws Exception {
        this.restaurantClient.getRestaurants("o").forEach((Restaurant restaurant) -> {
            System.out.println("\n\n\n[ " + restaurant.getId() + " " + restaurant.getName() + "]");
        });
    }


}

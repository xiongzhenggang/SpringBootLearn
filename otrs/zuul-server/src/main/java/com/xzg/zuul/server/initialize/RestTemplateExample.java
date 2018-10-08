package com.xzg.zuul.server.initialize;

import com.xzg.zuul.server.entity.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
/**
 * @author xzg
 * 预先数据的加载
 */
@Component
public  class RestTemplateExample implements CommandLineRunner {
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void run(String... strings) throws Exception {
        System.out.println("\n\n\n start RestTemplate client...");
        ResponseEntity<Collection<Restaurant>> exchange
                = this.restTemplate.exchange(
                "http://restaurant-service/v1/restaurants?name=o",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Collection<Restaurant>>() {
                },
                (Object) "restaurants");
        exchange.getBody().forEach((Restaurant restaurant) -> {
            System.out.println("\n\n\n[ " + restaurant.getId() + " " + restaurant.getName() + "]");
        });
    }
}
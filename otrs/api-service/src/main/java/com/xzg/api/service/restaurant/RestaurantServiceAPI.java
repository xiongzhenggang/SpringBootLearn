package com.xzg.api.service.restaurant;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.xzg.api.service.restaurant.entityDto.Restaurant;
import com.xzg.common.ServiceHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
//import org.apache.log4j.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
public class RestaurantServiceAPI {
    private static final Logger LOG = LoggerFactory.getLogger(RestaurantServiceAPI.class);
    @Autowired
    ServiceHelper serviceHelper;
    //@Qualifier("userInfoRestTemplate")
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    DiscoveryClient client;

    @RequestMapping(name="/service",method = RequestMethod.POST)
    public List<String> home(@RequestParam("name") String name) {
        System.out.println("service");
        return Arrays.asList("ok", "best");
//        return client.getServices();
    }
//
//    @GetMapping("/user")
//    public Principal user(Principal user) {
//        return user;
//    }

    @RequestMapping("/restaurant/{restaurant-id}")
    @HystrixCommand(fallbackMethod = "defaultRestaurant")
    public ResponseEntity<Restaurant> getRestaurant(
            @PathVariable("restaurant-id") int restaurantId) {
//        MDC.put("restaurantId", restaurantId);
        String url = "http://restaurant-service/v1/restaurants/" + restaurantId;
        LOG.debug("GetRestaurant from URL: {}", url);

        ResponseEntity<Restaurant> result = restTemplate.getForEntity(url, Restaurant.class);
        LOG.info("GetRestaurant http-status: {}", result.getStatusCode());
        LOG.debug("GetRestaurant body: {}", result.getBody());

        return serviceHelper.createOkResponse(result.getBody());
    }

    /**
     * Fetch restaurants with the specified name. A partial case-insensitive
     * match is supported. So <code>http://.../restaurants/rest</code> will find
     * any restaurants with upper or lower case 'rest' in their name.
     *
     * @param name
     * @return A non-null, non-empty collection of restaurants.
     */
    @HystrixCommand(fallbackMethod = "defaultRestaurants")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Collection<Restaurant>> findByName(@RequestParam("name") String name) {
        LOG.info(String.format("api-service findByName() invoked:{} for {} ", "v1/restaurants?name=", name));
//        MDC.put("restaurantId", name);
        String url = "http://restaurant-service/v1/restaurants?name=".concat(name);
        LOG.debug("GetRestaurant from URL: {}", url);
        Collection<Restaurant> restaurants;
        ResponseEntity<Collection> result = restTemplate.getForEntity(url, Collection.class);
        LOG.info("GetRestaurant http-status: {}", result.getStatusCode());
        LOG.debug("GetRestaurant body: {}", result.getBody());

        return serviceHelper.createOkResponse(result.getBody());
    }

    /**
     * Fallback method for getProductComposite()
     *
     * @param productId
     * @return
     */
    public ResponseEntity<Restaurant> defaultRestaurant(
            @PathVariable int restaurantId) {
        return serviceHelper.createResponse(null, HttpStatus.BAD_GATEWAY);
    }

    /**
     * Fallback method
     *
     * @param input
     * @return
     */
    public ResponseEntity<Collection<Restaurant>> defaultRestaurants(String input) {
        LOG.warn("Fallback method for user-service is being used.");
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NO_CONTENT);
    }
}



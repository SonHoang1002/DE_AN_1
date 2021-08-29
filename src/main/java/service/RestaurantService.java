package service;

import DAO.RestaurantDAO;
import com.mongodb.client.DistinctIterable;
import org.bson.Document;
import restaurant.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class RestaurantService {
    public List<Restaurant> getRestaurantsforHomePage() {
        List<Restaurant> list = new RestaurantDAO().getRestaurant(12);
        if (list == null) {
            list = new ArrayList<>();
            //add some sample movies;
        }
        return list;
    }
    public Restaurant getRestaurantByID(String id){
        Restaurant restaurant = new RestaurantDAO().getRestaurantByID(id);
        return restaurant;
    }
    public DistinctIterable<String> getCuisine() {
        DistinctIterable<String> list = new RestaurantDAO().getCuisine();
        return list;
    }

    public List<String> getCuisinesforHeader() {
        ArrayList<String> list = new ArrayList<>();
        new RestaurantDAO().getTopRestaurants(20).forEach(d -> list.add(d.getString("_id")));
        return list;
    }

    final static int NUM_OF_RESTAURANT_ON_PAGE = 12;
    public List<Restaurant> searchRestaurants(String by, String value, int page) {
        Document filter = new Document();
        Document sort = new Document("restaurant_id", -1);
        if (by != null && value != null)
            filter.append(by, value);

        List<Restaurant> list = new RestaurantDAO().searchRestaurants(filter, sort, NUM_OF_RESTAURANT_ON_PAGE, (page - 1) * NUM_OF_RESTAURANT_ON_PAGE);
        if (list == null) {
            list = new ArrayList<>();
            //add some sample movies;
        }
        return list;
    }

    public long getTotalPages(String by, String value) {
        Document filter = new Document();
        if (by != null && value != null)
            filter.append(by, value);
        long totalRestaurants = new RestaurantDAO().getRestaurantsNumber(filter);
        return (long) Math.ceil((float) totalRestaurants / NUM_OF_RESTAURANT_ON_PAGE);
    }
}

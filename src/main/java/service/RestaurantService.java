package service;

import DAO.RestaurantDAO;
import com.mongodb.client.DistinctIterable;
import restaurant.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class RestaurantService {
    public List<Restaurant> getRestaurantforHomePage() {
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
    public DistinctIterable<String> getRestaurants() {
        DistinctIterable<String> list = new RestaurantDAO().getRestaurants();
        return list;
    }

    public List<String> getRestaurantsforHeader() {
        ArrayList<String> list = new ArrayList<>();
        new RestaurantDAO().getTopRestaurants(15).forEach(d -> list.add(d.getString("_id")));
        return list;
    }
}

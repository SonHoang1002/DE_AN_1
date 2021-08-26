package service;

import DAO.RestaurantDAO;
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
}

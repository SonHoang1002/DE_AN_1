package DAO;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import restaurant.Restaurant;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class RestaurantDAO extends AbsDAO{
    public List<Restaurant> getRestaurant(int limit) {
        MongoCollection<Document> restaurant = getDB().getCollection("restaurants");
        List<Restaurant> list = new ArrayList<>();
        restaurant.find().limit(limit).forEach(d -> list.add(docToRestaurant(d)));
        return list;
    }

    public Restaurant getRestaurantByID(String id) {
        MongoCollection<Document> Restaurant = getDB().getCollection("restaurants");
        Document restaurant = Restaurant.find(eq("_id", new ObjectId(id))).first();
        return docToRestaurant(restaurant);
    }



    private Restaurant docToRestaurant(Bson bson) {
        Restaurant restaurant = new Restaurant();
        Document document = (Document) bson;
        restaurant.set_id(document.getObjectId("_id").toHexString());
        restaurant.setBorough(document.getString("borough"));
        restaurant.setCuisine(document.getString("cuisine"));
        restaurant.setName(MessageFormat.format("{0}", document.get("name")));
        restaurant.setRestaurant_id(document.getString("restaurant_id"));
        restaurant.setGrades((List<String>) document.get("grades"));
        return restaurant;
    }
}

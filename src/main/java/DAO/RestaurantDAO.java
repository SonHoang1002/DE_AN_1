package DAO;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.DistinctIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import restaurant.Restaurant;

import javax.print.Doc;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
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

    public DistinctIterable<String> getCuisines() {
        MongoCollection<Document> restaurants = getDB().getCollection("restaurants");
        DistinctIterable<String> cuisine = restaurants.distinct("cuisine", String.class);
        return cuisine;
    }

    public AggregateIterable<Document> getTopRestaurants(int limit) {
        MongoCollection<Document> restaurants = getDB().getCollection("restaurants");
        AggregateIterable<Document> result = restaurants.aggregate(Arrays.asList(new Document("$unwind", "$cuisine"),
                new Document("$group", new Document("_id", "$cuisine").append("numOfRestaurants",new Document("$sum", 1L))),
                new Document("$sort", new Document("numOfRestaurants", -1L)),
                new Document("$limit",limit)));
        return result;
    }

    private Restaurant docToRestaurant(Bson bson) {
        Restaurant restaurant = new Restaurant();
        Document document = (Document) bson;
        restaurant.set_id(document.getObjectId("_id").toHexString());
        restaurant.setBorough(document.getString("borough"));
        restaurant.setCuisine(document.getString("cuisine"));
        restaurant.setPoster1(document.getString("poster1"));
        restaurant.setPoster2(document.getString("poster2"));
        restaurant.setName(MessageFormat.format("{0}", document.get("name")));
        restaurant.setRestaurant_id(document.getString("restaurant_id"));
        restaurant.setGrades((List<String>) document.get("grades"));
        return restaurant;
    }
    public List<Restaurant> searchRestaurants(Document filter, Document sort, int limit, int skip) {
        MongoCollection<Document> restaurant = getDB().getCollection("restaurants");
        List<Restaurant> list = new ArrayList<>();
        restaurant.find(filter).sort(sort).limit(limit).skip(skip).forEach(d -> list.add(docToRestaurant(d)));
        return list;
    }

}

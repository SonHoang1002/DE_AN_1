package controller;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.WebContext;
import restaurant.Restaurant;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class HomeController implements IController {

    public void process(final HttpServletRequest request, final HttpServletResponse response, final ServletContext servletContext, final ITemplateEngine templateEngine) throws Exception {
        WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
        ctx.setVariable("site_name", "Restaurant");

        ConnectionString connectionString = new ConnectionString("mongodb+srv://root:12345@cluster0.vit2y.mongodb.net/sample_restaurants?retryWrites=true&w=majority");
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
        MongoClient mongoClient = MongoClients.create(settings);
        MongoDatabase database = mongoClient.getDatabase("sample_restaurants");
        MongoCollection<Document> movies = database.getCollection("restaurants");

        List<Restaurant> list = new ArrayList<>();

        movies.find().limit(12).forEach(d -> list.add(docToRestaurant(d)));
        ctx.setVariable("list", list);
        mongoClient.close();


        templateEngine.process("index", ctx, response.getWriter());
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
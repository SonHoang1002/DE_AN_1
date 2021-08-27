package controller;

import com.mongodb.client.DistinctIterable;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.WebContext;
import restaurant.Restaurant;
import service.RestaurantService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RestaurantController  implements IController{

    @Override
    public void process(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext, ITemplateEngine templateEngine) throws Exception {
        WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
        String id = request.getParameter("id");
        Restaurant restaurant = new RestaurantService().getRestaurantByID(id);
        ctx.setVariable("restaurant", restaurant);
        DistinctIterable<String> cuisines = new RestaurantService().getCuisines();
        ctx.setVariable("cuisines", cuisines);
        templateEngine.process("restaurant", ctx, response.getWriter());
    }
}
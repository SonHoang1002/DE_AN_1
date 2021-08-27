package controller;

import com.mongodb.client.DistinctIterable;
import restaurant.Restaurant;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.WebContext;
import service.RestaurantService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class HomeController implements IController {

    public void process(final HttpServletRequest request, final HttpServletResponse response, final ServletContext servletContext, final ITemplateEngine templateEngine) throws Exception {
        WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
        List<Restaurant> list = new RestaurantService().getRestaurantforHomePage();
        List<String> cuisines = new RestaurantService().getCuisinesforHeader();
        ctx.setVariable("cuisines", cuisines);

















        ctx.setVariable("list", list);
        templateEngine.process("index", ctx, response.getWriter());




    }
}
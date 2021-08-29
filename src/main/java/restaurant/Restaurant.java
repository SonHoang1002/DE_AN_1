package restaurant;

import lombok.Data;

import java.util.List;

@Data
public class Restaurant {
    private String _id;
    private String borough;
    private String cuisine;
    private List<String> grades;
    private String name;
    private String restaurant_id;
    private String poster1;
    private String poster2;

}

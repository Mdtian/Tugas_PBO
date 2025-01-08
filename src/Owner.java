import java.util.ArrayList;
import java.util.List;

public class Owner {
    private String id;
    private String name;
    private List<Food> foodItems;
    private List<Order> orders;

    // Constructor
    public Owner(String id, String name) {
        this.id = id;
        this.name = name;
        this.foodItems = new ArrayList<>();
        this.orders = new ArrayList<>();
    }

    // Method to add food item
    public void addFoodItem(Food food) {
        foodItems.add(food);
    }

    // Method to view all food items
    public List<Food> viewFoodItems() {
        return foodItems;
    }

    // Method to view all orders
    public List<Order> viewOrders() {
        return orders;
    }

    // Method to add an order
    public void addOrder(Order order) {
        orders.add(order);
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
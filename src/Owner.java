import java.util.ArrayList;
import java.util.List;

public class Owner extends Person {
    private List<Food> foodItems;
    private List<Order> orders;

    public Owner(String id, String nama) {
        super(id, nama);
        this.foodItems = new ArrayList<>();
        this.orders = new ArrayList<>();
    }

    public void addFoodItem(Food food) {
        foodItems.add(food);
    }

    public List<Food> viewFoodItems() {
        return foodItems;
    }

    public List<Order> viewOrders() {
        return orders;
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    @Override
    public String getRole() {
        return "owner";
    }
}
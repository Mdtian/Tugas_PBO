import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    public static void save(Order order) {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);
            
            // Save order
            String orderSql = "INSERT INTO orders (id, customer_id, tanggal_pesanan, total_harga, status) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(orderSql)) {
                pstmt.setString(1, order.getId());
                pstmt.setString(2, order.getCustomer().getId());
                pstmt.setTimestamp(3, new Timestamp(order.getTanggalPesanan().getTime()));
                pstmt.setDouble(4, order.getTotalHarga());
                pstmt.setString(5, order.getStatus());
                pstmt.executeUpdate();
            }
            
            // Save order details
            String detailSql = "INSERT INTO order_detail (order_id, food_id) VALUES (?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(detailSql)) {
                for (Food food : order.getFoodItems()) {
                    pstmt.setString(1, order.getId());
                    pstmt.setString(2, food.getId());
                    pstmt.executeUpdate();
                }
            }
            
            conn.commit();
        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT o.*, c.nama as customer_nama, c.no_meja " +
                    "FROM orders o " +
                    "JOIN customer c ON o.customer_id = c.id";
        
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                // Create Customer object using the new constructor
                Customer customer = new Customer(
                    rs.getString("customer_id"),
                    rs.getString("customer_nama"),
                    rs.getString("no_meja")
                );
                
                String orderId = rs.getString("id");
                List<Food> foodItems = getFoodItemsForOrder(orderId);
                
                Order order = new Order(
                    orderId,
                    customer,
                    foodItems
                );
                orders.add(order);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    private static List<Food> getFoodItemsForOrder(String orderId) {
        // This method remains unchanged as it doesn't deal with Person/Customer classes
        List<Food> foods = new ArrayList<>();
        String sql = "SELECT f.* FROM food f " +
                    "JOIN order_detail od ON f.id = od.food_id " +
                    "WHERE od.order_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, orderId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Food food = new Food(
                    rs.getString("id"),
                    rs.getString("nama"),
                    rs.getDouble("harga"),
                    rs.getString("kategori")
                );
                foods.add(food);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return foods;
    }
}
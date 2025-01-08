import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FoodDAO {
    public static void save(Food food) {
        String sql = "INSERT INTO food (id, nama, harga, kategori) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, food.getId());
            pstmt.setString(2, food.getNama());
            pstmt.setDouble(3, food.getHarga());
            pstmt.setString(4, food.getKategori());
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Food> getAllFood() {
        List<Food> foods = new ArrayList<>();
        String sql = "SELECT * FROM food";
        
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
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
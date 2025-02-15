import java.sql.*;

public class CustomerDAO {
    public static void save(Customer customer) {
        String sql = "INSERT INTO customer (id, nama, no_meja) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, customer.getId());
            pstmt.setString(2, customer.getNama());
            pstmt.setString(3, customer.getNoMeja());
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Customer getById(String id) {
        String sql = "SELECT * FROM customer WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return new Customer(
                    rs.getString("id"),
                    rs.getString("nama"),
                    rs.getString("no_meja")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
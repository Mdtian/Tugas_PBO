
// PaymentDAO.java
import java.sql.*;

public class PaymentDAO {
    public static void save(Payment payment) {
        String sql = "INSERT INTO payment (id, order_id, jumlah_bayar, tanggal_bayar, metode_pembayaran, status) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, payment.getId());
            pstmt.setString(2, payment.getOrder().getId());
            pstmt.setDouble(3, payment.getJumlahBayar());
            pstmt.setTimestamp(4, new Timestamp(payment.getTanggalBayar().getTime()));
            pstmt.setString(5, payment.getMetodePembayaran());
            pstmt.setString(6, payment.getStatus());
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
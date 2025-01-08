import java.util.Date;
import java.util.List;

public class Order {
    private String id;
    private Customer customer;
    private List<Food> foodItems;
    private Date tanggalPesanan;
    private double totalHarga;
    private String status;

    // Konstruktor
    public Order(String id, Customer customer, List<Food> foodItems) {
        this.id = id;
        this.customer = customer;
        this.foodItems = foodItems;
        this.tanggalPesanan = new Date();
        this.status = "Diproses";
        hitungTotalHarga();
    }

    private void hitungTotalHarga() {
        this.totalHarga = foodItems.stream()
                .mapToDouble(Food::getHarga)
                .sum();
    }

    // Getter dan Setter
    public String getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<Food> getFoodItems() {
        return foodItems;
    }

    public Date getTanggalPesanan() {
        return tanggalPesanan;
    }

    public double getTotalHarga() {
        return totalHarga;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

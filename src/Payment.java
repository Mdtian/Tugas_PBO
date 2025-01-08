import java.util.Date;

public class Payment {
    private String id;
    private Order order;
    private double jumlahBayar;
    private Date tanggalBayar;
    private String metodePembayaran;
    private String status;

    // Konstruktor
    public Payment(String id, Order order, double jumlahBayar, String metodePembayaran) {
        this.id = id;
        this.order = order;
        this.jumlahBayar = jumlahBayar;
        this.tanggalBayar = new Date();
        this.metodePembayaran = metodePembayaran;
        this.status = "Menunggu Pembayaran";
    }

    public void konfirmasiPembayaran() {
        if (jumlahBayar >= order.getTotalHarga()) {
            status = "Lunas";
            order.setStatus("Selesai");
        } else {
            status = "Pembayaran Tidak Lengkap";
        }
    }

    // Getter dan Setter
    public String getId() {
        return id;
    }

    public Order getOrder() {
        return order;
    }

    public double getJumlahBayar() {
        return jumlahBayar;
    }

    public Date getTanggalBayar() {
        return tanggalBayar;
    }

    public String getMetodePembayaran() {
        return metodePembayaran;
    }

    public String getStatus() {
        return status;
    }
}
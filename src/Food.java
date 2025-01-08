public class Food {
    private String id;
    private String nama;
    private double harga;
    private String kategori;  // "Makanan" atau "Minuman"

    public Food(String id, String nama, double harga, String kategori) {
        this.id = id;
        this.nama = nama;
        this.harga = harga;
        this.kategori = kategori;
    }

    public String getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public double getHarga() {
        return harga;
    }

    public String getKategori() {
        return kategori;
    }
}
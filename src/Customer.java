public class Customer {
    private String id;
    private String nama;
    private String noMeja;

    public Customer(String id, String nama, String noMeja) {
        this.id = id;
        this.nama = nama;
        this.noMeja = noMeja;
    }

    public String getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getNoMeja() {
        return noMeja;
    }
}
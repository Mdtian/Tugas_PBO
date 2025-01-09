public class Customer extends Person {
    private String noMeja;

    public Customer(String id, String nama, String noMeja) {
        super(id, nama);
        this.noMeja = noMeja;
    }

    public String getNoMeja() {
        return noMeja;
    }

    @Override
    public String getRole() {
        return "customer";
    }
}
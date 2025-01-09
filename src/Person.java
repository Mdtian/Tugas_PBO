public abstract class Person {
    protected String id;
    protected String nama;

    public Person(String id, String nama) {
        this.id = id;
        this.nama = nama;
    }

    public String getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    // Abstract method yang harus diimplementasikan oleh child class
    public abstract String getRole();
}
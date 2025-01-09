public class User extends Person {
    private String username;
    private String password;
    private String userRole; // "admin" atau "staff"

    public User(String id, String nama, String username, String password, String userRole) {
        super(id, nama);
        this.username = username;
        this.password = password;
        this.userRole = userRole;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getUserRole() {
        return userRole;
    }

    @Override
    public String getRole() {
        return userRole;
    }
}
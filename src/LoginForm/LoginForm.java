import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class LoginForm extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private Map<String, User> users;

    public LoginForm() {
        // Setup frame
        setTitle("Login Sistem Pesanan Makanan");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Initialize users (In real application, this should be in a database)
        initializeUsers();

        // Create main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Create form panel
        JPanel formPanel = new JPanel(new GridLayout(2, 2, 5, 5));

        // Username field
        formPanel.add(new JLabel("Username:"));
        txtUsername = new JTextField();
        formPanel.add(txtUsername);

        // Password field
        formPanel.add(new JLabel("Password:"));
        txtPassword = new JPasswordField();
        formPanel.add(txtPassword);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnLogin = new JButton("Login");
        btnLogin.addActionListener(e -> doLogin());
        buttonPanel.add(btnLogin);

        // Add panels to main panel
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add main panel to frame
        add(mainPanel);

        // Set enter key to trigger login
        getRootPane().setDefaultButton(btnLogin);
    }

    private void initializeUsers() {
        users = new HashMap<>();
        // Add some default users (In real application, this should be in a database)
        users.put("admin", new User("admin", "admin123", "admin"));
        users.put("staff", new User("staff", "staff123", "staff"));
    }

    private void doLogin() {
        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Username dan Password harus diisi!",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            // Tampilkan pesan selamat datang
            JOptionPane.showMessageDialog(this,
                "Login berhasil!\nSelamat datang " + username,
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
            
            // Buka FormPesanan
            FormPesanan formPesanan = new FormPesanan();
            formPesanan.setVisible(true);
            
            // Tutup form login
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                "Username atau Password salah!",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Set system look and feel
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new LoginForm().setVisible(true);
        });
    }
}
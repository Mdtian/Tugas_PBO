

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FormPesanan extends JFrame {
    private JTextField txtNamaPelanggan;
    private JSpinner spinnerNoMeja;
    private JComboBox<String> cbMakanan;
    private JComboBox<String> cbMinuman;
    private JList<String> listPesanan;
    private DefaultListModel<String> modelPesanan;
    private JLabel lblTotalHarga;
    private List<Food> daftarMakanan;
    private List<Food> daftarMinuman;
    private List<Food> pesananMakanan;

    public FormPesanan() {
        initComponents();
        loadDataFromDatabase();
    }

    private void initComponents() {
        setTitle("Sistem Pesanan Makanan");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel Pelanggan
        JPanel panelPelanggan = createCustomerPanel();
        add(panelPelanggan, BorderLayout.NORTH);

        // Panel Utama
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(createMenuPanel(), BorderLayout.NORTH);
        mainPanel.add(createOrderListPanel(), BorderLayout.CENTER);
        mainPanel.add(createTotalPanel(), BorderLayout.SOUTH);
        add(mainPanel, BorderLayout.CENTER);

        // Panel Tombol
        add(createButtonPanel(), BorderLayout.SOUTH);
    }

    private JPanel createCustomerPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Informasi Pelanggan"));
        
        panel.add(new JLabel("Nama:"));
        txtNamaPelanggan = new JTextField();
        panel.add(txtNamaPelanggan);
        
        panel.add(new JLabel("No. Meja:"));
        spinnerNoMeja = new JSpinner(new SpinnerNumberModel(1, 1, 50, 1));
        panel.add(spinnerNoMeja);

        return panel;
    }

    private JPanel createMenuPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 10, 0));
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Panel Makanan
        JPanel panelMakanan = new JPanel();
        panelMakanan.setLayout(new BoxLayout(panelMakanan, BoxLayout.Y_AXIS));
        panelMakanan.setBorder(BorderFactory.createTitledBorder("Makanan"));
        
        cbMakanan = new JComboBox<>();
        JButton btnTambahMakanan = new JButton("Tambah Makanan");
        btnTambahMakanan.addActionListener(e -> tambahItem(true));
        
        JPanel panelMakananContent = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelMakananContent.add(cbMakanan);
        panelMakananContent.add(btnTambahMakanan);
        panelMakanan.add(panelMakananContent);

        // Panel Minuman
        JPanel panelMinuman = new JPanel();
        panelMinuman.setLayout(new BoxLayout(panelMinuman, BoxLayout.Y_AXIS));
        panelMinuman.setBorder(BorderFactory.createTitledBorder("Minuman"));
        
        cbMinuman = new JComboBox<>();
        JButton btnTambahMinuman = new JButton("Tambah Minuman");
        btnTambahMinuman.addActionListener(e -> tambahItem(false));
        
        JPanel panelMinumanContent = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelMinumanContent.add(cbMinuman);
        panelMinumanContent.add(btnTambahMinuman);
        panelMinuman.add(panelMinumanContent);

        panel.add(panelMakanan);
        panel.add(panelMinuman);
        return panel;
    }

    private JPanel createOrderListPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Daftar Pesanan"));

        modelPesanan = new DefaultListModel<>();
        listPesanan = new JList<>(modelPesanan);
        listPesanan.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPesanan = new JScrollPane(listPesanan);

        JButton btnHapusPesanan = new JButton("Hapus Pesanan");
        btnHapusPesanan.addActionListener(e -> hapusPesanan());

        JPanel panelTombolHapus = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelTombolHapus.add(btnHapusPesanan);

        panel.add(scrollPesanan, BorderLayout.CENTER);
        panel.add(panelTombolHapus, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createTotalPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        lblTotalHarga = new JLabel("Total Harga: Rp 0");
        lblTotalHarga.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(lblTotalHarga);
        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnPesan = new JButton("Buat Pesanan");
        btnPesan.addActionListener(e -> buatPesanan());
        panel.add(btnPesan);
        return panel;
    }

    private void loadDataFromDatabase() {
        try {
            // Load makanan dan minuman dari database
            daftarMakanan = FoodDAO.getAllFood().stream()
                .filter(f -> f.getKategori().equals("Makanan"))
                .collect(Collectors.toList());
                
            daftarMinuman = FoodDAO.getAllFood().stream()
                .filter(f -> f.getKategori().equals("Minuman"))
                .collect(Collectors.toList());

            // Update ComboBox
            updateComboBoxes();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error loading data: " + e.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void updateComboBoxes() {
        cbMakanan.removeAllItems();
        cbMinuman.removeAllItems();

        for (Food makanan : daftarMakanan) {
            cbMakanan.addItem(makanan.getNama() + " - Rp " + makanan.getHarga());
        }

        for (Food minuman : daftarMinuman) {
            cbMinuman.addItem(minuman.getNama() + " - Rp " + minuman.getHarga());
        }
    }

    private void tambahItem(boolean isMakanan) {
        if (pesananMakanan == null) {
            pesananMakanan = new ArrayList<>();
        }
        
        int selectedIndex = isMakanan ? cbMakanan.getSelectedIndex() : cbMinuman.getSelectedIndex();
        Food selectedFood = isMakanan ? 
            daftarMakanan.get(selectedIndex) : 
            daftarMinuman.get(selectedIndex);
        
        pesananMakanan.add(selectedFood);
        modelPesanan.addElement(selectedFood.getNama() + " - Rp " + selectedFood.getHarga());
        
        hitungTotalHarga();
    }

    private void hapusPesanan() {
        int selectedIndex = listPesanan.getSelectedIndex();
        if (selectedIndex != -1) {
            modelPesanan.remove(selectedIndex);
            pesananMakanan.remove(selectedIndex);
            hitungTotalHarga();
        } else {
            JOptionPane.showMessageDialog(this, 
                "Pilih pesanan yang ingin dihapus!", 
                "Peringatan", 
                JOptionPane.WARNING_MESSAGE);
        }
    }

    private void hitungTotalHarga() {
        if (pesananMakanan != null) {
            double total = pesananMakanan.stream()
                    .mapToDouble(Food::getHarga)
                    .sum();
            lblTotalHarga.setText("Total Harga: Rp " + total);
        }
    }

    private void buatPesanan() {
        String nama = txtNamaPelanggan.getText();
        int noMeja = (Integer) spinnerNoMeja.getValue();

        if (nama.isEmpty() || pesananMakanan == null || pesananMakanan.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Harap lengkapi nama pelanggan dan pesanan!", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Buat dan simpan customer
            String customerId = "C" + System.currentTimeMillis();
            Customer customer = new Customer(customerId, nama, "Meja " + noMeja);
            CustomerDAO.save(customer);

            // Buat dan simpan order
            String orderId = "O" + System.currentTimeMillis();
            Order order = new Order(orderId, customer, pesananMakanan);
            OrderDAO.save(order);

            // Tampilkan konfirmasi
            showOrderConfirmation(order, customer);

            // Reset form
            resetForm();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error menyimpan pesanan: " + e.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void showOrderConfirmation(Order order, Customer customer) {
        StringBuilder pesananDetail = new StringBuilder();
        pesananDetail.append("Pesanan Berhasil Disimpan!\n\n");
        pesananDetail.append("ID Pesanan: ").append(order.getId()).append("\n");
        pesananDetail.append("Nama: ").append(customer.getNama()).append("\n");
        pesananDetail.append("No. Meja: ").append(customer.getNoMeja()).append("\n\n");
        
        List<Food> makanan = pesananMakanan.stream()
            .filter(f -> f.getKategori().equals("Makanan"))
            .collect(Collectors.toList());
        List<Food> minuman = pesananMakanan.stream()
            .filter(f -> f.getKategori().equals("Minuman"))
            .collect(Collectors.toList());
        
        if (!makanan.isEmpty()) {
            pesananDetail.append("Makanan:\n");
            for (Food f : makanan) {
                pesananDetail.append("- ").append(f.getNama())
                           .append(" (Rp ").append(f.getHarga()).append(")\n");
            }
        }
        
        if (!minuman.isEmpty()) {
            pesananDetail.append("\nMinuman:\n");
            for (Food f : minuman) {
                pesananDetail.append("- ").append(f.getNama())
                           .append(" (Rp ").append(f.getHarga()).append(")\n");
            }
        }
        
        pesananDetail.append("\nTotal Harga: Rp ").append(order.getTotalHarga());

        JOptionPane.showMessageDialog(this, 
            pesananDetail.toString(), 
            "Konfirmasi Pesanan", 
            JOptionPane.INFORMATION_MESSAGE);
    }

    private void resetForm() {
        txtNamaPelanggan.setText("");
        spinnerNoMeja.setValue(1);
        modelPesanan.clear();
        pesananMakanan = null;
        lblTotalHarga.setText("Total Harga: Rp 0");
    }

    public static void main(String[] args) {
        try {
            // Set Look and Feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            FormPesanan form = new FormPesanan();
            form.setLocationRelativeTo(null); // Center on screen
            form.setVisible(true);
        });
    }
}
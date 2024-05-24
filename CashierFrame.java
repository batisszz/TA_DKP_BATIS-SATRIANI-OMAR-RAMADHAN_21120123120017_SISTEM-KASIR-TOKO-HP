package src;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Locale;

public class CashierFrame extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField totalPriceField;
    private JTextField totalPaymentField;
    private JTextField changeField;
    private JComboBox<String> itemComboBox;
    private ArrayList<Item> itemList;
    private NumberFormat currencyFormat;

    public CashierFrame() {
        setTitle("Kasir Toko Hp");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        itemList = new ArrayList<>();
        itemList.add(new Item("Samsung Galaxy S22", 15000000));
        itemList.add(new Item("Samsung Galaxy S21", 12000000));
        itemList.add(new Item("iPhone 12", 15000000));
        itemList.add(new Item("Xiaomi Mi 11", 8000000));
        itemList.add(new Item("Iphone 14 Pro", 20000000));
        itemList.add(new Item("Infinix Hot 30 Pro", 2100000));
        itemList.add(new Item("ROG Phone 5", 9000000));
        itemList.add(new Item("OPPO A53", 3000000));

        currencyFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu manageMenu = new JMenu("Atur Barang");
        menuBar.add(manageMenu);

        JMenuItem addItemMenuItem = new JMenuItem("Masukkan Barang");
        manageMenu.add(addItemMenuItem);

        JMenuItem removeItemMenuItem = new JMenuItem("Hapus Barang");
        manageMenu.add(removeItemMenuItem);

        addItemMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openAddItemFrame();
            }
        });

        removeItemMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openRemoveItemFrame();
            }
        });

        JPanel panel = new JPanel();
        add(panel);
        placeComponents(panel);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel selectItemLabel = new JLabel("Pilih Barang:");
        selectItemLabel.setBounds(10, 10, 80, 25);
        panel.add(selectItemLabel);

        itemComboBox = new JComboBox<>();
        updateItemComboBox();
        itemComboBox.setBounds(100, 10, 200, 25);
        panel.add(itemComboBox);

        JLabel quantityLabel = new JLabel("Jumlah:");
        quantityLabel.setBounds(310, 10, 60, 25);
        panel.add(quantityLabel);

        JTextField quantityField = new JTextField("1");
        quantityField.setBounds(380, 10, 50, 25);
        panel.add(quantityField);

        JButton addButton = new JButton("Tambah");
        addButton.setBounds(440, 10, 80, 25);
        panel.add(addButton);

        JButton deleteButton = new JButton("Hapus");
        deleteButton.setBounds(530, 10, 80, 25);
        panel.add(deleteButton);

        JButton addItemButton = new JButton("Tambah Barang");
        addItemButton.setBounds(620, 10, 150, 25);
        panel.add(addItemButton);

        JButton removeItemButton = new JButton("Hapus Barang");
        removeItemButton.setBounds(620, 310, 150, 25);
        panel.add(removeItemButton);

        tableModel = new DefaultTableModel(new String[]{"Barang", "Harga", "Jumlah", "Total"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 50, 760, 250);
        panel.add(scrollPane);

        JLabel totalLabel = new JLabel("Total Harga");
        totalLabel.setBounds(10, 300, 80, 25);
        panel.add(totalLabel);

        totalPriceField = new JTextField();
        totalPriceField.setBounds(120, 300, 165, 25);
        totalPriceField.setEditable(false);
        panel.add(totalPriceField);

        JLabel totalPaymentLabel = new JLabel("Total Pembayaran");
        totalPaymentLabel.setBounds(10, 330, 120, 25);
        panel.add(totalPaymentLabel);

        totalPaymentField = new JTextField(20);
        totalPaymentField.setBounds(120, 330, 165, 25);
        panel.add(totalPaymentField);

        JLabel changeLabel = new JLabel("Kembalian");
        changeLabel.setBounds(10, 360, 80, 25);
        panel.add(changeLabel);

        changeField = new JTextField(20);
        changeField.setBounds(120, 360, 165, 25);
        changeField.setEditable(false);
        panel.add(changeField);

        JButton calculateChangeButton = new JButton("Hitung Pembayaran");
        calculateChangeButton.setBounds(10, 390, 255, 25);
        panel.add(calculateChangeButton);

        JButton resetButton = new JButton("Reset");
        resetButton.setBounds(360, 310, 80, 25);
        panel.add(resetButton);

        JButton logoutButton = new JButton("Logout");
        logoutButton.setBounds(450, 310, 80, 25);
        panel.add(logoutButton);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedItem = (String) itemComboBox.getSelectedItem();
                String[] itemDetails = selectedItem.split(" - ");
                String itemName = itemDetails[0];
                double price = 0;
                try {
                    Number number = currencyFormat.parse(itemDetails[1]);
                    price = number.doubleValue();
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
                int quantity = Integer.parseInt(quantityField.getText());
                if (quantity > 0) {
                    double total = quantity * price;
                    tableModel.addRow(new Object[]{itemName, currencyFormat.format(price), quantity, currencyFormat.format(total)});
                    calculateTotalPrice();
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    tableModel.removeRow(selectedRow);
                    calculateTotalPrice();
                }
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableModel.setRowCount(0);
                totalPriceField.setText("");
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginFrame loginFrame = new LoginFrame();
                loginFrame.setVisible(true);
                dispose();
            }
        });

        addItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openAddItemFrame();
            }
        });

        removeItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openRemoveItemFrame();
            }
        });    

        calculateChangeButton.addActionListener(new ActionListener() {
           @Override
            public void actionPerformed(ActionEvent e) {
                calculateChange();
            }
        });
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reset();
            }    
        });
    }

    private void openAddItemFrame() {
        AddItemFrame addItemFrame = new AddItemFrame(itemList, this);
        addItemFrame.setVisible(true);
    }

    private void openRemoveItemFrame() {
        RemoveItemFrame removeItemFrame = new RemoveItemFrame(itemList, this);
        removeItemFrame.setVisible(true);
    }

    private void calculateTotalPrice() {
        double total = 0;
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String totalString = (String) tableModel.getValueAt(i, 3);
            try {
                Number number = currencyFormat.parse(totalString);
                double totalValue = number.doubleValue();
                total += totalValue;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        totalPriceField.setText(currencyFormat.format(total));
    }

    private void calculateChange() {
        try {
            double totalPayment = Double.parseDouble(totalPaymentField.getText());
            double totalPrice = getTotalPrice();
            if (totalPayment >= totalPrice) {
                double change = totalPayment - totalPrice;
                changeField.setText(currencyFormat.format(change));
            } else {
                JOptionPane.showMessageDialog(null, "Maaf uang yang dibayarkan kurang", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Maaf silahkan menuliskan angka saja tanpa tanda titik", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private double getTotalPrice() {
        double total = 0;
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String totalString = (String) tableModel.getValueAt(i, 3);
            try {
                Number number = currencyFormat.parse(totalString);
                double totalValue = number.doubleValue();
                total += totalValue;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return total;
    }
    private void reset() {
        tableModel.setRowCount(0); 
        totalPriceField.setText("");
        totalPaymentField.setText(""); 
        changeField.setText(""); 
    }
    public void updateItemComboBox() {
        itemComboBox.removeAllItems();
        for (Item item : itemList) {
            itemComboBox.addItem(item.getName() + " - " + currencyFormat.format(item.getPrice()));
        }
    }
}

package src;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AddItemFrame extends JFrame {
    private ArrayList<Item> itemList;
    private CashierFrame cashierFrame;

    public AddItemFrame(ArrayList<Item> itemList, CashierFrame cashierFrame) {
        this.itemList = itemList;
        this.cashierFrame = cashierFrame;
        setTitle("Tambahkan Barang");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        add(panel);
        placeComponents(panel);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel nameLabel = new JLabel("Nama Barang:");
        nameLabel.setBounds(10, 20, 80, 25);
        panel.add(nameLabel);

        JTextField nameText = new JTextField(20);
        nameText.setBounds(100, 20, 165, 25);
        panel.add(nameText);

        JLabel priceLabel = new JLabel("Harga:");
        priceLabel.setBounds(10, 50, 80, 25);
        panel.add(priceLabel);

        JTextField priceText = new JTextField(20);
        priceText.setBounds(100, 50, 165, 25);
        panel.add(priceText);

        JButton addButton = new JButton("Tambah");
        addButton.setBounds(10, 80, 80, 25);
        panel.add(addButton);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameText.getText();
                double price = Double.parseDouble(priceText.getText());

                Item newItem = new Item(name, price);
                itemList.add(newItem);
                cashierFrame.updateItemComboBox();
                JOptionPane.showMessageDialog(null, "Barang Berhasil Ditambahkan", "Info", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            }
        });
    }
}

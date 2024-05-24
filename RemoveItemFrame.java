package src;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class RemoveItemFrame extends JFrame {
    private ArrayList<Item> itemList;
    private CashierFrame cashierFrame;

    public RemoveItemFrame(ArrayList<Item> itemList, CashierFrame cashierFrame) {
        this.itemList = itemList;
        this.cashierFrame = cashierFrame;
        setTitle("Hapus Barang");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        add(panel);
        placeComponents(panel);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel selectItemLabel = new JLabel("Pilih Barang:");
        selectItemLabel.setBounds(10, 20, 80, 25);
        panel.add(selectItemLabel);

        JComboBox<String> itemComboBox = new JComboBox<>();
        for (Item item : itemList) {
            itemComboBox.addItem(item.getName());
        }
        itemComboBox.setBounds(100, 20, 165, 25);
        panel.add(itemComboBox);

        JButton removeButton = new JButton("Hapus");
        removeButton.setBounds(10, 60, 80, 25);
        panel.add(removeButton);

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedItem = (String) itemComboBox.getSelectedItem();
                if (selectedItem != null) {
                    itemList.removeIf(item -> item.getName().equals(selectedItem));
                    cashierFrame.updateItemComboBox();
                    JOptionPane.showMessageDialog(null, "Barang Berhasil Dihapus", "Info", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                }
            }
        });
    }
}

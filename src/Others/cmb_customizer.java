/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Others;

import Connection.DBConn;
import java.awt.Component;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JList;

/**
 *
 * @author Administrator
 */
public class cmb_customizer {
    
    public void populateComboBox(JComboBox comboBox, String query) {
        comboBox.removeAllItems();

        try (Connection conn = DBConn.getConnection();
             PreparedStatement statement = conn.prepareStatement(query);
             ResultSet rs = statement.executeQuery()) {

            DefaultComboBoxModel<ComboBoxItem> model = new DefaultComboBoxModel<>();

            // Populate the combobox with data from the result set
            while (rs.next()) {
                int id = rs.getInt(1); // Assuming the first column is the ID
                String desc = rs.getString(2); // Assuming the second column is the description or doc_status_desc
                ComboBoxItem item = new ComboBoxItem(id, desc);
                model.addElement(item);
            }

            comboBox.setModel(model);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static ComboBoxItem findComboBoxItemByDescription(JComboBox<?> comboBox, String description) {
        ComboBoxItem selectedItem = null;
        ComboBoxModel<?> model = comboBox.getModel();
        int itemCount = model.getSize();
        for (int i = 0; i < itemCount; i++) {
            Object item = model.getElementAt(i);
            if (item instanceof ComboBoxItem) {
                ComboBoxItem comboBoxItem = (ComboBoxItem) item;
                if (comboBoxItem.getDescription().equals(description)) {
                    selectedItem = comboBoxItem;
                    break;
                }
            }
        }
        return selectedItem;
    }

    // Helper class to hold the ID and description of the combobox item
    public static class ComboBoxItem {
        private int id;
        private String description;

        public ComboBoxItem(int id, String description) {
            this.id = id;
            this.description = description;
        }

        public int getId() {
            return id;
        }

        public String getDescription() {
            return description;
        }

        @Override
        public String toString() {
            return description;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            ComboBoxItem otherItem = (ComboBoxItem) obj;
            return Objects.equals(description, otherItem.description);
        }

        @Override
        public int hashCode() {
            return Objects.hash(description);
        }
    }

    // Custom ListCellRenderer to display ComboBoxItem objects correctly
    public static class ComboBoxRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value instanceof ComboBoxItem) {
                ComboBoxItem item = (ComboBoxItem) value;
                setText(item.getDescription());
            }
            
            if ("Custom Input".equals(value)) {
                setText("Enter Custom Value");
            }
            
            return this;
        }
    }
}  

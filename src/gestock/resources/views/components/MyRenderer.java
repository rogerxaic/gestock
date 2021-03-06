package gestock.resources.views.components;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class MyRenderer extends DefaultTableCellRenderer {

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (!table.isRowSelected(row)) {
            if (row % 2 == 0) {
                c.setBackground(new Color(230, 230, 230));
            } else {
                c.setBackground(table.getBackground());
            }
        }
        return c;
    }
}

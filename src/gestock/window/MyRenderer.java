package gestock.window;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableCellRenderer;


public class MyRenderer extends DefaultTableCellRenderer {

    public Component getTableCellRendererComponent(JTable table, Object value, boolean   isSelected, boolean hasFocus, int row, int column)
    {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (! table.isRowSelected(row))
        {
            if(row % 2 == 0 )
                c.setBackground(Color.gray);
            else
                c.setBackground(table.getBackground());
        }
        return c;
    }

}

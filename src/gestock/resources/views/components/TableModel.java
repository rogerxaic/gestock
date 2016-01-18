package gestock.resources.views.components;

import javax.swing.table.DefaultTableModel;
/**
 * Created by Cristinuta on 12/5/2015.
 */
public class TableModel extends DefaultTableModel {

    public TableModel(String[] columnNames){
        super(new Object[0][2],columnNames);
    }

    public Class<?> getColumnClass(int columnIndex) {
        return columnIndex == 1 ? Integer.class : String.class;
    }
}

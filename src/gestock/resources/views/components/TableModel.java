package gestock.resources.views.components;

import javax.swing.table.DefaultTableModel;
import java.util.Date;

/**
 * Created by Cristinuta on 12/5/2015.
 */
public class TableModel extends DefaultTableModel {

    public TableModel(String[] columnNames){
        super(new Object[0][3],columnNames);
    }

    public Class<?> getColumnClass(int columnIndex) {
        if(columnIndex == 1){
            return Integer.class;
        } else {
            if (columnIndex == 3){
                return Date.class;
            } else {return String.class;}
        }
    }
}

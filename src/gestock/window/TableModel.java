package gestock.window;

import javax.swing.table.DefaultTableModel;
import javax.swing.text.StringContent;

/**
 * Created by Cristinuta on 12/5/2015.
 */
public class TableModel extends DefaultTableModel{
    public TableModel(String[] columnNames){
        super(new String[20][3],columnNames);


    }
}

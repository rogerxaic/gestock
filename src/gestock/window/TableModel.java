package gestock.window;

import javax.swing.table.DefaultTableModel;
import javax.swing.text.StringContent;

/**
 * Created by Cristinuta on 12/5/2015.
 */
public class TableModel extends DefaultTableModel{
 /*   private static final int COLUMN_NOM      = 0;
    private static final int COLUMN_QUANTITE    = 1;
    private static final int COLUMN_DEPEND     = 2;
*/

    public TableModel(String[] columnNames){
        super(new String[20][3],columnNames);
    }

 /*   public int getColumnCount() {
        return super.getColumnCount();
    }

    public int getRowCount() {
        return super.getRowCount();
    }
    public String getColumnName(int columnIndex) {
        return super.getColumnName(columnIndex);
    }

    public Class<?> getColumnClass(int columnIndex) {
        if (super.getDataVector().isEmpty()) {
            return Object.class;
        }
        return getValueAt(0, columnIndex).getClass();
    }

   public Object getValueAt(int rowIndex, int columnIndex) {
        BasicProduct = super.getDataVector().get(rowIndex);
        Object returnValue = null;

        switch (columnIndex) {
            case COLUMN_NOM:
                returnValue = element.g();
                break;
            case COLUMN_NAME:
                returnValue = employee.getName();
                break;
            case COLUMN_JOB:
                returnValue = employee.getJob();
                break;
            case COLUMN_AGE:
                returnValue = employee.getAge();
                break;
            default:
                throw new IllegalArgumentException("Invalid column index");
        }

        return returnValue;
    }
*/
}

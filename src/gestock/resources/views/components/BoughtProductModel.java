package gestock.resources.views.components;

import gestock.Gestock;
import gestock.entity.BoughtProduct;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * Created by Roger on 1/9/2016.
 */
public class BoughtProductModel extends AbstractTableModel {
    public static final int COLUMN_NO = 0;
    public static final int COLUMN_NAME = 1;
    public static final int COLUMN_BOUGHT = 2;
    public static final int COLUMN_EXPIRY = 3;
    public static final int COLUMN_QUANTITY = 4;
    public static final int COLUMN_REMAINING = 5;
    public static final int COLUMN_PRICE = 6;

    private String[] columnNames = {"No #", "Name", "Bought on", "Expiry date", "Quantity", "Remaining", "Price"};
    private List<BoughtProduct> listBoughtProducts;

    public BoughtProductModel(Gestock app, List<BoughtProduct> listBoughtProducts) {
        this.listBoughtProducts = listBoughtProducts;
        columnNames[0] = app.messages.getString("pantry.table.id");
        columnNames[1] = app.messages.getString("pantry.table.name");
        columnNames[2] = app.messages.getString("pantry.table.bought");
        columnNames[3] = app.messages.getString("pantry.table.expiry");
        columnNames[4] = app.messages.getString("pantry.table.quantity");
        columnNames[5] = app.messages.getString("pantry.table.remaining");
        columnNames[6] = app.messages.getString("pantry.table.price");
    }

    @Override
    public int getRowCount() {
        return listBoughtProducts.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (listBoughtProducts.isEmpty()) {
            return Object.class;
        }
        return getValueAt(0, columnIndex).getClass();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        BoughtProduct boughtProduct = listBoughtProducts.get(rowIndex);
        Object returnValue = null;

        switch (columnIndex) {
            case COLUMN_NO:
                returnValue = boughtProduct.getId();
                break;
            case COLUMN_NAME:
                returnValue = boughtProduct.getBaseProduct().toString();
                break;
            case COLUMN_BOUGHT:
                returnValue = boughtProduct.getBoughtDay();
                break;
            case COLUMN_EXPIRY:
                returnValue = boughtProduct.getExpirationDay();
                break;
            case COLUMN_QUANTITY:
                returnValue = boughtProduct.getQuantity();
                break;
            case COLUMN_REMAINING:
                returnValue = boughtProduct.getRemainingQuantity();
                break;
            case COLUMN_PRICE:
                returnValue = boughtProduct.getPrice().getTotalPrice();
                break;
            default:
                throw new IllegalArgumentException("Invalid column index");
        }

        return returnValue;
    }

    /*@Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        BoughtProduct boughtProduct = listBoughtProducts.get(rowIndex);
        if (columnIndex == COLUMN_NO) {
            boughtProduct.setIndex((int) value);
        }
    }*/
}

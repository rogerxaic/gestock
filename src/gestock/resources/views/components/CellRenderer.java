package gestock.resources.views.components;

import gestock.Gestock;
import gestock.entity.BaseProduct;
import gestock.util.Tools;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.Date;


public class CellRenderer extends DefaultTableCellRenderer {

    private Gestock model;

    public CellRenderer(Gestock model) {
        this.model = model;
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (!table.isRowSelected(row)) {

            javax.swing.table.TableModel tm = table.getModel();
            BaseProduct bp = model.findBoughtProductById((Integer) tm.getValueAt(row, 0)).getBaseProduct();
            int alert = bp.getAlert();

            switch (column) {
                case BoughtProductModel.COLUMN_NO:
                    c.setBackground(table.getBackground());
                    break;
                case BoughtProductModel.COLUMN_NAME:
                    c.setBackground(table.getBackground());
                    break;
                case BoughtProductModel.COLUMN_BOUGHT:
                    if (value instanceof Date) {
                        Date date = (Date) value;
                        super.setValue(date.toLocaleString());
                    }
                    c.setBackground(table.getBackground());
                    break;
                case BoughtProductModel.COLUMN_EXPIRY:
                    if (value instanceof Date) {
                        Date date = (Date) value;
                        Date now = new Date();
                        long difference = date.getTime() - now.getTime();

                        if (difference < 14 * 24 * 60 * 60 * 1000) {
                            int alpha = (int) Tools.map(difference, 14 * 24 * 3600 * 1000, 0, 0, 255);
                            int green = (int) Tools.map(difference, 14 * 24 * 3600 * 1000, 0, 255, 0);
                            alpha = (alpha > 255) ? 255 : (alpha < 0) ? 0 : alpha;
                            green = (green < 0) ? 0 : (green > 255) ? 255 : green;
                            Color color = new Color(255, green, 0, alpha);

                            c.setBackground(color);
                        } else {
                            c.setBackground(table.getBackground());
                        }
                        super.setValue(date.toLocaleString());
                    }
                    break;
                case BoughtProductModel.COLUMN_QUANTITY:
                    c.setBackground(table.getBackground());
                    break;
                case BoughtProductModel.COLUMN_REMAINING:
                    if (value instanceof Integer) {
                        int remaining = (int) value;
                        int alpha = (int) Tools.map(remaining, (alert + 1), 1, 0, 255);
                        int green = (int) Tools.map(remaining, (alert + 1), 1, 255, 0);
                        alpha = (alpha > 255) ? 255 : (alpha < 0) ? 0 : alpha;
                        green = (green < 0) ? 0 : (green > 255) ? 255 : green;
                        Color color = new Color(255, green, 0, alpha);
                        if (remaining <= alert) {
                            c.setBackground(color);
                        } else {
                            c.setBackground(table.getBackground());
                        }
                    }
                    break;
                case BoughtProductModel.COLUMN_PRICE:
                    c.setBackground(table.getBackground());
                    break;
                default:
                    c.setBackground(table.getBackground());
                    break;
            }
        }
        return c;
    }

}

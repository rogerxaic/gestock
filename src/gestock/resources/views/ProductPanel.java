package gestock.resources.views;

import gestock.controller.ProductController;
import gestock.entity.BaseProduct;
import gestock.util.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Roger on 12/25/2015.
 */
public class ProductPanel extends JPanel implements MouseListener, Observer, Scrollable {

    private CatalogueView catalogueView;
    private BaseProduct baseProduct;
    private JLabel text;
    private JLabel description;
    private int defaultFontSize;

    public ProductPanel(BaseProduct baseProduct, CatalogueView catalogueView) {
        super(new BorderLayout());
        setPreferredSize(new Dimension(150, 100));
        this.catalogueView = catalogueView;
        this.baseProduct = baseProduct;
        this.baseProduct.addObserver(this);
        this.defaultFontSize = new JLabel().getFont().getSize();
        text = new JLabel(baseProduct.getName());
        text.setFont(new Font(text.getFont().getName(), Font.PLAIN, defaultFontSize));
        add(text, BorderLayout.NORTH);
        description = new JLabel(String.valueOf(baseProduct.getDescription()));
        description.setFont(new Font(description.getFont().getName(), Font.ITALIC, defaultFontSize - 1));
        description.setForeground(new Color(128, 128, 128));
        add(description, BorderLayout.SOUTH);
        addMouseListener(this);
        setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        new ProductController(catalogueView.app, baseProduct);
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public Dimension getPreferredScrollableViewportSize() {
        return null;
    }

    @Override
    public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
        return 0;
    }

    @Override
    public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
        return 0;
    }

    @Override
    public boolean getScrollableTracksViewportWidth() {
        return true;
    }

    @Override
    public boolean getScrollableTracksViewportHeight() {
        return true;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg != null && arg instanceof Integer) {
            if (arg.equals(Constants.OBSERVER_PRODUCT_UPDATED)) {
                description.setText(String.valueOf(baseProduct.getDescription()));
                text.setText(baseProduct.getName());
                catalogueView.refresh();
            }
        }
    }
}

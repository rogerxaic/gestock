package gestock.resources.views;

import gestock.entity.BaseProduct;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Roger on 12/25/2015.
 */
public class ProductPanel extends JPanel implements MouseListener, Scrollable {

    private CatalogueView catalogueView;
    private BaseProduct baseProduct;
    private JLabel text;
    private int defaultFontSize;

    public ProductPanel(BaseProduct baseProduct, CatalogueView catalogueView) {
        super(new BorderLayout());
        setPreferredSize(new Dimension(150, 100));
        this.catalogueView = catalogueView;
        this.baseProduct = baseProduct;
        //this.notes = notes;
        //this.note = note;
        //this.index = notes.notes.indexOf(note);
        this.defaultFontSize = new JLabel().getFont().getSize();
        text = new JLabel(baseProduct.getName());
        text.setFont(new Font(text.getFont().getName(), Font.PLAIN, defaultFontSize));
        add(text, BorderLayout.NORTH);
        JLabel date = new JLabel(String.valueOf(baseProduct.getDescription()));
        date.setFont(new Font(date.getFont().getName(), Font.ITALIC, defaultFontSize - 1));
        date.setForeground(new Color(128, 128, 128));
        add(date, BorderLayout.SOUTH);
        addMouseListener(this);
        setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        new ProductView(catalogueView.app, baseProduct, true);
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
}

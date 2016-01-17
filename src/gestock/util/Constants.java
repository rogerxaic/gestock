package gestock.util;

/**
 * Created by rogerxaic on 12/27/2015.
 */
public final class Constants {

    public static final String OS = System.getProperty("os.name").toLowerCase();
    public static final int BUFFER = 2048;
    public static final String FS = System.getProperty("file.separator");

    /**
     * The {@code int} that represents the signal for added
     * products to the catalogue for the {@code Observable}.
     */
    public static final int OBSERVER_PRODUCT_CREATED = 1;
    /**
     * The {@code int} that represents the signal for updated
     * products of the catalogue for the {@code Observable}.
     */
    public static final int OBSERVER_PRODUCT_UPDATED = 3;
    /**
     * The {@code int} that represents the signal for deleted
     * products from the catalogue for the {@code Observable}.
     */
    public static final int OBSERVER_PRODUCT_DELETED = 4;

    public static final int OBSERVER_USER_CREATED = 5;

    public static final int OBSERVER_USER_UPDATED = 7;

    public static final int OBSERVER_PANTRY_PRODUCT_CREATED = 9;

    public static final int OBSERVER_SHOP_CREATED = 13;

    /**
     * Don't let anyone instantiate this class.
     */
    private Constants() {
    }
}

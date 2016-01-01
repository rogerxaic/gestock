package gestock.util;

/**
 * Created by rogerxaic on 12/27/2015.
 */
public final class Constants {

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

    /**
     * Don't let anyone instantiate this class.
     */
    private Constants() {
    }
}

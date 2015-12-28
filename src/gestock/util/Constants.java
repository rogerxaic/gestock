package gestock.util;

/**
 * Created by rogerxaic on 12/27/2015.
 */
public final class Constants {

    /**
     * The {@code int} that represents the signal for added
     * products to the catalogue for the {@code Observable}.
     */
    public static final int OBSERVER_CREATED_PRODUCT = 10;
    /**
     * The {@code int} that represents the signal for updated
     * products of the catalogue for the {@code Observable}.
     */
    public static final int OBSERVER_UPDATED_PRODUCT = 30;
    /**
     * The {@code int} that represents the signal for deleted
     * products from the catalogue for the {@code Observable}.
     */
    public static final int OBSERVER_DELETED_PRODUCT = 40;

    /**
     * Don't let anyone instantiate this class.
     */
    private Constants() {
    }
}

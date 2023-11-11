package christmas.fixture;

import christmas.domain.constants.MenuItem;
import christmas.domain.reservation.OrderMenuItem;

public class ChristmasFixture {

    private ChristmasFixture() {
    }

    public static OrderMenuItem createOrderMenuItem(MenuItem menuItem, int count) {
        return new OrderMenuItem(menuItem, count);
    }
}

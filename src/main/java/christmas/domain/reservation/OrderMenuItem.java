package christmas.domain.reservation;

import static christmas.domain.constants.ChristmasConstraints.isWithinOrderMenuCountRange;
import static christmas.exception.ErrorMessage.INVALID_ORDER;

import christmas.domain.constants.MenuItem;

public class OrderMenuItem {
    private final MenuItem menuItem;
    private final int count;

    public OrderMenuItem(MenuItem menuItem, int count) {
        this.menuItem = menuItem;
        this.count = count;
    }

    public static OrderMenuItem valueOf(String menuItemName, int count) {
        MenuItem menuItem = getMenuItem(menuItemName);
        validateCount(count);

        return new OrderMenuItem(menuItem, count);
    }

    private static MenuItem getMenuItem(String menuItem) {
        return MenuItem.findByName(menuItem)
                .orElseThrow(() -> new IllegalArgumentException(INVALID_ORDER.getMessage(menuItem)));
    }

    private static void validateCount(int count) {
        if (!isWithinOrderMenuCountRange(count)) {
            throw new IllegalArgumentException(INVALID_ORDER.getMessage(count));
        }
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public boolean isBeverage() {
        return menuItem.isBeverage();
    }

    public int getCount() {
        return count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OrderMenuItem that = (OrderMenuItem) o;

        return menuItem == that.menuItem;
    }

    @Override
    public int hashCode() {
        if (menuItem != null) {
            return menuItem.hashCode();
        }
        return 0;
    }

    @Override
    public String toString() {
        return "OrderMenuItem{" +
                "menuItem=" + menuItem +
                ", count=" + count +
                '}';
    }
}

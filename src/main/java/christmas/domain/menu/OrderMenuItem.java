package christmas.domain.menu;

import static christmas.common.exception.ErrorMessage.INVALID_ORDER;
import static christmas.domain.menu.constants.OrderMenuConstraints.isWithinOrderMenuCountRange;

import christmas.domain.menu.constants.Menu;

public class OrderMenuItem {
    private final Menu menu;
    private final int count;

    public OrderMenuItem(Menu menu, int count) {
        this.menu = menu;
        this.count = count;
    }

    public static OrderMenuItem valueOf(String menuItemName, int count) {
        Menu menu = getMenuItem(menuItemName);
        validateCount(count);

        return new OrderMenuItem(menu, count);
    }

    private static Menu getMenuItem(String menuItem) {
        return Menu.findByName(menuItem)
                .orElseThrow(() -> new IllegalArgumentException(INVALID_ORDER.getMessage(menuItem)));
    }

    private static void validateCount(int count) {
        if (!isWithinOrderMenuCountRange(count)) {
            throw new IllegalArgumentException(INVALID_ORDER.getMessage(count));
        }
    }

    public boolean isBeverage() {
        return menu.isBeverage();
    }

    public Menu getMenuItem() {
        return menu;
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

        return menu == that.menu;
    }

    @Override
    public int hashCode() {
        if (menu != null) {
            return menu.hashCode();
        }
        return 0;
    }

    @Override
    public String toString() {
        return "OrderMenuItem{" +
                "menuItem=" + menu +
                ", count=" + count +
                '}';
    }
}

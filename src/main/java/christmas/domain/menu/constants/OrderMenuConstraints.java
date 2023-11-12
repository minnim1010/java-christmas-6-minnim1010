package christmas.domain.menu.constants;

public class OrderMenuConstraints {
    public static final int MIN_ORDER_MENU_COUNT = 1;
    public static final int MAX_ORDER_MENU_COUNT = 20;
    public static final int MIN_TOTAL_ORDER_MENU_COUNT = 1;
    public static final int MAX_TOTAL_ORDER_MENU_COUNT = 20;

    private OrderMenuConstraints() {
    }

    public static boolean isWithinOrderMenuCountRange(int count) {
        return MIN_ORDER_MENU_COUNT <= count && count <= MAX_ORDER_MENU_COUNT;
    }

    public static boolean isWithinOrderMenuTotalCountRange(int totalCount) {
        return MIN_TOTAL_ORDER_MENU_COUNT <= totalCount && totalCount <= MAX_TOTAL_ORDER_MENU_COUNT;
    }
}

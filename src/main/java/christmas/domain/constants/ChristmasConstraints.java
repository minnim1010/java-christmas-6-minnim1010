package christmas.domain.constants;

public class ChristmasConstraints {
    public static final int PROMOTION_YEAR = 2023;
    public static final int PROMOTION_MONTH = 12;
    public static final int FIRST_DAY = 1;
    public static final int LAST_DAY = 31;
    public static final int MIN_ORDER_MENU_COUNT = 1;
    public static final int MAX_ORDER_MENU_COUNT = 20;
    public static final int MIN_TOTAL_ORDER_MENU_COUNT = 1;
    public static final int MAX_TOTAL_ORDER_MENU_COUNT = 20;

    private ChristmasConstraints() {
    }

    public static boolean isPromotionDay(int day) {
        return FIRST_DAY <= day && day <= LAST_DAY;
    }

    public static boolean isWithinOrderMenuCountRange(int count) {
        return MIN_ORDER_MENU_COUNT <= count && count <= MAX_ORDER_MENU_COUNT;
    }

    public static boolean isWithinOrderMenuTotalCountRange(int totalCount) {
        return MIN_TOTAL_ORDER_MENU_COUNT <= totalCount && totalCount <= MAX_TOTAL_ORDER_MENU_COUNT;
    }
}

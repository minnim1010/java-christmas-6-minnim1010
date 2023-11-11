package christmas.domain.reservation;

import static christmas.domain.constants.ChristmasConstraints.isWithinOrderMenuTotalCountRange;
import static christmas.exception.ErrorMessage.INVALID_ORDER;
import static christmas.exception.ErrorMessage.INVALID_ORDER_TOTAL_COUNT_RANGE;
import static christmas.exception.ErrorMessage.ORDERED_ONLY_BEVERAGE;

import christmas.domain.constants.MenuItem;
import java.util.EnumMap;
import java.util.List;

public class OrderMenu {
    private final EnumMap<MenuItem, Integer> items;

    private OrderMenu(List<OrderMenuItem> orderMenuItems) {
        this.items = new EnumMap<>(MenuItem.class);

        orderMenuItems.forEach(item -> items.put(item.getMenuItem(), item.getCount()));
    }

    public static OrderMenu valueOf(List<OrderMenuItem> orderMenuItems) {
        validateTotalOrderMenuCount(orderMenuItems);
        validateUniqueOrderMenuItems(orderMenuItems);
        validateNotOnlyBeverage(orderMenuItems);

        return new OrderMenu(orderMenuItems);
    }

    private static void validateNotOnlyBeverage(List<OrderMenuItem> orderMenuItems) {
        boolean onlyBeverage = orderMenuItems.stream().allMatch(OrderMenuItem::isBeverage);
        if (onlyBeverage) {
            throw new IllegalArgumentException(ORDERED_ONLY_BEVERAGE.getMessage());
        }
    }

    private static void validateUniqueOrderMenuItems(List<OrderMenuItem> orderMenuItems) {
        int uniqueItemsCount = (int) orderMenuItems.stream().distinct().count();
        if (uniqueItemsCount != orderMenuItems.size()) {
            throw new IllegalArgumentException(INVALID_ORDER.getMessage());
        }
    }

    private static void validateTotalOrderMenuCount(List<OrderMenuItem> orderMenuItems) {
        int totalCount = orderMenuItems.stream()
                .map(OrderMenuItem::getCount)
                .mapToInt(Integer::intValue)
                .sum();
        if (!isWithinOrderMenuTotalCountRange(totalCount)) {
            throw new IllegalArgumentException(INVALID_ORDER_TOTAL_COUNT_RANGE.getMessage(totalCount));
        }
    }

    @Override
    public String toString() {
        return "OrderMenu{" +
                "items=" + items +
                '}';
    }
}

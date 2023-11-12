package christmas.domain.menu;

import static christmas.common.exception.ErrorMessage.INVALID_ORDER;
import static christmas.common.exception.ErrorMessage.INVALID_ORDER_TOTAL_COUNT_RANGE;
import static christmas.common.exception.ErrorMessage.ORDERED_ONLY_BEVERAGE;
import static christmas.domain.menu.constants.OrderMenuConstraints.isWithinOrderMenuTotalCountRange;

import christmas.domain.base.Money;
import christmas.domain.menu.constants.MenuCategory;
import christmas.domain.menu.constants.MenuItem;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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

    public Money calculateTotalPrice() {
        int totalPrice = items.entrySet().stream()
                .map(entry -> calculateMenuItemPrice(entry.getKey(), entry.getValue()))
                .map(Money::getValue)
                .mapToInt(Integer::intValue)
                .sum();
        return Money.valueOf(totalPrice);
    }

    private Money calculateMenuItemPrice(MenuItem menuItem, Integer count) {
        return menuItem.getPrice().times(count);
    }

    public int getTotalCountByCategory(MenuCategory category) {
        return items.entrySet().stream()
                .filter(entry -> entry.getKey().getCategory().equals(category))
                .map(Entry::getValue)
                .mapToInt(Integer::intValue)
                .sum();
    }

    public Map<MenuItem, Integer> getItems() {
        return items;
    }

    @Override
    public String toString() {
        return "OrderMenu{" +
                "items=" + items +
                '}';
    }
}

package christmas.domain.menu;

import static christmas.common.exception.ErrorMessage.INVALID_ORDER;
import static christmas.common.exception.ErrorMessage.INVALID_ORDER_TOTAL_COUNT_RANGE;
import static christmas.common.exception.ErrorMessage.ORDERED_ONLY_BEVERAGE;
import static christmas.domain.menu.constants.OrderMenuConstraints.isWithinOrderMenuTotalCountRange;

import christmas.domain.base.Money;
import christmas.domain.menu.constants.Menu;
import christmas.domain.menu.constants.MenuCategory;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class OrderMenu {
    private final EnumMap<Menu, Integer> items;

    private OrderMenu(List<OrderMenuItem> orderMenuItems) {
        this.items = orderMenuItems.stream()
                .collect(Collectors.toMap(
                        OrderMenuItem::getMenuItem,
                        OrderMenuItem::getCount,
                        (existing, replacement) -> existing,
                        () -> new EnumMap<>(Menu.class)));
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

    private Money calculateMenuItemPrice(Menu menu, Integer count) {
        return menu.getPrice().times(count);
    }

    public int getTotalCountByCategory(MenuCategory category) {
        return items.entrySet().stream()
                .filter(entry -> entry.getKey().getCategory().equals(category))
                .map(Entry::getValue)
                .mapToInt(Integer::intValue)
                .sum();
    }

    public Map<Menu, Integer> getItems() {
        return items;
    }

    @Override
    public String toString() {
        return "OrderMenu{" +
                "items=" + items +
                '}';
    }
}

package christmas.domain.constants;

import christmas.domain.base.Money;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public enum MenuItem {
    MUSHROOM_CREAM_SOUP("양송이수프", Money.valueOf(6000), MenuCategory.APPETIZER),
    TAPAS("타파스", Money.valueOf(5500), MenuCategory.APPETIZER),
    CAESAR_SALAD("시저샐러드", Money.valueOf(8000), MenuCategory.APPETIZER),

    T_BONE_STEAK("티본스테이크", Money.valueOf(55000), MenuCategory.MAIN_COURSE),
    BBQ_RIB("바비큐립", Money.valueOf(54000), MenuCategory.MAIN_COURSE),
    SEAFOOD_PASTA("해산물파스타", Money.valueOf(35000), MenuCategory.MAIN_COURSE),
    CHRISTMAS_PASTA("크리스마스파스타", Money.valueOf(25000), MenuCategory.MAIN_COURSE),

    CHOCO_CAKE("초코케이크", Money.valueOf(15000), MenuCategory.DESSERT),
    ICE_CREAM("아이스크림", Money.valueOf(5000), MenuCategory.DESSERT),

    ZERO_COLA("제로콜라", Money.valueOf(3000), MenuCategory.BEVERAGE),
    RED_WINE("레드와인", Money.valueOf(60000), MenuCategory.BEVERAGE),
    CHAMPAGNE("샴페인", Money.valueOf(25000), MenuCategory.BEVERAGE);

    private static final Map<String, MenuItem> nameToMenuItems = Arrays.stream(values())
            .collect(Collectors.toMap(MenuItem::getName, menuItem -> menuItem));
    private final String name;
    private final Money price;
    private final MenuCategory category;

    MenuItem(String name, Money price, MenuCategory category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public static Optional<MenuItem> findByName(String name) {
        return Optional.ofNullable(nameToMenuItems.get(name));
    }

    public boolean isBeverage() {
        return this.category.equals(MenuCategory.BEVERAGE);
    }

    public String getName() {
        return name;
    }

    public Money getPrice() {
        return price;
    }

    public MenuCategory getCategory() {
        return category;
    }
}

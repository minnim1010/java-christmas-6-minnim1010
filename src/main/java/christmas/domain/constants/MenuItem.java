package christmas.domain.constants;

public enum MenuItem {
    MUSHROOM_CREAM_SOUP("양송이수프", 6000, MenuCategory.APPETIZER),
    TAPAS("타파스", 5500, MenuCategory.APPETIZER),
    CAESAR_SALAD("시저샐러드", 8000, MenuCategory.APPETIZER),

    T_BONE_STEAK("티본스테이크", 55000, MenuCategory.MAIN_COURSE),
    BBQ_RIB("바비큐립", 54000, MenuCategory.MAIN_COURSE),
    SEAFOOD_PASTA("해산물파스타", 35000, MenuCategory.MAIN_COURSE),
    CHRISTMAS_PASTA("크리스마스파스타", 25000, MenuCategory.MAIN_COURSE),

    CHOCO_CAKE("초코케이크", 15000, MenuCategory.DESSERT),
    ICE_CREAM("아이스크림", 5000, MenuCategory.DESSERT),

    ZERO_COLA("제로콜라", 3000, MenuCategory.BEVERAGE),
    RED_WINE("레드와인", 60000, MenuCategory.BEVERAGE),
    CHAMPAGNE("샴페인", 25000, MenuCategory.BEVERAGE);

    private final String name;
    private final int price;
    private final MenuCategory category;

    MenuItem(String name, int price, MenuCategory category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public MenuCategory getCategory() {
        return category;
    }
}

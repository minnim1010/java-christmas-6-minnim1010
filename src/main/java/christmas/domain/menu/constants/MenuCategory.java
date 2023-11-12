package christmas.domain.menu.constants;

public enum MenuCategory {
    APPETIZER("에피타이저"),
    MAIN_COURSE("메인"),
    DESSERT("디저트"),
    BEVERAGE("음료");

    private final String categoryName;

    MenuCategory(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }
}

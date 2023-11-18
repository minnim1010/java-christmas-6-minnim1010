package christmas.view.constants;

public enum MessageFormat {
    MENU_ITEM("%s %d개"),
    PRICE("%,d원"),
    PROMOTION_BENEFIT_ITEM("%s: -%,d원"),
    DISCOUNT_PRICE("-%,d원"),
    LINE_SEPARATOR(System.lineSeparator());

    public final String value;

    MessageFormat(String value) {
        this.value = value;
    }
}

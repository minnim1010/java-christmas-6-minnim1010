package christmas.exception;

public enum ErrorMessage {
    INVALID_NUMERIC_INPUT("숫자로만 입력해주세요."),
    INVALID_DELIMITER_INPUT(",로 구분해주세요."),
    INVALID_DATE("유효하지 않은 날짜입니다. 다시 입력해 주세요."),
    ORDERED_ONLY_BEVERAGE("음료만 주문할 수 없습니다. 다시 입력해 주세요."),
    INVALID_ORDER_TOTAL_COUNT_RANGE("총 메뉴 개수는 1개 이상 최대 20개 이하여야 합니다."),
    INVALID_ORDER("유효하지 않은 주문입니다. 다시 입력해 주세요.");

    private static final String PREFIX = "[ERROR] ";
    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return PREFIX + message;
    }

    public String getMessage(String arg1) {
        return getMessage() + ": " + arg1;
    }

    public String getMessage(int arg1) {
        return getMessage() + ": " + arg1;
    }

    public String getMessage(String arg1, String arg2) {
        return getMessage() + ": " + arg1 + ", " + arg2;
    }

    public String getMessage(int arg1, int arg2, int arg3) {
        return getMessage() + ": " + arg1 + ", " + arg2 + ", " + arg3;
    }
}

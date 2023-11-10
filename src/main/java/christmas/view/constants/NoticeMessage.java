package christmas.view.constants;

public enum NoticeMessage {
    GREET_MESSAGE("안녕하세요! 우테코 식당 12월 이벤트 플래너입니다."),
    INPUT_RESERVED_VISIT_DATE_MESSAGE("12월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!)"),
    INPUT_ORDER_MENU_MESSAGE("주문하실 메뉴를 메뉴와 개수를 알려 주세요. (e.g. 해산물파스타-2,레드와인-1,초코케이크-1)"),

    PROMOTION_BENEFIT_PREVIEW_START_MESSAGE("%d월 %d일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!"),
    ORDER_MENU_MESSAGE("<주문 메뉴>"),
    TOTAL_ORDER_PRICE_MESSAGE("<할인 전 총주문 금액>"),
    OUTPUT_GIVEAWAY_MENU_MESSAGE("<증정 메뉴>"),
    BENEFIT_LIST_MESSAGE("<혜택 내역>"),
    BENEFIT_PRICE_MESSAGE("<총혜택 금액>"),
    BENEFIT_APPLIED_PRICE_MESSAGE("<할인 후 예상 결제 금액>"),
    EVENT_BADGE_MESSAGE("<12월 이벤트 배지>"),

    NOT_APPLICABLE("없음");

    public final String value;

    NoticeMessage(String value) {
        this.value = value;
    }
}

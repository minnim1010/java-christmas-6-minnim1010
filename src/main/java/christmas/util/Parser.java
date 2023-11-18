package christmas.util;

import static christmas.common.exception.ErrorMessage.INVALID_DATE;
import static christmas.common.exception.ErrorMessage.INVALID_ORDER;
import static christmas.common.exception.ErrorMessage.INVALID_ORDER_COUNT_RANGE;

import christmas.domain.menu.OrderMenuItem;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    private static final Pattern SPACES_PATTERN = Pattern.compile("\\s+");
    private static final Pattern DELIMITER_PATTERN = Pattern.compile(",");
    private static final Pattern ORDER_MENU_ITEM_INPUT_PATTERN = Pattern.compile("([가-힣a-zA-Z0-9]+)-(\\d+)");
    private static final int ORDER_MENU_ITEM_NAME_GROUP_NUMBER = 1;
    private static final int ORDER_MENU_ITEM_COUNT_GROUP_NUMBER = 2;

    private Parser() {
    }

    public static int parseDate(String input) {
        try {
            String inputWithoutSpaces = removeSpaces(input);
            return Integer.parseInt(inputWithoutSpaces);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(INVALID_DATE.getMessage(input), e);
        }
    }

    private static String removeSpaces(String input) {
        return SPACES_PATTERN.matcher(input).replaceAll("");
    }

    public static OrderMenuItem parseOrderMenuItem(String input) {
        Matcher matcher = ORDER_MENU_ITEM_INPUT_PATTERN.matcher(input);
        if (!matcher.matches()) {
            throw new IllegalArgumentException(INVALID_ORDER.getMessage(input));
        }

        try {
            String menuItemName = matcher.group(ORDER_MENU_ITEM_NAME_GROUP_NUMBER);
            int count = Integer.parseInt(matcher.group(ORDER_MENU_ITEM_COUNT_GROUP_NUMBER));
            return OrderMenuItem.valueOf(menuItemName, count);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(INVALID_ORDER_COUNT_RANGE.getMessage(input));
        }
    }

    public static List<OrderMenuItem> parseOrderMenuItemList(String input) {
        String inputWithoutSpaces = removeSpaces(input);
        String[] split = DELIMITER_PATTERN.split(inputWithoutSpaces);
        return Arrays.stream(split)
                .map(Parser::parseOrderMenuItem)
                .toList();
    }
}

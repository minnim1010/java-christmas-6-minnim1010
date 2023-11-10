package christmas.view;

import static christmas.view.constants.MessageFormat.LINE_SEPARATOR;
import static christmas.view.constants.MessageFormat.MENU_ITEM;
import static christmas.view.constants.NoticeMessage.GREET_MESSAGE;
import static christmas.view.constants.NoticeMessage.INPUT_ORDER_MENU_MESSAGE;
import static christmas.view.constants.NoticeMessage.INPUT_RESERVED_VISIT_DATE_MESSAGE;
import static christmas.view.constants.NoticeMessage.ORDER_MENU_LIST_MESSAGE;
import static christmas.view.constants.NoticeMessage.PROMOTION_BENEFIT_PREVIEW_START_MESSAGE;

import christmas.domain.constants.MenuItem;
import christmas.dto.input.OrderMenuInputDto;
import christmas.dto.input.ReservedVisitDateInputDto;
import christmas.dto.output.OrderMenuOutputDto;
import christmas.dto.output.ReservedVisitDateOutputDto;
import christmas.view.io.reader.Reader;
import christmas.view.io.writer.Writer;
import java.util.EnumMap;
import java.util.stream.Collectors;

public class ChristmasView {
    private final Reader reader;
    private final Writer writer;

    public ChristmasView(Reader reader, Writer writer) {
        this.reader = reader;
        this.writer = writer;
    }

    public void greet() {
        writer.writeLine(GREET_MESSAGE.value);
    }

    public ReservedVisitDateInputDto inputReservedVisitDay() {
        writer.writeLine(INPUT_RESERVED_VISIT_DATE_MESSAGE.value);
        return new ReservedVisitDateInputDto(reader.readLine());
    }

    public OrderMenuInputDto inputOrderMenu() {
        writer.writeLine(INPUT_ORDER_MENU_MESSAGE.value);
        return new OrderMenuInputDto(reader.readLine());
    }

    public void outputPromotionBenefitPreviewStart(ReservedVisitDateOutputDto reservedVisitDateDto) {
        writer.writeLine(String.format(PROMOTION_BENEFIT_PREVIEW_START_MESSAGE.value,
                reservedVisitDateDto.reservedVisitDate().getMonth().getValue(),
                reservedVisitDateDto.reservedVisitDate().getDayOfMonth()));
    }

    public void outputOrderMenu(OrderMenuOutputDto orderMenuOutputDto) {
        EnumMap<MenuItem, Integer> orderMenu = orderMenuOutputDto.orderMenu();
        String orderMenuMessage = orderMenu.entrySet().stream()
                .map(entry ->
                        String.format(MENU_ITEM.value, entry.getKey().getName(), entry.getValue()))
                .collect(Collectors.joining(LINE_SEPARATOR.value));

        String resultMessage = LINE_SEPARATOR.value + String.join(LINE_SEPARATOR.value,
                ORDER_MENU_LIST_MESSAGE.value,
                orderMenuMessage);
        writer.writeLine(resultMessage);
    }
}

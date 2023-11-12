package christmas.dto.output;

import christmas.domain.menu.constants.Menu;
import java.util.EnumMap;

public record OrderMenuOutputDto(EnumMap<Menu, Integer> orderMenu) {
}

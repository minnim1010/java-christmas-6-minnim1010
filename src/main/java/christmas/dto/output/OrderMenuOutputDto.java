package christmas.dto.output;

import christmas.domain.constants.MenuItem;
import java.util.EnumMap;

public record OrderMenuOutputDto(EnumMap<MenuItem, Integer> orderMenu) {
}

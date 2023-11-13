package christmas.dto.output;

import christmas.domain.menu.constants.Menu;
import java.util.Map;

public record GiveawayOutputDto(Map<Menu, Integer> giveaways) {
}

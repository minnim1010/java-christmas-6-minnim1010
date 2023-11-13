package christmas.dto;

import christmas.domain.menu.constants.Menu;
import java.util.Map;

public record GiveawayOutputDto(Map<Menu, Integer> giveaways) {
}

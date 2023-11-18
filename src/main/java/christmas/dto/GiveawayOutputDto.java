package christmas.dto;

import christmas.domain.menu.constants.Menu;
import java.util.EnumMap;

public record GiveawayOutputDto(EnumMap<Menu, Integer> giveaways) {
}

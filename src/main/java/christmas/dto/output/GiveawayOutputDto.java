package christmas.dto.output;

import christmas.domain.menu.constants.Menu;

public record GiveawayOutputDto(Menu giveaway, int count) {
}

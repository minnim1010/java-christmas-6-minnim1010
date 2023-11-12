package christmas.dto.output;

import christmas.domain.menu.constants.MenuItem;

public record GiveawayOutputDto(MenuItem giveaway, int count) {
}

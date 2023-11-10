package christmas.dto.output;

import christmas.domain.constants.MenuItem;

public record GiveawayMenuOutputDto(MenuItem giveaway, int count) {
}

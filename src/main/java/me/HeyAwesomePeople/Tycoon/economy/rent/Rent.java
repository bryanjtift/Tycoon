package me.HeyAwesomePeople.Tycoon.economy.rent;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor public class Rent {

    @Getter private final String id;
    @Getter private final Long frequency;
    @Getter private final Integer payment;

}

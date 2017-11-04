package me.HeyAwesomePeople.Tycoon.world.plots;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class Rent {

    @Getter private final String name;
    @Getter private final long frequency;
    @Getter private final int cost;

}

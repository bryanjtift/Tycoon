package me.HeyAwesomePeople.Tycoon.utils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ZoneVector {
    private final int x;
    private final int y;
    private final int z;

    public boolean isInAABB(ZoneVector min, ZoneVector max) {
        return ((this.x <= max.x) && (this.x >= min.x) && (this.z <= max.z) && (this.z >= min.z) && (this.y <= max.y) && (this.y >= min.y));
    }
}
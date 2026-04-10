package net.tutla.gonzagamod.items;

import net.minecraft.component.type.FoodComponent;
import net.minecraft.item.Item;

public class PeriPeriLoadedFries extends Item {
    public PeriPeriLoadedFries(Settings settings) {
        super(settings
                .food(new FoodComponent(8, 7, false))
        );
    }
}

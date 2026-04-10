package net.tutla.gonzagamod.items;

import net.minecraft.component.type.FoodComponent;
import net.minecraft.item.Item;

public class CliftonMilk extends Item {
    public CliftonMilk(Item.Settings settings) {
        super(settings
                .food(
                new FoodComponent.Builder()
                        .nutrition(4)
                        .saturationModifier(0.3f)
                        .alwaysEdible()
                        .build()
                )
        );
    }
}

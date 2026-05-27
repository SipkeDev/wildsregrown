package com.wildsregrown.entities.mob.bandit;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public class Bandit extends Monster {

    public Bandit(EntityType<? extends Monster> entityType, Level world) {
        super(entityType, world);
    }

    @Override
    public void tick() {
        super.tick();
        if(this.level().isClientSide()) {
        }
    }

}

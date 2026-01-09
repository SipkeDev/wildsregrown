package com.wildsregrown.entities.mob.bandit;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.world.World;

public class Bandit extends HostileEntity {

    public Bandit(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void tick() {
        super.tick();
        if(this.getEntityWorld().isClient()) {
        }
    }

}

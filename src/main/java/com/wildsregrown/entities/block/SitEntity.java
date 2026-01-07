package com.wildsregrown.entities.block;

import com.google.common.collect.UnmodifiableIterator;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.Arm;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class SitEntity extends Entity {

    public SitEntity(EntityType<?> type, World world) {
        super(type, world);
        this.noClip = true;
    }

    @Override
    public void tick() {
        if (!this.getEntityWorld().isClient() && !this.hasPassengers()) {
            this.discard();
        }
    }

    @Override
    public Vec3d updatePassengerForDismount(LivingEntity passenger) {
        //return super.updatePassengerForDismount(passenger);
        Vec3d vec3d = getPassengerDismountOffset(this.getWidth(), passenger.getWidth(), this.getYaw() + (passenger.getMainArm() == Arm.RIGHT ? 90.0F : -90.0F));
        Vec3d vec3d2 = this.locateSafeDismountingPos(vec3d, passenger);
        if (vec3d2 != null) {
            return vec3d2;
        } else {
            Vec3d vec3d3 = getPassengerDismountOffset(this.getWidth(), passenger.getWidth(), this.getYaw() + (passenger.getMainArm() == Arm.LEFT ? 90.0F : -90.0F));
            Vec3d vec3d4 = this.locateSafeDismountingPos(vec3d3, passenger);
            return vec3d4 != null ? vec3d4 : this.getEntityPos();
        }
    }

    private Vec3d locateSafeDismountingPos(Vec3d offset, LivingEntity passenger) {

        double x = this.getX() + offset.x;
        double y = this.getBoundingBox().minY;
        double z = this.getZ() + offset.z;
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        UnmodifiableIterator iterator = passenger.getPoses().iterator();

        while(iterator.hasNext()) {
            EntityPose entityPose = (EntityPose) iterator.next();
            mutable.set(x, y, z);
            double g = this.getBoundingBox().maxY + (double)0.75F;

            while(true) {
                double h = this.getEntityWorld().getDismountHeight(mutable);
                if ((double)mutable.getY() + h > g) {
                    break;
                }

                if (Dismounting.canDismountInBlock(h)) {
                    Box box = passenger.getBoundingBox(entityPose);
                    Vec3d vec3d = new Vec3d(x, (double)mutable.getY() + h, z);
                    if (Dismounting.canPlaceEntityAt(this.getEntityWorld(), passenger, box.offset(vec3d))) {
                        passenger.setPose(entityPose);
                        return vec3d;
                    }
                }

                mutable.move(Direction.UP);
                if (!((double)mutable.getY() < g)) {
                    break;
                }
            }
        }

        return null;
    }

    @Override
    public boolean shouldRender(double x, double y, double z) {
        return false;
    }

    @Override
    protected void readCustomData(ReadView view) {

    }

    @Override
    protected void writeCustomData(WriteView view) {

    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
    }

    @Override
    public boolean damage(ServerWorld world, DamageSource source, float amount) {
        return false;
    }

}

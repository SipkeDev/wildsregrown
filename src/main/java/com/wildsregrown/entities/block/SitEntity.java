package com.wildsregrown.entities.block;

import com.google.common.collect.UnmodifiableIterator;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class SitEntity extends Entity {

    public SitEntity(EntityType<?> type, Level world) {
        super(type, world);
        this.noPhysics = true;
    }

    @Override
    public void tick() {
        if (!this.level().isClientSide() && !this.isVehicle()) {
            this.discard();
        }
    }

    @Override
    public Vec3 getDismountLocationForPassenger(LivingEntity passenger) {
        //return super.updatePassengerForDismount(passenger);
        Vec3 vec3d = getCollisionHorizontalEscapeVector(this.getBbWidth(), passenger.getBbWidth(), this.getYRot() + (passenger.getMainArm() == HumanoidArm.RIGHT ? 90.0F : -90.0F));
        Vec3 vec3d2 = this.locateSafeDismountingPos(vec3d, passenger);
        if (vec3d2 != null) {
            return vec3d2;
        } else {
            Vec3 vec3d3 = getCollisionHorizontalEscapeVector(this.getBbWidth(), passenger.getBbWidth(), this.getYRot() + (passenger.getMainArm() == HumanoidArm.LEFT ? 90.0F : -90.0F));
            Vec3 vec3d4 = this.locateSafeDismountingPos(vec3d3, passenger);
            return vec3d4 != null ? vec3d4 : this.position();
        }
    }

    private Vec3 locateSafeDismountingPos(Vec3 offset, LivingEntity passenger) {

        double x = this.getX() + offset.x;
        double y = this.getBoundingBox().minY;
        double z = this.getZ() + offset.z;
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        UnmodifiableIterator<Pose> iterator = passenger.getDismountPoses().iterator();

        while(iterator.hasNext()) {
            Pose entityPose = iterator.next();
            mutable.set(x, y, z);
            double g = this.getBoundingBox().maxY + (double)0.75F;

            while(true) {
                double h = this.level().getBlockFloorHeight(mutable);
                if ((double)mutable.getY() + h > g) {
                    break;
                }

                if (DismountHelper.isBlockFloorValid(h)) {
                    AABB box = passenger.getLocalBoundsForPose(entityPose);
                    Vec3 vec3d = new Vec3(x, (double)mutable.getY() + h, z);
                    if (DismountHelper.canDismountTo(this.level(), passenger, box.move(vec3d))) {
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
    protected void readAdditionalSaveData(ValueInput view) {

    }

    @Override
    protected void addAdditionalSaveData(ValueOutput view) {

    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
    }

    @Override
    public boolean hurtServer(ServerLevel world, DamageSource source, float amount) {
        return false;
    }

}

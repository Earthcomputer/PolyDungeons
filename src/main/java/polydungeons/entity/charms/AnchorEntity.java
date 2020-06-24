package polydungeons.entity.charms;

import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import polydungeons.entity.IServerWorld;
import polydungeons.item.PolyDungeonsItems;

import java.util.Map;
import java.util.UUID;

public class AnchorEntity extends CharmEntity {
    public static final float RADIUS = 100;

    public AnchorEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    @Override
    public boolean hasNoGravity() {
        return true;
    }

    public boolean collides() {
        return true;
    }

    @Override
    public ItemStack getItem() {
        return new ItemStack(PolyDungeonsItems.ANCHOR);
    }

    @Override
    protected Map<UUID, Vec3d> getPositionList(IServerWorld world) {
        return world.polydungeons_getAnchorPositions();
    }
}

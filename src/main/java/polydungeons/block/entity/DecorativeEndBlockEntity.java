package polydungeons.block.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.EndPortalBlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class DecorativeEndBlockEntity extends EndPortalBlockEntity {
    private final Direction[] FACINGS = Direction.values();
    private int cachedCullFaces = 0;
    private boolean hasCachedFaces = false;

    public DecorativeEndBlockEntity() {
        super(PolyDungeonsBlockEntities.END_BLOCK);
    }

    @Environment(EnvType.CLIENT)
    public void updateCullFaces() {
        assert world != null;
        hasCachedFaces = true;
        int mask;
        for (Direction dir : FACINGS) {
            mask = 1 << dir.getId();
            if (Block.shouldDrawSide(getCachedState(), world, getPos(), dir)) {
                cachedCullFaces |= mask;
            } else {
                cachedCullFaces &= ~mask;
            }
        }
    }

    @Environment(EnvType.CLIENT)
    @Override
    public boolean shouldDrawSide(Direction direction) {
        // Cull faces that are not visible
        if (!hasCachedFaces) {
            updateCullFaces();
        }
        return (cachedCullFaces & (1 << direction.getId())) != 0;
    }

    @Environment(EnvType.CLIENT)
    public static void updateCullCache(BlockPos pos, World world) {
        updateCullCacheNeighbor(pos.up(), world);
        updateCullCacheNeighbor(pos.down(), world);
        updateCullCacheNeighbor(pos.north(), world);
        updateCullCacheNeighbor(pos.east(), world);
        updateCullCacheNeighbor(pos.south(), world);
        updateCullCacheNeighbor(pos.west(), world);
    }

    @Environment(EnvType.CLIENT)
    public static void updateCullCacheNeighbor(BlockPos pos, World world) {
        BlockEntity be = world.getBlockEntity(pos);
        if (be instanceof DecorativeEndBlockEntity) {
            ((DecorativeEndBlockEntity) be).updateCullFaces();
        }
    }
}

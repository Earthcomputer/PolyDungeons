package polydungeons.item.charms;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import polydungeons.entity.PolyDungeonsEntities;
import polydungeons.entity.charms.AnchorEntity;
import polydungeons.entity.charms.SubstituteEntity;

public class SubstituteCharmItem extends CharmItem {
    @Override
    boolean createEntity(PlayerEntity creator, Vec3d pos) {
        SubstituteEntity newEntity = new SubstituteEntity(PolyDungeonsEntities.SUBSTITUTE, creator.getEntityWorld());
        newEntity.updatePosition(pos.x, pos.y + 1.5, pos.z);
        creator.getEntityWorld().spawnEntity(newEntity);
        return true;
    }
}

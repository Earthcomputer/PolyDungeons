package polydungeons.item.charms;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import polydungeons.entity.PolyDungeonsEntities;
import polydungeons.entity.charms.AnchorEntity;

public class AnchorCharmItem extends CharmItem {
    @Override
    boolean createEntity(PlayerEntity creator, Vec3d pos) {
        AnchorEntity newEntity = new AnchorEntity(PolyDungeonsEntities.ANCHOR, creator.getEntityWorld());
        newEntity.setPos(pos.x, pos.y + 1.5, pos.z);
        creator.getEntityWorld().spawnEntity(newEntity);
        return true;
    }
}

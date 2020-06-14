package polydungeons.client.entity;

import net.minecraft.entity.player.PlayerEntity;

public interface ILivingEntity {

	int getTimesTargeted();
	ILivingEntity setTimesHit(int timesHit);
	ILivingEntity incrementTimesTargeted();

	float getTargetPriority(PlayerEntity player);

	ILivingEntity markDirty();
}

package polydungeons.entity;

public interface IMobEntity {
    default void polydungeons_onControlTick() {
        // for subclasses to override
    }
}

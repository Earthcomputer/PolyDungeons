package polydungeons;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import polydungeons.block.PolyDungeonsBlocks;
import polydungeons.block.entity.PolyDungeonsBlockEntities;
import polydungeons.container.PolyDungeonsContainers;
import polydungeons.entity.PolyDungeonsEntities;
import polydungeons.item.PolyDungeonsItems;
import polydungeons.loot.PolyDungeonsLootTables;
import polydungeons.network.PolyDungeonsServerNetwork;
import polydungeons.sound.PolyDungeonsSoundEvents;
import polydungeons.structures.DungeonData;
import polydungeons.tag.PolyDungeonsBlockTags;
import polydungeons.tag.PolyDungeonsItemTags;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Properties;

public class PolyDungeons implements ModInitializer {
    public static final String MODID = "polydungeons";
    private static final Logger LOGGER = LogManager.getLogger(MODID);

    public static boolean ignoreDoMobSpawning;

    @Override
    public void onInitialize() {
        PolyDungeonsBlocks.registerAll();
        PolyDungeonsBlockEntities.registerAll();
        PolyDungeonsItems.registerAll();
        PolyDungeonsEntities.registerAll();
        PolyDungeonsSoundEvents.registerAll();
        PolyDungeonsContainers.registerAll();
        PolyDungeonsServerNetwork.registerPackets();

        PolyDungeonsLootTables.registerAll();

        DungeonData.init();

        PolyDungeonsBlockTags.register();
        PolyDungeonsItemTags.register();

        File configFile = new File(FabricLoader.getInstance().getConfigDirectory(), MODID + "/" + MODID + ".properties");
        Properties config = new Properties();
        if (!configFile.exists()) {
            config.setProperty("ignoreDoMobSpawning", "false");
            configFile.getParentFile().mkdirs();
            try (BufferedWriter writer = Files.newBufferedWriter(configFile.toPath(), StandardOpenOption.CREATE)) {
                config.store(writer, null);
                writer.flush();
            } catch (IOException e) {
                LOGGER.error("Failed to write default " + MODID + " config", e);
            }
        } else {
            try (BufferedReader reader = Files.newBufferedReader(configFile.toPath())) {
                config.load(reader);
            } catch (IOException e) {
                LOGGER.error("Failed to read " + MODID + " config", e);
            }
        }

        ignoreDoMobSpawning = Boolean.parseBoolean(config.getProperty("ignoreDoMobSpawning", "false"));
    }
}

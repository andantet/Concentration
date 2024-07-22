package net.deechael.concentration.fabric;

import net.deechael.concentration.Concentration;
import net.deechael.concentration.fabric.compat.EmbeddiumCompat;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;

/**
 * Main class for fabric version of Concentration
 *
 * @author DeeChael
 */
public class ConcentrationFabric implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        Concentration.init();

        if (FabricLoader.getInstance().isModLoaded("embeddium")) {
            EmbeddiumCompat.init();
        }
    }

}

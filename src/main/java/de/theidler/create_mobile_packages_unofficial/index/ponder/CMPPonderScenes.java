package de.theidler.create_mobile_packages_unofficial.index.ponder;

import com.tterrag.registrate.util.entry.ItemProviderEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import de.theidler.create_mobile_packages_unofficial.index.CMPBlocks;
import de.theidler.create_mobile_packages_unofficial.index.ponder.scenes.DronePortScenes;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.minecraft.resources.ResourceLocation;

public class CMPPonderScenes {
    public static void register(PonderSceneRegistrationHelper<ResourceLocation> helper) {
        PonderSceneRegistrationHelper<ItemProviderEntry<?>> HELPER = helper.withKeyFunction(RegistryEntry::getId);
        HELPER.forComponents(CMPBlocks.DRONE_PORT)
                .addStoryBoard("drone_port/main", DronePortScenes::dronePortScene)
                .addStoryBoard("drone_port/player", DronePortScenes::dronePortScenePlayer);
    }
}

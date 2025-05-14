package de.theidler.create_mobile_packages_unofficial.events; // Replace with your mod's package

import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.server.level.ServerPlayer; // Import ServerPlayer
import net.minecraft.core.BlockPos; // Import BlockPos
import net.minecraft.network.chat.Component; // Import Component

// Import relevant Create classes
import com.simibubi.create.content.logistics.stockTicker.StockTickerInteractionHandler;
import com.simibubi.create.content.logistics.tableCloth.ShoppingListItem;

// Import CreateLang for translatable messages
import com.simibubi.create.foundation.utility.CreateLang;


// Replace "yourmodid" with your mod's ID
// Ensure this class is registered with the Forge event bus in your main mod class or elsewhere.
// Example: MinecraftForge.EVENT_BUS.register(new YourModStockTickerIntegration());
@Mod.EventBusSubscriber(modid = "create_mobile_packages_unofficial", bus = Mod.EventBusSubscriber.Bus.FORGE) // Use your actual modid
public class StockTickerIntegration {

    // Server-side map to store the last player who interacted with a shopkeeper.
    // Key: UUID of the player
    // Value: The Player entity (using WeakHashMap to avoid memory leaks if player logs out)
    // This map is less critical in this approach since we modify the item directly,
    // but keeping it might be useful for other related logic.
    private static final Map<UUID, Player> lastShopkeeperInteractors = new WeakHashMap<>();

    // Subscribe to the Right Click Entity event
    // We set a very high priority to ensure this runs before Create's handler.
    @SubscribeEvent(priority = net.minecraftforge.eventbus.api.EventPriority.HIGHEST) // Use HIGHEST priority
    public static void onRightClickEntity(PlayerInteractEvent.EntityInteractSpecific event) {
        // Ensure this runs only on the server side
        if (event.getLevel().isClientSide()) {
            return;
        }

        Entity target = event.getTarget();
        Player player = event.getEntity();
        InteractionHand hand = event.getHand();
        ItemStack heldItem = player.getItemInHand(hand);

        if (player == null || target == null || player.isSpectator() || hand != InteractionHand.MAIN_HAND) {
            return; // Basic checks and only process main hand
        }

        // --- Check if the interacted entity is a Create Stock Keeper entity ---
        // We use Create's own method to determine if the entity is a Stock Keeper.
        BlockPos stockTickerPos = StockTickerInteractionHandler.getStockTickerPosition(target);

        // Check if the entity is recognized as a Stock Keeper AND the player is holding a Shopping List
        if (stockTickerPos != null && heldItem.getItem() instanceof ShoppingListItem) {

            // Get the current address from the Shopping List item
            String currentAddress = ShoppingListItem.getAddress(heldItem);

            // Check if the current address is our special "@player" string (case-insensitive, trimmed)
            if ("@player".equalsIgnoreCase(currentAddress.trim())) {
                // If it is, replace the address on the item with the player's DISPLAY NAME
                String playerIdentifier = player.getDisplayName().getString(); // Use player's display name

                // Modify the NBT of the Shopping List item in the player's hand
                // ShoppingListItem.getList(heldItem) gets the current ShoppingList data
                // ShoppingListItem.saveList(itemStack, shoppingList, address) saves the list data and sets the address
                ShoppingListItem.saveList(heldItem, ShoppingListItem.getList(heldItem), playerIdentifier);

                // Optional: Add a log message for debugging
                System.out.println("YOURMODID: Replaced @player address on Shopping List with player name: " + playerIdentifier + " for player " + player.getDisplayName().getString());

                // We do NOT cancel the event here. We want Create's
                // StockTickerInteractionHandler to continue and read the MODIFIED address
                // from the Shopping List item.
            }
        }
    }

    // The methods to get/clear lastShopkeeperInteractor are less critical in this approach
    // but can be kept if needed for other logic.
    public static ServerPlayer getLastShopkeeperInteractor(UUID playerUUID) {
        Player player = lastShopkeeperInteractors.get(playerUUID);
        if (player instanceof ServerPlayer serverPlayer && serverPlayer.isAlive() && !serverPlayer.getServer().getPlayerList().getPlayer(playerUUID).isRemoved()) {
            return serverPlayer;
        }
        lastShopkeeperInteractors.remove(playerUUID);
        return null;
    }

    public static void clearLastShopkeeperInteractor(UUID playerUUID) {
        lastShopkeeperInteractors.remove(playerUUID);
        System.out.println("YOURMODID: Cleared last shopkeeper interactor for UUID: " + playerUUID); // Log for debugging
    }
}

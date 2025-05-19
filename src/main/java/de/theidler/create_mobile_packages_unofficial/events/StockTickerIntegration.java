package de.theidler.create_mobile_packages_unofficial.events;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.core.BlockPos;
import com.simibubi.create.content.logistics.stockTicker.StockTickerInteractionHandler;
import com.simibubi.create.content.logistics.tableCloth.ShoppingListItem;


@Mod.EventBusSubscriber(modid = "create_mobile_packages_unofficial", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class StockTickerIntegration {

    @SubscribeEvent(priority = net.minecraftforge.eventbus.api.EventPriority.HIGHEST)
    public static void onRightClickEntity(PlayerInteractEvent.EntityInteractSpecific event) {
        if (event.getLevel().isClientSide()) {
            return;
        }

        Entity target = event.getTarget();
        Player player = event.getEntity();
        InteractionHand hand = event.getHand();
        ItemStack heldItem = player.getItemInHand(hand);

        if (player == null || target == null || player.isSpectator() || hand != InteractionHand.MAIN_HAND) {
            return;
        }

        BlockPos stockTickerPos = StockTickerInteractionHandler.getStockTickerPosition(target);

        if (stockTickerPos != null && heldItem.getItem() instanceof ShoppingListItem) {

            String currentAddress = ShoppingListItem.getAddress(heldItem);

            if (currentAddress.toLowerCase().contains("@player")) {
                String playerIdentifier = player.getDisplayName().getString();
                String newAddress = currentAddress.replaceAll("(?i)@player", "@" + playerIdentifier);

                // Modify the NBT of the Shopping List item in the player's hand
                // ShoppingListItem.getList(heldItem) gets the current ShoppingList data
                // ShoppingListItem.saveList(itemStack, shoppingList, address) saves the list data and sets the address
                ShoppingListItem.saveList(heldItem, ShoppingListItem.getList(heldItem), newAddress);

            }
        }
    }
}

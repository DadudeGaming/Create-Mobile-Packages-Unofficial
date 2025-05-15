package de.theidler.create_mobile_packages_unofficial.index.ponder.scenes;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.content.logistics.BigItemStack;
import com.simibubi.create.content.logistics.box.PackageItem;
import com.simibubi.create.content.logistics.box.PackageStyles;
import com.simibubi.create.content.logistics.redstoneRequester.AutoRequestData;
import com.simibubi.create.content.logistics.stockTicker.PackageOrderWithCrafts;
import com.simibubi.create.content.logistics.tableCloth.TableClothBlock;
import com.simibubi.create.content.logistics.tableCloth.TableClothBlockEntity;
import com.simibubi.create.foundation.ponder.CreateSceneBuilder;
import com.simibubi.create.infrastructure.ponder.scenes.highLogistics.PonderHilo;
import net.createmod.catnip.math.Pointing;
import net.createmod.ponder.api.PonderPalette;
import net.createmod.ponder.api.element.ParrotPose;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.createmod.ponder.api.scene.Selection;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import de.theidler.create_mobile_packages_unofficial.index.CMPBlocks;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;


public class DronePortScenes {
    public static void dronePortScene(SceneBuilder builder, SceneBuildingUtil util) {
        CreateSceneBuilder scene = new CreateSceneBuilder(builder);
        scene.title("drone_port", "Using Drone Ports");
        scene.configureBasePlate(0, 0, 7);
        scene.showBasePlate();

        scene.world().showSection(util.select().everywhere(), Direction.UP);

        BlockPos dronePort = util.grid().at(3, 2, 0);
        BlockPos packager = util.grid()
                .at(3, 2, 5);
        Selection belt = util.select().fromTo(3, 1, 0, 3, 1, 7);

        scene.world().setBlock(dronePort, CMPBlocks.DRONE_PORT.getDefaultState(), false);


        // Belt
        scene.world().setKineticSpeed(belt, -24);

        scene.overlay()
                .showText(90)
                .text("The Drone Port can send a Package to a Player")
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(util.vector()
                        .blockSurface(dronePort, Direction.SOUTH));

        scene.idle(100);

        // Package
        ItemStack box = PackageStyles.getDefaultBox()
                .copy();
        PackageItem.addAddress(box, "Dev");
        PonderHilo.packagerCreate(scene, packager, box);
        scene.idle(30);

        scene.overlay()
                .showText(90)
                .text("Set the Package address to the Player name")
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(util.vector()
                        .blockSurface(packager, Direction.SOUTH));
        scene.idle(100);

        scene.world()
                .createItemOnBelt(util.grid()
                        .at(3, 1, 4), Direction.SOUTH, box);
        PonderHilo.packagerClear(scene, packager);

        scene.rotateCameraY(-90);

        scene.idle(70);

        scene.world()
                .removeItemsFromBelt(util.grid()
                        .at(3, 1, 1));
        scene.world()
                .flapFunnel(util.grid()
                        .at(3, 2, 1), false);

        scene.overlay()
                .showText(90)
                .text("The Package will now be send to the Player")
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(util.vector()
                        .blockSurface(dronePort, Direction.SOUTH));
        scene.idle(100);

    }

    public static void dronePortScenePlayer(SceneBuilder builder, SceneBuildingUtil util) {
        CreateSceneBuilder scene = new CreateSceneBuilder(builder);
        scene.title("drone_port_player", "Shop Drone Delivery");
        scene.configureBasePlate(0, 0, 7);
        scene.scaleSceneView(0.925f);
        scene.setSceneOffsetY(-0.5f);
        scene.showBasePlate();

        Selection scaff1 = util.select()
                .position(5, 1, 1);
        Selection cloth1 = util.select()
                .position(5, 2, 1);
        Selection vault = util.select()
                .fromTo(4, 1, 4, 3, 3, 5);
        Selection packScaff = util.select()
                .position(2, 1, 4);
        BlockPos pack = util.grid()
                .at(2, 2, 4);
        Selection packager = util.select()
                .position(2, 2, 4);
        BlockPos link = util.grid()
                .at(2, 3, 4);
        Selection linkS = util.select()
                .position(2, 3, 4);
        Selection funnel = util.select()
                .position(1, 2, 4);
        Selection belt = util.select()
                .fromTo(1, 1, 5, 1, 1, 1);
        Selection largeCog = util.select()
                .position(2, 0, 7);
        Selection cogs = util.select()
                .fromTo(2, 1, 5, 2, 1, 6);
        Selection ticker = util.select()
                .position(3, 1, 1);
        Selection seat = util.select()
                .position(3, 1, 2);
        Selection funnel2 = util.select()
                .position(1, 2, 2);
        Selection droneport = util.select()
                .position(1, 2, 1);

        scene.idle(10);

        scene.world()
                .showSection(seat, Direction.DOWN);
        scene.idle(2);
        scene.special()
                .createBirb(util.vector()
                                .centerOf(util.grid()
                                        .at(3, 1, 2)),
                        ParrotPose.FacePointOfInterestPose::new);
        scene.idle(15);

        ItemStack listItem = AllItems.SHOPPING_LIST.asStack();
        scene.overlay()
                .showControls(util.vector()
                        .of(3, 3, 1.75), Pointing.DOWN, 90)
                .rightClick()
                .withItem(listItem);
        scene.idle(5);
        scene.effects()
                .indicateSuccess(util.grid()
                        .at(3, 1, 1));
        scene.idle(30);

        scene.overlay()
                .showText(90)
                .text("A player who purchases a package can be automatically assigned as the package address")
                .attachKeyFrame()
                .pointAt(util.vector()
                        .of(2.5, 2.75, 1.5))
                .placeNearTarget();
        scene.idle(100);

        scene.world()
                .showSection(vault, Direction.NORTH);
        scene.world()
                .showSection(packScaff, Direction.NORTH);
        scene.idle(10);
        scene.world()
                .showSection(packager, Direction.EAST);
        scene.idle(10);
        scene.world()
                .showSection(linkS, Direction.DOWN);

        scene.overlay()
                .showOutlineWithText(util.select()
                        .fromTo(3, 2, 4, 4, 3, 5), 100)
                .text("Start with the shop's inventory, a Packager and Stock link")
                .attachKeyFrame()
                .colored(PonderPalette.BLUE)
                .pointAt(util.vector()
                        .of(2, 3, 4))
                .placeNearTarget();
        scene.idle(110);

        ItemStack tickerItem = AllBlocks.STOCK_TICKER.asStack();
        scene.overlay()
                .showControls(util.vector()
                        .centerOf(2, 3, 4), Pointing.DOWN, 80)
                .rightClick()
                .withItem(tickerItem);
        scene.idle(5);

        AABB bb1 = new AABB(link);
        scene.overlay()
                .chaseBoundingBoxOutline(PonderPalette.BLUE, link, bb1.deflate(0.45), 10);
        scene.idle(1);
        bb1 = bb1.deflate(1 / 16f)
                .contract(0, 8 / 16f, 0);
        scene.overlay()
                .chaseBoundingBoxOutline(PonderPalette.BLUE, link, bb1, 50);
        scene.idle(26);

        scene.overlay()
                .showText(80)
                .text("Bind a Stock ticker to the link and place it in the shop")
                .attachKeyFrame()
                .colored(PonderPalette.BLUE)
                .pointAt(util.vector()
                        .centerOf(link))
                .placeNearTarget();
        scene.idle(70);

        scene.world()
                .showSection(ticker, Direction.DOWN);
        scene.idle(10);

        scene.overlay()
                .showText(90)
                .text("Employ a mob or blaze burner as the shop keeper")
                .attachKeyFrame()
                .pointAt(util.vector()
                        .of(2.5, 2.75, 1.5))
                .placeNearTarget();
        scene.idle(100);

        ItemStack logItem1 = new ItemStack(Items.OAK_LOG);
        scene.overlay()
                .showControls(util.vector()
                        .of(5, 3.5, 4), Pointing.RIGHT, 80)
                .withItem(logItem1);
        scene.idle(10);

        scene.overlay()
                .showText(70)
                .text("Fill the shop inventory with items to be sold")
                .attachKeyFrame()
                .pointAt(util.vector()
                        .of(4, 3, 4))
                .placeNearTarget();
        scene.idle(80);

        ItemStack clothItem = AllBlocks.TABLE_CLOTHS.get(DyeColor.LIGHT_GRAY)
                .asStack();
        scene.overlay()
                .showControls(util.vector()
                        .of(3, 3, 1.75), Pointing.DOWN, 120)
                .rightClick()
                .withItem(clothItem);
        scene.idle(30);

        scene.overlay()
                .showText(100)
                .text("To create a new trade, interact with the shop keeper while holding a table cloth")
                .attachKeyFrame()
                .pointAt(util.vector()
                        .of(2.5, 2.75, 1.5))
                .placeNearTarget();
        scene.idle(110);

        scene.overlay()
                .showText(80)
                .text("In the Package Address box, type @player")
                .attachKeyFrame()
                .colored(PonderPalette.GREEN)
                .pointAt(util.vector()
                        .of(2.5, 2.75, 1.5))
                .placeNearTarget();
        scene.idle(90);

        scene.world()
                .cycleBlockProperty(util.grid()
                        .at(5, 2, 1), TableClothBlock.HAS_BE);
        scene.world()
                .modifyBlockEntity(util.grid()
                        .at(5, 2, 1), TableClothBlockEntity.class, be -> {
                    AutoRequestData d = new AutoRequestData();
                    d.encodedRequest = PackageOrderWithCrafts.simple(List.of(new BigItemStack(logItem1)));
                    d.isValid = true;
                    be.requestData = d;
                    be.facing = Direction.NORTH;
                });

        scene.world()
                .showSection(scaff1, Direction.DOWN);
        scene.idle(10);
        scene.world()
                .showSection(cloth1, Direction.DOWN);
        scene.idle(20);

        ItemStack diamondItem = new ItemStack(Items.EMERALD);
        Vec3 filterSlot = util.vector()
                .of(5.25, 1.825, 1);
        scene.overlay()
                .showControls(filterSlot, Pointing.DOWN, 120)
                .rightClick()
                .withItem(diamondItem);
        scene.idle(5);
        scene.world()
                .setFilterData(util.select()
                        .position(5, 2, 1), TableClothBlockEntity.class, diamondItem);
        scene.idle(15);

        scene.overlay()
                .showText(90)
                .text("Once placed, set a price in the item slot on the side")
                .attachKeyFrame()
                .pointAt(filterSlot)
                .placeNearTarget();
        scene.idle(120);

        scene.overlay()
                .showControls(util.vector()
                                .centerOf(util.grid()
                                        .at(5, 2, 1)),
                        Pointing.DOWN, 90)
                .rightClick();
        scene.idle(10);

        scene.overlay()
                .showText(90)
                .text("Other players can now interact with the shop")
                .attachKeyFrame()
                .pointAt(util.vector()
                        .of(5, 2, 1.5))
                .placeNearTarget();
        scene.idle(100);

        scene.overlay()
                .showControls(util.vector()
                        .of(3, 3, 1.75), Pointing.DOWN, 90)
                .rightClick()
                .withItem(listItem);
        scene.idle(5);
        scene.effects()
                .indicateSuccess(util.grid()
                        .at(3, 1, 1));
        PonderHilo.linkEffect(scene, link);
        ItemStack box = PackageItem.containing(List.of());
        PonderHilo.packagerCreate(scene, pack, box);
        scene.idle(30);

        scene.overlay()
                .showText(120)
                .text("When checking out at the cashier, the bought items will be placed into a package with their username as the address")
                .attachKeyFrame()
                .pointAt(util.vector()
                        .of(2.5, 2.5, 4))
                .placeNearTarget();
        scene.idle(100);

        scene.world()
                .showSection(largeCog, Direction.UP);
        scene.idle(3);
        scene.world()
                .showSection(cogs, Direction.DOWN);
        scene.idle(3);
        scene.world()
                .showSection(belt, Direction.EAST);
        scene.idle(5);
        scene.world()
                .showSection(funnel, Direction.DOWN);
        scene.idle(15);
        scene.world()
                .createItemOnBelt(util.grid()
                        .at(1, 1, 4), Direction.EAST, box);
        PonderHilo.packagerClear(scene, pack);

        scene.rotateCameraY(-90);
        scene.world()
                .showSection(funnel2, Direction.DOWN);
        scene.world()
                .showSection(droneport, Direction.EAST);

        scene.idle(28);

        scene.world()
                .removeItemsFromBelt(util.grid()
                        .at(1, 1, 2));
        scene.world()
                .flapFunnel(util.grid()
                        .at(1, 2, 2), false);

        scene.idle(45);

        scene.overlay()
                .showText(90)
                .text("From there, they can be transported to a drone port, and sent to the player")
                .attachKeyFrame()
                .pointAt(util.vector()
                        .of(1, 2.5, 1.5))
                .placeNearTarget();
        scene.idle(100);

    }
}

package de.theidler.create_mobile_packages_unofficial.entities.robo_entity.states;

import de.theidler.create_mobile_packages_unofficial.blocks.drone_port.DronePortBlockEntity;
import de.theidler.create_mobile_packages_unofficial.entities.robo_entity.RoboEntity;
import de.theidler.create_mobile_packages_unofficial.entities.robo_entity.RoboEntityState;

public class LaunchPrepareState implements RoboEntityState {
    @Override
    public void tick(RoboEntity re) {
        DronePortBlockEntity.setOpen(re.getStartDronePortBlockEntity(),true);
        re.setState(new LaunchAscendState());
    }
}

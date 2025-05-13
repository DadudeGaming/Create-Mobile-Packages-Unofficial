package de.theidler.create_mobile_packages_unofficial.blocks.drone_port;

import java.util.List;

public interface IDronePortEntityTracker {
    void add(DronePortBlockEntity dpbe);
    void remove(DronePortBlockEntity dpbe);
    List<DronePortBlockEntity> getAll();
}


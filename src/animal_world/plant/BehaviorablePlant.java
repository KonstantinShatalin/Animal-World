package animal_world.plant;

import java.util.List;

public interface BehaviorablePlant{
    void act(List<PlantAbstract> newPlants);
    void incrementAge();
    void giveBirth(List<PlantAbstract> newPlants);
    int breed();
    boolean canBreed();
}

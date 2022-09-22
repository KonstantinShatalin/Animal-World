package plant;
import animal.Location;

import java.util.List;

public interface BehaviorablePlant{
    void act(List<Plant> newPlants);
    void incrementAge();
    void giveBirth(List<Plant> newPlants);
    int breed();
    boolean canBreed();
}

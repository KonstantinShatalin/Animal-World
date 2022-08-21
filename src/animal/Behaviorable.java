package animal;

import java.util.List;

public interface Behaviorable {
    void act(List<Animal> newHerbivorous);
    void incrementAge();
    void incrementHunger();
    Location findFood();
    void giveBirth(List<Animal> newHerbivorous);
    int breed();
    boolean canBreed();
}

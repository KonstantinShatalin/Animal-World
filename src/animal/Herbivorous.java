package animal;

import java.util.List;

public abstract class Herbivorous extends Animal {

    public Herbivorous(Field field, Location location) {
        super(field, location);
    }

    public abstract void act(List<Animal> newHerbivorous);
    public abstract void incrementAge();
    public abstract void incrementHunger();
    public abstract Location findFood();
    public abstract void giveBirth(List<Animal> newHerbivorous);
    public abstract int breed();
    public abstract boolean canBreed();
    }



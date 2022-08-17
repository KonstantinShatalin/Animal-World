package animal.predator;

import animal.*;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Wolf extends Predator {
    private static final int BREEDING_AGE = 25;
    private static final int MAX_AGE = 180;
    private static final double BREEDING_PROBABILITY = 0.09;
    private static final int MAX_LITTER_SIZE = 4;
    private static final int HERBIVOROUS_FOOD_VALUE = 7;
    private static final Random rand = RandomNumbers.getRandom();

    private int age;
    private int foodLevel;

    public Wolf(boolean randomAge, Field field, Location location) {
        super(field, location);
        if(randomAge) {
            age = rand.nextInt(MAX_AGE);
            foodLevel = rand.nextInt(HERBIVOROUS_FOOD_VALUE);
        }
        else {
            age = 0;
            foodLevel = HERBIVOROUS_FOOD_VALUE;
        }
    }

    public Wolf(Field field, Location location) {
        super(field, location);
    }
    @Override
    public void act(List<Animal> newPredator) {
        incrementAge();
        incrementHunger();
        if(isAlive()) {
            giveBirth(newPredator);
            Location newLocation = findFood();
            if(newLocation == null) {
                newLocation = getField().freeAdjacentLocation(getLocation());
            }
            if(newLocation != null) {
                setLocation(newLocation);
            }
            else {
                setDead();
            }
        }

    }

    @Override
    public void incrementAge() {
        age++;
        if(age > MAX_AGE) {
            setDead();
        }
    }

    @Override
    public void incrementHunger() {
        foodLevel--;
        if(foodLevel <= 0) {
            setDead();
        }
    }

    @Override
    public Location findFood() {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object animal = field.getObjectAt(where);
            if(animal instanceof Herbivorous) {
                Herbivorous herbivorous = (Herbivorous) animal;
                if(herbivorous.isAlive()) {
                    herbivorous.setDead();
                    foodLevel = HERBIVOROUS_FOOD_VALUE;
                    return where;
                }
            }
        }
        return null;
    }

    @Override
    public void giveBirth(List<Animal> newPredator) {
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && !free.isEmpty(); b++) {
            Location loc = free.remove(0);
            Wolf young = new Wolf(false, field, loc);
            newPredator.add(young);
        }

    }

    @Override
    public int breed() {
        int births = 0;
        if(canBreed() && rand.nextDouble() <= BREEDING_PROBABILITY) {
            births = rand.nextInt(MAX_LITTER_SIZE) + 1;
        }
        return births;
    }

    @Override
    public boolean canBreed() {
        return age >= BREEDING_AGE;
    }

}

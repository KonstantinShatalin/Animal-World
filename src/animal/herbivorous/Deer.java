package animal.herbivorous;

import animal.*;
import plant.Plant;

import java.util.List;
import java.util.Random;

public class Deer extends Animal implements Behaviorable
{
    private static final int BREEDING_AGE = 10;
    private static final int MAX_AGE = 45;
    private static final double BREEDING_PROBABILITY = 0.14;
    private static final int MAX_LITTER_SIZE = 5;
    private static final int PLANTS_FOOD_VALUE = 3;
    private static final Random rand = RandomNumbers.getRandom();

    private int age;
    private int foodLevel;

    public Deer(boolean randomAge, Field field, Location location) {
        super(field, location);
        age = 0;
        if(randomAge) {
            age = rand.nextInt(MAX_AGE);
            foodLevel = rand.nextInt(PLANTS_FOOD_VALUE);
        }else{
            age = 0;
            foodLevel = PLANTS_FOOD_VALUE;
        }
    }

    @Override
    public void act(List<Animal> newHerbivorous) {
        incrementAge();
        incrementHunger();
        if(isAlive()) {
            giveBirth(newHerbivorous);
            Location newLocation = findFood();
            if (newLocation == null) {
                newLocation = getField().freeAdjacentLocation(getLocation());
            }if (newLocation != null){
                setLocation(newLocation);
            }else {
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
        for (Location where : adjacent) {
            Object animal = field.getObjectAt(where);
            if (animal instanceof Plant plant) {
                if (plant.isAlive()) {
                    plant.setDead();
                    foodLevel = PLANTS_FOOD_VALUE;
                    return where;
                }
            }
        }
        return null;
    }

    @Override
    public void giveBirth(List<Animal> newHerbivorous) {
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && !free.isEmpty(); b++) {
            Location loc = free.remove(0);
            Deer young = new Deer(false, field, loc);
            newHerbivorous.add(young);
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

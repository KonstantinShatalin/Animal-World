package animal.herbivorous;

import animal.*;
import java.util.List;
import java.util.Random;

public class Duck extends Animal implements Runnable,Behaviorable{
    private List<Animal> newHerbivorous;

    public Duck(Field field, Location location, List<Animal> newHerbivorous) {
        super(field, location);
        this.newHerbivorous = newHerbivorous;
    }
    private static final int BREEDING_AGE = 7;
    private static final int MAX_AGE = 30;
    private static final double BREEDING_PROBABILITY = 0.18;
    private static final int MAX_LITTER_SIZE = 5;
    private static final int PLANTS_FOOD_VALUE = 2;
    private static final Random rand = RandomNumbers.getRandom();

    private int age;
    private int foodLevel;

    public Duck(boolean randomAge, Field field, Location location) {
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
    public synchronized Location findFood() {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        for (Location where : adjacent) {
            Object animal = field.getObjectAt(where);
            if (animal instanceof Plants plants) {
                if (plants.isAlive()) {
                    plants.setDead();
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
            Duck young = new Duck(false, field, loc);
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

    @Override
    public void run() {
        System.out.println("Thread Duck run!");
        act(newHerbivorous);
        incrementAge();
        incrementHunger();
        findFood();
        giveBirth(newHerbivorous);
        breed();
        canBreed();
    }
}

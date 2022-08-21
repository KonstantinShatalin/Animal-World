package animal.predator;

import animal.*;
import animal.herbivorous.*;

import java.util.List;
import java.util.Random;

public class Wolf extends Animal implements Runnable,Behaviorable{
    private List<Animal> newPredator;

    public Wolf(Field field, Location location, List<Animal> newPredator) {
        super(field, location);
        this.newPredator = newPredator;
    }
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
    public synchronized Location findFood() {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        for (Location where : adjacent) {
            Object animal = field.getObjectAt(where);
            if (animal instanceof Boar boar) {
                if (boar.isAlive()) {
                    boar.setDead();
                    foodLevel = HERBIVOROUS_FOOD_VALUE;
                    return where;
                }
            }if (animal instanceof Buffalo buffalo) {
                if (buffalo.isAlive()) {
                    buffalo.setDead();
                    foodLevel = HERBIVOROUS_FOOD_VALUE;
                    return where;
                }
            }if (animal instanceof Caterpillar caterpillar) {
                if (caterpillar.isAlive()) {
                    caterpillar.setDead();
                    foodLevel = HERBIVOROUS_FOOD_VALUE;
                    return where;
                }
            }if (animal instanceof Deer deer) {
                if (deer.isAlive()) {
                    deer.setDead();
                    foodLevel = HERBIVOROUS_FOOD_VALUE;
                    return where;
                }
            }if (animal instanceof Duck duck) {
                if (duck.isAlive()) {
                    duck.setDead();
                    foodLevel = HERBIVOROUS_FOOD_VALUE;
                    return where;
                }
            }if (animal instanceof Goat goat) {
                if (goat.isAlive()) {
                    goat.setDead();
                    foodLevel = HERBIVOROUS_FOOD_VALUE;
                    return where;
                }
            }if (animal instanceof Horse horse) {
                if (horse.isAlive()) {
                    horse.setDead();
                    foodLevel = HERBIVOROUS_FOOD_VALUE;
                    return where;
                }
            }if (animal instanceof Mouse mouse) {
                if (mouse.isAlive()) {
                    mouse.setDead();
                    foodLevel = HERBIVOROUS_FOOD_VALUE;
                    return where;
                }
            }if (animal instanceof Rabbit rabbit) {
                if (rabbit.isAlive()) {
                    rabbit.setDead();
                    foodLevel = HERBIVOROUS_FOOD_VALUE;
                    return where;
                }
            }if (animal instanceof Sheep sheep) {
                if (sheep.isAlive()) {
                    sheep.setDead();
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

    @Override
    public void run() {
        System.out.println("Thread Wolf run!");
        act(newPredator);
        incrementAge();
        incrementHunger();
        findFood();
        giveBirth(newPredator);
        breed();
        canBreed();

    }
}

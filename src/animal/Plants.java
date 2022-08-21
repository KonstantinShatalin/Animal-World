package animal;

import java.util.List;
import java.util.Random;

public class Plants extends Animal implements Runnable{
    private List<Animal> newPlants;

    public Plants(Field field, Location location, List<Animal> newPlants) {
        super(field, location);
        this.newPlants = newPlants;
    }

    private static final int BREEDING_AGE = 5;
    private static final int MAX_AGE = 10;
    private static final double BREEDING_PROBABILITY = 0.10;
    private static final int MAX_LITTER_SIZE = 7;
    private static final Random rand = RandomNumbers.getRandom();

    private int age;

    public Plants(boolean randomAge, Field field, Location location)
    {
        super(field, location);
        age = 0;
        if(randomAge) {
            age = rand.nextInt(MAX_AGE);
        }
    }


    public void act(List<Animal> newPlants)
    {
        incrementAge();
        if(isAlive()) {
            giveBirth(newPlants);
        } else {
                setDead();
            }
        }

    private void incrementAge()
    {
        age++;
        if(age > MAX_AGE) {
            setDead();
        }
    }

    private void giveBirth(List<Animal> newPlants)
    {
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && !free.isEmpty(); b++) {
            Location loc = free.remove(0);
            Plants young = new Plants(false, field, loc);
            newPlants.add(young);
        }
    }

    private int breed()
    {
        int births = 0;
        if(canBreed() && rand.nextDouble() <= BREEDING_PROBABILITY) {
            births = rand.nextInt(MAX_LITTER_SIZE) + 1;
        }
        return births;
    }

    private boolean canBreed()
    {
        return age >= BREEDING_AGE;
    }

    @Override
    public void run() {
        System.out.println("Thread Plants run!");
        act(newPlants);
        incrementAge();
        giveBirth(newPlants);
        breed();
        canBreed();
    }
}

package animal_world.plant;

import animal_world.islandObject.Field;
import animal_world.islandObject.Location;
import animal_world.islandObject.RandomNumbers;

import java.util.List;
import java.util.Random;

public class Plants extends PlantAbstract implements BehaviorablePlant
{
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
    @Override
    public void act(List<PlantAbstract> newPlants)
    {
        incrementAge();
        if(isAlive()) {
            giveBirth(newPlants);
        } else {
                setDead();
            }
        }
        @Override
    public void incrementAge()
    {
        age++;
        if(age > MAX_AGE) {
            setDead();
        }
    }
    @Override
    public void giveBirth(List<PlantAbstract> newPlants)
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
    @Override
    public int breed()
    {
        int births = 0;
        if(canBreed() && rand.nextDouble() <= BREEDING_PROBABILITY) {
            births = rand.nextInt(MAX_LITTER_SIZE) + 1;
        }
        return births;
    }
@Override
    public boolean canBreed()
    {
        return age >= BREEDING_AGE;
    }
}

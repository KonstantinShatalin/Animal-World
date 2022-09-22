package animal;

import animal.herbivorous.*;
import animal.predator.*;
import plant.Plant;
import plant.Plants;

import java.awt.*;
import java.util.List;
import java.util.*;

public class Simulator
{
    private static final int DEFAULT_WIDTH = 120;
    private static final int DEFAULT_DEPTH = 80;
    private static final double PREDATOR_CREATION_PROBABILITY = 0.04;
    private static final double HERBIVOROUS_CREATION_PROBABILITY = 0.08;
    private static final double PLANTS_CREATION_PROBABILITY = 0.10;

    private List<Animal> animals;
    private List<Plant> plants;
    private Field field;
    private int step;
    private SimulatorView view;

    public Simulator()
    {
        this(DEFAULT_DEPTH, DEFAULT_WIDTH);
    }

    public Simulator(int depth, int width)
    {
        if(width <= 0 || depth <= 0) {
            System.out.println("The dimensions must be greater than zero.");
            System.out.println("Using default values.");
            depth = DEFAULT_DEPTH;
            width = DEFAULT_WIDTH;
        }
        animals = new ArrayList<>();
        plants = new ArrayList<>();
        field = new Field(depth, width);

        view = new SimulatorView(depth, width);
                view.setColor(Bear.class, Color.GRAY);
                view.setColor(Boa.class, Color.BLACK);
                view.setColor(Eagle.class, Color.DARK_GRAY);
                view.setColor(Fox.class, Color.ORANGE);
                view.setColor(Wolf.class, Color.LIGHT_GRAY);
                view.setColor(Boar.class, Color.RED);
                view.setColor(Buffalo.class, Color.BLUE);
                view.setColor(Caterpillar.class, Color.WHITE);
                view.setColor(Deer.class, Color.YELLOW);
                view.setColor(Duck.class, Color.MAGENTA);
                view.setColor(Goat.class, Color.PINK);
                view.setColor(Horse.class, Color.pink);
                view.setColor(Mouse.class, Color.darkGray);
                view.setColor(Rabbit.class, Color.BLUE);
                view.setColor(Sheep.class, Color.LIGHT_GRAY);
                view.setColor(Plants.class,Color.GREEN);

        reset();
    }

    public void runLongSimulation()
    {
        simulate(1000);
    }


    public void simulate(int numSteps)
    {
        for(int step = 1; step <= numSteps && view.isViable(field); step++) {
            simulateOneStep();
        }
    }

    public void simulateOneStep()
    {
        delay();
        step++;

        List<Animal> newAnimals = new ArrayList<>();
        List<Plant> newPlants = new ArrayList<>();
        for(Iterator<Animal> it = animals.iterator(); it.hasNext(); ) {
            Animal animal = it.next();
            animal.act(newAnimals);
            if(!animal.isAlive()) {
                it.remove();
            }
        }
        for(Iterator<Plant> it = plants.iterator(); it.hasNext(); ) {
            Plant plant = it.next();
            plant.act(newPlants);
            if(!plant.isAlive()) {
                it.remove();
            }
        }

        animals.addAll(newAnimals);
        plants.addAll(newPlants);

        view.showStatus(step, field);
    }

    public void reset()
    {
        step = 0;
        animals.clear();
        plants.clear();
        populate();
        view.showStatus(step, field);
    }

    private void populate() {
        Random rand = RandomNumbers.getRandom();
        field.clear();
        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                if(rand.nextDouble() <= PREDATOR_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    animals.add(new Bear(true, field, location));
                    animals.add(new Boa(true,field,location));
                    animals.add(new Eagle(true,field,location));
                    animals.add(new Fox(true,field,location));
                    animals.add(new Wolf(true,field,location));
                }
                else if(rand.nextDouble() <= HERBIVOROUS_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    animals.add(new Boar(true, field, location));
                    animals.add(new Buffalo(true,field,location));
                    animals.add(new Caterpillar(true,field,location));
                    animals.add(new Deer(true,field,location));
                    animals.add(new Duck(true,field,location));
                    animals.add(new Goat(true,field,location));
                    animals.add(new Horse(true,field,location));
                    animals.add(new Mouse(true,field,location));
                    animals.add(new Rabbit(true,field,location));
                    animals.add(new Sheep(true,field,location));
                }
                else if (rand.nextDouble() <= PLANTS_CREATION_PROBABILITY){
                    Location location = new Location(row,col);
                    plants.add(new Plants(true,field,location));
                }
            }
        }
    }

    private void delay()
    {
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }
}

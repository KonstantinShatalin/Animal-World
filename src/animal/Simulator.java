package animal;

import animal.herbivorous.*;
import animal.predator.*;
import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Simulator
{
    private static final int DEFAULT_WIDTH = 120;
    private static final int DEFAULT_DEPTH = 80;
    private static final double PREDATOR_CREATION_PROBABILITY = 0.02;
    private static final double HERBIVOROUS_CREATION_PROBABILITY = 0.08;
    private static final double PLANTS_CREATION_PROBABILITY = 0.10;

    private List<Animal> animals;
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
        ArrayList<Class> allClass = new ArrayList<>();
        Collections.addAll(allClass,Bear.class, Boa.class, Eagle.class, Fox.class,Wolf.class, Boar.class,Buffalo.class,
                Caterpillar.class, Deer.class, Duck.class,Goat.class,Horse.class,Mouse.class,Rabbit.class,Sheep.class);
        animals = new ArrayList<>();
        field = new Field(depth, width);

        view = new SimulatorView(depth, width);
        for (Class clazz : allClass){
            if (clazz.equals(Bear.class)) {
                view.setColor(Bear.class, Color.GRAY);
            }
            if (clazz.equals(Boa.class)) {
                view.setColor(Boa.class, Color.BLACK);
            }
            if (clazz.equals(Eagle.class)) {
                view.setColor(Eagle.class, Color.DARK_GRAY);
            }
            if (clazz.equals(Fox.class)) {
                view.setColor(Fox.class, Color.ORANGE);
            }
            if (clazz.equals(Wolf.class)) {
                view.setColor(Wolf.class, Color.LIGHT_GRAY);
            }
            if (clazz.equals(Boar.class)) {
                view.setColor(Boar.class, Color.RED);
            }
            if (clazz.equals(Buffalo.class)) {
                view.setColor(Buffalo.class, Color.BLUE);
            }
            if (clazz.equals(Caterpillar.class)) {
                view.setColor(Caterpillar.class, Color.WHITE);
            }
            if (clazz.equals(Deer.class)) {
                view.setColor(Deer.class, Color.YELLOW);
            }
            if (clazz.equals(Duck.class)) {
                view.setColor(Duck.class, Color.MAGENTA);
            }
            if (clazz.equals(Goat.class)) {
                view.setColor(Goat.class, Color.PINK);
            }
            if (clazz.equals(Horse.class)) {
                view.setColor(Horse.class, Color.pink);
            }
            if (clazz.equals(Mouse.class)) {
                view.setColor(Mouse.class, Color.darkGray);
            }
            if (clazz.equals(Rabbit.class)) {
                view.setColor(Rabbit.class, Color.BLUE);
            }
            if (clazz.equals(Sheep.class)) {
                view.setColor(Sheep.class, Color.LIGHT_GRAY);
            }

        }
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
        for(Iterator<Animal> it = animals.iterator(); it.hasNext(); ) {
            Animal animal = it.next();
            animal.act(newAnimals);
            if(!animal.isAlive()) {
                it.remove();
            }
        }

        animals.addAll(newAnimals);

        view.showStatus(step, field);
    }

    public void reset()
    {
        step = 0;
        animals.clear();
        populate();

        view.showStatus(step, field);
    }

    private void populate() {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
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
                    Plants plants = new Plants(true,field,location);
                    animals.add(plants);
                }
            }
        }
        for (Animal animal : animals){
            Thread threadAnimals = new Thread((Runnable) animal);
            executorService.execute(threadAnimals);


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

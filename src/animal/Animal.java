package animal;

import java.util.List;

public abstract class Animal
{

    public static void main(String[] args) {
        Simulator simulator = new Simulator();
        simulator.runLongSimulation(); // длительная симуляция на 1000 итераций
       // simulator.simulate(20); симуляция на количество итераций
       // simulator.simulateOneStep(); симуляция одной итерации

    }

    private boolean alive;
    private Field field;
    private Location location;

    public Animal(Field field, Location location)
    {
        alive = true;
        this.field = field;
        setLocation(location);
    }

    abstract public void act(List<Animal> newAnimals);

    public boolean isAlive()
    {
        return alive;
    }

    public void setDead()
    {
        alive = false;
        if(location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }

    protected Location getLocation()
    {
        return location;
    }

    protected void setLocation(Location newLocation)
    {
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this,newLocation);
    }

    protected Field getField()
    {
        return field;
    }

}


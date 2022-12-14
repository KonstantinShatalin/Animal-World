package animal_world.animal;

import animal_world.islandObject.Field;
import animal_world.islandObject.Location;

import java.util.List;

public abstract class Animal
{

    private boolean alive;
    private Field field;
    private Location location;

    public Animal(Field field, Location location)
    {
        alive = true;
        this.field = field;
        setLocation(location);
    }

    public abstract void act(List<Animal> newAnimals);

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


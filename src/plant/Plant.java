package plant;
import animal.Field;
import animal.Location;

import java.util.List;

public abstract class Plant {
        private boolean alive;
        private Field field;
        private Location location;

        public Plant(Field field, Location location)
        {
            alive = true;
            this.field = field;
            setLocation(location);
        }

        abstract public void act(List<Plant> newPlant);

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

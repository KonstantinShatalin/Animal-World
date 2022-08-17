package animal;

import java.util.HashMap;

public class FieldStats
{
    private HashMap<Class, Counter> counters;
    private boolean countsValid;

    public FieldStats()
    {
        counters = new HashMap<>();
        countsValid = true;
    }

    public String getPopulationDetails(Field field)
    {
        StringBuffer buffer = new StringBuffer();
        if(!countsValid) {
            generateCounts(field);
        }
        for(Class key : counters.keySet()) {
            Counter info = counters.get(key);
            buffer.append(info.getName());
            buffer.append(": ");
            buffer.append(info.getCount());
            buffer.append(' ');
        }
        return buffer.toString();
    }

    public void reset()
    {
        countsValid = false;
        for(Class key : counters.keySet()) {
            Counter count = counters.get(key);
            count.reset();
        }
    }

    public void incrementCount(Class animalClass)
    {
        Counter count = counters.get(animalClass);
        if(count == null) {
            count = new Counter(animalClass.getName());
            counters.put(animalClass, count);
        }
        count.increment();
    }

    public void countFinished()
    {
        countsValid = true;
    }

    public boolean isViable(Field field)
    {
        int nonZero = 0;
        if(!countsValid) {
            generateCounts(field);
        }
        for(Class key : counters.keySet()) {
            Counter info = counters.get(key);
            if(info.getCount() > 0) {
                nonZero++;
            }
        }
        return nonZero > 1;
    }

    private void generateCounts(Field field)
    {
        reset();
        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                Object animal = field.getObjectAt(row, col);
                if(animal != null) {
                    incrementCount(animal.getClass());
                }
            }
        }
        countsValid = true;
    }
}


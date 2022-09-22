package animal_world.islandObject;

import java.util.Random;

public class RandomNumbers{
    private static final int SEED = 1000;
    private static final Random rand = new Random(SEED);
    private static final boolean useShared = true;

    public RandomNumbers() {
    }

    public static Random getRandom() {
        if (useShared) {
            return rand;
        } else {
            return new Random();
        }
    }
}

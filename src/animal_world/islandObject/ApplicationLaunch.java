package animal_world.islandObject;

public class ApplicationLaunch {
    public static void main(String[] args) {
        Simulator simulator = new Simulator();
        simulator.runLongSimulation(); // длительная симуляция на 1000 итераций
        // simulator.simulate(20); симуляция на количество итераций например 20
        // simulator.simulateOneStep(); симуляция одной итерации
    }
}

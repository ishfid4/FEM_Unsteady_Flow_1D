package pl.ishid.mes2;

/**
 * Created by ishfid on 25.12.16.
 */
public class Node {
    private int id;
    private double rPosition;
    private double temperature = 0.0;

    public Node(int id, double rPosition) {
        this.id = id;
        this.rPosition = rPosition;
    }

    public int getId() {
        return id;
    }

    public double getrPosition() {
        return rPosition;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
}

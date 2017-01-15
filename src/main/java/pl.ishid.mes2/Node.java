package pl.ishid.mes2;

/**
 * Created by ishfid on 25.12.16.
 */
public class Node {
    private int id;
    private double rPosition;
    private double c, ro, k;
    private double temperature = 0.0;

    public Node(int id, double rPosition, double c, double ro, double k) {
        this.id = id;
        this.rPosition = rPosition;
        this.c = c;
        this.ro = ro;
        this.k = k;
    }

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

    public double getC() {
        return c;
    }

    public double getRo() {
        return ro;
    }

    public double getK() {
        return k;
    }
}

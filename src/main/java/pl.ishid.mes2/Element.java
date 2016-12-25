package pl.ishid.mes2;

/**
 * Created by ishfid on 24.12.16.
 */
public class Element {
    private int id;
    private Node node1, node2;
    private InputData inputData;
    private double kMatrix[][];
    private double fVector[];

    public Element(int id, Node node1, Node node2, InputData inputData) {
        this.id = id;
        this.node1 = node1;
        this.node2 = node2;
        this.inputData = inputData;

        this.kMatrix = new double[2][2];
        this.fVector = new double[2];
        for (int i = 0; i < 2; ++i){
            fVector[i] = 0.0;
            for (int j = 0; j < 2; ++j){
                kMatrix[i][j] = 0.0;
            }
        }
    }

    //TODO: consider if it should be moved
    //For integration
    private int integratePoints = 2;
    private double weights[] = {1,1};
    private double ksi[] = {-0.5773502692,0.5773502692};
    //shape functions
    private double n[][] = {{(1 - ksi[0])/2, (1 - ksi[1])/2},
                            {(1 + ksi[0])/2, (1 + ksi[1])/2}};

    public void calculateKmatrix(){
        for (int i = 0; i < integratePoints; ++i){
            //transformation of coordinates?
            double rp = n[0][i] * node1.getrPosition() + n[1][i] * node2.getrPosition();

            kMatrix[0][0] += (inputData.getK() / inputData.getDeltaRadius())
                    * (rp * weights[i])
                    + ((inputData.getC() * inputData.getRo() * inputData.getDeltaRadius()) / inputData.getDeltaTau())
                    * n[0][i] * n[0][i] * rp * weights[i];

            kMatrix[0][1] += -(inputData.getK() / inputData.getDeltaRadius())
                    * (rp * weights[i])
                    + ((inputData.getC() * inputData.getRo() * inputData.getDeltaRadius()) / inputData.getDeltaTau())
                    * n[0][i] * n[1][i] * rp * weights[i];

            kMatrix[1][0] += -(inputData.getK() / inputData.getDeltaRadius())
                    * (rp * weights[i])
                    + ((inputData.getC() * inputData.getRo() * inputData.getDeltaRadius()) / inputData.getDeltaTau())
                    * n[0][i] * n[1][i] * rp * weights[i];

            kMatrix[1][1] += (inputData.getK() / inputData.getDeltaRadius())
                    * (rp * weights[i])
                    + (((inputData.getC() * inputData.getRo() * inputData.getDeltaRadius()) / inputData.getDeltaTau())
                    * n[1][i] * n[1][i] * rp * weights[i]);
        }

        //TODO: too low value?
        //boundary condition?
        if(node2.getrPosition() == inputData.getRadiusMax()) {
            kMatrix[1][1] += 2 * inputData.getAlpha() * inputData.getRadiusMax();
        }
    }

    //TODO: is it even correct?
    public void calculateFvector(){
        for (int i = 0; i < integratePoints; ++i) {
            //transformation of coordinates?
            double rp = n[0][i] * node1.getrPosition() + n[1][i] * node2.getrPosition();
            double temperatureP = (n[0][i] * node1.getTemperature()) + (n[1][i] * node2.getTemperature());

            fVector[0] += -((inputData.getC() * inputData.getRo() * inputData.getDeltaRadius()) / inputData.getDeltaTau())
                    * temperatureP * n[0][i] * rp * weights[i];

            fVector[1] += -((inputData.getC() * inputData.getRo() * inputData.getDeltaRadius()) / inputData.getDeltaTau())
                    * temperatureP * n[1][i] * rp * weights[i];
        }
        //boundary condition?
        if(node2.getrPosition() == inputData.getRadiusMax()) {
            fVector[1] -= 2 * inputData.getAlpha() * inputData.getRadiusMax() * inputData.getAmbientTemperature();
        }
    }

    public Node getNode1() {
        return node1;
    }

    public Node getNode2() {
        return node2;
    }

    public double[][] getkMatrix() {
        return kMatrix;
    }

    public double[] getfVector() {
        return fVector;
    }
}

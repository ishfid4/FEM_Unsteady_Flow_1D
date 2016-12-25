package pl.ishid.mes2;

import java.util.ArrayList;
import org.apache.commons.math3.linear.*;

/**
 * Created by ishfid on 25.12.16.
 */
public class EquationSystem {
    private double[][] globalKmatrix;
    private double[] globalFvector;
    private double[] temperatureVector;

    public EquationSystem(int nodeCount) {
        this.globalKmatrix = new double[nodeCount][nodeCount];
        for(int i = 0; i < nodeCount; ++i){
            for(int j = 0; j < nodeCount; ++j){
                this.globalKmatrix[i][j] = 0;
            }
        }

        this.globalFvector = new double[nodeCount];
        this.temperatureVector = new double[nodeCount];
        for (int i = 0; i < nodeCount; ++i){
            this.globalFvector[i] = 0;
            this.temperatureVector[i] = 0;
        }
    }

    public void agregateKmatrix(ArrayList<Element> elements){
        for (Element element: elements) {
            this.globalKmatrix[element.getNode1().getId()][element.getNode1().getId()] += element.getkMatrix()[0][0];
            this.globalKmatrix[element.getNode1().getId()][element.getNode2().getId()] += element.getkMatrix()[0][1];
            this.globalKmatrix[element.getNode2().getId()][element.getNode1().getId()] += element.getkMatrix()[1][0];
            this.globalKmatrix[element.getNode2().getId()][element.getNode2().getId()] += element.getkMatrix()[1][1];
        }
    }

    public void agregateFvector(ArrayList<Element> elements){
        for (Element element: elements) {
            this.globalFvector[element.getNode1().getId()] += element.getfVector()[0];
            this.globalFvector[element.getNode2().getId()] += element.getfVector()[1];
        }
    }

    public void solveEquationSystem(){
        DecompositionSolver solver;
        RealMatrix coefficients;
        RealVector constans;

        // Solving linear equation system
        coefficients = new Array2DRowRealMatrix(globalKmatrix, false);
        solver = new LUDecomposition(coefficients).getSolver();

        constans = new ArrayRealVector(globalFvector, false);
        constans = constans.mapMultiply(-1);
        this.temperatureVector = solver.solve(constans).toArray();
    }

    public double[] getTemperatureVector() {
        return temperatureVector;
    }
}

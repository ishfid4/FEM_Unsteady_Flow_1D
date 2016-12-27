package pl.ishid.mes2;

import javafx.util.Pair;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created by ishfid on 24.12.16.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        PrintWriter kfOut = null;
        PrintWriter out = null;
        InputData inputData = new InputData();
        inputData.importData("input.txt");

        ArrayList<Pair<Double,Double>> listOfTemperaturePairs = new ArrayList<>(); //Pair<InsideTemp, OutsideTemp>
        ArrayList<Node> nodeList = new ArrayList<>();
        ArrayList<Element> elements = new ArrayList<>();

        if (inputData.getRadiusMax() % inputData.getDeltaRadius() == 0){
            System.out.println("Node count ok");
        }else{
            System.out.println("Node count is NOT whole number!!!!");
        }
        int nodeCount = (int)(inputData.getRadiusMax() / inputData.getDeltaRadius());

        //Node creation
        for (int i = 0; i <= nodeCount; ++i){
            nodeList.add(new Node(i,i * inputData.getDeltaRadius()));
            nodeList.get(i).setTemperature(inputData.getBeginTemperature());
        }

        //Elements creation
        for (int i = 0; i < (nodeCount); ++i){
            elements.add(new Element(i,nodeList.get(i),nodeList.get(i+1),inputData));
        }

        try {
            kfOut = new PrintWriter("kfOut.txt");

            //Calculating temperatures for specified period of time with pre-defined delta time
            for (int time = 0; time < inputData.getTau(); time += inputData.getDeltaTau()) {
                //Resetting local K and F
                for (Element element: elements) {
                    element.resetKandF();
                }

                //Calculating local K matrix and F vector
                for (int i = 0; i < (nodeCount); ++i) {
                    elements.get(i).calculateKmatrix();
                    elements.get(i).calculateFvector();
                }

                //Solving equation system
                EquationSystem equationSystem = new EquationSystem(nodeCount + 1);
                equationSystem.agregateKmatrix(elements);
                equationSystem.agregateFvector(elements);
                equationSystem.solveEquationSystem();

                //Updating node temperatures
                double temperatures[] = equationSystem.getTemperatureVector();
                for (int i = 0; i <= nodeCount; ++i) {
                    nodeList.get(i).setTemperature(temperatures[i]);
                }
                listOfTemperaturePairs.add(new Pair<>(nodeList.get(0).getTemperature(),
                        nodeList.get(nodeCount).getTemperature()));

                //Saving K and F to file
                kfOut.println("Time: " + (time + inputData.getDeltaTau()));
                double k[][] = equationSystem.getGlobalKmatrix();
                double f[] = equationSystem.getGlobalFvector();
                for (int i = 0; i < k.length; ++i){
                    for (int j = 0; j < k.length; ++j){
                        kfOut.printf("%.3f \t", k[i][j]);
                    }
                    kfOut.println();
                }
                kfOut.println();
                for (int i = 0; i < f.length; ++i){
                    kfOut.print(f[i] + "\t");
                }
                kfOut.println("\n");
            }

            //Saving pairs of insideTemp and outsideTemp to file
            out = new PrintWriter("output.txt");

            for (Pair p: listOfTemperaturePairs) {
                out.println(p.getKey() + "\t" + p.getValue());
            }

        }finally {
            if (out != null) {
                out.close();
            }
            if (kfOut != null) {
                kfOut.close();
            }
        }
    }
}
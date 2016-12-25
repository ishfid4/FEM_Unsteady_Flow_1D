package pl.ishid.mes2;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by ishfid on 24.12.16.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        InputData inputData = new InputData();
        inputData.importData("input.txt");

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
        }

        //Elements creation
        for (int i = 0; i < (nodeCount); ++i){
            elements.add(new Element(i,nodeList.get(i),nodeList.get(i+1),inputData));
        }

        //Calculating local K matrix and F vector
        for (int i = 0; i < (nodeCount); ++i){
            elements.get(i).calculateKmatrix();
            elements.get(i).calculateFvector();
        }

        EquationSystem equationSystem = new EquationSystem(nodeCount + 1);
        equationSystem.agregateKmatrix(elements);
        equationSystem.agregateFvector(elements);
        equationSystem.solveEquationSystem();

        System.out.println("Hello");
    }
}

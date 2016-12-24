package pl.ishid.mes2;

import java.io.IOException;

/**
 * Created by ishfid on 24.12.16.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        InputData inputData = new InputData();
        inputData.importData("input.txt");
        System.out.println("Hello");
    }
}

package pl.ishid.mes2;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;

/**
 * Created by ishfid on 24.12.16.
 */
public class InputData {
    private double radiusMax, deltaRadius;
    private double deltaTau; //interval time
    private double tau;
    private double c;  //specific heat capacity
    private double ro; //density
    private double k;
    private double alpha;
    private double beginTemperature, ambientTemperature;

    public void importData(String path) throws IOException {
        FileInputStream in = null;
        Scanner scanner;

        try {
            in = new FileInputStream(path);
            scanner = new Scanner(in);
            scanner.useLocale(Locale.ENGLISH);

            this.radiusMax = scanner.nextDouble();
            this.deltaRadius = scanner.nextDouble();
            this.deltaTau = scanner.nextDouble();
            this.tau = scanner.nextDouble();
            this.c = scanner.nextDouble();
            this.ro = scanner.nextDouble();
            this.k = scanner.nextDouble();
            this.alpha = scanner.nextDouble();
            this.beginTemperature = scanner.nextDouble();
            this.ambientTemperature = scanner.nextDouble();

        }finally {
            if (in != null) {
                in.close();
            }
        }
    }

    public double getRadiusMax() {
        return radiusMax;
    }

    public double getDeltaRadius() {
        return deltaRadius;
    }

    public double getDeltaTau() {
        return deltaTau;
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

    public double getAlpha() {
        return alpha;
    }

    public double getBeginTemperature() {
        return beginTemperature;
    }

    public double getAmbientTemperature() {
        return ambientTemperature;
    }

    public double getTau() {
        return tau;
    }
}
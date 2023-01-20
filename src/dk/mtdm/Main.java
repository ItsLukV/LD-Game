package dk.mtdm;

import processing.core.PApplet;

public class Main {
    public static void main(String[] args) {
        String[] processingArgs = {"Sketch"};
        Sketch mySketch = new Sketch();
        PApplet.runSketch(processingArgs,mySketch);
    }
}

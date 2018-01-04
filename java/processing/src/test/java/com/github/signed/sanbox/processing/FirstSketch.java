package com.github.signed.sanbox.processing;

import processing.core.PApplet;

public class FirstSketch extends PApplet {

    public static void main(String[] args) {
        PApplet.main(FirstSketch.class);
    }

    @Override
    public void draw() {
        ellipse(50, 50, 10, 20);
    }
}

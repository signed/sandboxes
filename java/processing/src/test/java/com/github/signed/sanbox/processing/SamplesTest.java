package com.github.signed.sanbox.processing;

import org.junit.Test;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;

import java.awt.*;

public class SamplesTest {

    @Test
    public void basic_shape() throws Exception {
        PApplet applet = new PApplet() {
            @Override
            public void setup() {

            }

            @Override
            public PImage loadImage(String filename) {
                return super.loadImage(filename);
            }
        };

        Image img = null;
        PImage image = new PImage(img);
    }


}

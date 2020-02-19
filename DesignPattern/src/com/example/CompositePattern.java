package com.example;

import com.design.composite.Circle;
import com.design.composite.Drawing;
import com.design.composite.Shape;
import com.design.composite.Triangle;

public class CompositePattern {

    public static void main(String[] args) {
        Shape tri = new Triangle();
        Shape tri1 = new Triangle();
        Shape cir = new Circle();
		
        Drawing drawing = new Drawing();
        drawing.add(tri1);
        drawing.add(tri1);
        drawing.add(cir);
		
        drawing.draw("Red");
		
        drawing.clear();
		
        drawing.add(tri);
        drawing.add(cir);
        drawing.draw("Green");
    }

}

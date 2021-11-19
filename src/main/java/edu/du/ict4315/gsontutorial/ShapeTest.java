/*
 * This file is part of the tutorial on Gson
 * see https://github.com/mischwartz23/GsonTutorial.git
 * Author: M I Schwartz
 */
package edu.du.ict4315.gsontutorial;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author michael
 */
public class ShapeTest {

  static List<Shape> shapeList = new ArrayList<>();

  public static void main(String[] args) {
    shapeList.add(new Square(16.0));
    shapeList.add(new Square(4.5));
    shapeList.add(new Rectangle(10.0, 20.0));
    shapeList.add(new Circle(12.5));

    Shape[] shapeArray = shapeList.toArray(new Shape[0]);

    Gson gson = new GsonBuilder()
        .registerTypeAdapter(Shape.class, new ShapeSerialization())
        .create();
    System.out.println(Arrays.toString(shapeArray));
    String json1 = gson.toJson(shapeArray);
    System.out.println(json1);

    Shape[] newShapeArray = gson.fromJson(json1, Shape[].class);
    System.out.println(Arrays.toString(newShapeArray));
  }
}

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
public class ShapeTest2 {
  static List<Shape> shapeList = new ArrayList<>();
  
  public final static class Drawing {
    private List<Shape> shapeList;
    private String title;
    public Drawing(String title, List<Shape> shapeList) {
      this.title = title;
      this.shapeList = shapeList;
    }
  }
  
  public static void main(String[] args) {
    shapeList.add(new Square(16.0));
    shapeList.add(new Square(4.5));
    shapeList.add(new Rectangle(10.0, 20.0));
    shapeList.add(new Circle(12.5));
    
    Drawing drawing = new Drawing("Collection", shapeList);
    
    Gson gson = new GsonBuilder()
        .registerTypeAdapter(Shape.class, new ShapeSerialization())
        .create();
    
    String json = gson.toJson(drawing);
    System.out.println(json);
    Drawing myCopy = gson.fromJson(json, Drawing.class);
    System.out.println(gson.toJson(myCopy));
  }
}

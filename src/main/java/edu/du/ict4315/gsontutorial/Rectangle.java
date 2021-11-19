/*
 * This file is part of the tutorial on Gson
 * see https://github.com/mischwartz23/GsonTutorial.git
 * Author: M I Schwartz
 */
package edu.du.ict4315.gsontutorial;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;

/**
 *
 * @author michael
 */
public class Rectangle extends Shape {
  double length;
  double height;
  
  public Rectangle() {
    length = 0;
  }
  public Rectangle(double len, double hgt) {
    length = len;
    height = hgt;
  }
  public double getArea() {
    return length * height;
  }
  public double getPerimeter() {
    return 2 * length + 2 * height;
  } 
  
    public String toString() {
    return "Rectangle: [ length: "+length+", height: "+height+" ]";
  }
 @Override
  String toJsonString() {
   Gson gson = new GsonBuilder().create(); // new Gson();
		return gson.toJson(this);
  }

  @Override
  Shape fromJsonString(String jsonString) {
 		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		Rectangle  rectangle = new Rectangle();
    try {
			rectangle = gson.fromJson(jsonString, Rectangle.class);
		} catch (JsonParseException exc) {
			System.err.println("Incomplete: Cannot parse JSON: " + exc);
		}
		return rectangle;  
  }

  @Override
  String toJsonStringPretty() {
   Gson gson = new GsonBuilder().setPrettyPrinting().create(); // new Gson();
		return gson.toJson(this);
  }
}

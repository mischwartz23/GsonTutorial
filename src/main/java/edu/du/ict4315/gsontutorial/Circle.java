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
public class Circle extends Shape {
  double radius;
  
  public Circle() {
    radius = 0;
  }
  
  public Circle(double rad) {
    radius = rad;
  }
  public double getArea() {
    return Math.PI*radius*radius;
  }
  public double getPerimeter() {
    return Math.PI*2*radius;
  }
  
  @Override
  public String toString() {
    return "Circle: [ radius: "+radius+" ]";
  }

  @Override
  String toJsonString() {
   Gson gson = new GsonBuilder().create(); // new Gson();
		return gson.toJson(this);
  }

  @Override
  Shape fromJsonString(String jsonString) {
 		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		Circle  circle = new Circle();
    try {
			circle = gson.fromJson(jsonString, Circle.class);
		} catch (JsonParseException exc) {
			System.err.println("Incomplete: Cannot parse JSON: " + exc);
		}
		return circle;  
  }

  @Override
  String toJsonStringPretty() {
   Gson gson = new GsonBuilder().setPrettyPrinting().create(); // new Gson();
		return gson.toJson(this);
  }
}

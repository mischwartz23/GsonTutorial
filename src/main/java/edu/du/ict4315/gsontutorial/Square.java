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
public class Square extends Shape {
  double length;
  public Square() {
    length = 0;
  }
  public Square(double len) {
    length = len;
  }
  public double getArea() {
    return length * length;
  }
  public double getPerimeter() {
    return 4 * length;
  }
  
  public String toString() {
    return "Square: [ side: "+length+" ]";
  }
 @Override
  String toJsonString() {
   Gson gson = new GsonBuilder().create(); // new Gson();
		return gson.toJson(this);
  }

  @Override
  Shape fromJsonString(String jsonString) {
 		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		Square  square = new Square();
    try {
			square = gson.fromJson(jsonString, Square.class);
		} catch (JsonParseException exc) {
			System.err.println("Incomplete: Cannot parse JSON: " + exc);
		}
		return square;  
  }

  @Override
  String toJsonStringPretty() {
   Gson gson = new GsonBuilder().setPrettyPrinting().create(); // new Gson();
		return gson.toJson(this);
  }
}

/*
 * This file is part of the tutorial on Gson
 * see https://github.com/mischwartz23/GsonTutorial.git
 * Author: M I Schwartz
 */
package edu.du.ict4315.gsontutorial;

/**
 *
 * @author michael
 */
abstract public class Shape {
  abstract public double getArea();
  abstract public double getPerimeter();
  // Other methods are added to align with previous examples
  abstract String toJsonString();
  abstract Shape fromJsonString(String jsonString);
  abstract String toJsonStringPretty();
}
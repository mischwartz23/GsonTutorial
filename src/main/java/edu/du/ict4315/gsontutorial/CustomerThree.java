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
 * Step 1: create the object class Step 2: create needed getters, setters, and constructors
 *
 * @author michael
 */
public class CustomerThree {

  private int customerId;
  private String name;
  private Address address;
  private String phoneNumber;
  private CustomerType type;

  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Customer:");
    sb.append("[");
    sb.append("Id: ");
    sb.append(customerId);
    sb.append("; ");
    sb.append("Name: ");
    sb.append(name);
    sb.append("; ");
    sb.append("Address: ");
    sb.append(address);
    sb.append("; ");
    sb.append("Phone number: ");
    sb.append(phoneNumber);
    sb.append("; ");
    sb.append("Customer type: ");
    sb.append(type);
    sb.append("]");
    return sb.toString();
  }

  // Step 3: Add a toJsonString method
  public String toJsonString() {
    Gson gson = new GsonBuilder().create(); // new Gson();
    return gson.toJson(this);
  }

  public String toJsonStringPretty() {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    return gson.toJson(this);
  }

  // Step 4: Add a fromJsonString method
  public static CustomerThree fromJsonString(String jsonString) {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    CustomerThree customer = new CustomerThree();
    try {
      customer = gson.fromJson(jsonString, CustomerThree.class);
    } catch (JsonParseException exc) {
      System.err.println("Transaction incomplete: Cannot parse JSON: " + exc);
    }
    return customer;
  }

  // Step 5: In addition to creating a set of test cases, create a small "smoke test" main
  public static void main(String[] args) {
    CustomerThree customer1 = new CustomerThree();
    customer1.customerId = 2;
    customer1.name = "Martha Washington";
    customer1.address = new Address.AddressBuilder()
        .withStreet1("1600 Pennsylvania Avenue")
        .withCity("Washington DC")
        .withZipcode("20001")
        .build();
    customer1.phoneNumber = "202-555-1212";
    customer1.type = CustomerType.INDIVIDUAL;

    System.out.println(customer1);
    String jsonString = customer1.toJsonString();
    System.out.println(jsonString);
    CustomerThree customer2 = CustomerThree.fromJsonString(jsonString);
    System.out.println(customer2);
    System.out.println("The first and third lines should look the same!");
    System.out.println("Here is a pretty printed JSON string:");
    System.out.println(customer2.toJsonStringPretty());
  }
}

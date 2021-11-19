/*
 * This file is part of the tutorial on Gson
 * see https://github.com/mischwartz23/GsonTutorial.git
 * Author: M I Schwartz
 */
package edu.du.ict4315.gsontutorial;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import java.time.LocalDate;
import static java.time.temporal.ChronoUnit.YEARS;
import java.util.ArrayList;
import java.util.List;

/**
 * Step 1: create the object class Step 2: create needed getters, setters, and constructors
 *
 * @author michael
 */
public class CustomerSix {
  // This example explicitly serializes the properties in JSON.
  @SerializedName("id")
  private int customerId;
  @SerializedName("customer-name")
  private String name;
  @SerializedName("customer-address")
  private Address address;
  @SerializedName("customer-phone")
  private String phoneNumber;
  @SerializedName(value="customer-type", alternate={"type","ctype"})
  private CustomerType type;
  // This adapter shows how to read and write objects of this class into the JSON
  @JsonAdapter(LocalDateAdapter.class)
  private LocalDate birthday;
  private transient int age;
  private List<String> parkingIds = new ArrayList<>();

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
    sb.append("; ");
    sb.append("Parking ids: [");
    sb.append(String.join(", ", parkingIds));
    sb.append("]");
    sb.append("; ");
    sb.append("Birthday: ");
    sb.append(birthday);
    sb.append("; ");
    sb.append("Age: ");
    sb.append(getAge());
    sb.append("]");
    return sb.toString();
  }

  // Step 3: Add a toJsonString method
  public String toJsonString() {
    Gson gson = new GsonBuilder()
        .create(); // new Gson();
    return gson.toJson(this);
  }

  public String toJsonStringPretty() {
    Gson gson = new GsonBuilder()
        .setPrettyPrinting()
        .create();
    return gson.toJson(this);
  }

  // Step 4: Add a fromJsonString method
  public static CustomerSix fromJsonString(String jsonString) {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    CustomerSix customer = new CustomerSix();
    try {
      customer = gson.fromJson(jsonString, CustomerSix.class);
    } catch (JsonParseException exc) {
      System.err.println("Transaction incomplete: Cannot parse JSON: " + exc);
    }
    return customer;
  }

  // Step 5: In addition to creating a set of test cases, create a small "smoke test" main
  public static void main(String[] args) {
    CustomerSix customer1 = new CustomerSix();
    customer1.customerId = 2;
    customer1.name = "Martha Washington";
    customer1.address = new Address.AddressBuilder()
        .withStreet1("1600 Pennsylvania Avenue")
        .withCity("Washington DC")
        .withZipcode("20001")
        .build();
    customer1.phoneNumber = "202-555-1212";
    customer1.type = CustomerType.INDIVIDUAL;
    customer1.parkingIds.add("L17P33");
    customer1.parkingIds.add("L19P11");
    customer1.setBirthday(LocalDate.of(2000,2,28));

    System.out.println(customer1);
    String jsonString = customer1.toJsonString();
    System.out.println(jsonString);
    CustomerSix customer2 = CustomerSix.fromJsonString(jsonString);
    System.out.println(customer2);
    System.out.println("The first and third lines should look the same!");
    System.out.println("Here is a pretty printed JSON string:");
    System.out.println(customer2.toJsonStringPretty());
  }
  
  // Managing the transient
  public void setBirthday(LocalDate birthday) {
    this.birthday = birthday;
    calculateAge();
  }
  
  public int getAge() {
    if ( age > 0 ) {
      return age;
    }
    calculateAge();
    return age;
  }
    
  // Computing a transient
  private int calculateAge() {
    int retval = -1; // -1 means invalid birthday
    if ( birthday != null ) {
      age = (int)YEARS.between(birthday,LocalDate.now());
    }
    return retval;
  }
}


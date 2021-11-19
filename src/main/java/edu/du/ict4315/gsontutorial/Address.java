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
public class Address {

  private String street1;
  private String street2;
  private String city;
  private String state;
  private String country = "usa";
  private String zipcode;

  public static class AddressBuilder {

    private String street1;
    private String street2;
    private String city;
    private String state;
    private String country = "usa";
    private String zipcode;

    public AddressBuilder withStreet1(String street) {
      this.street1 = street;
      return this;
    }

    public AddressBuilder withStreet2(String street) {
      this.street2 = street;
      return this;
    }

    public AddressBuilder withCity(String city) {
      this.city = city;
      return this;
    }

    public AddressBuilder withState(String state) {
      this.state = state;
      return this;
    }

    public AddressBuilder withCountry(String country) {
      this.country = country;
      return this;
    }

    public AddressBuilder withZipcode(String zip) {
      this.zipcode = zip;
      return this;
    }

    public Address build() {
      Address address = new Address();
      address.street1 = this.street1;
      address.street2 = this.street2;
      address.city = this.city;
      address.state = this.state;
      address.zipcode = this.zipcode;
      return address;
    }
  }

  public String toString() {
    boolean componentFound = false;
    StringBuilder sb = new StringBuilder();
    sb.append("Address: ");
    sb.append("[");
    if (street1 != null) {
      componentFound = true;
      sb.append("Street: ");
      sb.append(street1);
    }
    if (street2 != null) {
      if (componentFound) {
        sb.append("; ");
      }
      componentFound = true;
      sb.append("Street: ");
      sb.append(street2);
    }
    if (city != null) {
      if (componentFound) {
        sb.append("; ");
      }
      componentFound = true;
      sb.append("City: ");
      sb.append(city);
    }
    if (state != null) {
      if (componentFound) {
        sb.append("; ");
      }
      componentFound = true;
      sb.append("State: ");
      sb.append(state);
    }
    if (country != null) {
      if (componentFound) {
        sb.append("; ");
      }
      componentFound = true;
      sb.append("Country: ");
      sb.append(country);
    }
    if (zipcode != null) {
      if (componentFound) {
        sb.append("; ");
      }
      componentFound = true;
      sb.append("Zip: ");
      sb.append(zipcode);
    }
    sb.append("]");
    return sb.toString();
  }

  public String toJsonString() {
    Gson gson = new GsonBuilder().create(); // new Gson();
    return gson.toJson(this);
  }

  public String toJsonStringPretty() {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    return gson.toJson(this);
  }

  public static Address fromJsonString(String jsonString) {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    Address address = new Address();
    try {
      address = gson.fromJson(jsonString, Address.class);
    } catch (JsonParseException exc) {
      System.err.println("Transaction incomplete: Cannot parse JSON: " + exc);
    }
    return address;
  }

  // Smoke test
  public static void main(String[] args) {
    Address myAddress = new Address();
    myAddress.street1 = "123 Main Street";
    myAddress.city = "Denver";
    myAddress.state = "Colorado";
    System.out.println(myAddress);
    String jAddress = myAddress.toJsonStringPretty();
    System.out.println(jAddress);
    Address address = Address.fromJsonString(jAddress);
    System.out.println(address);
  }
}

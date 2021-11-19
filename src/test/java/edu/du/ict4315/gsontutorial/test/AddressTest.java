/*
 * This file is part of the tutorial on Gson
 * see https://github.com/mischwartz23/GsonTutorial.git
 * Author: M I Schwartz
 */
package edu.du.ict4315.gsontutorial.test;

import edu.du.ict4315.gsontutorial.Address;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author michael
 */
public class AddressTest {

  private static String[][] jsonStrings = {
    { """
      {
        "street1": "123 Main Street",
        "city": "Denver",
        "state": "Colorado",
        "country": "usa"
      }""",  
      "Address: [Street: 123 Main Street; City: Denver; State: Colorado; Country: usa]",
      "{\"street1\":\"123 Main Street\",\"city\":\"Denver\",\"state\":\"Colorado\",\"country\":\"usa\"}"
    },
    {
      """
      {
        "street1": "123 Main Street",
        "street2": "Apartment 3G",
        "city": "Washington DC"
      }""",
      "Address: [Street: 123 Main Street; Street: Apartment 3G; City: Washington DC; Country: usa]",
      "{\"street1\":\"123 Main Street\",\"street2\":\"Apartment 3G\",\"city\":\"Washington DC\",\"country\":\"usa\"}"
    }
  };
  
  @Test 
  public void fromJson() {
    for (String [] s: jsonStrings) {
      String j = s[0];
      String p = s[1];
      Address a = Address.fromJsonString(j);
      assertEquals(a.toString(), p);
      // System.out.println(a);
    }
  }
  
  @Test
  public void toJson() {
    for (String [] s: jsonStrings) {
      String j = s[0];
      String p = s[1];
      String j2 = s[2];
      Address a = Address.fromJsonString(j);
      assertEquals(a.toJsonString(),j2);
    }
  }
  
  @Test
  public void toPrettyJson() {
    for (String [] s: jsonStrings) {
      String j = s[0];
      String p = s[1];
      String j2 = s[2];
      Address a = Address.fromJsonString(j2);
      assertEquals(Address.fromJsonString(j).toJsonStringPretty(),
                   Address.fromJsonString(j2).toJsonStringPretty());   
      assertEquals(Address.fromJsonString(j).toJsonString(),
                   Address.fromJsonString(j2).toJsonString());   
    }
  }

}

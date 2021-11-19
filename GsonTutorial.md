% Title: Gson Tutorial
% Author: M I Schwartz
% Date: 2021-12-01

# Introduction

JSON is a popular data format for data exchange between applications.

It stands for JavaScript Object Notation, though nowadays there are libraries in every language which can easily manipulate JSON objects.

Java used to be more difficult to use with JSON than other languages; Google's Gson library is intended to make this easy.

This tutorial is geared to establishing a working knowledge of the Gson library, which reads and writes JSON.

You will note the examples are lacking in getters, setters, costructors, and other niceties of Java classes.

This is done deliberately in order to strip th Gson capabilities to their most basic, and avoid "magic" invocations based on false assumptions about the library.

I welcome suggestions on simplifications and improvements, as well as non-obscure extensions for common situations an entry-level programmer is likelyl to encounter.

# Notes and references:

Here are resources I used to establish the approach in the code for this tutorial.

https://github.com/google/gson/blob/master/UserGuide.md

TODO: Uses Serializer/deserializer. Want to use typeadapter if possible.
https://stackoverflow.com/questions/38071530/gson-deserialize-interface-to-its-class-implementation

https://technology.finra.org/code/serialize-deserialize-interfaces-in-java.html

# Assumptions

I assume the reader has basic Java knowledge, and can build small applications.

I assume the reader has access to the code that accompanies this tutorial; only snippets or redactions are copied into the tutorial text below.

I assume the reader has a basic understanding of JSON, and can find syntax errors in a JSON literal if one is encountered.

I am assuming use of Java in the Netbeans IDE for my illustrations; the provided code assumes Maven (and thus a `pom.xml` file for dependencies). I believe this easily translates to a non-Maven implementation if needed, by including the Gson jar file as appropriate to the desired target environmnent.

I assume a current Java compiler. I believe any compiler beyond version 11 can handle the occassional multi-line literal and other such constructs in the code. I believe the small number of these extensions can be easily removed, and the code base can readily be converted to version 8 if needed.

I do not use lambdas in this exposition.

# Gson Dependency in `pom.xml`

Add the following dependency to your `pom.xml`

```
		<!-- Google's JSON package dependency -->
		<dependency>
			<groupId>com.google.code.gson</groupId>
			<artifactId>gson</artifactId>
			<version>2.8.5</version>
		</dependency>
```

Note that the precise version may advance, so you may use a higher, supported version.

# Other `pom.xml` dependencies

These dependencies are not related to Gson, but they are provided to avoid common misconfigurations

## Version Dependency in `pom.xml`

This section ensures the character set and compiler version are set.

```
<properties>
	<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
	<maven.compiler.source>17</maven.compiler.source>
	<maven.compiler.target>17</maven.compiler.target>
</properties>
```

## surefire plugin dependency in `pom.xml`

This section ensures a suitable version of the surefire plugin is available

```
<build>
	 <pluginManagement>
		 <plugins>
			 <plugin>
				 <groupId>org.apache.maven.plugins</groupId>
				 <artifactId>maven-surefire-plugin</artifactId>
				 <version>2.22.2</version>
			 </plugin>
		 </plugins>
	 </pluginManagement>
 </build>
```

## Junit / Jupiter dependencies in `pom.xml`

This section ensures a Junit5/Jupiter test management version that respects test annotations and visibility is available (in the dependencies section)


```
<dependency>
	 <groupId>org.junit.jupiter</groupId>
	 <artifactId>junit-jupiter-api</artifactId>
	 <version>5.6.3</version>
	 <scope>test</scope>
 </dependency>
 <dependency>
	 <groupId>org.junit.jupiter</groupId>
	 <artifactId>junit-jupiter-params</artifactId>
	 <version>5.6.3</version>
	 <scope>test</scope>
 </dependency>
 <dependency>
	 <groupId>org.junit.jupiter</groupId>
	 <artifactId>junit-jupiter-engine</artifactId>
	 <version>5.6.3</version>
	 <scope>test</scope>
 </dependency>
 ```

# Part 1: Simple Gson

## Start with the object to be converted to and from JSON

We will start with the customer (all methods are omitted)

```
+-------------------------------+
| Customer                      |
+-------------------------------+
| customer id: String           |
| name: String                  |
| address: String               |
| phone number: String          |
+-------------------------------+
```

(Note: We will deal with the Address as a type later)

## Create the class `Customer`

```
public class Customer {
  private int customerId;
  private String name;
  private String address; // more later
  private String phoneNumber;
}
```

## Add `toJsonString` method

Here we use the `GsonBuilder`, though in this case `new Gson()` would suffice.

```
public String toJsonString() {
    Gson gson = new GsonBuilder().create(); // new Gson();
		return gson.toJson(this);
}
```

## A variation for pretty-printing

Using an additional method in the builder, we can pretty-print the JSON

```
public String toJsonStringPretty() {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(this);
}
```

## Add `fromJsonString` method

This is a static method, much like a constructor

```
public static Customer fromJsonString(String jsonString) {
	Gson gson = new GsonBuilder().setPrettyPrinting().create();
  Customer customer = new Customer();
	try {
		customer = gson.fromJson(jsonString, Customer.class);
	} catch (JsonParseException exc) {
    // What should really be done in case of a parsing error?
		System.err.println("Cannot parse JSON: " + exc);
	}
	return customer;
}
```

## Part 1 "Smoke Test"

```
public static void main(String[] args) {
	Customer customer1 = new Customer();
	customer1.customerId = 2;
	customer1.name = "Martha Washington";
	customer1.address = "1600 Pennsylvania Avenue, Washington DC, 20001";
	customer1.phoneNumber = "202-555-1212";

	System.out.println(customer1);
	String jsonString = customer1.toJsonString();
	System.out.println(jsonString);
	Customer customer2 = Customer.fromJsonString(jsonString);
	System.out.println(customer2);
	System.out.println("The first and third lines should look the same!");
	System.out.println("Here is a pretty printed JSON string:");
	System.out.println(customer2.toJsonStringPretty());
}
```

## Part 1 "Smoke Test" output

```
Customer:[Id: 2; Name: Martha Washington; Address: 1600 Pennsylvania Avenue, Washington DC, 20001; Phone number: 202-555-1212]
{"customerId":2,"name":"Martha Washington","address":"1600 Pennsylvania Avenue, Washington DC, 20001","phoneNumber":"202-555-1212"}
Customer:[Id: 2; Name: Martha Washington; Address: 1600 Pennsylvania Avenue, Washington DC, 20001; Phone number: 202-555-1212]
The first and third lines should look the same!
Here is a pretty printed JSON string:
{
  "customerId": 2,
  "name": "Martha Washington",
  "address": "1600 Pennsylvania Avenue, Washington DC, 20001",
  "phoneNumber": "202-555-1212"
}
```

## Part 1 Summary

Congratulations! You can convert a Java class to and from JSON!

# Part 2:

Part 2 is common topics that occur when exchanging JSON objects.

- In particular:
  - Components
  - Collections
  - Enumerated types

# Intermediate: What about component objects?

## Adding an Address type
Let's make Address a type:

```
+-----------------------+
| Address               |
+-----------------------+
| street1: String       |
| street2: String       |
| city: String          |
| state: String         |
| country: String (usa) |
| zipcode: String       |
+-----------------------+
```

## Adding an Address type to Customer

```
+-------------------------------+
| CustomerToo                   |
+-------------------------------+
| customer id: String           |
| name: String                  |
| address: Address              |
| phone number: String          |
+-------------------------------+
```

## Let's give Address a builder

The builder pattern is a good one for classes with a lot of properties that may or may not be initialized. The alternative is _way_ too many constructors!

Note that the `Gson` library _also_ uses a builder pattern to construct its JSON generator and parser

```
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
```

## "Smoke test"

```
public static void main(String[] args) {
    CustomerToo customer1 = new CustomerToo();
    customer1.customerId = 2;
    customer1.name = "Martha Washington";
    customer1.address = new Address.AddressBuilder()
        .withStreet1("1600 Pennsylvania Avenue")
        .withCity("Washington DC")
        .withZipcode("20001")
        .build();
    customer1.phoneNumber = "202-555-1212";

    System.out.println(customer1);
    String jsonString = customer1.toJsonString();
    System.out.println(jsonString);
    CustomerToo customer2 = CustomerToo.fromJsonString(jsonString);
    System.out.println(customer2);
    System.out.println("The first and third lines should look the same!");
    System.out.println("Here is a pretty printed JSON string:");
    System.out.println(customer2.toJsonStringPretty());
}
```

## "Smoke Test" output

```
Customer:[Id: 2; Name: Martha Washington; Address: Address: [Street: 1600 Pennsylvania Avenue; City: Washington DC; Country: usa; Zip: 20001]; Phone number: 202-555-1212]
{"customerId":2,"name":"Martha Washington","address":{"street1":"1600 Pennsylvania Avenue","city":"Washington DC","country":"usa","zipcode":"20001"},"phoneNumber":"202-555-1212"}
Customer:[Id: 2; Name: Martha Washington; Address: Address: [Street: 1600 Pennsylvania Avenue; City: Washington DC; Country: usa; Zip: 20001]; Phone number: 202-555-1212]
The first and third lines should look the same!
Here is a pretty printed JSON string:
{
  "customerId": 2,
  "name": "Martha Washington",
  "address": {
    "street1": "1600 Pennsylvania Avenue",
    "city": "Washington DC",
    "country": "usa",
    "zipcode": "20001"
  },
  "phoneNumber": "202-555-1212"
}
```

# Intermediate: What about enumerated types

The default handling of enumerated types does not require any differences.

## Enumerated Type: Adding to the design

Let's give our Customer a CustomerType field:

```
+-------------------------------+
| CustomerThree                 |
+-------------------------------+
| customer id: String           |
| name: String                  |
| address: String               |
| phone number: String          |
| type: CustomerType            |
+-------------------------------+
```

## Enumerated Type: Adding to the code

Or, in code:

```
public enum CustomerType {
  INDIVIDUAL, SMALL_BUSINESS, CORPORATE
}

public class CustomerThree {
  // ...
  private CustomerType type;
  // ...
  public static void main(String[] args) {
    // ...
    customer1.type = CustomerType.INDIVIDUAL;
  }
}
```

## Enumerated Type: Output

```
Customer:[Id: 2; Name: Martha Washington; Address: Address: [Street: 1600 Pennsylvania Avenue; City: Washington DC; Country: usa; Zip: 20001]; Phone number: 202-555-1212; Customer type: INDIVIDUAL]
{"customerId":2,"name":"Martha Washington","address":{"street1":"1600 Pennsylvania Avenue","city":"Washington DC","country":"usa","zipcode":"20001"},"phoneNumber":"202-555-1212","type":"INDIVIDUAL"}
Customer:[Id: 2; Name: Martha Washington; Address: Address: [Street: 1600 Pennsylvania Avenue; City: Washington DC; Country: usa; Zip: 20001]; Phone number: 202-555-1212; Customer type: INDIVIDUAL]
The first and third lines should look the same!
Here is a pretty printed JSON string:
{
  "customerId": 2,
  "name": "Martha Washington",
  "address": {
    "street1": "1600 Pennsylvania Avenue",
    "city": "Washington DC",
    "country": "usa",
    "zipcode": "20001"
  },
  "phoneNumber": "202-555-1212",
  "type": "INDIVIDUAL"
}
```

# Intermediate What about Collections?

Similar to enumerations, Collections of identically-typed objects also require no special handling.

## Collections: Add to the design

Let's give our Customer a collection of parking ids (String)

Again, the default handling works well.

```
+-------------------------------+
| CustomerFour                  |
+-------------------------------+
| customer id: String           |
| name: String                  |
| address: String               |
| phone number: String          |
| type: CustomerType            |
| parking ids: List of String   |
+-------------------------------+
```

## Collections: Add to the code

Or, in abbreviated code:

```
public class CustomerFour {
  // ...
  private List<String> parkingIds = new ArrayList<>();
  // ...
  public static void main(String[] args) {
    // ...
    customer1.parkingIds.add("L17P33");
    customer1.parkingIds.add("L19P11");
    // ...
  }
}
```

## Collections: Output

```
Customer:[Id: 2; Name: Martha Washington; Address: Address: [Street: 1600 Pennsylvania Avenue; City: Washington DC; Country: usa; Zip: 20001]; Phone number: 202-555-1212; Customer type: INDIVIDUAL; Parking ids: [L17P33, L19P11]]
{"customerId":2,"name":"Martha Washington","address":{"street1":"1600 Pennsylvania Avenue","city":"Washington DC","country":"usa","zipcode":"20001"},"phoneNumber":"202-555-1212","type":"INDIVIDUAL","parkingIds":["L17P33","L19P11"]}
Customer:[Id: 2; Name: Martha Washington; Address: Address: [Street: 1600 Pennsylvania Avenue; City: Washington DC; Country: usa; Zip: 20001]; Phone number: 202-555-1212; Customer type: INDIVIDUAL; Parking ids: [L17P33, L19P11]]
The first and third lines should look the same!
Here is a pretty printed JSON string:
{
  "customerId": 2,
  "name": "Martha Washington",
  "address": {
    "street1": "1600 Pennsylvania Avenue",
    "city": "Washington DC",
    "country": "usa",
    "zipcode": "20001"
  },
  "phoneNumber": "202-555-1212",
  "type": "INDIVIDUAL",
  "parkingIds": [
    "L17P33",
    "L19P11"
  ]
}
```

## Collections: Summary

Note that in this example the collection was all of a single base type. This is more complicated if the collection is of various derived or implementing types. This topic will be addressed later.

# Part 3: Advanced Topics

This is still a beginner's tutorial, so these advanced topics may seem tame!

* These topics include:
	- Changing the name of the JSON property from the class field name
	- Ignoring some fields (not including them in the JSON)
	- Adapters for working with types that don't automatically serialize and deserialize
	- Working with collections of objects derived or implementing a base type or interface

Code and output are not shown for these topics. Examine and run the code samples to see these.

## Advanced: Changing the serialized name

What if your required JSON format doesn't quite agree with your class?

If it is due to naming policies, Gson has you covered!

* There are the following built-in enum constants to take care of it in `FieldNamingPolicy`
	- `IDENTITY`: Must be unchanged
	- `LOWER_CASE_WITH_DASHES`: cardFace becomes card-face
	- `LOWER_CASE_WITH_UNDERSCORES`: cardFace becomes card_face
	- `UPPER_CAMEL_CASE`: cardFace becomes CardFace
	- `UPPER_CAME_CASE_WITH_SPACES`: cardFace becomes CARD FACE

Example: See `CustomerFive.java`

Usage example:

```
GsonBuilder builder = new GsonBuilder();
Gson gson = builder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_DASHES)
                   .create();
gson.toJson(yourObject);
```

Further customization can be done by implementing the FieldNamingStrategy interface in other ways.

## Advanced: Alternate names (@SerializedName)

* This annotation can be used two different ways
	- The JSON property name can be set
	- Alternate JSON property names can be guided to a canonical property name

```
public class CustomerSix {
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
	// ...
}
```

Also in this example, JSON String (and attributes "type" and "ctype" fold in to "customer-type")

## Advanced: Alternate names result fragment

```
{
  "id": 2,
  "customer-name": "Martha Washington",
  "customer-address": {
    "street1": "1600 Pennsylvania Avenue",
    "city": "Washington DC",
    "country": "usa",
    "zipcode": "20001"
  },
  "customer-phone": "202-555-1212",
  "customer-type": "INDIVIDUAL"
}
```

## Advanced: Adapters (@JsonAdapter): Writing the adapter

Some classes are not supported by the Gson framework.

One example is `java.time.LocalDate`. This can be inconvenient if you want to exchange a date in your JSON.

Gson provides an extension mechanism to allow the programmer to establish how such a class is read and written.

Here is an example for `LocalDate`, using its internal `toString()` and `parse()` methods to do the heavy lifting. This is almost always how `TypeAdapter`s work.

```
public final class LocalDateAdapter extends TypeAdapter<LocalDate> {

    @Override
    public void write( final JsonWriter jsonWriter, final LocalDate localDate ) throws IOException {
        jsonWriter.value(localDate.toString());
    }

    @Override
    public LocalDate read( final JsonReader jsonReader ) throws IOException {
        return LocalDate.parse(jsonReader.nextString());
    }
}
```

## Advanced: Adapters (@JsonAdapter): Using the adapter

The adapter is applied in the class through use of the `@JsonAdapter` annotation:

```
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

  @JsonAdapter(LocalDateAdapter.class)
  private LocalDate birthday;
```

## Advanced: Transient fields (transient)

A variable is marked `transient` in Java if it should not take part in serialization. This is true whether using Gson or not.

Fields marked `transient` do not become JSON properties.

```
public class CustomerSix {
  // ...
  @SerializedName(value="customer-type", alternate={"type","ctype"})
  private CustomerType type;
	@JsonAdapter(LocalDateAdapter.class)
  private LocalDate birthday;
  private transient int age;
  private List<String> parkingIds = new ArrayList<>();
```

The `age` property (which is computed based on the current date and the birthday) will not be present in the JSON representation

## Advanced: Ignoring fields (@Expose)

A class can explicitly list which fields it wishes to include in the JSON by

1. Marking those fields with the `@Expose` annotation; and
2. Including `excludeFieldsWithoutExposeAnnotation()` in the builder construction:

```
Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
```

Note that this technique allows construction of serializers that include all the fields by simply omitting the `excludeFieldsWithoutExposeAnnotation` from the `GsonBuilder` construction.

Even more advanced cases could use `setExclusionStrategies` to provide rules for exclusion, including class name matching, specific class types, or looking for specific annotations. Serialization and deserialization exclusion strategies can be set up separately.

## Advanced: Containers with multiple derived types

This situation requires storing a discriminant in the record for reconstruction. The class name is the right discriminant value to use.

This is done using the `TypeAdapter` class or `JsonSerializer` and `JsonDeserializer` interfaces.
The adapter can be registered for general use via the GsonBuilder, or applied directly with the `JsonAdapter` method.

This method is especially useful with library types with no access to source code. It works by adding the class (as a string) to the serialized object, and obtaining and using that type in deserialization.

With classes one does have source to, adding a discriminator can be done in the class itself.

## Advanced: Polymorphic containers: `Shape`

* This example is built around a `Shape` base class and 3 specializations:
 	- Circle
 	- Rectangle
 	- Square

```
abstract public class Shape {
  abstract public double getArea();
  abstract public double getPerimeter();
}
```

## Advanced: Polymorphic containers: `JsonSerializer` &amp; `JsonDeserializer`

```
public class ShapeSerialization implements JsonSerializer, JsonDeserializer {
  // Data to add to each object
  private static final String CLASSNAME = "CLASSNAME";
  private static final String DATA = "DATA";
  @Override
  public JsonElement serialize(Object jsonElement, Type type,
                               JsonSerializationContext jsonSerializationContext) {
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty(CLASSNAME, jsonElement.getClass().getName());
    jsonObject.add(DATA, jsonSerializationContext.serialize(jsonElement));
    return jsonObject;
  }
  @Override
  public Shape deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context)
      throws JsonParseException {
    JsonObject jsonObject = jsonElement.getAsJsonObject();
    JsonPrimitive primitive = (JsonPrimitive) jsonObject.get(CLASSNAME);
    String className = primitive.getAsString();
    Class objectClass = getObjectClass(className);
    return context.deserialize(jsonObject.get(DATA), objectClass);
  }
  public Class getObjectClass(String className) {
    try {
      return Class.forName(className);
    } catch (ClassNotFoundException e) {
      throw new JsonParseException(e.getMessage());
    }
  }
}
```

## Advanced: Polymorphic containers: Using the serialization

```
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
```

## Advanced: Polymorphic containers: Sample output

Note the way the type wraps the data in the JSON representation

```
[Square: [ side: 16.0 ], Square: [ side: 4.5 ], Rectangle: [ length: 10.0, height: 20.0 ], Circle: [ radius: 12.5 ]]
[{"CLASSNAME":"edu.du.ict4315.gsontutorial.Square","DATA":{"length":16.0}},{"CLASSNAME":"edu.du.ict4315.gsontutorial.Square","DATA":{"length":4.5}},{"CLASSNAME":"edu.du.ict4315.gsontutorial.Rectangle","DATA":{"length":10.0,"height":20.0}},{"CLASSNAME":"edu.du.ict4315.gsontutorial.Circle","DATA":{"radius":12.5}}]
[Square: [ side: 16.0 ], Square: [ side: 4.5 ], Rectangle: [ length: 10.0, height: 20.0 ], Circle: [ radius: 12.5 ]]
```


# Summary

* With the Part 1: Simple Gson, I believe you can handle most of your JSON needs
* With the Part 2: Intermediate Gson, you can handle almost every situation easily.
* With the Part 3: Advanced Gson, several of the remaining corners are explored.
* If what you need is not covered in any of these 3 sections, I highly recommend you consult the Gson User Guide referenced above.

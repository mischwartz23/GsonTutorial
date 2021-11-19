/*
 * This file is part of the tutorial on Gson
 * see https://github.com/mischwartz23/GsonTutorial.git
 * Author: M I Schwartz
 */
package edu.du.ict4315.gsontutorial;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

/**
 *
 * @author michael
 */
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

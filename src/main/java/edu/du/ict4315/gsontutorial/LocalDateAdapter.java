/*
 * This file is part of the tutorial on Gson
 * see https://github.com/mischwartz23/GsonTutorial.git
 * Author: M I Schwartz
 */
package edu.du.ict4315.gsontutorial;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.time.LocalDate;

/**
 * File: LocalDateAdapter.java
 * Note: This class could be placed as a private or inner class in CustomerSix
 * Note: Extending TypeAdapter is preferred to implementing a JsonSerializer
 * @author michael
 */
// Creating an adapter (could be in its own file)
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

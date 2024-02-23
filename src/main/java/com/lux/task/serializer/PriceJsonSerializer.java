package com.lux.task.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class PriceJsonSerializer extends JsonSerializer<Double> {
    private static final DecimalFormat PRICE_FORMAT = new DecimalFormat("#.##");

    static {
        PRICE_FORMAT.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.US));
    }

    @Override
    public void serialize(Double value, JsonGenerator jsonGenerator, SerializerProvider provider)
            throws IOException {
        String formatted = PRICE_FORMAT.format(value);
        jsonGenerator.writeString(formatted);
    }
}
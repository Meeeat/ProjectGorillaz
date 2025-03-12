package com.javarush.siberia.model.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.postgresql.util.PGobject;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import static com.javarush.siberia.util.AppConstants.ERROR_UNABLE_TO_JSONB;
import static com.javarush.siberia.util.AppConstants.ERROR_UNABLE_TO_MAP;

@Converter(autoApply = true)
public class JsonbType implements AttributeConverter<Map<String, String>, PGobject> {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public PGobject convertToDatabaseColumn(Map<String, String> attribute) {
        try {
            PGobject pgObject = new PGobject();
            pgObject.setType("jsonb");
            if (attribute == null) {
                pgObject.setValue(null);
            } else {
                pgObject.setValue(mapper.writeValueAsString(attribute));
            }
            return pgObject;
        } catch (JsonProcessingException | SQLException e) {
            throw new IllegalArgumentException(ERROR_UNABLE_TO_JSONB, e);
        }
    }

    @Override
    public Map<String, String> convertToEntityAttribute(PGobject dbData) {
        try {
            if (dbData == null || dbData.getValue() == null) {
                return null;
            }
            return mapper.readValue(dbData.getValue(), Map.class);
        } catch (IOException e) {
            throw new IllegalArgumentException(ERROR_UNABLE_TO_MAP, e);
        }
    }
}
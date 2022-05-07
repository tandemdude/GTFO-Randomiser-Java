package io.github.tandemdude.gtforandomiser.repositories;

import com.fasterxml.jackson.databind.json.JsonMapper;
import io.github.tandemdude.gtforandomiser.models.data.Stage;
import lombok.SneakyThrows;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class StageConverter implements AttributeConverter<Stage, String> {
    private static final JsonMapper MAPPER = new JsonMapper();

    @Override
    @SneakyThrows
    public String convertToDatabaseColumn(Stage name) {
        return MAPPER.writeValueAsString(name);
    }

    @Override
    @SneakyThrows
    public Stage convertToEntityAttribute(String json) {
        return MAPPER.readValue(json, Stage.class);
    }
}

package io.github.tandemdude.gtforandomiser.repositories;

import com.fasterxml.jackson.databind.json.JsonMapper;
import io.github.tandemdude.gtforandomiser.models.data.FullLoadout;
import lombok.SneakyThrows;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class FullLoadoutConverter implements AttributeConverter<FullLoadout, String> {
    private static final JsonMapper MAPPER = new JsonMapper();

    @Override
    @SneakyThrows
    public String convertToDatabaseColumn(FullLoadout name) {
        return MAPPER.writeValueAsString(name);
    }

    @Override
    @SneakyThrows
    public FullLoadout convertToEntityAttribute(String json) {
        return MAPPER.readValue(json, FullLoadout.class);
    }
}

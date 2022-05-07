package io.github.tandemdude.gtforandomiser.repositories;

import com.fasterxml.jackson.databind.json.JsonMapper;
import io.github.tandemdude.gtforandomiser.models.data.Loadout;
import lombok.SneakyThrows;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class LoadoutConverter implements AttributeConverter<Loadout, String> {
    private static final JsonMapper MAPPER = new JsonMapper();

    @Override
    @SneakyThrows
    public String convertToDatabaseColumn(Loadout name) {
        return MAPPER.writeValueAsString(name);
    }

    @Override
    @SneakyThrows
    public Loadout convertToEntityAttribute(String json) {
        return MAPPER.readValue(json, Loadout.class);
    }
}

package com.example.memderadmin.infra;

import com.example.memderadmin.domain.Gender;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class GenderConverter implements AttributeConverter<Gender, String> {
    @Override
    public String convertToDatabaseColumn(Gender type) {
        if (type == null) {
            return null;
        }
        return type.getCode();
    }

    @Override
    public Gender convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }
        return Gender.ofCode(code);
    }
}

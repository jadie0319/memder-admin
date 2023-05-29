package com.example.memderadmin.infra;


import com.example.memderadmin.domain.Role;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class RoleConverter implements AttributeConverter<Role, String> {
    @Override
    public String convertToDatabaseColumn(Role type) {
        if (type == null) {
            return null;
        }
        return type.name();
    }

    @Override
    public Role convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }
        return Role.valueOf(code);
    }
}

package com.podo.climb.entity.converter;

import com.podo.climb.model.OauthType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class OauthTypeConverter implements AttributeConverter<OauthType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(OauthType attribute) {
        return attribute.getValue();
    }

    @Override
    public OauthType convertToEntityAttribute(Integer dbData) {
        return OauthType.valueOf(dbData);
    }

}

package com.podo.climb.entity.converter;

import com.podo.climb.model.MemberRoleType;
import com.podo.climb.model.OauthType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class MemberRoleTypeConverter implements AttributeConverter<MemberRoleType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(MemberRoleType attribute) {
        return attribute.getValue();
    }

    @Override
    public MemberRoleType convertToEntityAttribute(Integer dbData) {
        return MemberRoleType.valueOf(dbData);
    }

}

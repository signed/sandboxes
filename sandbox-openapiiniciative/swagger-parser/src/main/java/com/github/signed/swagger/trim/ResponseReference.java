package com.github.signed.swagger.trim;

import java.lang.reflect.Field;

import io.swagger.models.RefResponse;
import io.swagger.models.refs.GenericRef;

public class ResponseReference {

    private final RefResponse refResponse;

    public ResponseReference(RefResponse refResponse) {
        this.refResponse = refResponse;
    }

    public String responseIdentifier() {
        GenericRef genericRef = accessGenericRef();

        return genericRef.getSimpleRef();
    }

    private GenericRef accessGenericRef(){
        try {
            Field field = refResponse.getClass().getDeclaredField("genericRef");
            field.setAccessible(true);
            return (GenericRef)field.get(refResponse);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Should not happen");
        }
    }

}

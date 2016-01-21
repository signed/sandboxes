package com.github.signed.swagger;

import java.util.function.Supplier;

import io.swagger.parser.SwaggerParser;
import io.swagger.parser.util.SwaggerDeserializationResult;

public class SwaggerValidator {

    public final Supplier<String> schemaSupplier;

    public SwaggerValidator(Supplier<String> schemaSupplier) {
        this.schemaSupplier = schemaSupplier;
    }

    public ValidationResult validate(JsonBlob swagger) {
        ValidationResultBuilder validationResultBuilder = new ValidationResultBuilder();
        getBasicMessagesFromSwaggerParser(swagger, validationResultBuilder);
        return validationResultBuilder.build();
    }

    private void getBasicMessagesFromSwaggerParser(JsonBlob swagger, ValidationResultBuilder validationResultBuilder) {
        SwaggerDeserializationResult result = new SwaggerParser().readWithInfo(swagger.asString());
        result.getMessages().forEach(validationResultBuilder::addMessageFromSwaggerParser);
    }
}

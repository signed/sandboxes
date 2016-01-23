package com.github.signed.swagger.validate;

import static java.util.Optional.ofNullable;

import java.util.Collections;
import java.util.function.Supplier;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonschema.core.report.ProcessingMessage;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;

import io.swagger.parser.SwaggerParser;
import io.swagger.parser.util.SwaggerDeserializationResult;
import io.swagger.util.Json;

public class SwaggerValidate {

    public final Supplier<String> schemaSupplier;

    public SwaggerValidate(Supplier<String> schemaSupplier) {
        this.schemaSupplier = schemaSupplier;
    }

    public ValidationResult validate(JsonBlob swaggerDefinition) {
        ValidationResultBuilder validationResultBuilder = new ValidationResultBuilder();
        getBasicMessagesFromSwaggerParser(swaggerDefinition, validationResultBuilder);
        validateAgainstJsonSchema(swaggerDefinition, validationResultBuilder);
        return validationResultBuilder.build();
    }

    private void getBasicMessagesFromSwaggerParser(JsonBlob swagger, ValidationResultBuilder validationResultBuilder) {
        SwaggerDeserializationResult result = new SwaggerParser().readWithInfo(swagger.asString());
        ofNullable(result.getMessages()).orElse(Collections.emptyList()).forEach(message -> validationResultBuilder.message().level("error").message(message));
    }

    private void validateAgainstJsonSchema(JsonBlob swagger, ValidationResultBuilder validationResultBuilder) {
        try {
            JsonNode schemaObject = Json.mapper().readTree(schemaSupplier.get());
            JsonNode spec = Json.mapper().readTree(swagger.asString());

            JsonSchemaFactory factory = JsonSchemaFactory.byDefault();
            JsonSchema schema = factory.getJsonSchema(schemaObject);
            ProcessingReport report = schema.validate(spec);

            for (ProcessingMessage pm : report) {
                JsonNode jsonNode = pm.asJson();

                validationResultBuilder.message().level(levelFor(pm)).message(jsonNode.get("message").asText());
            }
        } catch (Exception ex) {
            validationResultBuilder.schemaValidationThrowAnExceptionFailed(ex);
        }
    }

    private String levelFor(ProcessingMessage report) {
        switch (report.getLogLevel()) {
            case DEBUG:
                return "debug";
            case ERROR:
                return "error";
            case FATAL:
                return "fatal";
            case INFO:
                return "info";
            case NONE:
                return "none";
            case WARNING:
                return "warning";
            default:
                throw new UnsupportedOperationException();
        }
    }
}

package com.github.signed.swagger;

import java.util.Optional;

import io.swagger.models.RefResponse;
import io.swagger.models.Response;

public class ResponseBuilder {
    private Optional<String> maybeResponseDefinitionIdentifier = Optional.empty();
    private Optional<String> maybeDescription = Optional.empty();
    private Optional<PropertyBuilder> maybePropertyBuilder = Optional.empty();

    public ResponseBuilder withDescription(String description) {
        maybeDescription = Optional.of(description);
        return this;
    }

    public ResponseBuilder referencingResponseDefinition(String responseDefinitionIdentifier){
        maybeResponseDefinitionIdentifier = Optional.ofNullable(responseDefinitionIdentifier);
        return this;
    }

    public ResponseBuilder withSchema(PropertyBuilder propertyBuilder){
        this.maybePropertyBuilder = Optional.of(propertyBuilder);
        return this;
    }

    public Response build() {
        Response response = maybeResponseDefinitionIdentifier.map(reference -> (Response) new RefResponse(reference)).orElse(new Response());
        maybePropertyBuilder.ifPresent(builder -> response.setSchema(builder.build()));
        maybeDescription.ifPresent(response::setDescription);
        return response;
    }
}

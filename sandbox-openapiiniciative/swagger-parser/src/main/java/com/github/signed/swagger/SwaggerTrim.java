package com.github.signed.swagger;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;
import static java.util.Optional.ofNullable;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import io.swagger.models.Model;
import io.swagger.models.Operation;
import io.swagger.models.Path;
import io.swagger.models.RefModel;
import io.swagger.models.Swagger;
import io.swagger.models.Tag;
import io.swagger.models.parameters.BodyParameter;
import io.swagger.models.parameters.Parameter;

public class SwaggerTrim {

    private final ParameterDispatch parameterDispatch = new ParameterDispatch();

    public Swagger trim(Swagger swagger) {
        Set<String> tagReferences = ofNullable(swagger.getPaths()).orElse(emptyMap()).values().stream().map(allTagsReferencedInPath()).flatMap(Set::stream).collect(Collectors.toSet());
        List<Tag> referencedTagDefinitions = ofNullable(swagger.getTags()).orElse(emptyList()).stream().filter(tag -> tagReferences.contains(tag.getName())).collect(Collectors.toList());
        swagger.setTags((referencedTagDefinitions.isEmpty()) ? null : referencedTagDefinitions);

        Set<String> definitionReferences = ofNullable(swagger.getPaths()).orElse(Collections.emptyMap()).values().stream().map(path -> ofNullable(path.getParameters()).orElse(emptyList())).flatMap(List::stream)
                .map(parameterDispatch::getReference).filter(Optional::isPresent).map(Optional::get).map(RefModel::getSimpleRef).collect(Collectors.toSet());

        Map<String, Model> referencedDefinitions = ofNullable(swagger.getDefinitions()).orElse(Collections.emptyMap()).entrySet().stream()
                .filter(stringModelEntry -> definitionReferences.contains(stringModelEntry.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        swagger.setDefinitions(referencedDefinitions.isEmpty() ? null : referencedDefinitions);

        return swagger;
    }

    public static class ParameterDispatch {

        public Optional<RefModel> getReference(Parameter parameter) {
            if (parameter instanceof BodyParameter) {
                BodyParameter bodyParameter = (BodyParameter) parameter;
                Model schema = bodyParameter.getSchema();
                if (schema instanceof RefModel) {
                    return Optional.of((RefModel) schema);
                } else {
                    throw new RuntimeException("still work to do");
                }
            }
            return Optional.empty();
        }

    }

    private Function<Path, Set<String>> allTagsReferencedInPath() {
        return path -> path.getOperations().stream()
                .map(Operation::getTags)
                .map(strings -> (null == strings) ? Collections.<String>emptyList() : strings)
                .flatMap(List::stream)
                .collect(Collectors.toSet());
    }
}

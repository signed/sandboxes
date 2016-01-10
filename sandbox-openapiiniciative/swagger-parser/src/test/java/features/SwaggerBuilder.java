package features;

import java.util.Optional;

import io.swagger.models.Path;
import io.swagger.models.Swagger;

public class SwaggerBuilder {

    private Optional<String> path = Optional.empty();


    public SwaggerBuilder withPath(String path) {
        this.path = Optional.of(path);
        return this;
    }

    public Swagger build() {
        Swagger swagger = new Swagger();
        path.ifPresent(s -> swagger.path(s, new Path()));
        return swagger;
    }
}

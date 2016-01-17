package com.github.signed.swagger;

public class TestFiles {

    public static class Json{

        public static String petstoreExample() {
            return "src/test/resources/petstore-expanded.json";
        }
    }

    public static class Yaml{

        public static String mapDefinition() {
            return "src/test/resources/map-definitions.yaml";
        }

        public static String modelWithComposition() {
            return "src/test/resources/models-with-composition.yaml";
        }

    }
}

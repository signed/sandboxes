package language.pattern

//http://groovy.codehaus.org/Decorator+Pattern
class Decorator {
    private static class Logger {
        def log(String message) {
            println message
        }
    }

}

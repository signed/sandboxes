# How to run #
most of the configuration is done in `application.yml`.
There is a profile for each service that is configured with spring application builder and therefore no configuration spills over between the services.

1. run `example.eureka.EurekaApplication` to start a local eureka server
   The server will connect to itself as initial connect.
1. run `example.zuul.ZuulBootApplication` to start a local zuul server
   1. zuul registers as a client with the running eureka server
   1. is setup to forward calls to banana and apple applications (route based)
1. run `example.backend.appel.AppleApplication` and `example.backend.banana.BananaApplication`
   1. booth will register as instances with eureka server
   1. send basic `HTTP GET` at the `localhost:<port>/` to pick fruits
1. send `HTTP GET` to  `localhost:8035/apples` or `localhost:8035/bananas`. Zuul will discover running services via eureka and forward the calls

As soon as all services are running `example.zuul.ZuulBootApplicationTest` should run successfully.
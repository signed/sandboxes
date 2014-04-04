package com.github.signed.integration.camel;

import java.util.Collection;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.camel.CamelContext;
import org.apache.camel.Component;
import org.apache.camel.Endpoint;
import org.apache.camel.ErrorHandlerFactory;
import org.apache.camel.Processor;
import org.apache.camel.Route;
import org.apache.camel.Service;
import org.apache.camel.VetoCamelContextStartException;
import org.apache.camel.spi.LifecycleStrategy;
import org.apache.camel.spi.RouteContext;

public class LifecycleStrategyAdapter implements LifecycleStrategy {
    @Override
    public void onContextStart(CamelContext context) throws VetoCamelContextStartException {

    }

    @Override
    public void onContextStop(CamelContext context) {

    }

    @Override
    public void onComponentAdd(String name, Component component) {

    }

    @Override
    public void onComponentRemove(String name, Component component) {

    }

    @Override
    public void onEndpointAdd(Endpoint endpoint) {

    }

    @Override
    public void onEndpointRemove(Endpoint endpoint) {

    }

    @Override
    public void onServiceAdd(CamelContext context, Service service, Route route) {

    }

    @Override
    public void onServiceRemove(CamelContext context, Service service, Route route) {

    }

    @Override
    public void onRoutesAdd(Collection<Route> routes) {

    }

    @Override
    public void onRoutesRemove(Collection<Route> routes) {

    }

    @Override
    public void onRouteContextCreate(RouteContext routeContext) {

    }

    @Override
    public void onErrorHandlerAdd(RouteContext routeContext, Processor errorHandler, ErrorHandlerFactory errorHandlerBuilder) {

    }

    @Override
    public void onErrorHandlerRemove(RouteContext routeContext, Processor errorHandler, ErrorHandlerFactory errorHandlerBuilder) {

    }

    @Override
    public void onThreadPoolAdd(CamelContext camelContext, ThreadPoolExecutor threadPool, String id, String sourceId, String routeId, String threadPoolProfileId) {

    }

    @Override
    public void onThreadPoolRemove(CamelContext camelContext, ThreadPoolExecutor threadPool) {

    }
}

package com.github.signed.tryanderror.jersey;

import com.github.signed.tryanderror.jersey.resources.GuiceResourceProvider;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.servlet.GuiceServletContextListener;
import com.sun.jersey.guice.JerseyServletModule;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

public class GuiceConfig extends GuiceServletContextListener {
   @Override
   protected Injector getInjector() {
       System.out.println("\ncreate the injector\n");
       JerseyServletModule jerseyServletModule = new ResourceModule();
       ServiceLoader<GuiceResourceProvider> serviceLoader = ServiceLoader.load(GuiceResourceProvider.class);

       List<Module> modules = new ArrayList<Module>();
       modules.add(jerseyServletModule);

       for(GuiceResourceProvider provider:serviceLoader){
           modules.add(provider.getResourceModule());
       }

       return Guice.createInjector(modules);
   }
}
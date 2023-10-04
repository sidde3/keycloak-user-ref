package org.example.demo.spi;

import org.example.demo.spi.service.ServiceProvider;
import org.example.demo.spi.service.ServiceProviderFactory;
import org.keycloak.provider.Provider;
import org.keycloak.provider.ProviderFactory;
import org.keycloak.provider.Spi;

public class SimpleCustomSPI implements Spi {
    @Override
    public boolean isInternal() {
        return false;
    }

    @Override
    public String getName() {
        return "custom-spi";
    }

    @Override
    public Class<? extends Provider> getProviderClass() {
        return ServiceProvider.class;
    }

    @Override
    public Class<? extends ProviderFactory> getProviderFactoryClass() {
        return ServiceProviderFactory.class;
    }
}

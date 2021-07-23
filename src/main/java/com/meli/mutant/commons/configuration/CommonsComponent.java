package com.meli.mutant.commons.configuration;

import dagger.Component;

import javax.inject.Singleton;

import com.meli.mutant.handler.Mutant;
import com.meli.mutant.handler.Stats;

@Singleton
@Component(modules = {CommonsModule.class})
public interface CommonsComponent {
    void inject(Mutant mutant);
    void inject(Stats stats);
}
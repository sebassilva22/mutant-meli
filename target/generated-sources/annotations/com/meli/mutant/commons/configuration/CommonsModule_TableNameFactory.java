package com.meli.mutant.commons.configuration;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;

@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class CommonsModule_TableNameFactory implements Factory<String> {
  private final CommonsModule module;

  public CommonsModule_TableNameFactory(CommonsModule module) {
    this.module = module;
  }

  @Override
  public String get() {
    return tableName(module);
  }

  public static CommonsModule_TableNameFactory create(CommonsModule module) {
    return new CommonsModule_TableNameFactory(module);
  }

  public static String tableName(CommonsModule instance) {
    return Preconditions.checkNotNullFromProvides(instance.tableName());
  }
}

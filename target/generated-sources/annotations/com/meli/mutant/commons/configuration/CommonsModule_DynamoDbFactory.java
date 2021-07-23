package com.meli.mutant.commons.configuration;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class CommonsModule_DynamoDbFactory implements Factory<DynamoDbClient> {
  private final CommonsModule module;

  public CommonsModule_DynamoDbFactory(CommonsModule module) {
    this.module = module;
  }

  @Override
  public DynamoDbClient get() {
    return dynamoDb(module);
  }

  public static CommonsModule_DynamoDbFactory create(CommonsModule module) {
    return new CommonsModule_DynamoDbFactory(module);
  }

  public static DynamoDbClient dynamoDb(CommonsModule instance) {
    return Preconditions.checkNotNullFromProvides(instance.dynamoDb());
  }
}

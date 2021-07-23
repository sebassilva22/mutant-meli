package com.meli.mutant.commons.configuration;

import com.meli.mutant.dal.dao.IMutantDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import javax.inject.Provider;
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
public final class CommonsModule_MutantImplementFactory implements Factory<IMutantDao> {
  private final CommonsModule module;

  private final Provider<DynamoDbClient> dynamoDbProvider;

  private final Provider<String> tableNameProvider;

  public CommonsModule_MutantImplementFactory(CommonsModule module,
      Provider<DynamoDbClient> dynamoDbProvider, Provider<String> tableNameProvider) {
    this.module = module;
    this.dynamoDbProvider = dynamoDbProvider;
    this.tableNameProvider = tableNameProvider;
  }

  @Override
  public IMutantDao get() {
    return mutantImplement(module, dynamoDbProvider.get(), tableNameProvider.get());
  }

  public static CommonsModule_MutantImplementFactory create(CommonsModule module,
      Provider<DynamoDbClient> dynamoDbProvider, Provider<String> tableNameProvider) {
    return new CommonsModule_MutantImplementFactory(module, dynamoDbProvider, tableNameProvider);
  }

  public static IMutantDao mutantImplement(CommonsModule instance, DynamoDbClient dynamoDb,
      String tableName) {
    return Preconditions.checkNotNullFromProvides(instance.mutantImplement(dynamoDb, tableName));
  }
}

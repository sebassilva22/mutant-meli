package com.meli.mutant.commons.configuration;

import com.meli.mutant.dal.dao.IMutantDao;
import com.meli.mutant.handler.Mutant;
import com.meli.mutant.handler.Mutant_MembersInjector;
import com.meli.mutant.handler.Stats;
import com.meli.mutant.handler.Stats_MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.DoubleCheck;
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
public final class DaggerCommonsComponent implements CommonsComponent {
  private Provider<DynamoDbClient> dynamoDbProvider;

  private Provider<String> tableNameProvider;

  private Provider<IMutantDao> mutantImplementProvider;

  private DaggerCommonsComponent(CommonsModule commonsModuleParam) {

    initialize(commonsModuleParam);
  }

  public static Builder builder() {
    return new Builder();
  }

  public static CommonsComponent create() {
    return new Builder().build();
  }

  @SuppressWarnings("unchecked")
  private void initialize(final CommonsModule commonsModuleParam) {
    this.dynamoDbProvider = DoubleCheck.provider(CommonsModule_DynamoDbFactory.create(commonsModuleParam));
    this.tableNameProvider = DoubleCheck.provider(CommonsModule_TableNameFactory.create(commonsModuleParam));
    this.mutantImplementProvider = DoubleCheck.provider(CommonsModule_MutantImplementFactory.create(commonsModuleParam, dynamoDbProvider, tableNameProvider));
  }

  @Override
  public void inject(Mutant mutant) {
    injectMutant(mutant);
  }

  @Override
  public void inject(Stats stats) {
    injectStats(stats);
  }

  private Mutant injectMutant(Mutant instance) {
    Mutant_MembersInjector.injectMutantDao(instance, mutantImplementProvider.get());
    return instance;
  }

  private Stats injectStats(Stats instance) {
    Stats_MembersInjector.injectMutantDao(instance, mutantImplementProvider.get());
    return instance;
  }

  public static final class Builder {
    private CommonsModule commonsModule;

    private Builder() {
    }

    public Builder commonsModule(CommonsModule commonsModule) {
      this.commonsModule = Preconditions.checkNotNull(commonsModule);
      return this;
    }

    public CommonsComponent build() {
      if (commonsModule == null) {
        this.commonsModule = new CommonsModule();
      }
      return new DaggerCommonsComponent(commonsModule);
    }
  }
}

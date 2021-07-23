package com.meli.mutant.handler;

import com.meli.mutant.dal.dao.IMutantDao;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import javax.annotation.Generated;
import javax.inject.Provider;

@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class Stats_MembersInjector implements MembersInjector<Stats> {
  private final Provider<IMutantDao> mutantDaoProvider;

  public Stats_MembersInjector(Provider<IMutantDao> mutantDaoProvider) {
    this.mutantDaoProvider = mutantDaoProvider;
  }

  public static MembersInjector<Stats> create(Provider<IMutantDao> mutantDaoProvider) {
    return new Stats_MembersInjector(mutantDaoProvider);
  }

  @Override
  public void injectMembers(Stats instance) {
    injectMutantDao(instance, mutantDaoProvider.get());
  }

  @InjectedFieldSignature("com.meli.mutant.handler.Stats.mutantDao")
  public static void injectMutantDao(Stats instance, IMutantDao mutantDao) {
    instance.mutantDao = mutantDao;
  }
}

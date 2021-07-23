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
public final class Mutant_MembersInjector implements MembersInjector<Mutant> {
  private final Provider<IMutantDao> mutantDaoProvider;

  public Mutant_MembersInjector(Provider<IMutantDao> mutantDaoProvider) {
    this.mutantDaoProvider = mutantDaoProvider;
  }

  public static MembersInjector<Mutant> create(Provider<IMutantDao> mutantDaoProvider) {
    return new Mutant_MembersInjector(mutantDaoProvider);
  }

  @Override
  public void injectMembers(Mutant instance) {
    injectMutantDao(instance, mutantDaoProvider.get());
  }

  @InjectedFieldSignature("com.meli.mutant.handler.Mutant.mutantDao")
  public static void injectMutantDao(Mutant instance, IMutantDao mutantDao) {
    instance.mutantDao = mutantDao;
  }
}

package com.stockanalyzer.ui.detail;

import com.stockanalyzer.data.repository.StockRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast"
})
public final class StockDetailViewModel_Factory implements Factory<StockDetailViewModel> {
  private final Provider<StockRepository> repositoryProvider;

  public StockDetailViewModel_Factory(Provider<StockRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public StockDetailViewModel get() {
    return newInstance(repositoryProvider.get());
  }

  public static StockDetailViewModel_Factory create(Provider<StockRepository> repositoryProvider) {
    return new StockDetailViewModel_Factory(repositoryProvider);
  }

  public static StockDetailViewModel newInstance(StockRepository repository) {
    return new StockDetailViewModel(repository);
  }
}

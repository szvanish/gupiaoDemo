package com.stockanalyzer.data.repository;

import com.stockanalyzer.data.api.StockApiService;
import com.stockanalyzer.data.db.WatchlistDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class StockRepository_Factory implements Factory<StockRepository> {
  private final Provider<StockApiService> apiProvider;

  private final Provider<WatchlistDao> daoProvider;

  public StockRepository_Factory(Provider<StockApiService> apiProvider,
      Provider<WatchlistDao> daoProvider) {
    this.apiProvider = apiProvider;
    this.daoProvider = daoProvider;
  }

  @Override
  public StockRepository get() {
    return newInstance(apiProvider.get(), daoProvider.get());
  }

  public static StockRepository_Factory create(Provider<StockApiService> apiProvider,
      Provider<WatchlistDao> daoProvider) {
    return new StockRepository_Factory(apiProvider, daoProvider);
  }

  public static StockRepository newInstance(StockApiService api, WatchlistDao dao) {
    return new StockRepository(api, dao);
  }
}

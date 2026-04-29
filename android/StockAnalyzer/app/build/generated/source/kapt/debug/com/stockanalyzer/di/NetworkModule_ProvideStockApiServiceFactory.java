package com.stockanalyzer.di;

import com.stockanalyzer.data.api.StockApiService;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import retrofit2.Retrofit;

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
public final class NetworkModule_ProvideStockApiServiceFactory implements Factory<StockApiService> {
  private final Provider<Retrofit> retrofitProvider;

  public NetworkModule_ProvideStockApiServiceFactory(Provider<Retrofit> retrofitProvider) {
    this.retrofitProvider = retrofitProvider;
  }

  @Override
  public StockApiService get() {
    return provideStockApiService(retrofitProvider.get());
  }

  public static NetworkModule_ProvideStockApiServiceFactory create(
      Provider<Retrofit> retrofitProvider) {
    return new NetworkModule_ProvideStockApiServiceFactory(retrofitProvider);
  }

  public static StockApiService provideStockApiService(Retrofit retrofit) {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideStockApiService(retrofit));
  }
}

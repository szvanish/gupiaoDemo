package com.stockanalyzer.data.repository;

import com.stockanalyzer.data.api.NewsApiService;
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
public final class NewsRepository_Factory implements Factory<NewsRepository> {
  private final Provider<NewsApiService> apiProvider;

  public NewsRepository_Factory(Provider<NewsApiService> apiProvider) {
    this.apiProvider = apiProvider;
  }

  @Override
  public NewsRepository get() {
    return newInstance(apiProvider.get());
  }

  public static NewsRepository_Factory create(Provider<NewsApiService> apiProvider) {
    return new NewsRepository_Factory(apiProvider);
  }

  public static NewsRepository newInstance(NewsApiService api) {
    return new NewsRepository(api);
  }
}

# 股票分析 Android App 实施计划

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** 构建原生 Android 应用（Kotlin + XML），通过 Retrofit 调用后端 API，展示股票搜索、六维分析、AI 研报和财经新闻，自选股数据持久化到 Room 数据库。

**Architecture:** MVVM + Repository 模式。UI 层（Fragment/Activity）观察 ViewModel 的 LiveData，ViewModel 调用 Repository，Repository 统一管理网络请求（Retrofit）和本地存储（Room）。Hilt 负责依赖注入。

**Tech Stack:** Kotlin, XML Layouts + ViewBinding, MVVM, Hilt, Retrofit2 + OkHttp3 + Gson, Room, Kotlin Coroutines + Flow, MPAndroidChart, Navigation Component, Material Design 3

**前置条件:** 后端已在本地运行（`http://localhost:8000`），或已部署云端。Android 手机与电脑在同一 WiFi 下。

---

## 文件结构

```
android/StockAnalyzer/
├── build.gradle.kts                          # 项目级构建
├── app/
│   ├── build.gradle.kts                      # 模块级构建（依赖声明）
│   └── src/main/
│       ├── AndroidManifest.xml
│       ├── java/com/stockanalyzer/
│       │   ├── StockAnalyzerApp.kt           # Hilt Application
│       │   ├── MainActivity.kt               # 底部导航宿主
│       │   ├── data/
│       │   │   ├── api/
│       │   │   │   ├── StockApiService.kt    # Retrofit 接口
│       │   │   │   ├── NewsApiService.kt
│       │   │   │   └── ApiResponse.kt        # 通用响应模型
│       │   │   ├── db/
│       │   │   │   ├── AppDatabase.kt
│       │   │   │   ├── WatchlistDao.kt
│       │   │   │   └── WatchlistEntity.kt
│       │   │   ├── model/
│       │   │   │   ├── Stock.kt              # 数据模型（与后端 JSON 对应）
│       │   │   │   └── News.kt
│       │   │   └── repository/
│       │   │       ├── StockRepository.kt
│       │   │       └── NewsRepository.kt
│       │   ├── di/
│       │   │   ├── NetworkModule.kt          # Hilt: Retrofit/OkHttp 提供
│       │   │   └── DatabaseModule.kt         # Hilt: Room 提供
│       │   └── ui/
│       │       ├── search/
│       │       │   ├── SearchFragment.kt
│       │       │   ├── SearchViewModel.kt
│       │       │   └── SearchAdapter.kt
│       │       ├── watchlist/
│       │       │   ├── WatchlistFragment.kt
│       │       │   ├── WatchlistViewModel.kt
│       │       │   └── WatchlistAdapter.kt
│       │       ├── detail/
│       │       │   ├── StockDetailActivity.kt
│       │       │   ├── StockDetailViewModel.kt
│       │       │   └── IndicatorPanelAdapter.kt
│       │       ├── news/
│       │       │   ├── NewsFragment.kt
│       │       │   ├── NewsViewModel.kt
│       │       │   └── NewsAdapter.kt
│       │       └── settings/
│       │           └── SettingsFragment.kt
│       └── res/
│           ├── layout/
│           │   ├── activity_main.xml
│           │   ├── activity_stock_detail.xml
│           │   ├── fragment_search.xml
│           │   ├── fragment_watchlist.xml
│           │   ├── fragment_news.xml
│           │   ├── fragment_settings.xml
│           │   ├── item_search_result.xml
│           │   ├── item_watchlist_stock.xml
│           │   └── item_news.xml
│           ├── menu/
│           │   └── bottom_nav_menu.xml
│           ├── navigation/
│           │   └── nav_graph.xml
│           └── values/
│               ├── strings.xml
│               └── colors.xml
```

---

### Task 1: Android 项目初始化与依赖配置

**Files:**
- Create: `android/StockAnalyzer/app/build.gradle.kts`
- Create: `android/StockAnalyzer/build.gradle.kts`
- Create: `android/StockAnalyzer/app/src/main/AndroidManifest.xml`

- [ ] **Step 1: 用 Android Studio 创建新项目**

打开 Android Studio → New Project → Empty Views Activity
- Name: `StockAnalyzer`
- Package: `com.stockanalyzer`
- Save location: `D:/aiproject/gupiao/android/StockAnalyzer`
- Language: Kotlin
- Minimum SDK: API 26 (Android 8.0)

- [ ] **Step 2: 配置项目级 build.gradle.kts**

```kotlin
// android/StockAnalyzer/build.gradle.kts
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.kapt) apply false
    alias(libs.plugins.hilt.android) apply false
}
```

- [ ] **Step 3: 配置 app/build.gradle.kts（完整依赖）**

```kotlin
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.hilt.android)
}

android {
    namespace = "com.stockanalyzer"
    compileSdk = 34
    defaultConfig {
        applicationId = "com.stockanalyzer"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures { viewBinding = true }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions { jvmTarget = "17" }
}

dependencies {
    // Hilt
    implementation("com.google.dagger:hilt-android:2.51")
    kapt("com.google.dagger:hilt-android-compiler:2.51")
    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
    // Room
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")
    // Lifecycle
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.0")
    // Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.7")
    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.0")
    // MPAndroidChart
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")
    // Material Design
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("androidx.cardview:cardview:1.0.0")
    // Preferences
    implementation("androidx.preference:preference-ktx:1.2.1")
    // Test
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.3.1")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.espresso:espresso-core:3.5.1")
}
```

在项目根 `settings.gradle.kts` 中添加 JitPack（MPAndroidChart 来源）：
```kotlin
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}
```

- [ ] **Step 4: 配置 AndroidManifest.xml（添加网络权限）**

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android">
    <uses-permission android:name="android.permission.INTERNET" />
    <application
        android:name=".StockAnalyzerApp"
        android:allowBackup="true"
        android:label="@string/app_name"
        android:theme="@style/Theme.StockAnalyzer"
        android:usesCleartextTraffic="true">
        <activity
            android:name=".MainActivity"
            android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.MAIN"/>
                <category android:name="android.intent.category.LAUNCHER"/>
            </intent-filter>
        </activity>
        <activity android:name=".ui.detail.StockDetailActivity"/>
    </application>
</manifest>
```

> `usesCleartextTraffic="true"` 允许本地开发时使用 HTTP（生产环境应配置 HTTPS）

- [ ] **Step 5: 编译验证**

在 Android Studio 中运行 Build → Make Project，确认无编译错误。

- [ ] **Step 6: Commit**

```bash
git add android/
git commit -m "feat: Android project setup with all dependencies"
```

---

### Task 2: Hilt 依赖注入配置

**Files:**
- Create: `app/src/main/java/com/stockanalyzer/StockAnalyzerApp.kt`
- Create: `app/src/main/java/com/stockanalyzer/di/NetworkModule.kt`
- Create: `app/src/main/java/com/stockanalyzer/di/DatabaseModule.kt`

- [ ] **Step 1: 创建 Application 类**

`StockAnalyzerApp.kt`:
```kotlin
package com.stockanalyzer

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class StockAnalyzerApp : Application()
```

- [ ] **Step 2: 创建 NetworkModule**

`di/NetworkModule.kt`:
```kotlin
package com.stockanalyzer.di

import android.content.Context
import com.stockanalyzer.data.api.NewsApiService
import com.stockanalyzer.data.api.StockApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideBaseUrl(@ApplicationContext context: Context): String {
        val prefs = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
        return prefs.getString("api_base_url", "http://10.0.2.2:8000") + "/"
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(baseUrl: String, client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideStockApiService(retrofit: Retrofit): StockApiService =
        retrofit.create(StockApiService::class.java)

    @Provides
    @Singleton
    fun provideNewsApiService(retrofit: Retrofit): NewsApiService =
        retrofit.create(NewsApiService::class.java)
}
```

> `10.0.2.2` 是 Android 模拟器访问宿主机 localhost 的特殊 IP；真机调试时在设置页改为电脑的局域网 IP。

- [ ] **Step 3: 创建 DatabaseModule**

`di/DatabaseModule.kt`:
```kotlin
package com.stockanalyzer.di

import android.content.Context
import androidx.room.Room
import com.stockanalyzer.data.db.AppDatabase
import com.stockanalyzer.data.db.WatchlistDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "stock_analyzer.db").build()

    @Provides
    fun provideWatchlistDao(db: AppDatabase): WatchlistDao = db.watchlistDao()
}
```

- [ ] **Step 4: 编译验证**

Build → Make Project，确认 Hilt 注解处理器无报错。

- [ ] **Step 5: Commit**

```bash
git add app/src/main/java/com/stockanalyzer/
git commit -m "feat: Hilt DI setup with network and database modules"
```

---

### Task 3: 数据模型与 Retrofit API 接口

**Files:**
- Create: `data/model/Stock.kt`
- Create: `data/model/News.kt`
- Create: `data/api/StockApiService.kt`
- Create: `data/api/NewsApiService.kt`

- [ ] **Step 1: 创建 data/model/Stock.kt**

```kotlin
package com.stockanalyzer.data.model

import com.google.gson.annotations.SerializedName

data class StockSearchResult(
    val code: String,
    val name: String,
    val market: String,
    val price: Double?,
    @SerializedName("change_pct") val changePct: Double?
)

data class StockQuote(
    val code: String,
    val name: String,
    val market: String,
    val price: Double,
    val change: Double,
    @SerializedName("change_pct") val changePct: Double,
    val volume: Double,
    val amount: Double,
    val high: Double,
    val low: Double,
    val open: Double,
    @SerializedName("prev_close") val prevClose: Double
)

data class KLineBar(
    val date: String,
    val open: Double,
    val high: Double,
    val low: Double,
    val close: Double,
    val volume: Double
)

data class TechnicalIndicators(
    val ma5: Double?, val ma10: Double?, val ma20: Double?, val ma60: Double?,
    @SerializedName("macd_dif") val macdDif: Double?,
    @SerializedName("macd_dea") val macdDea: Double?,
    @SerializedName("macd_bar") val macdBar: Double?,
    val rsi6: Double?, val rsi12: Double?, val rsi24: Double?,
    @SerializedName("kdj_k") val kdjK: Double?,
    @SerializedName("kdj_d") val kdjD: Double?,
    @SerializedName("kdj_j") val kdjJ: Double?,
    @SerializedName("boll_upper") val bollUpper: Double?,
    @SerializedName("boll_mid") val bollMid: Double?,
    @SerializedName("boll_lower") val bollLower: Double?,
    @SerializedName("volume_ratio") val volumeRatio: Double?,
    @SerializedName("turnover_rate") val turnoverRate: Double?,
    val score: Int
)

data class FundamentalData(
    @SerializedName("pe_ttm") val peTtm: Double?,
    val pb: Double?, val ps: Double?,
    val roe: Double?, val roa: Double?,
    @SerializedName("revenue_growth_yoy") val revenueGrowthYoy: Double?,
    @SerializedName("profit_growth_yoy") val profitGrowthYoy: Double?,
    @SerializedName("gross_margin") val grossMargin: Double?,
    @SerializedName("net_margin") val netMargin: Double?,
    @SerializedName("debt_ratio") val debtRatio: Double?,
    @SerializedName("current_ratio") val currentRatio: Double?,
    @SerializedName("free_cash_flow") val freeCashFlow: Double?,
    val eps: Double?,
    @SerializedName("dividend_yield") val dividendYield: Double?,
    val score: Int
)

data class ValuationData(
    @SerializedName("industry_avg_pe") val industryAvgPe: Double?,
    @SerializedName("industry_avg_pb") val industryAvgPb: Double?,
    @SerializedName("pe_percentile") val pePercentile: Double?,
    @SerializedName("pb_percentile") val pbPercentile: Double?,
    val peg: Double?,
    val score: Int
)

data class SentimentData(
    @SerializedName("main_net_inflow") val mainNetInflow: Double?,
    @SerializedName("super_large_net") val superLargeNet: Double?,
    @SerializedName("large_net") val largeNet: Double?,
    @SerializedName("northbound_net") val northboundNet: Double?,
    @SerializedName("margin_balance") val marginBalance: Double?,
    @SerializedName("news_heat_score") val newsHeatScore: Int,
    @SerializedName("is_limit_up") val isLimitUp: Boolean,
    @SerializedName("is_limit_down") val isLimitDown: Boolean,
    val score: Int
)

data class ChipData(
    @SerializedName("institution_holding_pct") val institutionHoldingPct: Double?,
    @SerializedName("shareholder_count_change_pct") val shareholderCountChangePct: Double?,
    @SerializedName("top10_holder_change") val top10HolderChange: String?,
    val score: Int
)

data class MacroData(
    @SerializedName("industry_name") val industryName: String?,
    @SerializedName("industry_change_pct") val industryChangePct: Double?,
    @SerializedName("corr_with_index") val corrWithIndex: Double?,
    @SerializedName("policy_summary") val policySummary: String?,
    val score: Int
)

data class StockAnalysis(
    val code: String, val name: String, val market: String,
    val technical: TechnicalIndicators,
    val fundamental: FundamentalData,
    val valuation: ValuationData,
    val sentiment: SentimentData,
    val chip: ChipData,
    val macro: MacroData
)

data class AIReport(
    val code: String, val name: String, val market: String,
    val summary: String,
    @SerializedName("market_review") val marketReview: String,
    @SerializedName("fundamental_analysis") val fundamentalAnalysis: String,
    @SerializedName("technical_analysis") val technicalAnalysis: String,
    @SerializedName("valuation_analysis") val valuationAnalysis: String,
    @SerializedName("sentiment_analysis") val sentimentAnalysis: String,
    val risks: List<String>,
    val recommendation: String,
    val rating: String,
    @SerializedName("target_price_low") val targetPriceLow: Double?,
    @SerializedName("target_price_high") val targetPriceHigh: Double?,
    @SerializedName("generated_at") val generatedAt: String,
    val cached: Boolean
)
```

- [ ] **Step 2: 创建 data/model/News.kt**

```kotlin
package com.stockanalyzer.data.model

import com.google.gson.annotations.SerializedName

data class NewsItem(
    val id: String,
    val title: String,
    val source: String,
    @SerializedName("published_at") val publishedAt: String,
    val url: String,
    @SerializedName("related_stocks") val relatedStocks: List<String> = emptyList(),
    val summary: String?
)
```

- [ ] **Step 3: 创建 data/api/StockApiService.kt**

```kotlin
package com.stockanalyzer.data.api

import com.stockanalyzer.data.model.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface StockApiService {

    @GET("stock/search")
    suspend fun searchStock(
        @Query("q") query: String,
        @Query("market") market: String
    ): List<StockSearchResult>

    @GET("stock/{code}/quote")
    suspend fun getQuote(
        @Path("code") code: String,
        @Query("market") market: String
    ): StockQuote

    @GET("stock/{code}/kline")
    suspend fun getKline(
        @Path("code") code: String,
        @Query("market") market: String,
        @Query("period") period: String = "day"
    ): List<KLineBar>

    @GET("stock/{code}/analysis")
    suspend fun getAnalysis(
        @Path("code") code: String,
        @Query("market") market: String
    ): StockAnalysis

    @GET("stock/{code}/report")
    suspend fun getReport(
        @Path("code") code: String,
        @Query("market") market: String
    ): AIReport
}
```

- [ ] **Step 4: 创建 data/api/NewsApiService.kt**

```kotlin
package com.stockanalyzer.data.api

import com.stockanalyzer.data.model.NewsItem
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NewsApiService {

    @GET("news/hot")
    suspend fun getHotNews(
        @Query("market") market: String = "all",
        @Query("page") page: Int = 1
    ): List<NewsItem>

    @GET("news/stock/{code}")
    suspend fun getStockNews(
        @Path("code") code: String,
        @Query("market") market: String,
        @Query("page") page: Int = 1
    ): List<NewsItem>
}
```

- [ ] **Step 5: 编译验证**

Build → Make Project，确认模型类无编译错误。

- [ ] **Step 6: Commit**

```bash
git add app/src/main/java/com/stockanalyzer/data/
git commit -m "feat: data models and Retrofit API service interfaces"
```

---

### Task 4: Room 数据库（自选股持久化）

**Files:**
- Create: `data/db/WatchlistEntity.kt`
- Create: `data/db/WatchlistDao.kt`
- Create: `data/db/AppDatabase.kt`

- [ ] **Step 1: 创建 WatchlistEntity.kt**

```kotlin
package com.stockanalyzer.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "watchlist")
data class WatchlistEntity(
    @PrimaryKey val id: String,       // "{market}:{code}"
    val code: String,
    val name: String,
    val market: String,
    val sortOrder: Int = 0,
    val addedAt: Long = System.currentTimeMillis()
)
```

- [ ] **Step 2: 创建 WatchlistDao.kt**

```kotlin
package com.stockanalyzer.data.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface WatchlistDao {

    @Query("SELECT * FROM watchlist ORDER BY sortOrder ASC, addedAt DESC")
    fun getAllFlow(): Flow<List<WatchlistEntity>>

    @Query("SELECT * FROM watchlist WHERE id = :id LIMIT 1")
    suspend fun findById(id: String): WatchlistEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: WatchlistEntity)

    @Delete
    suspend fun delete(entity: WatchlistEntity)

    @Query("UPDATE watchlist SET sortOrder = :order WHERE id = :id")
    suspend fun updateOrder(id: String, order: Int)
}
```

- [ ] **Step 3: 创建 AppDatabase.kt**

```kotlin
package com.stockanalyzer.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [WatchlistEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun watchlistDao(): WatchlistDao
}
```

- [ ] **Step 4: 编译验证**

Build → Make Project，Room 注解处理器生成实现类无报错。

- [ ] **Step 5: Commit**

```bash
git add app/src/main/java/com/stockanalyzer/data/db/
git commit -m "feat: Room database with watchlist DAO"
```

---

### Task 5: Repository 层

**Files:**
- Create: `data/repository/StockRepository.kt`
- Create: `data/repository/NewsRepository.kt`

- [ ] **Step 1: 创建 StockRepository.kt**

```kotlin
package com.stockanalyzer.data.repository

import com.stockanalyzer.data.api.StockApiService
import com.stockanalyzer.data.db.WatchlistDao
import com.stockanalyzer.data.db.WatchlistEntity
import com.stockanalyzer.data.model.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepository @Inject constructor(
    private val api: StockApiService,
    private val dao: WatchlistDao
) {
    suspend fun search(query: String, market: String): Result<List<StockSearchResult>> =
        runCatching { api.searchStock(query, market) }

    suspend fun getQuote(code: String, market: String): Result<StockQuote> =
        runCatching { api.getQuote(code, market) }

    suspend fun getKline(code: String, market: String, period: String): Result<List<KLineBar>> =
        runCatching { api.getKline(code, market, period) }

    suspend fun getAnalysis(code: String, market: String): Result<StockAnalysis> =
        runCatching { api.getAnalysis(code, market) }

    suspend fun getReport(code: String, market: String): Result<AIReport> =
        runCatching { api.getReport(code, market) }

    fun getWatchlistFlow(): Flow<List<WatchlistEntity>> = dao.getAllFlow()

    suspend fun addToWatchlist(code: String, name: String, market: String) {
        dao.insert(WatchlistEntity(id = "$market:$code", code = code, name = name, market = market))
    }

    suspend fun removeFromWatchlist(id: String) {
        dao.findById(id)?.let { dao.delete(it) }
    }

    suspend fun isInWatchlist(code: String, market: String): Boolean =
        dao.findById("$market:$code") != null
}
```

- [ ] **Step 2: 创建 NewsRepository.kt**

```kotlin
package com.stockanalyzer.data.repository

import com.stockanalyzer.data.api.NewsApiService
import com.stockanalyzer.data.model.NewsItem
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository @Inject constructor(private val api: NewsApiService) {

    suspend fun getHotNews(market: String = "all", page: Int = 1): Result<List<NewsItem>> =
        runCatching { api.getHotNews(market, page) }

    suspend fun getStockNews(code: String, market: String, page: Int = 1): Result<List<NewsItem>> =
        runCatching { api.getStockNews(code, market, page) }
}
```

- [ ] **Step 3: Commit**

```bash
git add app/src/main/java/com/stockanalyzer/data/repository/
git commit -m "feat: repository layer wrapping API and Room DAO"
```

---

### Task 6: 导航与 MainActivity

**Files:**
- Create: `res/navigation/nav_graph.xml`
- Create: `res/menu/bottom_nav_menu.xml`
- Create: `res/layout/activity_main.xml`
- Create: `MainActivity.kt`

- [ ] **Step 1: 创建底部导航菜单 res/menu/bottom_nav_menu.xml**

```xml
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android">
    <item android:id="@+id/watchlistFragment"
        android:title="自选"
        android:icon="@drawable/ic_star"/>
    <item android:id="@+id/searchFragment"
        android:title="搜索"
        android:icon="@drawable/ic_search"/>
    <item android:id="@+id/newsFragment"
        android:title="新闻"
        android:icon="@drawable/ic_news"/>
    <item android:id="@+id/settingsFragment"
        android:title="设置"
        android:icon="@drawable/ic_settings"/>
</menu>
```

> 在 res/drawable 中添加四个矢量图标：`ic_star.xml`, `ic_search.xml`, `ic_news.xml`, `ic_settings.xml`（Android Studio → File → New → Vector Asset 选择对应图标）

- [ ] **Step 2: 创建导航图 res/navigation/nav_graph.xml**

```xml
<?xml version="1.0" encoding="utf-8"?>
<navigation xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/nav_graph"
    app:startDestination="@id/watchlistFragment">

    <fragment android:id="@+id/watchlistFragment"
        android:name="com.stockanalyzer.ui.watchlist.WatchlistFragment"
        android:label="自选股"/>
    <fragment android:id="@+id/searchFragment"
        android:name="com.stockanalyzer.ui.search.SearchFragment"
        android:label="搜索"/>
    <fragment android:id="@+id/newsFragment"
        android:name="com.stockanalyzer.ui.news.NewsFragment"
        android:label="新闻"/>
    <fragment android:id="@+id/settingsFragment"
        android:name="com.stockanalyzer.ui.settings.SettingsFragment"
        android:label="设置"/>
</navigation>
```

- [ ] **Step 3: 创建 res/layout/activity_main.xml**

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">

    <androidx.fragment.app.FragmentContainerView
        android:id="@+id/nav_host_fragment"
        android:name="androidx.navigation.fragment.NavHostFragment"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="1"
        app:defaultNavHost="true"
        app:navGraph="@navigation/nav_graph"/>

    <com.google.android.material.bottomnavigation.BottomNavigationView
        android:id="@+id/bottom_nav"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:menu="@menu/bottom_nav_menu"/>
</LinearLayout>
```

- [ ] **Step 4: 创建 MainActivity.kt**

```kotlin
package com.stockanalyzer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.stockanalyzer.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHost = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        binding.bottomNav.setupWithNavController(navHost.navController)
    }
}
```

- [ ] **Step 5: 创建四个空 Fragment 占位（让项目能运行）**

每个 Fragment 用相同模板，以 `WatchlistFragment` 为例：
```kotlin
package com.stockanalyzer.ui.watchlist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.stockanalyzer.R

class WatchlistFragment : Fragment(R.layout.fragment_watchlist)
```

同样创建 `SearchFragment`, `NewsFragment`, `SettingsFragment`，布局文件各放一个 `TextView` 占位即可。

- [ ] **Step 6: 运行 App，验证底部导航可切换 Tab**

- [ ] **Step 7: Commit**

```bash
git add app/src/main/
git commit -m "feat: main activity with bottom navigation and nav graph"
```

---

### Task 7: 搜索页（SearchFragment）

**Files:**
- Modify: `ui/search/SearchFragment.kt`
- Create: `ui/search/SearchViewModel.kt`
- Create: `ui/search/SearchAdapter.kt`
- Modify: `res/layout/fragment_search.xml`
- Create: `res/layout/item_search_result.xml`

- [ ] **Step 1: 创建 item_search_result.xml**

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.cardview.widget.CardView xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_margin="4dp"
    app:cardCornerRadius="8dp"
    app:cardElevation="2dp">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal"
        android:padding="12dp">

        <LinearLayout
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:orientation="vertical">

            <TextView android:id="@+id/tvName"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:textSize="16sp"
                android:textStyle="bold"/>

            <TextView android:id="@+id/tvCode"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:textColor="#888888"
                android:textSize="12sp"/>
        </LinearLayout>

        <LinearLayout
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:orientation="vertical"
            android:gravity="end">

            <TextView android:id="@+id/tvPrice"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:textSize="16sp"
                android:textStyle="bold"/>

            <TextView android:id="@+id/tvChangePct"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:textSize="13sp"/>
        </LinearLayout>
    </LinearLayout>
</androidx.cardview.widget.CardView>
```

- [ ] **Step 2: 创建 fragment_search.xml**

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">

    <com.google.android.material.tabs.TabLayout
        android:id="@+id/tabMarket"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"/>

    <EditText
        android:id="@+id/etSearch"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="输入股票名称或代码"
        android:padding="12dp"
        android:inputType="text"
        android:imeOptions="actionSearch"/>

    <ProgressBar
        android:id="@+id/progressBar"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center_horizontal"
        android:visibility="gone"/>

    <TextView
        android:id="@+id/tvEmpty"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:gravity="center"
        android:padding="32dp"
        android:text="输入关键词搜索股票"
        android:visibility="gone"/>

    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/recyclerView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:padding="8dp"/>
</LinearLayout>
```

- [ ] **Step 3: 创建 SearchAdapter.kt**

```kotlin
package com.stockanalyzer.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.stockanalyzer.data.model.StockSearchResult
import com.stockanalyzer.databinding.ItemSearchResultBinding

class SearchAdapter(
    private val onClick: (StockSearchResult) -> Unit
) : ListAdapter<StockSearchResult, SearchAdapter.VH>(DIFF) {

    inner class VH(private val binding: ItemSearchResultBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: StockSearchResult) {
            binding.tvName.text = item.name
            binding.tvCode.text = "${item.code}  ${item.market}股"
            binding.tvPrice.text = item.price?.let { "%.2f".format(it) } ?: "--"
            val pct = item.changePct
            if (pct != null) {
                val sign = if (pct >= 0) "+" else ""
                binding.tvChangePct.text = "$sign${"%.2f".format(pct)}%"
                binding.tvChangePct.setTextColor(if (pct >= 0) 0xFFE53935.toInt() else 0xFF43A047.toInt())
            } else {
                binding.tvChangePct.text = "--"
            }
            binding.root.setOnClickListener { onClick(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VH(
        ItemSearchResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind(getItem(position))

    companion object {
        val DIFF = object : DiffUtil.ItemCallback<StockSearchResult>() {
            override fun areItemsTheSame(a: StockSearchResult, b: StockSearchResult) =
                a.code == b.code && a.market == b.market
            override fun areContentsTheSame(a: StockSearchResult, b: StockSearchResult) = a == b
        }
    }
}
```

- [ ] **Step 4: 创建 SearchViewModel.kt**

```kotlin
package com.stockanalyzer.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stockanalyzer.data.model.StockSearchResult
import com.stockanalyzer.data.repository.StockRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: StockRepository
) : ViewModel() {

    private val _results = MutableLiveData<List<StockSearchResult>>()
    val results: LiveData<List<StockSearchResult>> = _results

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    var currentMarket = "A"
    private var searchJob: Job? = null

    fun search(query: String) {
        searchJob?.cancel()
        if (query.isBlank()) {
            _results.value = emptyList()
            return
        }
        searchJob = viewModelScope.launch {
            delay(300) // debounce
            _isLoading.value = true
            repository.search(query, currentMarket)
                .onSuccess { _results.value = it }
                .onFailure { _error.value = it.message }
            _isLoading.value = false
        }
    }
}
```

- [ ] **Step 5: 实现 SearchFragment.kt**

```kotlin
package com.stockanalyzer.ui.search

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.stockanalyzer.R
import com.stockanalyzer.databinding.FragmentSearchBinding
import com.stockanalyzer.ui.detail.StockDetailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchViewModel by viewModels()
    private lateinit var adapter: SearchAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSearchBinding.bind(view)

        adapter = SearchAdapter { result ->
            val intent = Intent(requireContext(), StockDetailActivity::class.java).apply {
                putExtra("code", result.code)
                putExtra("name", result.name)
                putExtra("market", result.market)
            }
            startActivity(intent)
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        listOf("A股", "港股", "美股").forEach { binding.tabMarket.addTab(binding.tabMarket.newTab().setText(it)) }
        binding.tabMarket.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewModel.currentMarket = listOf("A", "HK", "US")[tab.position]
                viewModel.search(binding.etSearch.text.toString())
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) = Unit
            override fun onTabReselected(tab: TabLayout.Tab?) = Unit
        })

        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) { viewModel.search(s.toString()) }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) = Unit
        })

        viewModel.results.observe(viewLifecycleOwner) { results ->
            adapter.submitList(results)
            binding.tvEmpty.visibility = if (results.isEmpty()) View.VISIBLE else View.GONE
        }
        viewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            binding.progressBar.visibility = if (loading) View.VISIBLE else View.GONE
        }
    }

    override fun onDestroyView() { super.onDestroyView(); _binding = null }
}
```

- [ ] **Step 6: 运行 App，验证搜索功能**

切换到搜索 Tab，输入"招商"，验证列表出现结果并可点击。

- [ ] **Step 7: Commit**

```bash
git add app/src/main/java/com/stockanalyzer/ui/search/
git add app/src/main/res/layout/fragment_search.xml
git add app/src/main/res/layout/item_search_result.xml
git commit -m "feat: search screen with market tab and debounced search"
```

---

### Task 8: 自选股页（WatchlistFragment）

**Files:**
- Modify: `ui/watchlist/WatchlistFragment.kt`
- Create: `ui/watchlist/WatchlistViewModel.kt`
- Create: `ui/watchlist/WatchlistAdapter.kt`
- Modify: `res/layout/fragment_watchlist.xml`
- Create: `res/layout/item_watchlist_stock.xml`

- [ ] **Step 1: 创建 item_watchlist_stock.xml**

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.cardview.widget.CardView xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_margin="6dp"
    app:cardCornerRadius="10dp"
    app:cardElevation="3dp">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal"
        android:padding="14dp"
        android:gravity="center_vertical">

        <LinearLayout
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:orientation="vertical">

            <TextView android:id="@+id/tvName"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:textSize="17sp"
                android:textStyle="bold"/>

            <TextView android:id="@+id/tvCodeMarket"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:textColor="#888888"
                android:textSize="12sp"/>
        </LinearLayout>

        <LinearLayout
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:orientation="vertical"
            android:gravity="end">

            <TextView android:id="@+id/tvPrice"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:textSize="18sp"
                android:textStyle="bold"/>

            <TextView android:id="@+id/tvChangePct"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:textSize="14sp"/>
        </LinearLayout>
    </LinearLayout>
</androidx.cardview.widget.CardView>
```

- [ ] **Step 2: 创建 fragment_watchlist.xml**

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">

    <TextView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="我的自选"
        android:textSize="20sp"
        android:textStyle="bold"
        android:padding="16dp"/>

    <TextView android:id="@+id/tvEmpty"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:gravity="center"
        android:padding="48dp"
        android:text="暂无自选股，去搜索添加"
        android:visibility="gone"/>

    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/recyclerView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:padding="8dp"/>
</LinearLayout>
```

- [ ] **Step 3: 创建 WatchlistViewModel.kt**

```kotlin
package com.stockanalyzer.ui.watchlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.stockanalyzer.data.repository.StockRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WatchlistViewModel @Inject constructor(
    private val repository: StockRepository
) : ViewModel() {

    val watchlist = repository.getWatchlistFlow().asLiveData()

    fun removeStock(id: String) {
        viewModelScope.launch { repository.removeFromWatchlist(id) }
    }
}
```

- [ ] **Step 4: 创建 WatchlistAdapter.kt**

```kotlin
package com.stockanalyzer.ui.watchlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.stockanalyzer.data.db.WatchlistEntity
import com.stockanalyzer.databinding.ItemWatchlistStockBinding

class WatchlistAdapter(
    private val onClick: (WatchlistEntity) -> Unit,
    private val onLongClick: (WatchlistEntity) -> Unit
) : ListAdapter<WatchlistEntity, WatchlistAdapter.VH>(DIFF) {

    inner class VH(private val binding: ItemWatchlistStockBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: WatchlistEntity) {
            binding.tvName.text = item.name
            binding.tvCodeMarket.text = "${item.code}  ${item.market}股"
            binding.tvPrice.text = "--"
            binding.tvChangePct.text = "--"
            binding.root.setOnClickListener { onClick(item) }
            binding.root.setOnLongClickListener { onLongClick(item); true }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VH(
        ItemWatchlistStockBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind(getItem(position))

    companion object {
        val DIFF = object : DiffUtil.ItemCallback<WatchlistEntity>() {
            override fun areItemsTheSame(a: WatchlistEntity, b: WatchlistEntity) = a.id == b.id
            override fun areContentsTheSame(a: WatchlistEntity, b: WatchlistEntity) = a == b
        }
    }
}
```

- [ ] **Step 5: 实现 WatchlistFragment.kt**

```kotlin
package com.stockanalyzer.ui.watchlist

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.stockanalyzer.R
import com.stockanalyzer.databinding.FragmentWatchlistBinding
import com.stockanalyzer.ui.detail.StockDetailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WatchlistFragment : Fragment(R.layout.fragment_watchlist) {

    private var _binding: FragmentWatchlistBinding? = null
    private val binding get() = _binding!!
    private val viewModel: WatchlistViewModel by viewModels()
    private lateinit var adapter: WatchlistAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentWatchlistBinding.bind(view)

        adapter = WatchlistAdapter(
            onClick = { entity ->
                startActivity(Intent(requireContext(), StockDetailActivity::class.java).apply {
                    putExtra("code", entity.code)
                    putExtra("name", entity.name)
                    putExtra("market", entity.market)
                })
            },
            onLongClick = { entity ->
                AlertDialog.Builder(requireContext())
                    .setTitle("删除自选")
                    .setMessage("确认从自选股中移除 ${entity.name}？")
                    .setPositiveButton("删除") { _, _ -> viewModel.removeStock(entity.id) }
                    .setNegativeButton("取消", null)
                    .show()
            }
        )
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        viewModel.watchlist.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
            binding.tvEmpty.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    override fun onDestroyView() { super.onDestroyView(); _binding = null }
}
```

- [ ] **Step 6: 运行 App，验证自选股页显示（初始为空）**

- [ ] **Step 7: Commit**

```bash
git add app/src/main/java/com/stockanalyzer/ui/watchlist/
git add app/src/main/res/layout/fragment_watchlist.xml
git add app/src/main/res/layout/item_watchlist_stock.xml
git commit -m "feat: watchlist screen with Room-backed live list"
```

---

### Task 9: 股票详情页（行情 + K线图）

**Files:**
- Create: `ui/detail/StockDetailActivity.kt`
- Create: `ui/detail/StockDetailViewModel.kt`
- Create: `res/layout/activity_stock_detail.xml`

- [ ] **Step 1: 创建 activity_stock_detail.xml**

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.core.widget.NestedScrollView xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        android:padding="12dp">

        <!-- 行情头部 -->
        <LinearLayout android:layout_width="match_parent" android:layout_height="wrap_content"
            android:orientation="horizontal" android:gravity="center_vertical">
            <LinearLayout android:layout_width="0dp" android:layout_height="wrap_content"
                android:layout_weight="1" android:orientation="vertical">
                <TextView android:id="@+id/tvStockName" android:layout_width="wrap_content"
                    android:layout_height="wrap_content" android:textSize="22sp" android:textStyle="bold"/>
                <TextView android:id="@+id/tvStockCode" android:layout_width="wrap_content"
                    android:layout_height="wrap_content" android:textColor="#888" android:textSize="13sp"/>
            </LinearLayout>
            <Button android:id="@+id/btnWatchlist" android:layout_width="wrap_content"
                android:layout_height="wrap_content" android:text="+ 自选"/>
        </LinearLayout>

        <TextView android:id="@+id/tvPrice" android:layout_width="wrap_content"
            android:layout_height="wrap_content" android:textSize="36sp" android:textStyle="bold"
            android:layout_marginTop="8dp"/>
        <TextView android:id="@+id/tvChange" android:layout_width="wrap_content"
            android:layout_height="wrap_content" android:textSize="16sp"/>

        <!-- 成交量/额 -->
        <LinearLayout android:layout_width="match_parent" android:layout_height="wrap_content"
            android:orientation="horizontal" android:layout_marginTop="8dp">
            <TextView android:id="@+id/tvVolume" android:layout_width="0dp"
                android:layout_height="wrap_content" android:layout_weight="1" android:textSize="13sp"/>
            <TextView android:id="@+id/tvAmount" android:layout_width="0dp"
                android:layout_height="wrap_content" android:layout_weight="1" android:textSize="13sp"/>
        </LinearLayout>

        <!-- K线周期切换 -->
        <com.google.android.material.tabs.TabLayout
            android:id="@+id/tabPeriod"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginTop="16dp"/>

        <!-- K线图 -->
        <com.github.mikephil.charting.charts.CandleStickChart
            android:id="@+id/candleChart"
            android:layout_width="match_parent"
            android:layout_height="240dp"
            android:layout_marginTop="8dp"/>

        <!-- 六维雷达图 -->
        <TextView android:layout_width="wrap_content" android:layout_height="wrap_content"
            android:text="综合评分" android:textSize="16sp" android:textStyle="bold"
            android:layout_marginTop="24dp"/>

        <com.github.mikephil.charting.charts.RadarChart
            android:id="@+id/radarChart"
            android:layout_width="match_parent"
            android:layout_height="220dp"/>

        <!-- AI 研报 -->
        <TextView android:layout_width="wrap_content" android:layout_height="wrap_content"
            android:text="AI 分析研报" android:textSize="16sp" android:textStyle="bold"
            android:layout_marginTop="24dp"/>

        <ProgressBar android:id="@+id/progressReport"
            android:layout_width="wrap_content" android:layout_height="wrap_content"
            android:layout_gravity="center_horizontal"/>

        <TextView android:id="@+id/tvRating" android:layout_width="match_parent"
            android:layout_height="wrap_content" android:textSize="18sp" android:textStyle="bold"
            android:padding="12dp" android:gravity="center" android:layout_marginTop="8dp"/>

        <TextView android:id="@+id/tvSummary" android:layout_width="match_parent"
            android:layout_height="wrap_content" android:textSize="15sp" android:padding="8dp"/>

        <TextView android:id="@+id/tvReportDetail" android:layout_width="match_parent"
            android:layout_height="wrap_content" android:textSize="14sp" android:padding="8dp"
            android:lineSpacingExtra="4dp"/>

        <TextView android:id="@+id/tvRisks" android:layout_width="match_parent"
            android:layout_height="wrap_content" android:textColor="#E53935"
            android:textSize="14sp" android:padding="8dp"/>

        <TextView android:id="@+id/tvRecommendation" android:layout_width="match_parent"
            android:layout_height="wrap_content" android:textSize="14sp"
            android:padding="8dp" android:layout_marginBottom="24dp"/>
    </LinearLayout>
</androidx.core.widget.NestedScrollView>
```

- [ ] **Step 2: 创建 StockDetailViewModel.kt**

```kotlin
package com.stockanalyzer.ui.detail

import androidx.lifecycle.*
import com.stockanalyzer.data.model.*
import com.stockanalyzer.data.repository.StockRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StockDetailViewModel @Inject constructor(
    private val repository: StockRepository
) : ViewModel() {

    private val _quote = MutableLiveData<StockQuote>()
    val quote: LiveData<StockQuote> = _quote

    private val _kline = MutableLiveData<List<KLineBar>>()
    val kline: LiveData<List<KLineBar>> = _kline

    private val _analysis = MutableLiveData<StockAnalysis>()
    val analysis: LiveData<StockAnalysis> = _analysis

    private val _report = MutableLiveData<AIReport?>()
    val report: LiveData<AIReport?> = _report

    private val _isInWatchlist = MutableLiveData(false)
    val isInWatchlist: LiveData<Boolean> = _isInWatchlist

    private val _isLoadingReport = MutableLiveData(false)
    val isLoadingReport: LiveData<Boolean> = _isLoadingReport

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun load(code: String, market: String, period: String = "day") {
        viewModelScope.launch {
            repository.getQuote(code, market).onSuccess { _quote.value = it }
                .onFailure { _error.value = it.message }
            repository.getKline(code, market, period).onSuccess { _kline.value = it }
            repository.getAnalysis(code, market).onSuccess { _analysis.value = it }
            _isInWatchlist.value = repository.isInWatchlist(code, market)
        }
    }

    fun loadReport(code: String, market: String) {
        viewModelScope.launch {
            _isLoadingReport.value = true
            repository.getReport(code, market)
                .onSuccess { _report.value = it }
                .onFailure { _error.value = "研报生成失败: ${it.message}" }
            _isLoadingReport.value = false
        }
    }

    fun toggleWatchlist(code: String, name: String, market: String) {
        viewModelScope.launch {
            val inList = repository.isInWatchlist(code, market)
            if (inList) repository.removeFromWatchlist("$market:$code")
            else repository.addToWatchlist(code, name, market)
            _isInWatchlist.value = !inList
        }
    }
}
```

- [ ] **Step 3: 实现 StockDetailActivity.kt**

```kotlin
package com.stockanalyzer.ui.detail

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.data.RadarData
import com.github.mikephil.charting.data.RadarDataSet
import com.github.mikephil.charting.data.RadarEntry
import com.google.android.material.tabs.TabLayout
import com.stockanalyzer.data.model.AIReport
import com.stockanalyzer.data.model.KLineBar
import com.stockanalyzer.data.model.StockAnalysis
import com.stockanalyzer.data.model.StockQuote
import com.stockanalyzer.databinding.ActivityStockDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StockDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStockDetailBinding
    private val viewModel: StockDetailViewModel by viewModels()
    private lateinit var code: String
    private lateinit var name: String
    private lateinit var market: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStockDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        code = intent.getStringExtra("code") ?: return
        name = intent.getStringExtra("name") ?: code
        market = intent.getStringExtra("market") ?: "A"

        binding.tvStockName.text = name
        binding.tvStockCode.text = "$code · ${market}股"

        setupPeriodTabs()
        setupObservers()

        viewModel.load(code, market)
        viewModel.loadReport(code, market)
    }

    private fun setupPeriodTabs() {
        listOf("日K", "周K", "月K").forEach { binding.tabPeriod.addTab(binding.tabPeriod.newTab().setText(it)) }
        binding.tabPeriod.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val period = listOf("day", "week", "month")[tab.position]
                viewModel.load(code, market, period)
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) = Unit
            override fun onTabReselected(tab: TabLayout.Tab?) = Unit
        })
    }

    private fun setupObservers() {
        viewModel.quote.observe(this) { renderQuote(it) }
        viewModel.kline.observe(this) { renderKline(it) }
        viewModel.analysis.observe(this) { renderRadar(it) }
        viewModel.report.observe(this) { it?.let { renderReport(it) } }
        viewModel.isLoadingReport.observe(this) { loading ->
            binding.progressReport.visibility = if (loading) View.VISIBLE else View.GONE
        }
        viewModel.isInWatchlist.observe(this) { inList ->
            binding.btnWatchlist.text = if (inList) "已自选" else "+ 自选"
        }
        binding.btnWatchlist.setOnClickListener { viewModel.toggleWatchlist(code, name, market) }
    }

    private fun renderQuote(q: StockQuote) {
        binding.tvPrice.text = "%.2f".format(q.price)
        val sign = if (q.changePct >= 0) "+" else ""
        binding.tvChange.text = "$sign${"%.2f".format(q.change)}  $sign${"%.2f".format(q.changePct)}%"
        val color = if (q.changePct >= 0) 0xFFE53935.toInt() else 0xFF43A047.toInt()
        binding.tvPrice.setTextColor(color)
        binding.tvChange.setTextColor(color)
        binding.tvVolume.text = "成交量: ${"%.0f".format(q.volume / 10000)}万"
        binding.tvAmount.text = "成交额: ${"%.2f".format(q.amount / 1e8)}亿"
    }

    private fun renderKline(bars: List<KLineBar>) {
        val entries = bars.mapIndexed { i, bar ->
            CandleEntry(i.toFloat(), bar.high.toFloat(), bar.low.toFloat(),
                bar.open.toFloat(), bar.close.toFloat())
        }
        val dataset = CandleDataSet(entries, "").apply {
            increasingColor = 0xFFE53935.toInt()
            decreasingColor = 0xFF43A047.toInt()
            shadowColor = 0xFF888888.toInt()
            setDrawValues(false)
        }
        binding.candleChart.apply {
            data = CandleData(dataset)
            description.isEnabled = false
            legend.isEnabled = false
            invalidate()
        }
    }

    private fun renderRadar(analysis: StockAnalysis) {
        val scores = listOf(
            analysis.technical.score,
            analysis.fundamental.score,
            analysis.valuation.score,
            analysis.sentiment.score,
            analysis.chip.score,
            analysis.macro.score,
        )
        val entries = scores.map { RadarEntry(it.toFloat()) }
        val dataset = RadarDataSet(entries, "综合评分").apply {
            color = 0xFF1565C0.toInt()
            fillColor = 0x441565C0.toInt()
            setDrawFilled(true)
        }
        binding.radarChart.apply {
            data = RadarData(dataset)
            xAxis.valueFormatter = object : com.github.mikephil.charting.formatter.ValueFormatter() {
                val labels = listOf("技术", "基本", "估值", "情绪", "筹码", "宏观")
                override fun getFormattedValue(value: Float) = labels.getOrElse(value.toInt()) { "" }
            }
            description.isEnabled = false
            invalidate()
        }
    }

    private fun renderReport(r: AIReport) {
        val ratingColor = when (r.rating) {
            "强烈买入", "买入" -> 0xFFE53935.toInt()
            "强烈卖出", "卖出" -> 0xFF43A047.toInt()
            else -> 0xFF1565C0.toInt()
        }
        binding.tvRating.text = "【${r.rating}】"
        binding.tvRating.setTextColor(ratingColor)
        r.targetPriceLow?.let { low ->
            r.targetPriceHigh?.let { high ->
                binding.tvRating.append("  目标价: ${"%.2f".format(low)}-${"%.2f".format(high)}")
            }
        }
        binding.tvSummary.text = r.summary
        binding.tvReportDetail.text = """
            【行情复盘】${r.marketReview}
            
            【基本面】${r.fundamentalAnalysis}
            
            【技术面】${r.technicalAnalysis}
            
            【估值分析】${r.valuationAnalysis}
            
            【情绪与资金】${r.sentimentAnalysis}
        """.trimIndent()
        binding.tvRisks.text = "【风险提示】\n" + r.risks.joinToString("\n") { "• $it" }
        binding.tvRecommendation.text = "【操作建议】${r.recommendation}"
    }
}
```

- [ ] **Step 4: 运行 App，搜索并点击一只股票，验证详情页显示**

- [ ] **Step 5: Commit**

```bash
git add app/src/main/java/com/stockanalyzer/ui/detail/
git add app/src/main/res/layout/activity_stock_detail.xml
git commit -m "feat: stock detail with K-line chart, radar chart and AI report"
```

---

### Task 10: 新闻页（NewsFragment）

**Files:**
- Modify: `ui/news/NewsFragment.kt`
- Create: `ui/news/NewsViewModel.kt`
- Create: `ui/news/NewsAdapter.kt`
- Modify: `res/layout/fragment_news.xml`
- Create: `res/layout/item_news.xml`

- [ ] **Step 1: 创建 item_news.xml**

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.cardview.widget.CardView xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_margin="4dp"
    app:cardCornerRadius="8dp"
    app:cardElevation="2dp">

    <LinearLayout android:layout_width="match_parent" android:layout_height="wrap_content"
        android:orientation="vertical" android:padding="12dp">

        <TextView android:id="@+id/tvTitle"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:textSize="15sp" android:textStyle="bold"
            android:maxLines="2" android:ellipsize="end"/>

        <LinearLayout android:layout_width="match_parent" android:layout_height="wrap_content"
            android:orientation="horizontal" android:layout_marginTop="6dp">

            <TextView android:id="@+id/tvSource"
                android:layout_width="0dp" android:layout_height="wrap_content"
                android:layout_weight="1" android:textColor="#888" android:textSize="12sp"/>

            <TextView android:id="@+id/tvTime"
                android:layout_width="wrap_content" android:layout_height="wrap_content"
                android:textColor="#888" android:textSize="12sp"/>
        </LinearLayout>
    </LinearLayout>
</androidx.cardview.widget.CardView>
```

- [ ] **Step 2: 创建 fragment_news.xml**

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">

    <com.google.android.material.tabs.TabLayout
        android:id="@+id/tabNews"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"/>

    <androidx.swiperefreshlayout.widget.SwipeRefreshLayout
        android:id="@+id/swipeRefresh"
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <androidx.recyclerview.widget.RecyclerView
            android:id="@+id/recyclerView"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:padding="8dp"/>
    </androidx.swiperefreshlayout.widget.SwipeRefreshLayout>
</LinearLayout>
```

- [ ] **Step 3: 创建 NewsAdapter.kt**

```kotlin
package com.stockanalyzer.ui.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.stockanalyzer.data.model.NewsItem
import com.stockanalyzer.databinding.ItemNewsBinding

class NewsAdapter(private val onClick: (NewsItem) -> Unit) :
    ListAdapter<NewsItem, NewsAdapter.VH>(DIFF) {

    inner class VH(private val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: NewsItem) {
            binding.tvTitle.text = item.title
            binding.tvSource.text = item.source
            binding.tvTime.text = item.publishedAt.take(16)
            binding.root.setOnClickListener { onClick(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VH(
        ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind(getItem(position))

    companion object {
        val DIFF = object : DiffUtil.ItemCallback<NewsItem>() {
            override fun areItemsTheSame(a: NewsItem, b: NewsItem) = a.id == b.id
            override fun areContentsTheSame(a: NewsItem, b: NewsItem) = a == b
        }
    }
}
```

- [ ] **Step 4: 创建 NewsViewModel.kt**

```kotlin
package com.stockanalyzer.ui.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stockanalyzer.data.model.NewsItem
import com.stockanalyzer.data.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {

    private val _news = MutableLiveData<List<NewsItem>>()
    val news: LiveData<List<NewsItem>> = _news

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    var currentTab = 0  // 0=全市场热点, 1=自选股追踪

    fun loadHotNews(market: String = "all") {
        viewModelScope.launch {
            _isLoading.value = true
            repository.getHotNews(market)
                .onSuccess { _news.value = it }
            _isLoading.value = false
        }
    }
}
```

- [ ] **Step 5: 实现 NewsFragment.kt**

```kotlin
package com.stockanalyzer.ui.news

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.stockanalyzer.R
import com.stockanalyzer.databinding.FragmentNewsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsFragment : Fragment(R.layout.fragment_news) {

    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NewsViewModel by viewModels()
    private lateinit var adapter: NewsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentNewsBinding.bind(view)

        adapter = NewsAdapter { item ->
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(item.url)))
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        listOf("全市场热点", "自选股追踪").forEach {
            binding.tabNews.addTab(binding.tabNews.newTab().setText(it))
        }
        binding.tabNews.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewModel.currentTab = tab.position
                viewModel.loadHotNews()
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) = Unit
            override fun onTabReselected(tab: TabLayout.Tab?) = Unit
        })

        binding.swipeRefresh.setOnRefreshListener { viewModel.loadHotNews() }

        viewModel.news.observe(viewLifecycleOwner) { adapter.submitList(it) }
        viewModel.isLoading.observe(viewLifecycleOwner) { binding.swipeRefresh.isRefreshing = it }

        viewModel.loadHotNews()
    }

    override fun onDestroyView() { super.onDestroyView(); _binding = null }
}
```

- [ ] **Step 6: 在 build.gradle.kts 添加 SwipeRefreshLayout 依赖**

```kotlin
implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
```

- [ ] **Step 7: Commit**

```bash
git add app/src/main/java/com/stockanalyzer/ui/news/
git add app/src/main/res/layout/fragment_news.xml
git add app/src/main/res/layout/item_news.xml
git commit -m "feat: news screen with hot news and pull-to-refresh"
```

---

### Task 11: 设置页（SettingsFragment）

**Files:**
- Modify: `ui/settings/SettingsFragment.kt`
- Create: `res/xml/preferences.xml`

- [ ] **Step 1: 创建 res/xml/preferences.xml**

```xml
<?xml version="1.0" encoding="utf-8"?>
<PreferenceScreen xmlns:android="http://schemas.android.com/apk/res/android">
    <EditTextPreference
        android:key="api_base_url"
        android:title="后端 API 地址"
        android:summary="默认: http://10.0.2.2:8000（模拟器用）\n真机请填局域网 IP"
        android:defaultValue="http://10.0.2.2:8000"
        android:inputType="textUri"/>
    <EditTextPreference
        android:key="claude_api_key"
        android:title="Claude API Key"
        android:summary="用于生成 AI 研报"
        android:inputType="textPassword"/>
    <ListPreference
        android:key="refresh_interval"
        android:title="数据刷新频率"
        android:entries="@array/refresh_entries"
        android:entryValues="@array/refresh_values"
        android:defaultValue="30"/>
    <Preference
        android:key="clear_cache"
        android:title="清理缓存"
        android:summary="清除本地缓存数据"/>
</PreferenceScreen>
```

在 `res/values/arrays.xml` 中添加：
```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <string-array name="refresh_entries">
        <item>15分钟</item>
        <item>30分钟</item>
        <item>1小时</item>
    </string-array>
    <string-array name="refresh_values">
        <item>15</item>
        <item>30</item>
        <item>60</item>
    </string-array>
</resources>
```

- [ ] **Step 2: 实现 SettingsFragment.kt**

```kotlin
package com.stockanalyzer.ui.settings

import android.os.Bundle
import android.widget.Toast
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.stockanalyzer.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        findPreference<Preference>("clear_cache")?.setOnPreferenceClickListener {
            requireContext().cacheDir.deleteRecursively()
            Toast.makeText(requireContext(), "缓存已清理", Toast.LENGTH_SHORT).show()
            true
        }
    }
}
```

- [ ] **Step 3: Commit**

```bash
git add app/src/main/java/com/stockanalyzer/ui/settings/
git add app/src/main/res/xml/preferences.xml
git add app/src/main/res/values/arrays.xml
git commit -m "feat: settings screen with API URL, API key and cache management"
```

---

### Task 12: 全链路联调验证

- [ ] **Step 1: 启动后端**

```bash
cd D:/aiproject/gupiao/backend
venv\Scripts\activate
docker start stock-redis
uvicorn main:app --reload --host 0.0.0.0 --port 8000
```

- [ ] **Step 2: 获取电脑局域网 IP**

```bash
ipconfig  # Windows，找到 IPv4 地址，例如 192.168.1.100
```

- [ ] **Step 3: 在 App 设置页配置后端地址**

打开 App → 设置 → 后端 API 地址 → 填入 `http://192.168.1.100:8000`

- [ ] **Step 4: 功能验收清单**

- [ ] 搜索"招商"→ A股结果出现
- [ ] 搜索"AAPL"切换到美股 → Apple 出现
- [ ] 点击股票 → 详情页显示实时价格和涨跌幅
- [ ] K线图正确渲染（日K/周K/月K 可切换）
- [ ] 雷达图显示六维评分
- [ ] AI 研报正文加载完成（约10-30秒）
- [ ] 操作建议卡片显示评级和目标价
- [ ] "加入自选"按钮生效，自选股页出现该股票
- [ ] 长按自选股 → 删除弹窗正常工作
- [ ] 新闻页加载热点新闻
- [ ] 点击新闻跳转浏览器

- [ ] **Step 5: Commit 最终版本**

```bash
git add .
git commit -m "feat: Android app complete - all features verified"
```

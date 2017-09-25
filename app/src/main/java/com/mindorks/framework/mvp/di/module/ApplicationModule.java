/*
 * Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://mindorks.com/license/apache-v2
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package com.mindorks.framework.mvp.di.module;

import android.app.Application;
import android.content.Context;

import com.mindorks.framework.mvp.BuildConfig;
import com.mindorks.framework.mvp.R;
import com.mindorks.framework.mvp.data.AppDataManager;
import com.mindorks.framework.mvp.data.DataManager;
import com.mindorks.framework.mvp.data.db.AppDbHelper;
import com.mindorks.framework.mvp.data.db.DbHelper;
import com.mindorks.framework.mvp.data.network.ApiHeader;
import com.mindorks.framework.mvp.data.network.ApiHelper;
import com.mindorks.framework.mvp.data.network.AppApiHelper;
import com.mindorks.framework.mvp.data.prefs.AppPreferencesHelper;
import com.mindorks.framework.mvp.data.prefs.PreferencesHelper;
import com.mindorks.framework.mvp.di.ApiInfo;
import com.mindorks.framework.mvp.di.ApplicationContext;
import com.mindorks.framework.mvp.di.DatabaseInfo;
import com.mindorks.framework.mvp.di.PreferenceInfo;
import com.mindorks.framework.mvp.utils.AppConstants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 *  Modules类里面的方法专门提供依赖，所以我们定义一个类，用@Module注解，这样Dagger在构造类的实例的时候，就知道从哪里去找到需要的依赖。
 *  modules的一个重要特征是它们设计为分区并组合在一起（比如说，在我们的app中可以有多个组成在一起的modules）。
 *  application context，lbs服务，全局设置等！！！！！！
 *  ---------->用于专门提供依赖,里面提供了一系列provide方法,用于告诉Dagger 去哪里找到这些依赖
 */

@Module //所以我们定义一个类，用@Module注解，这样Dagger在构造类的实例的时候，就知道从哪里去找到需要的依赖。
public class ApplicationModule {

    private final Application mApplication;

    //构造函数
    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides //在modules中，我们定义的方法是用这个注解，以此来告诉Dagger我们想要构造对象并提供这些依赖。
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @DatabaseInfo
    String provideDatabaseName() {
        return AppConstants.DB_NAME;//数据库.db
    }

    @Provides
    @ApiInfo
    String provideApiKey() {
        return BuildConfig.API_KEY;
    }

    @Provides
    @PreferenceInfo
    String providePreferenceName() {
        return AppConstants.PREF_NAME;//sp
    }

    @Provides  //在modules中，我们定义的方法是用这个注解，以此来告诉Dagger我们想要构造对象并提供这些依赖。
    @Singleton //使用该注解标记该类只创建一次，不能被继承。一般在类上用该注解。
    DataManager provideDataManager(AppDataManager appDataManager) {
        return appDataManager;//操作AppDataManager
    }

    @Provides
    @Singleton
    DbHelper provideDbHelper(AppDbHelper appDbHelper) {
        return appDbHelper;//操作数据库类
    }

    @Provides
    @Singleton
    PreferencesHelper providePreferencesHelper(AppPreferencesHelper appPreferencesHelper) {
        return appPreferencesHelper;//操作sp工具类
    }

    @Provides
    @Singleton
    ApiHelper provideApiHelper(AppApiHelper appApiHelper) {
        return appApiHelper;//与api相关
    }

    @Provides
    @Singleton
    ApiHeader.ProtectedApiHeader provideProtectedApiHeader(@ApiInfo String apiKey, PreferencesHelper preferencesHelper) {
        return new ApiHeader.ProtectedApiHeader(
                apiKey,
                preferencesHelper.getCurrentUserId(),//用户id
                preferencesHelper.getAccessToken());//用户token
    }

    @Provides
    @Singleton
    CalligraphyConfig provideCalligraphyDefaultConfig() {
        return new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/source-sans-pro/SourceSansPro-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build();//全局字体config
    }
}

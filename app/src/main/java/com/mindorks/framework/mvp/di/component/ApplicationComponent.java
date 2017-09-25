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

package com.mindorks.framework.mvp.di.component;

import android.app.Application;
import android.content.Context;

import com.mindorks.framework.mvp.MvpApp;
import com.mindorks.framework.mvp.data.DataManager;
import com.mindorks.framework.mvp.di.ApplicationContext;
import com.mindorks.framework.mvp.di.module.ApplicationModule;
import com.mindorks.framework.mvp.service.SyncService;

import javax.inject.Singleton;

import dagger.Component;

/**
 * 基类全局组件接口！
 * Singleton 单例
 * Components从根本上来说就是一个注入器，也可以说是@Inject和@Module的桥梁，它的主要作用就是连接这两个部分。
 *
 * Singleton 就是一个普通的作用域通道，使用了作用域@Scope注释的代码，会变成单例模式。
 */

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(MvpApp app);

    void inject(SyncService service);

    @ApplicationContext
    Context context();

    //关键点，就是子组件需要这个里面的某个实例的时候，这里需要使用一个接口，将需要的实例做一个返回动作
    Application application();

    DataManager getDataManager();
}
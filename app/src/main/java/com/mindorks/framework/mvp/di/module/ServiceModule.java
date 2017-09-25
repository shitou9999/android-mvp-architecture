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

import android.app.Service;

import dagger.Module;

/**
 * Created by janisharali on 01/02/17.
 */

@Module ////用于专门提供依赖,里面提供了一系列provide方法,用于告诉Dagger 去哪里找到这些依赖
public class ServiceModule {

    private final Service mService;

    public ServiceModule(Service service) {
        mService = service;
    }
}

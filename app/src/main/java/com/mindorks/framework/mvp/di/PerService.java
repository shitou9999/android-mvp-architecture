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

package com.mindorks.framework.mvp.di;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Dagger2的Scope，除了Singleton（root），其他都是自定义的，无论你给它命名PerActivity、
 * PerFragment，其实都只是一个命名而已，真正起作用的是inject的位置，以及dependency
 * Scope起的更多是一个限制作用，比如不同层级的Component需要有不同的scope
 *
 * @Scope类注解可以放在什么位置，怎么用？ 第一种最常见的用法：即放在@Module中的@provides处和@Component处，这两处可以配合使用。假如如@Component(modules
 * = {PresenterModule.class, ViewModule.class})，如果@Component依赖的某一或多个module中的@provides有@Scope，则@Component也必须有，否则编译时会报错。
 */

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerService {
}


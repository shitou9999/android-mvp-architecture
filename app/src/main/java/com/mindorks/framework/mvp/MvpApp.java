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

package com.mindorks.framework.mvp;

import android.app.Application;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.interceptors.HttpLoggingInterceptor.Level;
import com.mindorks.framework.mvp.data.DataManager;
import com.mindorks.framework.mvp.di.component.ApplicationComponent;
import com.mindorks.framework.mvp.di.component.DaggerApplicationComponent;
import com.mindorks.framework.mvp.di.module.ApplicationModule;
import com.mindorks.framework.mvp.utils.AppLogger;

import javax.inject.Inject;
import javax.inject.Qualifier;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


/**
 * https://github.com/MindorksOpenSource/android-mvp-architecture/issues
 * Created by janisharali on 27/01/17.
 */

public class MvpApp extends Application {
    // @Inject支持构造函数、方法和字段注解，也可能使用于静态实例成员。可注解成员可以是任意修饰符
    //    @Inject注解的构造函数可以是无参或多个参数的构造函数。@Inject每个类中最多注解一个构造函数。
    //用@Inject注解------>字段不能是final的,拥有一个合法的名称
    // 在方法上注解：
//    用@Inject注解 不能是抽象方法   不能声明自身参数类型 可以有返回结果 拥有一个合法的名称  可以有0个或多个参数
    //在需要依赖的地方使用这个注解,告诉Dagger这个类或者字段需要依赖注入
    @Inject
    DataManager mDataManager;

    @Inject
    CalligraphyConfig mCalligraphyConfig;//自定义字体的类库

    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        //全局组件
        mApplicationComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
        //dagger注入组件
        mApplicationComponent.inject(this);

        AppLogger.init();//初始化log
        //网络库初始化
        AndroidNetworking.initialize(getApplicationContext());
        if (BuildConfig.DEBUG) {
            AndroidNetworking.enableLogging(Level.BODY);
        }
        //自定义字体库初始化
        CalligraphyConfig.initDefault(mCalligraphyConfig);
    }

    //返回全局组件
    public ApplicationComponent getComponent() {
        return mApplicationComponent;
    }

    // Needed to replace the component with a test specific one
    //需要用一个特定于测试的组件来替换组件
    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }


    //Dagger是一个完全在编译期间进行的依赖注入框架，完全去除了反射
    //少些很多公式化代码，更容易测试，降低耦合，创建可复用可互换的模块

    //Dagger1：
//    多个注入点：依赖，通过injected
//    多种绑定方法：依赖，通过provided
//    多个modules：实现某种功能的绑定集合
//    多个对象图： 实现一个范围的modules集合

    //Dagger2利用生成和写的代码混合达到看似所有的产生和提供依赖的代码都是手写的样子。
//    再也没有使用反射：图的验证、配置和预先设置都在编译的时候执行。
//    容易调试和可跟踪：完全具体地调用提供和创建的堆栈
//    更好的性能：谷歌声称他们提高了13%的处理性能
//    代码混淆：使用派遣方法，就如同自己写的代码一样


    //@Inject: 通常在需要依赖的地方使用这个注解。换句话说，你用它告诉Dagger这个类或者字段需要依赖注入。这样，Dagger就会构造一个这个类的实例并满足他们的依赖。

//    @Module: Modules类里面的方法专门提供依赖，所以我们定义一个类，用@Module注解，这样Dagger在构造类的实例的时候，就知道从哪里去找到需要的 依赖。
//    modules的一个重要特征是它们设计为分区并组合在一起（比如说，在我们的app中可以有多个组成在一起的modules）。

//    @Provide: 在modules中，我们定义的方法是用这个注解，以此来告诉Dagger我们想要构造对象并提供这些依赖。

//    @Component: Components从根本上来说就是一个注入器，也可以说是@Inject和@Module的桥梁，它的主要作用就是连接这两个部分。
//    Components可以提供所有定义了的类型的实例，比如：我们必须用@Component注解一个接口然后列出所有的@Modules组成该组件，
//    如 果缺失了任何一块都会在编译的时候报错。所有的组件都可以通过它的modules知道依赖的范围。

//    @Scope: Scopes可是非常的有用，Dagger2可以通过自定义注解限定注解作用域。后面会演示一个例子，这是一个非常强大的特点，
//    *******局部单例 注解*************注解作用域,通过自定义注解限定对象的作用范围,(如@PerActivity自定义注解,限定对象的存活时间和Activity一致)********************
//    因为就如前面说的一样，没 必要让每个对象都去了解如何管理他们的实例。在scope的例子中，我们用自定义的@PerActivity注解一个类，
//    所以这个对象存活时间就和 activity的一样。简单来说就是我们可以定义所有范围的粒度(@PerFragment, @PerUser, 等等)。

//    Qualifier: 当类的类型不足以鉴别一个依赖的时候，我们就可以使用这个注解标示。例如：在Android中，我们会需要不同类型的context，
//    所以我们就可以定义 qualifier注解“@ForApplication”和“@ForActivity”，这样当注入一个context的时候，我们就可以告诉 Dagger我们想要哪种类型的context。

//    构造方法注入：在类的构造方法前面注释@Inject
//    成员变量注入：在类的成员变量（非私有）前面注释@Inject
//    函数方法注入：在函数前面注释@Inject
//    这个顺序是Dagger建议使用的，因为在运行的过程中，总会有一些奇怪的问题甚至是空指针，
//    这也意味着你的依赖在对象创建的时候可能还没有初始化 完成。这在Android的activity或者fragment中使用成员变量注入会经常遇到，
//    因为我们没有在它们的构造方法中使用。

    //Singleton
//    单例,使用@Singleton注解之后，对象只会被初始化一次，之后的每次都会被直接注入相同的对象,@Singleton 就是一个内置的作用域
//    @Qualifier
//    限定符,当@Inject不能通过类的类型来鉴别一个 依赖的时候,会使用到(如@ForApplication 和 @ForActivity,分别代表 Application和Activity的Context)







}




































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

import com.mindorks.framework.mvp.di.PerActivity;
import com.mindorks.framework.mvp.di.module.ActivityModule;
import com.mindorks.framework.mvp.ui.about.AboutFragment;
import com.mindorks.framework.mvp.ui.feed.FeedActivity;
import com.mindorks.framework.mvp.ui.feed.blogs.BlogFragment;
import com.mindorks.framework.mvp.ui.feed.opensource.OpenSourceFragment;
import com.mindorks.framework.mvp.ui.login.LoginActivity;
import com.mindorks.framework.mvp.ui.main.MainActivity;
import com.mindorks.framework.mvp.ui.main.rating.RateUsDialog;
import com.mindorks.framework.mvp.ui.splash.SplashActivity;

import dagger.Component;

/**
 * activity组件！！
 * 界面
 * dialog
 * Component是@Inject和@Module的桥梁,需要列出所有的@Modules以组成该组件
 * //Component用这个标注标识是一个连接器
 *
 * 03标注一个Compoent,用来指示我是生成的实例，用户到时候用的是这个。modules标记下的Compoent会将ActivityModule里面提供的类生成出来。
 * 04在LoginActivity
 *
 *
 * dependencies------》依赖一个组件，我们的模块里面有没有需要别的组件提供的对象！！！！！！
 * modules---------->可以配置多个模块
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
/*
    Dagger2的注释思路：关键的点是@Component，这个是个连接器，用来连接提供方和使用方的，所以它是桥梁。
    它使用在组件里面标记使用的Module（标记用到了哪个Module，主要是看使用方需要哪些对象进行构造，
    然后将它的提供方@module写在这里） 然后我们写入一个void inject(MainActivity activity);
    这里后面的参数，就是我们的使用方了。如此一来，我们在使用的地方，
    使用类似这种方式（DaggerMainActivityComponent.builder().build().inject(this);）的动作，
    将使用方类里面的标记 为@Inject的类初始化掉，完成自动初始化的动作。
    */


// //这个连接器要注入的对象。这个inject标注的意思是，我后面的参数对象里面有标注为@Inject的属性，这个标注的属性是需要这个连接器注入进来的。
    void inject(MainActivity activity);
//哪里需要注入就写一个接口即可
    void inject(LoginActivity activity);

    void inject(SplashActivity activity);

    void inject(FeedActivity activity);

    void inject(AboutFragment fragment);

    void inject(OpenSourceFragment fragment);

    void inject(BlogFragment fragment);

    void inject(RateUsDialog dialog);

}

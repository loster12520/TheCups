## kotlin-js研究日志 - 01 - 基础环境搭建

### 写在前面

研究了好几天的kotlin-js，终于在今天成功运行起来。

其中，我踩了好多坑，今天写这篇文章就是为了和大家分享，遇到了这些问题需要怎么解决。

首先需要明确的是我们将在开启一个最基础kotlin-js项目中需要使用到的技术：

- Kotlin：编写代码
- Gradle：构建工具，主要用来管理整个管理各个组件、下载依赖、编译的流程
- Node.js：JavaScript运行环境
- yarn：JavaScript包管理工具
- webpack：JavaScript模块打包工具，提供开发服务器

要将kotlin代码转化为一个可以在浏览器中打开的页面，我们需要经历以下几个步骤：

1. 在`build.gradle.kts`中配置好kotlin-js的相关插件和依赖
2. 依靠gradle插件自动将kotlin代码编译成JavaScript代码
3. 依靠gradle插件自动安装node.js和yarn
4. 依靠yarn安装前端相关依赖
5. 依靠webpack将所有的JavaScript代码打包成一个可以在浏览器中打开的文件
6. 依靠webpack-dev-server启动一个本地服务器，在浏览器中打开
7. 在浏览器中查看效果

> 由于官方文档推荐使用kotlin multiplatform来进行kotlin-js的开发，所以本文将基于kotlin multiplatform来进行开发。

### maven镜像选择及kotlin-wrappers版本管理

> 本文使用的gradle版本为8.13，jdk版本采用的是openjdk 21。已知最新的gradle版本9.0.0在idea中会出现gradle脚本索引失效，所以为了开发方便还是使用8.13版本；jdk的版本倒是无所谓，我也尝试了zulu 17，同样也可以正常使用。

相信读到这篇文章的各位大哥们，肯定都是在gradle中有了丰富的使用经验，在一个项目中肯定一开始会使用国内的maven镜像来下载依赖。

不过这就是这个项目的第一个坑。kotlin-wrappers目前还在快速迭代中，所以版本的更新是很快的，最新的仓库会很快放置进maven中央仓库中，不过不会很快同步到各个镜像。

所以如果你使用了国内的maven镜像，很有可能会找不到最新版本的kotlin-wrappers，从而导致编译失败。

所以建议在仓库的`settings.gradle.kts`中，使用官方的maven仓库：

> 插件仓库目前我使用了阿里云的镜像，暂时没有遇到什么问题，如果你遇到了问题，请优先替换成官方仓库。

```kotlin
pluginManagement {
    repositories {
        maven { url = uri("https://maven.aliyun.com/repository/gradle-plugin") }
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        maven { url = uri("https://maven.aliyun.com/repository/public") }
    }
}
```

当然，为了后续方便管理kotlin-wrappers的版本，建议在`settings.gradle.kts`中创建一个`versionCatalog`，用来统一管理kotlin、kotlinx-serialization和kotlin-wrappers的版本号：

```kotlin
dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            val kotlinVersion = "2.2.20"
            plugin("serialization", "org.jetbrains.kotlin.plugin.serialization").version(kotlinVersion)
            library("kotlin-test", "org.jetbrains.kotlin", "kotlin-test").version(kotlinVersion)

            val serializationVersion = "1.9.0"
            library("serialization-json", "org.jetbrains.kotlinx", "kotlinx-serialization-json").version(
                serializationVersion
            )
        }
        create("kotlinWrappers") {
            val wrappersVersion = "2025.9.6"
            from("org.jetbrains.kotlin-wrappers:kotlin-wrappers-catalog:$wrappersVersion")
        }
    }
}
```
### 安装插件和依赖

> 本文中，我们会使用react，所以会增加对应的依赖；同样的，在kotlin-js中使用css，官方推荐是使用emotion-css，也就是css in js的一种方案，编码起来是最舒服的，所以我们这篇文章中也默认加入这个依赖。各位大哥可以根据自己的需求，自行增删依赖。

首先是增加插件，刚刚在`settings.gradle.kts`中已经增加了`serialization`插件，所以我们只需要在`build.gradle.kts`中引入即可：

```kotlin
plugins {
    kotlin("multiplatform") version "2.2.20"
}
```

然后是增加依赖，主要是kotlin-wrappers相关的依赖：

```kotlin
kotlin {
    sourceSets {
        val jsMain by getting {
            dependencies {
                implementation(kotlinWrappers.js)
                implementation(kotlinWrappers.react)
                implementation(kotlinWrappers.reactDom)
                implementation(kotlinWrappers.emotion.css)
                implementation(kotlinWrappers.emotion.react)
            }
        }
    }
}
```

当然，为了在kotlin代码中能够使用css，需要在`js`的`browser`中增加css支持：

```kotlin
kotlin {
    js(IR) {
        browser {
            commonWebpackConfig {
                cssSupport.enabled = true
            }
        }
    }
}
```

### 编写kotlin代码

现在，如果你使用的是idea，就可以导入这个gradle项目了，接着就直接可以开始愉快地编写代码了。

先创建一个html文件，放在`src/jsMain/resources`目录下：

```html
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Hello, Kotlin/JS!</title>
</head>
<body>
<div id="root"></div>
<script src="${your-project-name}.js"></script>
</body>
</html>
```

其中，请将`${your-project-name}`替换成你项目的名称；如果你是在一个项目的子模块中使用kotlin-js，请将`${your-project-name}`替换成你子模块的名称。

然后在`src/jsMain/kotlin`目录下创建一个`Main.kt`文件，编写如下代码：

```kotlin
import emotion.react.css
import kotlinx.browser.document
import react.Fragment
import react.create
import react.dom.client.createRoot
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h1
import web.cssom.NamedColor
import web.dom.Element

fun main() {
    val container = document.getElementById("root") as Element? ?: error("Couldn't find root container!")
    createRoot(container).render(Fragment.create {
        div {
            css {
                color = NamedColor.red
            }
            h1 {
                +"Hello, React+Kotlin/JS!"
            }
        }
    })
}
```

### 运行项目

现在，我们已经完成了所有的代码编写工作，接下来就可以运行项目了。

请先执行`jsBrowserDevelopmentRun`任务，如果是第一次执行这个任务，gradle会自动帮你安装node.js和yarn，并且使用yarn安装前端依赖，这个过程会比较慢，请耐心等待。

> 如果在这一步中，gradle报错了，并提示运行`kotlinUpgradeYarnLock`任务，请按照提示运行这个任务，然后再重新运行`jsBrowserDevelopmentRun`任务即可。

安装完成后，gradle会自动启动一个本地服务器，并且在浏览器中打开页面。

如果一切顺利，你应该可以在浏览器中看到一个红色的`Hello, React+Kotlin/JS!`。

> 如果你想要实现热更新，可以在执行`jsBrowserDevelopmentRun`任务的时候，增加`--continuous`参数，这样在你修改代码并保存后，浏览器中的页面会自动刷新。
> 
> 这时，你将`Main.kt`中的`NamedColor.red`修改为`NamedColor.blue`，保存后，浏览器中的页面会自动刷新，并且文字颜色变为蓝色。
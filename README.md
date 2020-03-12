[![Release](https://jitpack.io/v/bagy94/android-base.svg)](https://jitpack.io/#bagy94/android-base)
# Base module
- Kotlin
- MVVM
- Koin
- AndroidX
- Retrofit
- Navigation component
- RxJava, RxAndroid, RxKotlin, RxBindings

# Configuration
Included dependenies in <i>base/base_dependencies.gradle</i>

# Project implementation
Add it in your root build.gradle at the end of repositories
```
allprojects {
  repositories {
    ...maven { url 'https://jitpack.io' }
  }
}
```
Add library dependecy to module build.gradle
```
dependencies {
  ...
  implementation 'com.github.bagy94:android-base:<version>'
  ...
}


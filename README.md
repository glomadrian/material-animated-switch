Material Animated Switch  
-----------------
[![Download](https://api.bintray.com/packages/glomadrian/maven/MaterialAnimatedSwitch/images/download.svg) ](https://bintray.com/glomadrian/maven/MaterialAnimatedSwitch/_latestVersion)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Material%20Animated%20Switch-green.svg?style=flat)](https://android-arsenal.com/details/1/1985)

A material Switch with icon animations and color transitions



![Demo Screenshot][1]

![Demo Screenshot][2]


### Sample video:

[Youtube Material Animated Switch video](https://youtu.be/MBJpCfXuVuU)

### Sample app:

<a href="https://play.google.com/store/apps/details?id=com.github.glomadrian.materialanimatedswitch.sample">
  <img alt="Get it on Google Play"
       src="https://developer.android.com/images/brand/en_generic_rgb_wo_60.png" />
</a>

Based on
----------

[Inbox Pinned](https://dribbble.com/shots/2098916-Inbox-Pinned) by [Derek Torsani](https://dribbble.com/dmtors)


How to use
----------

By default show the inbox pinned switch

```xml
<com.github.glomadrian.materialanimatedswitch.MaterialAnimatedSwitch
     android:id="@+id/pin"
     android:layout_width="wrap_content"
     android:layout_height="wrap_content"
     />
```


With custom colors and icons attributes
```xml
<com.github.glomadrian.materialanimatedswitch.MaterialAnimatedSwitch
     android:id="@+id/pin"
     android:layout_width="wrap_content"
     android:layout_height="wrap_content"  
     app:base_release_color="@color/md_deep_orange_900"
     app:base_press_color="@color/md_deep_orange_50"
     app:ball_release_color="@color/md_deep_orange_500"
     app:ball_press_color="@color/md_white_1000"
     app:icon_release="@drawable/ic_play_arrow_white_36dp"
     app:icon_press="@drawable/ic_stop_deep_orange_500_36dp"
     />
```

Remember put this for custom attribute usage

```java

xmlns:app="http://schemas.android.com/apk/res-auto"

```


For Gradle
---------------------

Add repository

```groovy
repositories {
  maven {
    url "http://dl.bintray.com/glomadrian/maven"
  }
}
```
Add dependency
```groovy
compile 'com.github.glomadrian:MaterialAnimatedSwitch:1.0@aar'
```
Developed By
------------
Adrián García Lomas - <glomadrian@gmail.com>
* [Twitter](https://twitter.com/glomadrian)
* [LinkedIn](https://es.linkedin.com/in/glomadrian )

License
-------

    Copyright 2015 Adrián García Lomas

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

[1]: ./art/swtich.gif
[2]: ./art/all.png

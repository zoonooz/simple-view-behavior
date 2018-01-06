[![Build Status](https://travis-ci.org/zoonooz/simple-view-behavior.svg?branch=master)](https://travis-ci.org/zoonooz/simple-view-behavior)

# simple-view-behavior

Simple View Behavior for Android [CoordinatorLayout](http://developer.android.com/reference/android/support/design/widget/CoordinatorLayout.html).

This library provide you easy ways to config and setup the view behavior without creating subclass of
[Behavior](http://developer.android.com/reference/android/support/design/widget/CoordinatorLayout.Behavior.html)

<p align="center"><img src="https://raw.githubusercontent.com/zoonooz/simple-view-behavior/master/screenshot/example.gif"/></p>

## Installation

```
compile 'com.zoonref:simple-view-behavior:1.0'
```

## Usage
You can setup behavior effect by using only xml option.

1. set the layout behavior to this library class `app:layout_behavior="com.zoonref.SimpleViewBehavior"`
2. set the dependency view by specify resource id by `app:dependsOn="@+id/{your view id}"`
3. set the type of dependency `app:dependType="{x,y,width,height}"`
4. set the dependency view target value according to the type you choose `app:dependTargetXXX="{value}"`
5. set the target appearance that you want your view to animate to

There are **two** ways to specify the animation when the dependency view changed.

### 1. xml

```
<View
        android:layout_width="40dp"
        android:layout_height="40dp"
        android:layout_marginTop="8dp"
        android:layout_marginLeft="20dp"
        android:rotationX="90"
        android:elevation="10dp"
        android:background="@android:color/holo_blue_bright"
        android:alpha="0"
        app:layout_behavior="com.zoonref.SimpleViewBehavior"
        app:dependsOn="@+id/app_bar"
        app:dependType="y"
        app:dependTargetY="-156dp"
        app:targetAlpha="1"
        app:targetRotateX="0"/>
```

This View will depend on the `y` positon of `@+id/app_bar`  which is AppBarLayout. View will animation from current state of AppBarLayout until
y position of AppBarLayout is `-156dp` with the animation, View will animate alpha to `app:targetAlpha="1"` rotate x to `app:targetRotateX="0"`

**Don't forget to add** `xmlns:app="http://schemas.android.com/apk/res-auto"`

**The output will be**

<p align="center"><img src="https://raw.githubusercontent.com/zoonooz/simple-view-behavior/master/screenshot/xml.gif"/></p>

### 2. view animation resource file

When you use animation, all of animate target option from xml will be ignored.

for example

```
<View
        android:layout_width="100dp"
        android:layout_height="100dp"
        android:layout_gravity="center|top"
        android:layout_marginTop="80dp"
        android:elevation="10dp"
        android:background="@android:color/holo_orange_dark"
        app:layout_behavior="com.zoonref.SimpleViewBehavior"
        app:dependsOn="@+id/app_bar"
        app:dependType="y"
        app:dependTargetY="-156dp"
        app:animation="@anim/rotate"/>
```
```
<set xmlns:android="http://schemas.android.com/apk/res/android">
    <scale
        android:duration="100"
        android:fromXScale="1"
        android:fromYScale="1"
        android:pivotX="50%"
        android:pivotY="50%"
        android:toXScale="40dp"
        android:toYScale="40dp"/>
    <rotate
        android:duration="100"
        android:fromDegrees="0"
        android:pivotX="50%"
        android:pivotY="50%"
        android:toDegrees="360"/>
    <alpha
        android:duration="100"
        android:toAlpha="0"/>
</set>
```

**The output will be**

<p align="center"><img src="https://raw.githubusercontent.com/zoonooz/simple-view-behavior/master/screenshot/resource.gif"/></p>

### Options

You can see all available option at [attrs.xml](https://github.com/zoonooz/simple-view-behavior/blob/master/library/src/main/res/values/attrs.xml)

## Author

Amornchai Kanokpullwad, [@zoonref](https://twitter.com/zoonref)

## License

simple-view-behavior is available under the MIT license. See the LICENSE file for more info.

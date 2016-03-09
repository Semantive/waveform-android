# Waveform Android 

[![](https://jitpack.io/v/Semantive/waveform-android.svg)](https://jitpack.io/#Semantive/waveform-android)

Waveform Android is an audio visualisation component based on the Ringdroid app: https://github.com/google/ringdroid. It provides easy to use, reusable fragment, which can be integrated into any app. 

Waveform Android is memory optimized and can easily handle large audio files. The major change was to completely replace the drawing algorithm. Instead of keeping cached audio values for all zoom levels, I dynamically draw waveform using input values and a scaling factor. Moreover, internal audio representation used by Ringdroid has been cleaned.

![ScreenShot](https://raw.github.com/Semantive/waveform-android/master/screenshots/screenshot.png)
![ScreenShot](https://raw.github.com/Semantive/waveform-android/master/screenshots/screenshot_segments.png)

Key features
-----

- reusable fragment,
- memory optimized
- waveform visualisation of audio file,
- integrated player
- 4 zoom levels
- easy to customize
- custom colors for waveform's segments

You can find an example in MainActivity.

Usage
-----

**1. Gradle**
 ```gradle
repositories {
    maven { url "https://jitpack.io" }
}

dependencies {
    compile 'com.github.Semantive:waveform-android:v1.0'
}
```

**2. Maven**
```xml
<repositories>
    <repository>
	      <id>jitpack.io</id>
	      <url>https://jitpack.io</url>
    </repository>
</repositories>
	
<dependency>
    <groupId>com.github.Semantive</groupId>
    <artifactId>waveform-android</artifactId>
    <version>v1.0</version>
</dependency>
```

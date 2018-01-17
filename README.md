# Waveform Android 

[![](https://jitpack.io/v/Semantive/waveform-android.svg)](https://jitpack.io/#Semantive/waveform-android)

**Disclaimer: this project is no longer actively maintained.**

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
    compile 'com.github.Semantive:waveform-android:v1.2'
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
    <version>v1.2</version>
</dependency>
```

License
-----
Copyright 2008-2011 Google Inc., 2015-2016 Anna Stępień

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.

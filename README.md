# Waveform Android 

Waveform Android is an audio visualisation component based on the Ringdroid app: https://github.com/google/ringdroid. It provides easy to use, reusable fragment, which can be integrated into any app. 

Waveform Android is memory optimized and can easily handle large audio files. The major change was to completely replace the drawing algorithm. Instead of keeping cached audio values for all zoom levels, I dynamically draw waveform using input values and a scaling factor. Moreover, internal audio representation used by Ringdroid has been cleaned.

![ScreenShot](https://raw.github.com/Semantive/waveform-android/master/screenshot.png)

Key features:
- reusable fragment,
- memory optimized
- waveform visualisation of audio file,
- integrated player
- 4 zoom levels
- easy to customize

You can find an example in MainActivity.

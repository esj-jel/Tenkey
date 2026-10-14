# Kotoba — Grammar and Widget

This repository contains two separate app products: **Kotoba Grammar** and **Kotoba Widget**. Their Android application IDs are `dev.kotoba.grammar` and `dev.tenkey.kotoba.widget`. The legacy Tenkey Japanese keyboard remains available inside Kotoba Grammar as an optional feature.

Kotoba is designed to work offline. Lesson cards, language labels and preferences are bundled or stored on-device; neither Android app declares Internet permission. There is no account or backend.

## Apps

### Kotoba Grammar

A daily lesson screen with a language picker, character/word cards, translations, concise explanations, pronunciation guides, and a next-card action. The Japanese pack includes kana, kanji and radical notes, with readings shown in kana and romaji. Cantonese uses Jyutping, Korean uses Latin readings, and Chinese uses Pinyin.

The catalog contains **111 selectable language labels**, including Japanese, Cantonese, Korean, and both Simplified and Traditional Chinese. This is catalog coverage, not 111 complete translation/lesson packs. The offline starter lesson packs are Japanese, Cantonese, Korean, Simplified Chinese and Traditional Chinese. Other language selections currently fall back to the Japanese starter content. Additional licensed offline content must be added before claiming full lesson support for those languages.

### Kotoba Widget

A separate Android app that configures a transparent daily study widget. Its home-screen widget can be added from the Android widget picker; the provider opts into the keyguard category where an OS/OEM supports it. A runtime-only screen-on listener attempts to advance and refresh the card while the app process is alive. Android can stop the process and does not guarantee lock-screen widgets or wake-time refresh on every device, including Pixel and MagicOS. The widget also has a manual next-card tap and a periodic refresh request.

The iOS WidgetKit extension declares Lock Screen accessory and Home Screen families. WidgetKit controls when timelines refresh, so neither platform guarantees a new lesson on every device wake. The daily timeline is the supported offline baseline.

## Appearance and platform targets

- Android modules compile and target SDK 36 (Android 16); minimum Android version is 8.0 / API 26.
- Material 3 dynamic colors are used where available. Compatibility with MagicOS 10 and other OEM distributions is best-effort and needs real-device validation.
- iOS source targets iOS 16 and conditionally uses the iOS 26+ Glass effect, falling back to system material on older releases. An iOS 27 SDK/Xcode build and device test are not available in this Linux environment.
- The transparent widget surface lets the host wallpaper/system show through where the widget framework allows it.

## Build Android locally

Install JDK 17+, Android SDK Platform 36, Android Build Tools 36.0.0 and Platform Tools. Then run:

```bash
./gradlew :grammar:testDebugUnitTest :grammar:lintDebug :grammar:assembleDebug
./gradlew :widget:lintDebug :widget:assembleDebug
```

APK outputs:

```text
app/build/outputs/apk/debug/grammar-debug.apk
widget/build/outputs/apk/debug/widget-debug.apk
```

Install with:

```bash
adb install -r app/build/outputs/apk/debug/grammar-debug.apk
adb install -r widget/build/outputs/apk/debug/widget-debug.apk
```

Open **Kotoba Grammar**, then **Open daily grammar lesson**. The keyboard can be enabled under Android Settings → System → Keyboard. Open **Kotoba Widget**, choose a lesson language, and add its widget from the launcher's widget picker. Lock-screen placement is conditional on device software.

## Build iOS locally on macOS

The iOS sources define separate **KotobaGrammar** and **KotobaWidget** apps plus a WidgetKit lock-screen extension. Generate the Xcode project with XcodeGen, then archive and export each app with a valid Apple signing team/profile:

```bash
cd ios
xcodegen generate -s project.yml
xcodebuild -project Kotoba.xcodeproj -scheme KotobaGrammar -configuration Release -sdk iphoneos -archivePath build/KotobaGrammar.xcarchive archive
xcodebuild -project Kotoba.xcodeproj -scheme KotobaWidget -configuration Release -sdk iphoneos -archivePath build/KotobaWidget.xcarchive archive
```

An `.ipa` requires macOS/Xcode and code signing. No `.ipa` is included from this Linux build environment. An iOS simulator, Android emulator, MagicOS device and iPhone are not available here, so the source has not been device-tested.

## Brand icon

The scalable vector icon source is [`branding/kotoba.svg`](branding/kotoba.svg).

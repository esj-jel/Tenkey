# Local Keyboard

Native Android IME skeleton in Kotlin with Gradle Kotlin DSL.

## Current baseline

- Android `InputMethodService` declared in the manifest.
- Default language state: Japanese (`ja`) with Hiragana tenkey mode.
- Hiragana/Katakana toggle key.
- Globe key delegates to Android's next-input-method switcher.
- Space drag gesture seam for cursor movement.
- Offline clipboard history repository.
- Emoji, kaomoji, clipboard, and devtools utility-panel seams.
- Material You-inspired gradient keyboard surface.
- 55-language metadata catalog including Arabic, English, Swedish, and Japanese.
- Local Hunspell-ready dictionary loader: place `{language}.dic` files in `app/src/main/assets/dictionaries/`; `.aff` parsing and full suggestion ranking remain the next implementation step.

## Build locally

Install Android Studio or the Android command-line tools, then install:

- Android SDK Platform 35
- Android SDK Build-Tools 35.0.0
- Android SDK Platform-Tools
- JDK 17+

From this directory:

```bash
./gradlew :app:assembleDebug
```

APK output:

```text
app/build/outputs/apk/debug/app-debug.apk
```

Install on a connected Android device:

```bash
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

Then open **Settings → System → Keyboard → On-screen keyboard**, enable **Local Keyboard**, and select it.

## Important scope note

This is the native project foundation and a functional IME shell, not yet a production-complete 55-language dictionary bundle. Dictionary licensing and file packaging must be completed before shipping. The cursor gesture currently provides the interaction seam; production cursor movement should use `getTextBeforeCursor()`/`getTextAfterCursor()` and an offset-preserving selection model.

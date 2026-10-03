# Tenkey

Native Android IME in Kotlin with a Florisboard-inspired Material You surface.

## v1.0.2 features

Tenkey starts in Japanese Hiragana tenkey mode and includes a Hiragana/Katakana switch, Android globe switching, a long-press language shelf, cursor movement by dragging the space key, emoji/kaomoji/clipboard/devtools panels, 55-language metadata, custom keyboard profiles, user-imported fonts, haptic and sound preferences, and an in-app tutorial.

The Custom keyboard studio lets users define a language tag, keyboard name, and rows locally. An optional AI designer accepts an API key entered by the user, stores it encrypted with Android Keystore, and sends requests only to the configured OpenAI-compatible endpoint. No API key is bundled in the app.

The project includes a local Hunspell-ready dictionary loader. Add licensed `{language}.dic` files under `app/src/main/assets/dictionaries/`; full `.aff` rule evaluation and production dictionary bundles remain follow-up work.

## Build locally

Install Android Studio or the Android command-line tools with Android SDK Platform 35, Android SDK Build-Tools 35.0.0, Platform-Tools, and JDK 17+.

```bash
./gradlew :app:testDebugUnitTest :app:lintDebug :app:assembleDebug
```

APK output:

```text
app/build/outputs/apk/debug/app-debug.apk
```

Install on a connected Android device:

```bash
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

Then open **Settings → System → Keyboard → On-screen keyboard**, enable **Tenkey**, and select it. Open the launcher app for the tutorial, font import, haptic/sound controls, and Custom keyboard studio.

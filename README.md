# Tenkey — Kotoba Grammar + Kotoba Widget

Tenkey contains **two separate, offline-first language-learning apps**: **Kotoba Grammar** and **Kotoba Widget**. They have separate Android application IDs (`dev.kotoba.grammar` and `dev.tenkey.kotoba.widget`) and separate iOS app targets. The optional Tenkey Japanese keyboard remains in Kotoba Grammar.

No account, backend, analytics service, or network permission is required. Language labels, starter cards, and preferences are bundled or saved on-device.

## Kotoba Grammar

A standalone daily lesson app with a language picker, character/letter and word cards, translations, short explanations, and pronunciation readings. Character cards precede word cards in each bundled starter pack. Japanese cards include kana, kanji, and radical notes with readings in kana and romaji; Cantonese readings use Jyutping, Korean readings use romanization, and Chinese readings use Pinyin.

The picker has **111 language labels**, including Japanese, Cantonese, Korean, Simplified Chinese, and Traditional Chinese. 12 curated offline lesson packs are included: Japanese, Cantonese, Korean, Simplified and Traditional Chinese, English, Spanish, French, German, Italian, Portuguese, and Hindi. The remaining 99 selections use the Japanese starter pack as a transparent fallback; they are labels, not complete translated lesson packs.

## Kotoba Widget

A separate app that configures a transparent study widget. Android exposes a home-screen widget and declares the legacy keyguard category where the OS/OEM supports it. It shows the current new character or word, translation, explanation, and pronunciation. Tapping the card advances it. A runtime-only Android screen-on listener attempts to advance and refresh when the widget app process is alive; Android may stop that process, and Pixel, MagicOS, and other launchers do not guarantee third-party lock-screen widget placement or a refresh on every wake.

The iOS WidgetKit extension provides Lock Screen accessory and Home Screen families with a clear widget background. iOS controls WidgetKit timeline refreshes and does not expose a reliable every-unlock callback to widgets, so new lessons are scheduled daily (with manual refresh from Kotoba Widget), not guaranteed on every screen wake.

## Appearance and platform targets

- Android modules compile/target **API 36 (Android 16)** and support Android 8.0 / API 26 and later. Material 3 dynamic colors are used for app screens; widget content remains transparent so the host surface shows through.
- MagicOS 10.0.0 behavior is best-effort and requires testing on a real Honor device/launcher. Lock-screen widget support depends on the OS and OEM.
- iOS apps target **iOS 16 and later**. Kotoba Grammar uses system materials and conditionally applies Liquid Glass on iOS 26 and later. iOS 27 SDK/runtime compatibility needs validation with the corresponding Apple Xcode release and devices.
- `branding/kotoba.svg` is the scalable brand icon source. Platform icon assets are packaged separately for Android/iOS.

## Build Android locally

Install JDK 17+, Android SDK Platform 36, Android Build Tools 36.0.0, and Platform Tools. Then run from the repository root:

```bash
./gradlew :grammar:testDebugUnitTest :grammar:lintDebug :grammar:assembleDebug
./gradlew :widget:lintDebug :widget:assembleDebug
```

Debug APK outputs are signed with the local Android debug key for testing, not production release. The APKs are created at:

```text
app/build/outputs/apk/debug/grammar-debug.apk
widget/build/outputs/apk/debug/widget-debug.apk
```

A public production update should use a stable owner-controlled release-signing key.
```bash
adb install -r app/build/outputs/apk/debug/grammar-debug.apk
adb install -r widget/build/outputs/apk/debug/widget-debug.apk
```

Open **Kotoba Grammar** and tap **Open daily grammar lesson**. The keyboard is optional and can be enabled from Android system keyboard settings. In **Kotoba Widget**, choose a language and add the widget through the launcher's widget picker. Lock-screen placement depends on the OS/OEM.

## Build iOS locally on macOS

Requires macOS, Xcode, XcodeGen, an Apple development team, and matching App Group provisioning for `group.dev.tenkey.kotoba`. From the repository root:

```bash
cd ios
xcodegen generate -s project.yml
xcodebuild -project Kotoba.xcodeproj -scheme KotobaGrammar -configuration Release -sdk iphoneos -archivePath build/KotobaGrammar.xcarchive archive
xcodebuild -project Kotoba.xcodeproj -scheme KotobaWidget -configuration Release -sdk iphoneos -archivePath build/KotobaWidget.xcarchive archive
```

Export each archive as an `.ipa` in Xcode or with `xcodebuild -exportArchive` and a valid export-options plist. This Linux workspace cannot run Xcode or produce a signed, installable iOS `.ipa`; no iOS emulator/device or Apple signing assets are available here. The iOS project source and build configuration are included for local macOS builds.

## Testing and release notes

Run the Android unit tests, lint, and APK assembly commands above. No Android emulator, Pixel/MagicOS device, or Apple simulator is available in this workspace, so device-specific lock-screen placement and wake refresh still need real-device validation. See [RELEASE_NOTES_v1.2.0.md](RELEASE_NOTES_v1.2.0.md) for this project update.

# Tenkey v0.0.1

First development release of the native Kotlin Android IME.

## Included

- Japanese Hiragana tenkey as the default mode.
- Hiragana/Katakana switching.
- Globe-key input-method switching.
- 55-language metadata catalog.
- Material You-inspired gradient keyboard surface.
- Local clipboard, emoji, kaomoji, and devtools panel foundations.
- Local Hunspell-ready dictionary loader seam.
- Space-drag cursor movement.

## Bug fix in this release

- Fixed space-drag cursor movement to calculate an absolute target from the editor's current selection instead of treating the drag delta as an absolute cursor index.

## Known limitations

- Full Hunspell `.aff` rule evaluation and production dictionary bundles are not included yet.
- Glide typing is a UI/interaction seam, not a production path recognizer yet.
- The 55-language catalog is metadata; language-specific layouts and dictionaries still need to be completed before a production release.

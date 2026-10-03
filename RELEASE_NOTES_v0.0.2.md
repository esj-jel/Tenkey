# Tenkey v0.0.2

This patch release fixes the keyboard collapsing when the IME opens.

The custom keyboard view now reports a stable density-aware 300dp height during measurement and requests match-parent width with an explicit height. This prevents Android's input-method window from measuring the custom view at zero height.

The v0.0.1 compatibility fixes remain included: API-safe globe switching, API-qualified navigation bar styling, and relative space-drag cursor movement.

Validation completed:

- Unit tests passed.
- Android lint completed with zero errors.
- Debug APK assembled successfully.

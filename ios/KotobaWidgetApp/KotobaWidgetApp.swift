import SwiftUI
import WidgetKit

@main
struct KotobaWidgetApp: App {
    var body: some Scene { WindowGroup { WidgetHomeView() } }
}

struct WidgetHomeView: View {
    @AppStorage("kotoba.widget.language", store: UserDefaults(suiteName: "group.dev.tenkey.kotoba")) private var language = "ja"
    private let options = IOSLanguageCatalog.all

    var body: some View {
        NavigationStack {
            Form {
                Section("Study language") {
                    Picker("Language", selection: $language) {
                        ForEach(options) { item in Text("\(item.displayName) · \(item.tag)").tag(item.tag) }
                    }
                    Text("12 offline starter packs are included. The other 99 language labels use the Japanese fallback pack until additional local content is added.")
                        .font(.footnote).foregroundStyle(.secondary)
                }
                Section {
                    Text("Add Kotoba Daily from the Lock Screen or Home Screen widget gallery. iOS controls widget refresh timing, so a new lesson cannot be guaranteed on every screen wake.")
                        .font(.callout).foregroundStyle(.secondary)
                }
            }
            .navigationTitle("Kotoba Widget")
            .toolbar { Button("Refresh") { WidgetCenter.shared.reloadAllTimelines() } }
        }.tint(.purple)
    }
}

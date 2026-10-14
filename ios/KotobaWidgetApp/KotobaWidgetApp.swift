import SwiftUI
import WidgetKit

@main
struct KotobaWidgetApp: App {
    var body: some Scene { WindowGroup { WidgetHomeView() } }
}

struct WidgetHomeView: View {
    @AppStorage("kotoba.widget.language", store: UserDefaults(suiteName: "group.dev.tenkey.kotoba")) private var language = "ja"
    private let options = [("ja", "Japanese"), ("yue", "Cantonese"), ("ko", "Korean"), ("zh-Hans", "Chinese · Simplified"), ("zh-Hant", "Chinese · Traditional")]

    var body: some View {
        NavigationStack {
            Form {
                Section("Study language") {
                    Picker("Language", selection: $language) {
                        ForEach(options, id: \.0) { Text($0.1).tag($0.0) }
                    }
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

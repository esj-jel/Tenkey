import SwiftUI
import WidgetKit

struct KotobaEntry: TimelineEntry {
    let date: Date
    let card: LessonCard
}

struct KotobaProvider: TimelineProvider {
    func placeholder(in context: Context) -> KotobaEntry { KotobaEntry(date: .now, card: LessonCatalog.next(language: "ja", step: 0)) }
    func getSnapshot(in context: Context, completion: @escaping (KotobaEntry) -> Void) { completion(entry(for: .now)) }
    func getTimeline(in context: Context, completion: @escaping (Timeline<KotobaEntry>) -> Void) {
        let now = Date()
        let tomorrow = Calendar.current.startOfDay(for: Calendar.current.date(byAdding: .day, value: 1, to: now) ?? now.addingTimeInterval(86_400))
        completion(Timeline(entries: [entry(for: now), entry(for: tomorrow)], policy: .after(tomorrow)))
    }
    private func entry(for date: Date) -> KotobaEntry {
        let defaults = UserDefaults(suiteName: "group.dev.tenkey.kotoba")
        let language = defaults?.string(forKey: "kotoba.widget.language") ?? "ja"
        let day = Calendar.current.ordinality(of: .day, in: .era, for: date) ?? 1
        return KotobaEntry(date: date, card: LessonCatalog.next(language: language, step: day))
    }
}

struct KotobaLockWidgetView: View {
    @Environment(\.widgetFamily) private var family
    let entry: KotobaEntry

    var body: some View {
        let content = VStack(alignment: .leading, spacing: 4) {
            Text("KOTOBA · DAILY").font(.system(size: 9, weight: .bold, design: .rounded)).tracking(1.2).opacity(0.78)
            if family == .accessoryInline {
                Text("\(entry.card.written) · \(entry.card.translation)")
            } else {
                Text(entry.card.written).font(.system(size: family == .accessoryCircular ? 27 : 35, weight: .semibold, design: .rounded)).lineLimit(1).minimumScaleFactor(0.6)
                Text(entry.card.translation).font(.system(size: 13, weight: .medium)).lineLimit(1)
                if family != .accessoryCircular { Text(entry.card.pronunciation).font(.system(size: 11)).opacity(0.83).lineLimit(1) }
            }
        }.foregroundStyle(.primary)
        if #available(iOSApplicationExtension 17.0, *) {
            content.containerBackground(.clear, for: .widget)
        } else {
            content.background(.ultraThinMaterial)
        }
    }
}

struct KotobaWidget: Widget {
    let kind = "KotobaDailyWidget"
    var body: some WidgetConfiguration {
        StaticConfiguration(kind: kind, provider: KotobaProvider()) { entry in KotobaLockWidgetView(entry: entry) }
            .configurationDisplayName("Kotoba Daily")
            .description("A transparent daily character or word card.")
            .supportedFamilies([.accessoryInline, .accessoryCircular, .accessoryRectangular, .systemSmall, .systemMedium])
    }
}

@main
struct KotobaWidgetBundle: WidgetBundle {
    var body: some Widget { KotobaWidget() }
}

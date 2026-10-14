import SwiftUI
import UIKit

@main
struct KotobaGrammarApp: App {
    var body: some Scene {
        WindowGroup { GrammarHomeView() }
    }
}

struct GrammarHomeView: View {
    @AppStorage("kotoba.language") private var language = "ja"
    @AppStorage("kotoba.step") private var step = 0
    private let languages = IOSLanguageCatalog.all
    private var card: LessonCard { LessonCatalog.next(language: language, step: step) }

    var body: some View {
        NavigationStack {
            ScrollView {
                VStack(alignment: .leading, spacing: 20) {
                    VStack(alignment: .leading, spacing: 6) {
                        Text("KOTOBA GRAMMAR").font(.caption.weight(.bold)).tracking(1.8).foregroundStyle(.secondary)
                        Text("A little language,\nevery day.").font(.system(size: 34, weight: .bold, design: .rounded))
                    }
                    Picker("Language", selection: $language) {
                        ForEach(languages) { item in Text("\(item.displayName) · \(item.tag)").tag(item.tag) }
                    }.pickerStyle(.menu).tint(.purple)
                    lessonCard
                    Button { step += 1 } label: {
                        Label("Next character or word", systemImage: "arrow.right.circle.fill")
                            .font(.headline).frame(maxWidth: .infinity).padding(.vertical, 14)
                    }.buttonStyle(.borderedProminent).tint(.purple)
                    Text("111 language labels are included. On-device starter lessons are bundled for Japanese, Cantonese, Korean and Chinese (Simplified and Traditional); other selections currently fall back to the Japanese starter pack. No network requests are used.")
                        .font(.footnote).foregroundStyle(.secondary)
                }.padding(22)
            }
            .background(Color(uiColor: .systemGroupedBackground))
            .navigationTitle("Today")
            .navigationBarTitleDisplayMode(.inline)
        }.tint(.purple)
    }

    private var lessonCard: some View {
        VStack(alignment: .leading, spacing: 12) {
            Text(card.kind.uppercased()).font(.caption.weight(.bold)).tracking(1.2).foregroundStyle(.purple)
            Text(card.written).font(.system(size: 58, weight: .semibold, design: .rounded)).minimumScaleFactor(0.65).lineLimit(1)
            Text(card.translation).font(.title2.weight(.semibold))
            Text(card.explanation).font(.body).foregroundStyle(.secondary)
            Divider()
            Label(card.pronunciation, systemImage: "speaker.wave.2.fill").font(.headline)
        }
        .frame(maxWidth: .infinity, alignment: .leading).padding(22)
        .background {
            if #available(iOS 26.0, *) {
                RoundedRectangle(cornerRadius: 30, style: .continuous).fill(.clear).glassEffect(.regular, in: .rect(cornerRadius: 30))
            } else {
                RoundedRectangle(cornerRadius: 30, style: .continuous).fill(.ultraThinMaterial)
            }
        }
        .overlay(RoundedRectangle(cornerRadius: 30).strokeBorder(.white.opacity(0.45), lineWidth: 1))
    }
}

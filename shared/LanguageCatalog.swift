import Foundation

struct LanguageOption: Decodable, Identifiable {
    let tag: String
    let displayName: String
    var id: String { tag }
}

enum IOSLanguageCatalog {
    static let all: [LanguageOption] = {
        guard let url = Bundle.main.url(forResource: "languages", withExtension: "json"),
              let data = try? Data(contentsOf: url),
              let result = try? JSONDecoder().decode([LanguageOption].self, from: data),
              !result.isEmpty else {
            return [LanguageOption(tag: "ja", displayName: "日本語"), LanguageOption(tag: "yue", displayName: "粵語"), LanguageOption(tag: "ko", displayName: "한국어"), LanguageOption(tag: "zh-Hans", displayName: "简体中文"), LanguageOption(tag: "zh-Hant", displayName: "繁體中文")]
        }
        return result
    }()
}

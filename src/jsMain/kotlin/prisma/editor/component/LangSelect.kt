package prisma.editor.component

import org.w3c.dom.HTMLElement
import kotlinx.html.*
import kotlinx.html.dom.createHTMLDocument
import kotlinx.html.js.div
import kotlinx.html.js.onchange
import kotlinx.html.js.option
import kotlinx.html.js.select
import kotlinx.browser.document
import kotlinx.html.js.a
import prisma.editor.Config
import org.w3c.dom.HTMLSelectElement

class LangSelect {

    companion object {
        const val TAG = "lang-select"
    }

    fun buildEditorDom(): HTMLElement {
        val document = createHTMLDocument()
        document.body!!.append {
            div {
                attributes["TAG"] = TAG
                select {
                    id = "language-select"
                    val availableLanguages = Config.SITE_LANGUAGES[Config.currentSite] ?: emptyMap()
                    for ((langCode, langName) in availableLanguages) {
                        option {
                            value = langCode
                            text(langName)
                            if (langCode == Config.currentLanguage) {
                                selected = true
                            }
                        }
                    }
                    onchange {
                        val selectedLanguage = (it.target as HTMLSelectElement).value
                        Config.currentLanguage = selectedLanguage
                        console.log("refreshUI") // Or your preferred method to trigger UI refresh
                    }
                }
            }
        }
        return document.body!!.firstElementChild as HTMLElement
    }

    fun buildStaticLinks(currentUrl: String): HTMLElement {
        val document = createHTMLDocument()
        document.body!!.append {
            div {
                attributes["TAG"] = TAG // Optional: Add tag for consistency, though not used for editor logic
                val availableLanguages = Config.SITE_LANGUAGES[Config.currentSite] ?: emptyMap()
                for ((langCode, langName) in availableLanguages) {
                    // Basic URL replacement assuming /lang-code/ pattern
                    val newUrl = replaceLanguageCodeInUrl(currentUrl, langCode)
                    a {
                        href = newUrl
                        text(langName)
                        // Add classes for styling if needed
                        // classes = setOf("language-link")
                    }
                }
            }
        }
        return document.body!!.firstElementChild as HTMLElement
    }

    // Helper function to replace language code in URL (basic implementation)
    private fun replaceLanguageCodeInUrl(url: String, newLangCode: String): String {
        val parts = url.split("/")
        if (parts.size > 2 && parts[1].length == 2) { // Assumes /lang-code/pattern
            return "/" + newLangCode + "/" + parts.drop(2).joinToString("/")
        }
        return url // Return original URL if pattern not matched
    }
}
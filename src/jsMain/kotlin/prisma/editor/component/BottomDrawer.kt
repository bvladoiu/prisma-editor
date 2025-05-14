package prisma.editor.component

import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.html.*
import kotlinx.html.dom.*
import org.w3c.dom.HTMLElement
import org.w3c.dom.HTMLOptionElement
import org.w3c.dom.HTMLSelectElement
import prisma.editor.Editor
import prisma.editor.Config
import prisma.editor.css.CssRuleDefinition
import prisma.editor.css.Theme
import prisma.editor.css.Typography
import kotlin.js.JSON

/**
 * BottomDrawer component that renders a drawer at the bottom of the screen
 * for editing site, language, and page settings.
 * @param isOpen Whether the drawer is open
 */
class BottomDrawer(
    var isOpen: Boolean = false
) {
    // Use Config's properties for site, language, and page tag
    var currentSite: String
        get() = Config.currentSite
        set(value) { Config.currentSite = value }

    var currentLanguage: String
        get() = Config.currentLanguage
        set(value) { Config.currentLanguage = value }

    var currentPageTag: String
        get() = Config.currentPageTag
        set(value) { Config.currentPageTag = value }
    init {
        load()
    }

    fun preview(): HTMLElement {
        val drawer = document.create.aside {
            id = "bottom-drawer"
            attributes[TAG] = ""
            attributes["role"] = "dialog"
            attributes["aria-labelledby"] = "drawer-title"
            attributes[Editor.TAG] = ""
            asDynamic().kotlinInstance = this@BottomDrawer

            if (isOpen) {
                attributes["style"] = "transform: translateY(0px);"
            }

            h3 {
                id = "drawer-title"
                classes = setOf(Typography.HEADLINE)
                +"Site, Language, and Page Settings"
            }

            form {
                attributes[CONTENT_TAG] = ""

                div {
                    attributes[FIELD_TAG] = ""
                    attributes["role"] = "group"
                    attributes["aria-labelledby"] = "site-group-label"

                    h4 {
                        id = "site-group-label"
                        classes = setOf(Typography.TAGLINE)
                        +"Site"
                    }

                    label {
                        htmlFor = "site-select"
                        classes = setOf(Typography.CAPTION)
                        +"Site:"
                    }
                    select {
                        id = "site-select"
                        classes = setOf(Typography.BODY)
                        attributes["onchange"] = "prisma.editor.component.BottomDrawer.updateLanguageOptions()"

                        // Add options for sites
                        option {
                            value = "contadeal"
                            +"ContaDeal"
                        }
                        option {
                            value = "prisma"
                            +"PRISMA-Software"
                        }
                    }
                }

                div {
                    attributes[FIELD_TAG] = ""
                    attributes["role"] = "group"
                    attributes["aria-labelledby"] = "language-group-label"

                    h4 {
                        id = "language-group-label"
                        classes = setOf(Typography.TAGLINE)
                        +"Language"
                    }

                    label {
                        htmlFor = "language-select"
                        classes = setOf(Typography.CAPTION)
                        +"Language:"
                    }
                    select {
                        id = "language-select"
                        classes = setOf(Typography.BODY)
                    }
                }

                div {
                    attributes[FIELD_TAG] = ""
                    attributes["role"] = "group"
                    attributes["aria-labelledby"] = "page-group-label"

                    h4 {
                        id = "page-group-label"
                        classes = setOf(Typography.TAGLINE)
                        +"Page"
                    }

                    label {
                        htmlFor = "page-select"
                        classes = setOf(Typography.CAPTION)
                        +"Page:"
                    }
                    select {
                        id = "page-select"
                        classes = setOf(Typography.BODY)

                        // Add options for pages
                        option {
                            value = "home-page"
                            +"Home"
                        }
                        // Add more page options as needed
                    }
                }
            }
        }

        // Initialize language options and page select based on current values
        window.setTimeout({
            updateLanguageOptions()

            // Set the current page tag in the select element
            val pageSelect = document.getElementById("page-select") as? HTMLSelectElement
            if (pageSelect != null) {
                pageSelect.value = currentPageTag
            }
        }, 100)

        return drawer
    }

    fun commit() {
        val data = mapOf(
            "site" to currentSite,
            "language" to currentLanguage,
            "pageTag" to currentPageTag,
            "isOpen" to isOpen
        )
        val jsonData = JSON.stringify(data)
        console.log("save:${Config.currentSite}_${Config.currentLanguage}_$TAG", jsonData)
    }

    fun load() {
        console.log("load:${Config.currentSite}_${Config.currentLanguage}_$TAG")
    }

    fun set(data: dynamic) {
        if (data.site != null) {
            currentSite = data.site as String
        }
        if (data.language != null) {
            currentLanguage = data.language as String
        }
        if (data.pageTag != null) {
            currentPageTag = data.pageTag as String
        }
        if (data.isOpen != null) {
            isOpen = data.isOpen as Boolean
        }
        refresh()
    }

    fun refresh() {
        val existingElement = document.querySelector("[$TAG]")
        if (existingElement != null) {
            existingElement.parentElement?.replaceChild(preview(), existingElement)
        } else {
            console.warn("No existing element with attribute [$TAG] found to refresh.")
            document.body?.appendChild(preview())
        }
    }

    fun toggle() {
        isOpen = !isOpen
        val drawer = document.getElementById("bottom-drawer") as? HTMLElement
        drawer?.style?.transform = if (isOpen) "translateY(0px)" else "translateY(100%)"
    }

    fun updateValues() {
        val siteSelect = document.getElementById("site-select") as? HTMLSelectElement
        val languageSelect = document.getElementById("language-select") as? HTMLSelectElement
        val pageSelect = document.getElementById("page-select") as? HTMLSelectElement

        if (siteSelect != null && languageSelect != null && pageSelect != null) {
            currentSite = siteSelect.value
            currentLanguage = languageSelect.value
            currentPageTag = pageSelect.value
            commit()
        }
    }

    companion object {
        const val TAG = "bottom-drawer"
        const val CONTENT_TAG = "bottom-drawer-content"
        const val FIELD_TAG = "bottom-drawer-field"

        @JsName("updateLanguageOptions")
        fun updateLanguageOptions() {
            val siteSelect = document.getElementById("site-select") as? HTMLSelectElement
            val languageSelect = document.getElementById("language-select") as? HTMLSelectElement
            val bottomDrawer = document.querySelector("[$TAG]")?.asDynamic()?.kotlinInstance as? BottomDrawer

            if (siteSelect != null && languageSelect != null && bottomDrawer != null) {
                val selectedSite = siteSelect.value

                // Clear existing options
                languageSelect.innerHTML = ""

                // Get languages for the selected site
                val languages = when (selectedSite) {
                    "contadeal" -> arrayOf("en", "ro")
                    "prisma" -> arrayOf("en", "de")
                    else -> arrayOf("en")
                }

                // Add new options
                for (language in languages) {
                    val option = document.createElement("option") as HTMLOptionElement
                    option.value = language
                    option.text = language.uppercase()
                    languageSelect.add(option)
                }

                // Try to set the current language
                languageSelect.value = bottomDrawer.currentLanguage

                // If the value didn't change (language not available), select the first option
                if (languageSelect.value != bottomDrawer.currentLanguage && languageSelect.options.length > 0) {
                    languageSelect.selectedIndex = 0
                }
            }
        }

        @JsName("toggleDrawer")
        fun toggleDrawer() {
            val drawer = document.querySelector("[$TAG]")?.asDynamic()?.kotlinInstance as? BottomDrawer
            drawer?.toggle()
        }

        fun cssRules(): List<CssRuleDefinition> {
            return listOf(
                "[$TAG]" to {
                    position = "fixed"
                    bottom = "0"
                    left = "0"
                    width = "100%"
                    backgroundColor = Theme.white
                    setProperty("box-shadow", "0 -${Theme.spacing} ${Theme.spacing} ${Theme.shadowLight}")
                    padding = Theme.spacing
                    transform = "translateY(100%)"
                    transition = "transform 0.3s ease-in-out"
                    zIndex = "1000"
                },

                "[$TAG] h3" to {
                    margin = "0 0 ${Theme.spacing}"
                    color = Theme.darkBackground
                },

                "[$CONTENT_TAG]" to {
                    display = "flex"
                    flexDirection = "column"
                    setProperty("gap", Theme.spacing)
                },

                "[$FIELD_TAG]" to {
                    display = "flex"
                    flexDirection = "column"
                    setProperty("gap", Theme.spacing)
                },

                "[$FIELD_TAG] label" to {
                    color = Theme.mediumGray
                },

                "[$FIELD_TAG] select" to {
                    padding = Theme.spacing
                    borderRadius = Theme.spacing
                    border = "1px solid ${Theme.lightGray}"
                }
            )
        }
    }
}

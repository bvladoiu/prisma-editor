package prisma.editor.component

import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.html.*
import kotlinx.html.dom.*
import org.w3c.dom.HTMLElement
import org.w3c.dom.HTMLDialogElement
import org.w3c.dom.HTMLOptionElement
import org.w3c.dom.HTMLSelectElement
import prisma.editor.EditorJs
import prisma.editor.Config
import prisma.editor.css.CssRuleDefinition
import prisma.editor.css.Theme
import prisma.editor.css.Typography
import kotlin.js.JSON

class BottomDrawer(
    var isOpen: Boolean = false
) {
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
        val dialog = document.create.dialog {
            id = "bottom-drawer"
            attributes["TAG"] = TAG
            attributes["aria-labelledby"] = "drawer-title"
            asDynamic().kotlinInstance = this@BottomDrawer

            if (isOpen) {
                attributes["open"] = ""
            }

            h3 {
                id = "drawer-title"
                attributes["TAG"] = Typography.HEADLINE
                +"Site, Language, and Page Settings"
            }

            form {
                attributes["TAG"] = CONTENT_TAG

                div {
                    attributes["TAG"] = FIELD_TAG
                    attributes["role"] = "group"
                    attributes["aria-labelledby"] = "site-group-label"

                    h4 {
                        id = "site-group-label"
                        attributes[Typography.TAGLINE] = ""
                        +"Site"
                    }

                    label {
                        htmlFor = "site-select"
                        attributes[Typography.CAPTION] = ""
                        +"Site:"
                    }
                    select {
                        id = "site-select"
                        attributes[Typography.BODY] = ""
                        attributes["onchange"] = "prisma.editor.component.BottomDrawer.updateLanguageOptions()"

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
                    attributes["TAG"] = FIELD_TAG
                    attributes["role"] = "group"
                    attributes["aria-labelledby"] = "language-group-label"

                    h4 {
                        id = "language-group-label"
                        attributes[Typography.TAGLINE] = ""
                        +"Language"
                    }

                    label {
                        htmlFor = "language-select"
                        attributes[Typography.CAPTION] = ""
                        +"Language:"
                    }
                    select {
                        id = "language-select"
                        attributes[Typography.BODY] = ""
                    }
                }

                div {
                    attributes["TAG"] = FIELD_TAG
                    attributes["role"] = "group"
                    attributes["aria-labelledby"] = "page-group-label"

                    h4 {
                        id = "page-group-label"
                        attributes[Typography.TAGLINE] = ""
                        +"Page"
                    }

                    label {
                        htmlFor = "page-select"
                        attributes[Typography.CAPTION] = ""
                        +"Page:"
                    }
                    select {
                        id = "page-select"
                        attributes[Typography.BODY] = ""

                        option {
                            value = "home-page"
                            +"Home"
                        }
                    }
                }
            }
        }

        window.setTimeout({
            updateLanguageOptions()

            val pageSelect = document.getElementById("page-select") as? HTMLSelectElement
            if (pageSelect != null) {
                pageSelect.value = currentPageTag
            }
        }, 100)

        return dialog
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
        // Update properties
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

        // Refresh the DOM element
        refresh()

        // Ensure dialog state in DOM matches the 'isOpen' property after refresh
        val dialog = document.getElementById("bottom-drawer") as? HTMLDialogElement
        if (dialog != null) {
            if (isOpen && !dialog.hasAttribute("open")) {
                dialog.showModal()
            } else if (!isOpen && dialog.hasAttribute("open")) {
                dialog.close()
            }
        }
    }

    fun refresh() {
        val existingElement = document.querySelector("dialog[TAG='$TAG']") as? HTMLDialogElement
        if (existingElement != null) {
            // Store the current open state before replacing
            val wasOpen = existingElement.hasAttribute("open")
            val newElement = preview()

            // Replace the element
            existingElement.parentElement?.replaceChild(newElement, existingElement)

            // Restore the open state on the new element if it was open
            if (wasOpen) {
                // Need a slight delay to re-show after replacement
                window.setTimeout({
                    (document.getElementById("bottom-drawer") as? HTMLDialogElement)?.showModal()
                }, 0) // Use a 0ms delay to allow DOM update cycle
            }
        } else {
            console.warn("No existing element with attribute [TAG='$TAG'] found to refresh.")
            document.body?.appendChild(preview())

            // If appending and it should be open, show it
            if (isOpen) {
                (document.getElementById("bottom-drawer") as? HTMLDialogElement)?.showModal()
            }
        }
    }

    fun toggle() {
        isOpen = !isOpen
        val dialog = document.getElementById("bottom-drawer") as? HTMLDialogElement
        if (dialog != null) {
            if (isOpen) {
                dialog.showModal() // Use showModal() for a modal dialog with backdrop
            } else {
                dialog.close() // This sets dialog.open = false
            }
        }
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
            val bottomDrawer = document.querySelector("dialog[TAG='$TAG']")?.asDynamic()?.kotlinInstance as? BottomDrawer

            if (siteSelect != null && languageSelect != null && bottomDrawer != null) {
                val selectedSite = siteSelect.value

                languageSelect.innerHTML = ""

                val languages = when (selectedSite) {
                    "contadeal" -> arrayOf("en", "ro")
                    "prisma" -> arrayOf("en", "de")
                    else -> arrayOf("en")
                }

                for (language in languages) {
                    val option = document.createElement("option") as HTMLOptionElement
                    option.value = language
                    option.text = language.uppercase()
                    languageSelect.add(option)
                }

                languageSelect.value = bottomDrawer.currentLanguage

                if (languageSelect.value != bottomDrawer.currentLanguage && languageSelect.options.length > 0) {
                    languageSelect.selectedIndex = 0
                }
            }
        }

        @JsName("toggleDrawer")
        fun toggleDrawer() {
            // Query the dialog element and call toggle() on the Kotlin instance
            val drawer = document.querySelector("dialog[TAG='$TAG']")?.asDynamic()?.kotlinInstance as? BottomDrawer
            drawer?.toggle()
        }

        fun cssRules(): List<CssRuleDefinition> {
            return listOf(
                "dialog[TAG='$TAG']" to {
                    position = "fixed"
                    bottom = "-100%" // Start off-screen at the bottom
                    left = "0"
                    width = "100%"
                    border = "none" // Remove default dialog border
                    padding = Theme.spacing
                    margin = "0" // Remove default dialog margin
                    backgroundColor = Theme.white
                    setProperty("box-shadow", "0 -${Theme.spacing} ${Theme.spacing} ${Theme.shadowLight}")
                    setProperty("transition", "bottom 0.3s ease-in-out") // Add transition for animation
                    zIndex = "1000"
                },

                // Style the dialog when it's open
                "dialog[TAG='$TAG'][open]" to {
                    bottom = "0" // Slide up to view when open
                },

                // Style the backdrop created by showModal()
                "dialog[TAG='$TAG']::backdrop" to {
                    backgroundColor = "rgba(0, 0, 0, 0.5)" // Semi-transparent black overlay
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

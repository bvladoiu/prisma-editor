package prisma.editor.component

import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.html.*
import kotlinx.html.dom.*
import org.w3c.dom.HTMLElement
import org.w3c.dom.HTMLDialogElement
import org.w3c.dom.HTMLOptionElement
import org.w3c.dom.asList
import org.w3c.dom.events.Event
import org.w3c.dom.get
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
                // This form will contain the three main sections
                attributes["TAG"] = CONTENT_TAG // Reuse CONTENT_TAG for the form itself

                // Language Section
                div {
                    attributes["TAG"] = "language-section" // Unique tag for the language section
                    attributes["role"] = "group"
                    attributes["aria-labelledby"] = "site-group-label"

                    h4 {
                        id = "site-group-label"
                        attributes[Typography.TAGLINE] = ""
                        +"Languages"
                    }

                    div {
                        attributes["TAG"] = FIELD_TAG // Reuse FIELD_TAG for internal fields
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
                         attributes["TAG"] = FIELD_TAG // Reuse FIELD_TAG for internal fields
                        label {
                            htmlFor = "language-select"
                            attributes[Typography.CAPTION] = ""
                            +"Current Language:"
                        }
                        select {
                            id = "language-select"
                            attributes[Typography.BODY] = ""
                        }
                    }

                    // Add New Language Input and Button
                    div {
                        attributes["TAG"] = FIELD_TAG
                        label {
                            htmlFor = "new-language-input"
                            attributes[Typography.CAPTION] = ""
                            +"Add New Language:"
                        }
                        input {
                            id = "new-language-input"
                            type = InputType.text
                            attributes[Typography.BODY] = ""
                            placeholder = "e.g., es"
                        }
                        button {
                            id = "add-language-button"
                            attributes[Typography.BUTTON] = ""
                            +"Add Language"
                        }
                        comment("Placeholder for Add New Language functionality")
                    }
                }
                // Container for the list of existing languages
                div {
                    id = "language-list-container" // ID for the language list container

                }
                // Pages Section
                div {
                    attributes["TAG"] = "pages-section" // Unique tag for the section
 attributes["role"] = "group"
                    attributes["aria-labelledby"] = "page-group-label"
                    // Placeholder for Page group label (kept for consistency)
                    h4 {
                         id = "page-group-label"
                        attributes[Typography.TAGLINE] = ""
                        +"Page"
                }

                    div {
                        id = "page-list-container" // Container for the page list

                }

                    // Button to create a new page
                    div {
 attributes["TAG"] = FIELD_TAG // Reuse FIELD_TAG for layout
 button {
 id = "create-page-button"
                            attributes[Typography.BUTTON] = ""
 +"Create New Page"
                        }
                    }
                }
            }

 // Add event listener for the Add Language button after the element is created
            document.getElementById("add-language-button")?.addEventListener("click", {
 val newLanguageInput = document.getElementById("new-language-input") as? HTMLInputElement
 val newLanguage = newLanguageInput?.value?.trim()
                if (!newLanguage.isNullOrEmpty()) {
                    Config.sites[Config.currentSite]?.languages?.add(newLanguage) // Assuming Config.sites and languages are mutable
                    commit() // Save the updated config
                    newLanguageInput.value = "" // Clear the input field
                    updateLanguageOptions() // Refresh the language dropdown
 }
            })
        }

        window.setTimeout({
            updateLanguageOptions() // This now also loads pages after setting language options
        }, 100)

        // Add event listener for the Create New Page button after the element is created
 document.getElementById("create-page-button")?.addEventListener("click", {
            val pageName = window.prompt("Enter the name for the new page:")
            if (!pageName.isNullOrEmpty()) {
 console.log("createPage:$pageName")
 }
        })

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
        console.log("getPages:${Config.currentSite}:${Config.currentLanguage}")
        console.log("load:${Config.currentSite}_${Config.currentLanguage}_$TAG")
    }

    fun setPageList(pageList: dynamic) {
        val pageListContainer = document.getElementById("page-list-container")
        if (pageListContainer != null) {
 pageListContainer.innerHTML = "" // Clear previous list

 // Add a select dropdown for current page selection
 pageListContainer.appendChild(document.create.div {
 attributes["TAG"] = FIELD_TAG
 label {
 htmlFor = "page-select"
 attributes[Typography.CAPTION] = ""
 +"Select Page:"
 }
 select {
 id = "page-select"
 attributes[Typography.BODY] = ""
 pageList.asList().forEach { page ->
 option {
 value = page.tag as String // Assuming page object has a 'tag' property
 text = page.name as String // Assuming page object has a 'name' property
 }
 }
 attributes["onchange"] = "prisma.editor.component.BottomDrawer.updateCurrentPage()"
 }
 })

 // Display list of pages with actions
 pageList.asList().forEach { page ->
 pageListContainer.appendChild(document.create.div {
 attributes["TAG"] = "page-item" // Unique tag for each page item
 attributes[Typography.BODY] = ""
 +page.name + " (" + page.url + ")" // Display name and URL

 // Action buttons for each page
 button {
 attributes[Typography.BUTTON] = ""
 +"Select"
 }
 button {
 attributes[Typography.BUTTON] = ""
 +"Edit"
 }
 button {
 attributes[Typography.BUTTON] = ""
 +"Delete"
 }
 })
 }
        }
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

 @JsName("updateCurrentPage")
 fun updateCurrentPage() { // This function updates both site and language
        val siteSelect = document.getElementById("site-select") as? HTMLSelectElement
        val languageSelect = document.getElementById("language-select") as? HTMLSelectElement
        val pageSelect = document.getElementById("page-select") as? HTMLSelectElement

        if (siteSelect != null && languageSelect != null && pageSelect != null) { // Check if all required elements exist
 currentSite = siteSelect.value

            // Only update language if it has actually changed to avoid unnecessary page reload
 if (currentLanguage != languageSelect.value) {
 currentLanguage = languageSelect.value
 // When language changes, we need to request the new page list and potentially reload the current page content
 load() // This will now trigger fetching pages as well
 }

 currentPageTag = pageSelect.value
 commit()
        }
    }

    companion object {
        const val TAG = "bottom-drawer"
        const val CONTENT_TAG = "bottom-drawer-content"
        const val FIELD_TAG = "bottom-drawer-field"

        @JsName("updateLanguageOptions")
 fun updateLanguageOptions() { // This function populates the language dropdown based on the selected site
            val siteSelect = document.getElementById("site-select") as? HTMLSelectElement
            val languageSelect = document.getElementById("language-select") as? HTMLSelectElement
            val bottomDrawer = document.querySelector("dialog[TAG='$TAG']")?.asDynamic()?.kotlinInstance as? BottomDrawer
            // Ensure all required elements and the BottomDrawer instance are available
            if (siteSelect != null && languageSelect != null && bottomDrawer != null) {
                val selectedSite = siteSelect.value

 languageSelect.innerHTML = ""

                // Assuming Config.sites has a structure like Map<String, SiteConfig> where SiteConfig has a list of languages
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

 // If the stored language is not available for the new site, default to the first available
 if (languageSelect.value != bottomDrawer.currentLanguage && languageSelect.options.length > 0) {
 languageSelect.selectedIndex = 0
 }

                // After updating language options, request the page list for the new site/language
 bottomDrawer.load()
 }
 }

        @JsName("toggleDrawer")
        fun toggleDrawer() {
            // Query the dialog element and call toggle() on the Kotlin instance
            val drawer = document.querySelector("dialog[TAG='$TAG']")?.asDynamic()?.kotlinInstance as? BottomDrawer
            drawer?.toggle()
        }

        fun cssRules(): List<CssRuleDefinition> {
            return listOf(\n                "dialog[TAG='$TAG']" to {\n                    position = "fixed"\n                    bottom = "-100%" // Start off-screen at the bottom
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
                "dialog[TAG='$TAG'][open]" to {\n                    bottom = "0" // Slide up to view when open
                },

                // Style the backdrop created by showModal()
                "dialog[TAG='$TAG']::backdrop" to {\n                    backgroundColor = "rgba(0, 0, 0, 0.5)" // Semi-transparent black overlay
                },

                "[$TAG] h3" to {\n                    margin = "0 0 ${Theme.spacing}"
                    color = Theme.darkBackground
                },

                "[$CONTENT_TAG]" to {\n                    display = "flex"
                    flexDirection = "column"
                    setProperty("gap", Theme.spacing)
                },

                "[$FIELD_TAG]" to {\n                    display = "flex"
                    flexDirection = "column"
                    setProperty("gap", Theme.spacing)
                },

                "[$FIELD_TAG] label" to {\n                    color = Theme.mediumGray
                },

                "[$FIELD_TAG] select" to {\n                    padding = Theme.spacing
                    borderRadius = Theme.spacing
                    border = "1px solid ${Theme.lightGray}"
                }
            )\n        }
    }
}
                    }
                }
                 attributes["onchange"] = "prisma.editor.component.BottomDrawer.updateCurrentPage()"
            })
        }
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
    
    @JsName("updateCurrentPage")
    fun updateCurrentPage() {
        val siteSelect = document.getElementById("site-select") as? HTMLSelectElement
        val languageSelect = document.getElementById("language-select") as? HTMLSelectElement
        val pageSelect = document.getElementById("page-select") as? HTMLSelectElement

        if (siteSelect != null && languageSelect != null && pageSelect != null) {
            currentSite = siteSelect.value

            // Only update language if it has actually changed to avoid unnecessary page reload
            if (currentLanguage != languageSelect.value) {
                 currentLanguage = languageSelect.value
                 // When language changes, we need to request the new page list
                 load() // This will now trigger fetching pages as well
            }

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
 val languageListContainer = document.getElementById("language-list-container")
            val bottomDrawer = document.querySelector("dialog[TAG='$TAG']")?.asDynamic()?.kotlinInstance as? BottomDrawer

            if (siteSelect != null && languageSelect != null && bottomDrawer != null) {
                val selectedSite = siteSelect.value

                languageSelect.innerHTML = ""

                // Get languages for the selected site from Config (assuming mutable structure)
                val languages = Config.sites[selectedSite]?.languages ?: mutableListOf()

                // Populate the language select dropdown
 for (language in languages) {
                    val option = document.createElement("option") as HTMLOptionElement
                    option.value = language
                    option.text = language.uppercase()
                    languageSelect.add(option)
                }

                // Populate the language list container with edit/delete options
 if (languageListContainer != null) {
 languageListContainer.innerHTML = "" // Clear previous list

 for (language in languages) {
 languageListContainer.appendChild(document.create.div {
 attributes["TAG"] = "language-item" // Unique tag for each language item
 attributes[Typography.BODY] = ""
 +"${language.uppercase()} " // Display language name with a space for buttons

 // Action buttons for each language
 button {
 attributes[Typography.BUTTON] = ""
 +"Edit"
 addEventListener("click", { event: Event ->
                    val newLanguageName = window.prompt("Enter the new name for language '$language':")
                    if (!newLanguageName.isNullOrEmpty()) {
 console.log("updateLanguage:$selectedSite:$language:$newLanguageName")
                    }
 event.stopPropagation() // Prevent potential parent element clicks
 })
 }
 button {
 attributes[Typography.BUTTON] = ""
 +"Delete"
 addEventListener("click", { event: Event ->
                    if (window.confirm("Are you sure you want to delete language '$language'?")) {
 console.log("deleteLanguage:$selectedSite:$language")
                    }
 event.stopPropagation() // Prevent potential parent element clicks
 })
 }
 })
 }

                // Set the dropdown value to the current language
                languageSelect.value = bottomDrawer.currentLanguage

                 // If the stored language is not available for the new site, default to the first available
                if (languageSelect.value != bottomDrawer.currentLanguage && languageSelect.options.length > 0) {
                    languageSelect.selectedIndex = 0
                 }


                // After updating language options, load the drawer state and page list
                bottomDrawer.load() // This now triggers fetching pages as well
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

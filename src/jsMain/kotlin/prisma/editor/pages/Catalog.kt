package prisma.editor.pages

import kotlinx.browser.document
import kotlinx.html.*
import kotlinx.html.dom.*
import kotlinx.html.js.onClickFunction
import org.w3c.dom.HTMLElement
import org.w3c.dom.events.Event
import prisma.editor.Config
import prisma.editor.component.*
import kotlin.js.JSON

/**
 * Catalog page for showcasing all components with their commit/set/load APIs.
 * This page saves and retrieves data against the 'common' site name and language.
 */
class Catalog : Page() {
    override val tag: String = TAG

    // Map to store component data
    private val componentData = mutableMapOf<String, Any>()

    init {
        // Set the site to 'common' for the catalog
        Config.currentSite = "common"
        load()
    }

    companion object {
        const val TAG = "catalog-page"

        // List of all component types to showcase
        private val COMPONENT_TYPES = listOf(
            "Hero",
            "Expertise",
            "ArticleCard",
            "KeywordStrip",
            "Section",
            "FloatingActionButton"
        )

        /**
         * CSS rules for the catalog page.
         */
        fun cssRules(): List<prisma.editor.css.CssRuleDefinition> {
            return listOf(
                "[catalog-page]" to {
                    width = "100%"
                    padding = "16px"
                },

                "[component-showcase]" to {
                    border = "1px solid #ccc"
                    borderRadius = "4px"
                    padding = "16px"
                    marginBottom = "16px"
                    backgroundColor = "#f9f9f9"
                },

                "[component-buttons]" to {
                    display = "flex"
                    marginTop = "16px"
                },

                "[component-buttons] button:not(:last-child)" to {
                    marginRight = "8px"
                },

                ".component-button" to {
                    padding = "8px 16px"
                    backgroundColor = "#4CAF50"
                    color = "white"
                    border = "none"
                    borderRadius = "4px"
                    cursor = "pointer"
                },

                ".component-button:hover" to {
                    backgroundColor = "#45a049"
                }
            )
        }
    }

    override fun create(): HTMLElement {
        // Call parent's create() to set up common page elements
        val container = super.create()

        // Add catalog-page attribute
        container.setAttribute("catalog-page", "")

        // Add header section
        val headerSection = Section(
            title = "Component Catalog",
            isDivider = false
        )
        val headerElement = headerSection.preview()

        val headerContent = document.create.div {
            p {
                +"This page showcases all available components with their commit/set/load APIs."
            }
            p {
                +"All component data is saved against the 'common' site and selected language."
            }
        }

        headerElement.querySelector("div")?.appendChild(headerContent)
        container.appendChild(headerElement)

        // Add component showcase sections
        addComponentShowcase(container)

        return container
    }

    private fun addComponentShowcase(container: HTMLElement) {
        // Hero component showcase
        addComponentSection(container, "Hero Component", createHeroComponent())

        // Expertise component showcase
        addComponentSection(container, "Expertise Component", createExpertiseComponent())

        // ArticleCard component showcase
        addComponentSection(container, "ArticleCard Component", createArticleCardComponent())

        // KeywordStrip component showcase
        addComponentSection(container, "KeywordStrip Component", createKeywordStripComponent())

        // Section component showcase
        addComponentSection(container, "Section Component", createSectionComponent())

        // FloatingActionButton component showcase
        addComponentSection(container, "FloatingActionButton Component", createFabComponent())
    }

    private fun addComponentSection(container: HTMLElement, title: String, componentElement: HTMLElement) {
        val section = Section(
            title = title,
            isDivider = true
        )
        val sectionElement = section.preview()

        val componentContainer = document.create.div {
            attributes["component-showcase"] = ""
        }

        componentContainer.appendChild(componentElement)

        // Add buttons for component operations
        val buttonsContainer = document.create.div {
            attributes["component-buttons"] = ""

            button {
                attributes["class"] = "component-button"
                attributes["data-component"] = componentElement.getAttribute("TAG") ?: ""
                attributes["data-action"] = "commit"
                +"Save"

                // Add click event listener
                onClickFunction = { event ->
                    val componentTag = (event.currentTarget as HTMLElement).getAttribute("data-component")
                    val component = document.querySelector("[TAG='$componentTag']")?.asDynamic()?.kotlinInstance
                    component?.commit()
                }
            }

            button {
                attributes["class"] = "component-button"
                attributes["data-component"] = componentElement.getAttribute("TAG") ?: ""
                attributes["data-action"] = "load"
                +"Load"

                // Add click event listener
                onClickFunction = { event ->
                    val componentTag = (event.currentTarget as HTMLElement).getAttribute("data-component")
                    val component = document.querySelector("[TAG='$componentTag']")?.asDynamic()?.kotlinInstance
                    component?.load()
                }
            }
        }

        componentContainer.appendChild(buttonsContainer)
        sectionElement.querySelector("div")?.appendChild(componentContainer)
        container.appendChild(sectionElement)
    }

    private fun createHeroComponent(): HTMLElement {
        val hero = Hero(
            name = "Hero Component Example",
            description = "This is an example of the Hero component with its commit/set/load APIs.",
            buttonText = "Action Button"
        )
        return hero.preview()
    }

    private fun createExpertiseComponent(): HTMLElement {
        val expertise = Expertise(
            name = "Expertise Component Example",
            description = "This is an example of the Expertise component with its commit/set/load APIs."
        )
        return expertise.preview()
    }

    private fun createArticleCardComponent(): HTMLElement {
        val articleCard = ArticleCard(
            title = "Article Card Example",
            author = "Author Name",
            date = "Jan 1, 2024"
        )
        return articleCard.preview()
    }

    private fun createKeywordStripComponent(): HTMLElement {
        val keywords = listOf("Keyword1", "Keyword2", "Keyword3", "Keyword4", "Keyword5")
        val keywordStrip = KeywordStrip(keywords)
        return keywordStrip.preview()
    }

    private fun createSectionComponent(): HTMLElement {
        val section = Section(
            title = "Section Component Example",
            isDivider = false
        )
        val sectionElement = section.preview()

        val content = document.create.p {
            +"This is an example of the Section component with its commit/set/load APIs."
        }

        sectionElement.querySelector("div")?.appendChild(content)
        return sectionElement
    }

    private fun createFabComponent(): HTMLElement {
        val fab = FloatingActionButton("add") {
            console.log("FAB clicked")
        }
        return fab.preview()
    }

    override fun commit() {
        // Save all component data
        val data = componentData.toMap()
        val jsonData = JSON.stringify(data)
        console.log("save:${Config.currentSite}_${Config.currentLanguage}_$tag", jsonData)
    }

    override fun load() {
        // Load all component data
        console.log("load:${Config.currentSite}_${Config.currentLanguage}_$tag")
    }

    override fun set(data: dynamic) {
        // Set component data
        if (data != null) {
            for (key in js("Object").keys(data)) {
                componentData[key as String] = data[key]
            }
        }
        refresh()
    }
}

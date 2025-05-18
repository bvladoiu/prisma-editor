package prisma.editor.pages

import kotlinx.browser.document
import kotlinx.html.button
import kotlinx.html.div
import kotlinx.html.dom.create
import kotlinx.html.js.onClickFunction
import kotlinx.html.p
import org.w3c.dom.HTMLElement
import prisma.editor.Config
import prisma.editor.component.*

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
            initialTitle = "Component Catalog",
            initialIsDivider = false
        )
        val headerElement = headerSection.buildHtml()

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

        // Hero Section + related dummy data + buildhtml + renderto
            var title = "Hero Component"
            var componentElement = Hero(
                initialName = "Hero Component Example",
                initialDescription = "This is an example of the Hero component with its commit/set/load APIs.",
                initialButtonText = "Action Button"
            ).buildHtml()

            var section = Section(
                initialTitle = title,
                initialIsDivider = true
            )
            var sectionElement = section.buildHtml()

            var componentContainer = document.create.div {
                attributes["component-showcase"] = ""
            }

            componentContainer.appendChild(componentElement)

            // Add buttons for component operations
            var buttonsContainer = document.create.div {
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

        // Expertise Section + related dummy data + buildhtml + renderto

            title = "Expertise Component"
            componentElement = Expertise(
                initialName = "Expertise Component Example",
                initialDescription = "This is an example of the Expertise component with its commit/set/load APIs."
            ).buildHtml()

            section = Section(
                initialTitle = title,
                initialIsDivider = true
            )
            sectionElement = section.buildHtml()

            componentContainer = document.create.div {
                attributes["component-showcase"] = ""
            }

            componentContainer.appendChild(componentElement)

            // Add buttons for component operations
            buttonsContainer = document.create.div {
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


        // ArticleCard Section + related dummy data + buildhtml + renderto

            title = "ArticleCard Component"
            componentElement = ArticleCard(
                initialTitle = "Article Card Example",
                initialAuthor = "Author Name",
                initialDate = "Jan 1, 2024"
            ).buildHtml()

            section = Section(
                initialTitle = title,
                initialIsDivider = true
            )
            sectionElement = section.buildHtml()

            componentContainer = document.create.div {
                attributes["component-showcase"] = ""
            }

            componentContainer.appendChild(componentElement)

            // Add buttons for component operations
            buttonsContainer = document.create.div {
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


        // KeywordStrip Section + related dummy data + buildhtml + renderto

            title = "KeywordStrip Component"
            componentElement = KeywordStrip(
                initialKeywords = listOf("Keyword1", "Keyword2", "Keyword3", "Keyword4", "Keyword5")
            ).buildHtml()

            section = Section(
                initialTitle = title,
                initialIsDivider = true
            )
            sectionElement = section.buildHtml()

            componentContainer = document.create.div {
                attributes["component-showcase"] = ""
            }

            componentContainer.appendChild(componentElement)

            // Add buttons for component operations
            buttonsContainer = document.create.div {
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


        // Section Component + related dummy data + buildhtml + renderto

            title = "Section Component"
            var sectionComponent = Section(
                initialTitle = "Section Component Example",
                initialIsDivider = false
            )
            componentElement = sectionComponent.buildHtml()
            var content = document.create.p {
                +"This is an example of the Section component with its commit/set/load APIs."
            }
            componentElement.querySelector("div")?.appendChild(content)

            section = Section(
                initialTitle = title,
                initialIsDivider = true
            )
            sectionElement = section.buildHtml()

            componentContainer = document.create.div {
                attributes["component-showcase"] = ""
            }

            componentContainer.appendChild(componentElement)

            // Add buttons for component operations
            buttonsContainer = document.create.div {
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


        // FloatingActionButton Section + related dummy data + buildhtml + renderto

            title = "FloatingActionButton Component"
            componentElement = FloatingActionButton("add") {
                console.log("FAB clicked")
            }.buildHtml()

            section = Section(
                initialTitle = title,
                initialIsDivider = true
            )
            sectionElement = section.buildHtml()

            componentContainer = document.create.div {
                attributes["component-showcase"] = ""
            }

            componentContainer.appendChild(componentElement)

            // Add buttons for component operations
            buttonsContainer = document.create.div {
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


        return container
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

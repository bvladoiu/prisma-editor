package prisma.editor.ui.pages

import kotlinx.browser.document
import kotlinx.html.*
import kotlinx.html.dom.*
import web.html.HTMLElement
import org.w3c.dom.HTMLElement as W3CHTMLElement
import prisma.editor.model.Section
import prisma.editor.model.Update
import prisma.editor.model.fetchResource
import prisma.editor.ui.component.*
import kotlin.js.Json

@JsName("undefined")
external val undefined: dynamic

/**
 * Creates the home page component that aggregates other components.
 * Follows the composite pattern where the create() method calls create() on child components.
 */
class Home : HTMLElement {
    // Properties from the Home model
    var navigationBrand: String = ""
    var navigationItems: List<String> = emptyList()
    var heroName: String = ""
    var heroDescription: String = ""
    var heroButtonText: String = ""
    var sections: List<Section> = emptyList()
    var footerCopyright: String = ""
    var footerLinks: List<String> = emptyList()

    /**
     * Creates the home page.
     */
    suspend fun create(): W3CHTMLElement {
        // Load data from JSON
        val jsonString = fetchResource("/data/en/home.json")
        parseHomeData(jsonString)

        return createUI()
    }

    /**
     * Parse JSON string to extract home data
     */
    private fun parseHomeData(jsonString: String) {
        val jsonObj = JSON.parse<Json>(jsonString)

        // Parse navigation
        val navObj = jsonObj["navigation"].unsafeCast<Json>()
        navigationBrand = navObj["brand"].toString()
        navigationItems = navObj["items"].unsafeCast<Array<String>>().toList()

        // Parse hero
        val heroObj = jsonObj["hero"].unsafeCast<Json>()
        heroName = heroObj["title"].toString()
        heroDescription = heroObj["description"].toString()
        heroButtonText = heroObj["buttonText"].toString()

        // Parse sections
        val sectionsArray = jsonObj["sections"].unsafeCast<Array<Json>>()
        sections = sectionsArray.map { sectionObj ->
            val title = sectionObj["title"]
            val isDivider = sectionObj["isDivider"].unsafeCast<Boolean>()

            // Parse items if present
            val items = if (sectionObj["items"] !== undefined) {
                sectionObj["items"].unsafeCast<Array<Json>>().map { itemObj ->
                    prisma.editor.model.Expertise(
                        name = itemObj["title"].toString(),
                        description = itemObj["description"].toString()
                    )
                }
            } else null

            // Parse keywordStrip if present
            val keywordStrip = if (sectionObj["keywordStrip"] !== undefined) {
                sectionObj["keywordStrip"].unsafeCast<Array<String>>().toList()
            } else null

            // Parse updates if present
            val updates = if (sectionObj["updates"] !== undefined) {
                sectionObj["updates"].unsafeCast<Array<Json>>().map { updateObj ->
                    Update(
                        title = updateObj["title"].toString(),
                        author = updateObj["author"].toString(),
                        date = updateObj["date"].toString()
                    )
                }
            } else null

            // Parse buttonText if present
            val buttonText = if (sectionObj["buttonText"] !== undefined) {
                sectionObj["buttonText"].toString()
            } else null

            Section(
                title = if (title !== undefined) title.toString() else null,
                isDivider = isDivider,
                items = items,
                keywordStrip = keywordStrip,
                updates = updates,
                buttonText = buttonText
            )
        }

        // Parse footer
        val footerObj = jsonObj["footer"].unsafeCast<Json>()
        footerCopyright = footerObj["copyright"].toString()
        footerLinks = footerObj["links"].unsafeCast<Array<String>>().toList()
    }

    /**
     * Creates the UI for the home page.
     */
    private fun createUI(): W3CHTMLElement {
        // Create the main container
        val container = document.create.div {
            attributes["home-page"] = ""
            attributes["style"] = """
                max-width: 1200px;
                margin: 0 auto;
                padding: 0 16px;
            """
        }

        // Hero Section
        val heroComponent = Hero(
            name = heroName,
            description = heroDescription,
            buttonText = heroButtonText
        )
        val heroSection = Section()
        val heroElement = heroSection.create(
            title = null,
            isDivider = false,
            marginTop = "5rem",
            content = ""
        )

        // Create hero content and append it to the section
        val heroContent = heroComponent.create()

        // Find the content div in the section and append the hero content to it
        heroElement.querySelector("div")?.appendChild(heroContent)

        // Append hero section to the container
        container.appendChild(heroElement)

        // Render all sections
        sections.forEach { section ->
            val sectionComponent = Section()
            val sectionElement = sectionComponent.create(
                title = section.title,
                isDivider = section.isDivider
            )

            // Create section content based on its type
            when {
                // Section with items (Core Expertise or Why Work With Us)
                section.items != null -> {
                    val minWidth = if (section.title == "Why Work With Us?") "200px" else "300px"

                    // Create a grid container for the items
                    val itemsContainer = document.create.div {
                        attributes["style"] = """
                            display: grid;
                            grid-template-columns: repeat(auto-fit, minmax($minWidth, 1fr));
                            gap: 16px;
                        """
                    }

                    // Add items to the grid
                    section.items.forEach { item ->
                        if (section.title == "Our Core Expertise") {
                            val expertiseComponent = Expertise(item.name, item.description)
                            val expertiseElement = expertiseComponent.preview()
                            itemsContainer.appendChild(expertiseElement)
                        } else {
                            val pitchComponent = Pitch()
                            val pitchElement = pitchComponent.create(
                                title = item.name,
                                description = item.description
                            )
                            itemsContainer.appendChild(pitchElement)
                        }
                    }

                    // Append the grid to the section content div
                    sectionElement.querySelector("div")?.appendChild(itemsContainer)

                    // Add button if present (for Why Work With Us section)
                    if (section.buttonText != null) {
                        val buttonContainer = document.create.div {
                            attributes["style"] = "margin-top: 2rem;"
                        }

                        val buttonElement = document.create.button {
                            attributes["style"] = """
                                background-color: #7112a1;
                                color: #56b2f0;
                                padding: 8px 16px;
                                border: 0;
                                border-radius: 4px;
                                cursor: pointer;
                            """
                            +section.buttonText
                        }

                        buttonContainer.appendChild(buttonElement)
                        sectionElement.querySelector("div")?.appendChild(buttonContainer)
                    }
                }

                // Keyword Strip section
                section.keywordStrip != null -> {
                    val keywordStripComponent = KeywordStrip()
                    val keywordStripElement = keywordStripComponent.create(
                        keywords = section.keywordStrip
                    )
                    sectionElement.querySelector("div")?.appendChild(keywordStripElement)
                }

                // Latest Updates section
                section.updates != null -> {
                    val updatesComponent = Updates()
                    val updatesElement = updatesComponent.create(
                        updates = section.updates
                    )
                    sectionElement.querySelector("div")?.appendChild(updatesElement)
                }
            }

            // Append section to the container
            container.appendChild(sectionElement)
        }

        // Footer
        val footerComponent = Footer(
            copyright = footerCopyright,
            links = footerLinks
        )
        val footerElement = footerComponent.create()
        container.appendChild(footerElement)

        return container
    }
}

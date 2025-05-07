package prisma.editor.pages

import kotlinx.browser.document
import kotlinx.html.*
import kotlinx.html.dom.*
import org.w3c.dom.HTMLElement
import prisma.editor.component.*
import prisma.editor.Editor
import prisma.editor.styles.Theme

/**
 * Creates the home page component that aggregates other components.
 */
class Home {
    /**
     * Creates the home page.
     */
    fun create(): HTMLElement {
        // Create the main container
        val container = document.create.div {
            attributes["home-page"] = ""
            attributes["style"] = """
                max-width: ${Theme.Spacing.maxContentWidth};
                margin: 0 auto;
                padding: 0 16px;
            """
        }

        // Welcome Section
        val welcomeSection = Section(
            title = "Welcome to Prisma Editor",
            isDivider = true
        )
        val welcomeElement = welcomeSection.preview()
        
        // Add content to the welcome section
        val welcomeContent = document.create.div {
            p {
                attributes["style"] = """
                    font-size: ${Theme.Typography.fontMd};
                    line-height: ${Theme.Typography.lineHeightLarge};
                    margin-bottom: ${Theme.Spacing.md};
                """
                +"Welcome to Prisma Editor, a powerful tool for editing and managing your content."
            }
            
            p {
                attributes["style"] = """
                    font-size: ${Theme.Typography.fontMd};
                    line-height: ${Theme.Typography.lineHeightLarge};
                """
                +"Use the import and export functions to save and load your settings."
            }
        }
        
        // Find the content div in the section and append the welcome content
        welcomeElement.querySelector("div")?.appendChild(welcomeContent)
        
        // Append welcome section to the container
        container.appendChild(welcomeElement)
        
        // Features Section
        val featuresSection = Section(
            title = "Features",
            isDivider = true
        )
        val featuresElement = featuresSection.preview()
        
        // Create a grid container for the features
        val featuresContainer = document.create.div {
            attributes["style"] = """
                display: grid;
                grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
                gap: 16px;
            """
        }
        
        // Add features
        val features = listOf(
            Pair("Import/Export", "Save and load your settings with the import and export functions."),
            Pair("Customization", "Customize your editor with various settings and options."),
            Pair("Responsive Design", "The editor works on all devices and screen sizes.")
        )
        
        features.forEach { (title, description) ->
            val expertiseComponent = Expertise(title, description)
            val expertiseElement = expertiseComponent.preview()
            featuresContainer.appendChild(expertiseElement)
        }
        
        // Append the grid to the section content div
        featuresElement.querySelector("div")?.appendChild(featuresContainer)
        
        // Append features section to the container
        container.appendChild(featuresElement)
        
        // Latest Updates Section
        val latestSection = Section(
            title = "Latest Updates",
            isDivider = true
        )
        val latestElement = latestSection.preview()
        
        // Create article cards
        val articles = listOf(
            ArticleCard("New Import/Export Feature", "Admin", "Today"),
            ArticleCard("Improved UI Design", "Designer", "Yesterday"),
            ArticleCard("Bug Fixes and Performance Improvements", "Developer", "Last Week")
        )
        
        // Create Latest component with the articles
        val latestComponent = Latest(articles)
        val latestListElement = latestComponent.preview()
        
        // Append the latest list to the section content div
        latestElement.querySelector("div")?.appendChild(latestListElement)
        
        // Append latest section to the container
        container.appendChild(latestElement)
        
        // Settings Section
        val settingsSection = Section(
            title = "Settings",
            isDivider = true
        )
        val settingsElement = settingsSection.preview()
        
        // Create settings content
        val settingsContent = document.create.div {
            p {
                attributes["style"] = """
                    font-size: ${Theme.Typography.fontMd};
                    line-height: ${Theme.Typography.lineHeightLarge};
                    margin-bottom: ${Theme.Spacing.md};
                """
                +"You can import and export your settings using the buttons below."
            }
            
            // Import/Export buttons
            div {
                attributes["style"] = """
                    display: flex;
                    gap: ${Theme.Spacing.md};
                    margin-top: ${Theme.Spacing.md};
                """
                
                button {
                    attributes["style"] = """
                        background-color: ${Theme.Colors.secondary};
                        color: ${Theme.Colors.white};
                        padding: ${Theme.Spacing.sm} ${Theme.Spacing.md};
                        border: 0;
                        border-radius: ${Theme.Spacing.borderRadius};
                        cursor: pointer;
                        font-size: ${Theme.Typography.fontSm};
                    """
                    attributes["id"] = "import-button"
                    +"Import Settings"
                }
                
                button {
                    attributes["style"] = """
                        background-color: ${Theme.Colors.primary};
                        color: ${Theme.Colors.white};
                        padding: ${Theme.Spacing.sm} ${Theme.Spacing.md};
                        border: 0;
                        border-radius: ${Theme.Spacing.borderRadius};
                        cursor: pointer;
                        font-size: ${Theme.Typography.fontSm};
                    """
                    attributes["id"] = "export-button"
                    +"Export Settings"
                }
            }
        }
        
        // Append the settings content to the section content div
        settingsElement.querySelector("div")?.appendChild(settingsContent)
        
        // Append settings section to the container
        container.appendChild(settingsElement)
        
        // Add JavaScript for import/export buttons
        val script = document.createElement("script") as HTMLElement
        script.innerHTML = """
            document.getElementById('import-button').addEventListener('click', function() {
                const jsonString = prompt('Paste your settings JSON:');
                if (jsonString) {
                    prisma.editor.Editor.import(jsonString);
                    alert('Settings imported successfully!');
                }
            });
            
            document.getElementById('export-button').addEventListener('click', function() {
                const jsonString = prisma.editor.Editor.export();
                prompt('Copy your settings JSON:', jsonString);
            });
        """
        container.appendChild(script)
        
        return container
    }
}
package prisma.editor.styles

import kotlinx.browser.document
import org.w3c.dom.css.*
import prisma.editor.component.*

/**
 * Main stylesheet that aggregates all styles from components and Theme.
 * This is used to provide a single stylesheet for the application.
 */
object Main {
    /**
     * Creates and returns a stylesheet for the application.
     * This method uses CSSOM API to create a stylesheet with all styles.
     */
    fun stylesheet(): CSSStyleSheet {
        // Create a new style element
        val styleElement = document.createElement("style")
        document.head?.appendChild(styleElement)

        // Get the stylesheet from the document's styleSheets collection
        val stylesheet = document.styleSheets[document.styleSheets.length - 1] as CSSStyleSheet

        // Add Theme stylesheet
        // This will add typography and global styles
        Theme.stylesheet()

        // Add component stylesheets
        ArticleCard.stylesheet()
        Latest.stylesheet()
        Footer.stylesheet()
        Hero.stylesheet()
        KeywordStrip.stylesheet()
        NavigationMenu.stylesheet()
        Pitch.stylesheet()
        Section.stylesheet()
        Expertise.stylesheet()
        EditorScaffold.stylesheet()
        HomeStyles.stylesheet()

        return stylesheet
    }

    /**
     * Initializes the main stylesheet by calling the stylesheet() method.
     * This should be called once when the application starts.
     */
    fun initialize() {
        stylesheet()
    }

    /**
     * Exports the main stylesheet as a CSS string.
     * This can be used to download the stylesheet.
     * 
     * Note: This method is not implemented yet as it requires access to the stylesheet rules
     * which is not straightforward with CSSOM API. We'll implement it later if needed.
     */
    fun exportStylesheet(): String {
        // This is a placeholder for now
        return "/* Stylesheet export not implemented yet */"
    }
}

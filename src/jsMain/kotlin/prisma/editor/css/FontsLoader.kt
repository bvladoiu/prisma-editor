package prisma.editor.css

import kotlinx.browser.document
import org.w3c.dom.HTMLLinkElement

/**
 * FontsLoader manages font loading for the application.
 * It tracks icon names and provides methods for generating HTML link tags
 * for the head element to optimize font loading.
 */
object FontsLoader {
    // Set to track unique icon names used in the application
    private val iconNames = mutableSetOf<String>()
    
    /**
     * Registers an icon name to be included in the Material Symbols font loading.
     * @param name The name of the Material Symbols icon.
     */
    fun registerIcon(name: String) {
        iconNames.add(name)
    }
    
    /**
     * Gets all registered icon names in alphabetical order.
     * @return List of icon names in alphabetical order.
     */
    fun getRegisteredIcons(): List<String> {
        return iconNames.toList().sorted()
    }
    
    /**
     * Generates an HTML link tag for loading Material Symbols font with only the required icons.
     * Follows best practices for the Google Font service.
     * Omits the optical size axis as it can cause bugs when not auto-managed by the browser.
     * @return HTML link element that can be added to the head.
     */
    fun getMaterialSymbolsLink(): HTMLLinkElement {
        val sortedIcons = getRegisteredIcons()
        
        // If no icons are registered, return a link with a default set of axes
        if (sortedIcons.isEmpty()) {
            return createLinkElement(
                "https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:wght@100..700&display=swap"
            )
        }
        
        // Create a query parameter with all registered icons
        val iconParam = sortedIcons.joinToString(",")
        
        // Create the URL with the icon parameter and required axes (omitting 'opsz')
        val url = "https://fonts.googleapis.com/icon?family=Material+Symbols+Outlined" +
                "&text=$iconParam" +
                "&wght@100..700" +
                "&fill@0..1" +
                "&grad@-50..200"
        
        return createLinkElement(url)
    }
    
    /**
     * Generates an HTML link tag for loading Lexend variable font.
     * @return HTML link element that can be added to the head.
     */
    fun getLexendLink(): HTMLLinkElement {
        return createLinkElement(
            "https://fonts.googleapis.com/css2?family=Lexend:wght@100..700&display=swap"
        )
    }
    
    /**
     * Generates an HTML link tag for loading Roboto Flex variable font.
     * @return HTML link element that can be added to the head.
     */
    fun getRobotoFlexLink(): HTMLLinkElement {
        return createLinkElement(
            "https://fonts.googleapis.com/css2?family=Roboto+Flex:wght@100..900&display=swap"
        )
    }
    
    /**
     * Adds all required font link tags to the document head.
     * This should be called early in the application initialization.
     */
    fun addFontLinksToHead() {
        document.head?.apply {
            appendChild(getMaterialSymbolsLink())
            appendChild(getLexendLink())
            appendChild(getRobotoFlexLink())
        }
    }
    
    /**
     * Helper method to create a link element with the given URL.
     * @param url The URL for the link element.
     * @return HTML link element.
     */
    private fun createLinkElement(url: String): HTMLLinkElement {
        val link = document.createElement("link") as HTMLLinkElement
        link.rel = "stylesheet"
        link.href = url
        return link
    }
}
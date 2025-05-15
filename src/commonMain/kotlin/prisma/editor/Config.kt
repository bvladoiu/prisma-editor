package prisma.editor

/**
 * Configuration object for the Prisma Editor.
 * This object is accessible from both JVM and JS code.
 * It contains the sites being worked on by content editors and their languages.
 */
object Config {
    /**
     * Data class representing a site with its codename and display name
     */
    data class Site(val codename: String, val displayName: String)

    /**
     * Available sites in the editor
     */
    val SITES = mapOf(
        "contadeal" to Site("contadeal", "ContaDeal"),
        "prisma" to Site("prisma", "PRISMA-Software"),
        "common" to Site("common", "Common")
    )

    /**
     * Available languages for each site
     */
    val SITE_LANGUAGES = mapOf(
        "contadeal" to listOf("en", "ro"),
        "prisma" to listOf("en", "de"),
        "common" to listOf("en")
    )

    /**
     * Current site being edited
     */
    var currentSite: String = "prisma"

    /**
     * Current language being edited
     */
    var currentLanguage: String = "en"

    /**
     * Current page tag being edited
     */
    var currentPageTag: String = "catalog-page"

    /**
     * Updates the current site and ensures the language is valid for that site
     */
    fun updateSite(site: String) {
        currentSite = site

        // Ensure the current language is valid for the new site
        SITE_LANGUAGES[site]?.contains(currentLanguage)?.let {
            if (!it) {
                // Default to the first language for the site
                currentLanguage = SITE_LANGUAGES[site]?.firstOrNull() ?: "en"
            }
        }
    }

    /**
     * Updates the current language
     */
    fun updateLanguage(language: String) {
        // Only update if the language is valid for the current site
        if (SITE_LANGUAGES[currentSite]?.contains(language) == true) {
            currentLanguage = language
        }
    }

    /**
     * Updates the current page tag
     */
    fun updatePageTag(pageTag: String) {
        currentPageTag = pageTag
    }

    /**
     * Creates a map of the current configuration
     */
    fun toMap(): Map<String, Any> {
        return mapOf(
            "site" to currentSite,
            "language" to currentLanguage,
            "pageTag" to currentPageTag
        )
    }

    /**
     * Updates the configuration from a map
     */
    fun fromMap(map: Map<String, Any>) {
        map["site"]?.toString()?.let { updateSite(it) }
        map["language"]?.toString()?.let { updateLanguage(it) }
        map["pageTag"]?.toString()?.let { updatePageTag(it) }
    }
}

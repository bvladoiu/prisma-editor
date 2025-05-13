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
        "prisma" to Site("prisma", "PRISMA-Software")
    )

    /**
     * Available languages for each site
     */
    val SITE_LANGUAGES = mapOf(
        "contadeal" to listOf("en", "ro"),
        "prisma" to listOf("en", "de")
    )
}

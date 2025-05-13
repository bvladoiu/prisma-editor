package prisma.editor

/**
 * Configuration object for the Prisma Editor.
 * This object is accessible from both JVM and JS code.
 */
object Config {
    /**
     * The default language for the editor.
     */
    const val DEFAULT_LANGUAGE = "en"
    
    /**
     * Available languages for the editor.
     */
    val AVAILABLE_LANGUAGES = listOf("en", "de")
    
    /**
     * Default theme for the editor.
     */
    const val DEFAULT_THEME = "light"
    
    /**
     * Available themes for the editor.
     */
    val AVAILABLE_THEMES = listOf("light", "dark")
    
    /**
     * Default font size for the editor.
     */
    const val DEFAULT_FONT_SIZE = 16
    
    /**
     * Minimum font size for the editor.
     */
    const val MIN_FONT_SIZE = 12
    
    /**
     * Maximum font size for the editor.
     */
    const val MAX_FONT_SIZE = 24
    
    /**
     * Font size step for the editor.
     */
    const val FONT_SIZE_STEP = 2
    
    /**
     * Default settings map for the editor.
     */
    val defaultSettings = mapOf(
        "language" to DEFAULT_LANGUAGE,
        "theme" to DEFAULT_THEME,
        "fontSize" to DEFAULT_FONT_SIZE.toString()
    )
}
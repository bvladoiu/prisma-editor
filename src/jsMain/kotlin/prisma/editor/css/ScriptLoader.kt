package prisma.editor.css

import kotlinx.browser.document
import org.w3c.dom.HTMLScriptElement

/**
 * ScriptLoader manages script loading for the application.
 * It provides methods for generating HTML script tags
 * and loading scripts programmatically.
 */
object ScriptLoader {
    // Set to track scripts that have been loaded
    private val loadedScripts = mutableSetOf<String>()
    
    /**
     * Loads the client.js script.
     * @return HTML script element that was added to the document.
     */
    fun loadClientScript(): HTMLScriptElement {
        return loadScript("js/client.js")
    }
    
    /**
     * Loads a script with the given URL.
     * @param url The URL of the script to load.
     * @return HTML script element that was added to the document.
     */
    fun loadScript(url: String): HTMLScriptElement {
        // If the script is already loaded, don't load it again
        if (url in loadedScripts) {
            console.warn("Script $url is already loaded")
            // Return an empty script element
            return document.createElement("script") as HTMLScriptElement
        }
        
        val script = createScriptElement(url)
        document.body?.appendChild(script)
        loadedScripts.add(url)
        return script
    }
    
    /**
     * Adds all required scripts to the document.
     * This should be called after the document body is ready.
     */
    fun addScriptsToBody() {
        loadClientScript()
    }
    
    /**
     * Helper method to create a script element with the given URL.
     * @param url The URL for the script element.
     * @return HTML script element.
     */
    private fun createScriptElement(url: String): HTMLScriptElement {
        val script = document.createElement("script") as HTMLScriptElement
        script.src = url
        script.type = "text/javascript"
        return script
    }
}
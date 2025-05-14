package prisma.editor.desktop

import prisma.editor.main

/**
 * Desktop application entry point that delegates to the main project's run function.
 * This class serves as the entry point for the desktop application and native image.
 */
object DesktopMain {
    @JvmStatic
    fun main(args: Array<String>) {
        // Delegate to the main project's run function
        main()
    }
}
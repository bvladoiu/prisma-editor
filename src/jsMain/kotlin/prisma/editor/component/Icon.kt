package prisma.editor.component

import kotlinx.browser.document
import kotlinx.html.FlowContent
import kotlinx.html.dom.create
import kotlinx.html.span
import org.w3c.dom.HTMLElement
import prisma.editor.css.FontsLoader
import prisma.editor.css.Typography.ICON

/**
 * Icon component that renders a Material Symbols icon.
 * @param name The name of the Material Symbols icon to display.
 */
class Icon(
    var name: String,
) {
    init {
        FontsLoader.registerIcon(name)
    }

    fun preview(): HTMLElement {
        return document.create.span {
            attributes[ICON] = ""
            asDynamic().kotlinInstance = this@Icon
            +name
        }
    }

    /**
     * Renders the icon component as FlowContent that can be embedded in other components.
     * @return FlowContent that can be embedded in other components
     */
    fun render(): FlowContent.() -> Unit = {
        span {
            attributes[ICON] = ""
            +name
        }
    }
}

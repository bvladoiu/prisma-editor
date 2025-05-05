package prisma.editor

import kotlinx.browser.document
import org.w3c.dom.Element
import org.w3c.dom.events.Event

/**
 * DSL for DOM and CSS manipulation
 */

class CssBuilder {
    private val properties = mutableMapOf<String, String>()

    fun property(name: String, value: String) {
        properties[name] = value
    }

    // Common CSS properties
    fun outline(value: String) = property("outline", value)
    fun userSelect(value: String) = property("user-select", value)
    fun pointerEvents(value: String) = property("pointer-events", value)
    fun minHeight(value: String) = property("min-height", value)
    fun cursor(value: String) = property("cursor", value)
    fun position(value: String) = property("position", value)
    fun top(value: String) = property("top", value)
    fun right(value: String) = property("right", value)
    fun display(value: String) = property("display", value)
    fun gap(value: String) = property("gap", value)
    fun zIndex(value: String) = property("z-index", value)
    fun backgroundColor(value: String) = property("background-color", value)
    fun color(value: String) = property("color", value)
    fun width(value: String) = property("width", value)
    fun height(value: String) = property("height", value)
    fun borderRadius(value: String) = property("border-radius", value)
    fun alignItems(value: String) = property("align-items", value)
    fun justifyContent(value: String) = property("justify-content", value)
    fun fontWeight(value: String) = property("font-weight", value)
    fun fontSize(value: String) = property("font-size", value)

    override fun toString(): String {
        return properties.entries.joinToString("; ") { (name, value) -> "$name: $value" }
    }
}

// Extension function to apply styles to an Element
fun Element.style(init: CssBuilder.() -> Unit) {
    val css = CssBuilder().apply(init)
    this.setAttribute("style", css.toString())
}

// DOM DSL
class ElementBuilder(private val element: Element) {
    fun attribute(name: String, value: String) {
        element.setAttribute(name, value)
    }

    fun style(init: CssBuilder.() -> Unit) {
        element.style(init)
    }

    fun text(value: String) {
        element.textContent = value
    }

    fun onClick(handler: (Event) -> Unit) {
        element.addEventListener("click", handler)
    }

    fun append(child: Element) {
        element.appendChild(child)
    }
}

// Function to create an element with DSL
fun element(tagName: String, init: ElementBuilder.() -> Unit): Element {
    val element = document.createElement(tagName)
    ElementBuilder(element).init()
    return element
}

// Predefined CSS styles
fun CssBuilder.editableElement() {
    outline("1px dashed red")
    userSelect("text !important")
    pointerEvents("auto !important")
    minHeight("1em")
    cursor("text")
    position("relative")
}

fun CssBuilder.iconContainer() {
    position("absolute")
    top("0")
    right("0")
    display("flex")
    gap("5px")
    zIndex("9999")
    pointerEvents("auto")
}

fun CssBuilder.controlIcon(backgroundColor: String = "blue") {
    this.backgroundColor(backgroundColor)
    color("white")
    width("16px")
    height("16px")
    borderRadius("50%")
    display("flex")
    alignItems("center")
    justifyContent("center")
    cursor("pointer")
    fontWeight("bold")
    fontSize("14px")
    userSelect("none")
}

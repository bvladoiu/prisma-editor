package prisma.editor


import kotlinx.browser.document
import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLElement
import org.w3c.dom.events.Event


abstract class UiComponent {
    var text: String = ""
    var onClick: ((Event) -> Unit)? = null

    class StyleBuilder {
        var color: String = ""
        var backgroundColor: String = ""
        var padding: String = ""
        var margin: String = ""
        var textAlign: String = ""
        var fontSize: String = ""
        var borderTop: String = ""
        var width: String = ""
        var height: String = ""

        fun applyTo(element: HTMLElement) {
            if (color.isNotEmpty()) element.style.color = color
            if (backgroundColor.isNotEmpty()) element.style.backgroundColor = backgroundColor
            if (padding.isNotEmpty()) element.style.padding = padding
            if (margin.isNotEmpty()) element.style.margin = margin
            if (textAlign.isNotEmpty()) element.style.textAlign = textAlign
            if (fontSize.isNotEmpty()) element.style.fontSize = fontSize
            if (borderTop.isNotEmpty()) element.style.borderTop = borderTop
            if (width.isNotEmpty()) element.style.width = width
            if (height.isNotEmpty()) element.style.height = height
        }
    }

    private val styleBuilder = StyleBuilder()

    fun style(init: StyleBuilder.() -> Unit) {
        styleBuilder.init()
    }

    abstract fun createElement(): HTMLElement

    protected fun applyCommonProperties(element: HTMLElement) {
        if (text.isNotEmpty()) {
            element.textContent = text
        }

        onClick?.let { handler ->
            element.onclick = handler
        }

        styleBuilder.applyTo(element)
    }
}

class PageComponent : UiComponent() {
    private val children = mutableListOf<UiComponent>()

    fun header(init: HeaderComponent.() -> Unit) {
        val header = HeaderComponent()
        header.init()
        children.add(header)
    }

    fun section(init: SectionComponent.() -> Unit) {
        val section = SectionComponent()
        section.init()
        children.add(section)
    }

    fun footer(init: FooterComponent.() -> Unit) {
        val footer = FooterComponent()
        footer.init()
        children.add(footer)
    }

    override fun createElement(): HTMLElement {
        val element = document.createElement("div") as HTMLDivElement
        applyCommonProperties(element)

        children.forEach { child ->
            element.appendChild(child.createElement())
        }

        return element
    }
}

class HeaderComponent : UiComponent() {
    override fun createElement(): HTMLElement {
        val element = document.createElement("header") as HTMLElement
        applyCommonProperties(element)
        return element
    }
}

class SectionComponent : UiComponent() {
    private val children = mutableListOf<UiComponent>()

    fun button(init: ButtonComponent.() -> Unit) {
        val button = ButtonComponent()
        button.init()
        children.add(button)
    }

    override fun createElement(): HTMLElement {
        val element = document.createElement("section") as HTMLElement
        applyCommonProperties(element)

        children.forEach { child ->
            element.appendChild(child.createElement())
        }

        return element
    }
}

class ButtonComponent : UiComponent() {
    override fun createElement(): HTMLElement {
        val element = document.createElement("button") as HTMLButtonElement
        applyCommonProperties(element)
        return element
    }
}

class FooterComponent : UiComponent() {
    override fun createElement(): HTMLElement {
        val element = document.createElement("footer") as HTMLElement
        applyCommonProperties(element)
        return element
    }
}

fun page(init: PageComponent.() -> Unit): HTMLElement {
    val page = PageComponent()
    page.init()
    return page.createElement()
}

fun header(init: HeaderComponent.() -> Unit): HTMLElement {
    val header = HeaderComponent()
    header.init()
    return header.createElement()
}

fun section(init: SectionComponent.() -> Unit): HTMLElement {
    val section = SectionComponent()
    section.init()
    return section.createElement()
}

fun button(init: ButtonComponent.() -> Unit): HTMLElement {
    val button = ButtonComponent()
    button.init()
    return button.createElement()
}

fun footer(init: FooterComponent.() -> Unit): HTMLElement {
    val footer = FooterComponent()
    footer.init()
    return footer.createElement()
}
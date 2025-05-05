package prisma.editor

import androidx.compose.runtime.Composable
import kotlinx.browser.document
import kotlinx.browser.window
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.renderComposable
import org.w3c.dom.Element
import org.w3c.dom.HTMLAnchorElement
import org.w3c.dom.HTMLStyleElement
import org.w3c.dom.asList
import org.w3c.dom.events.Event
import org.w3c.dom.events.KeyboardEvent
import org.w3c.dom.events.MouseEvent
import org.w3c.files.Blob
import org.w3c.files.BlobPropertyBag
import org.w3c.dom.url.URL

/*fun main() {
    renderComposeHtml()
    setupShirtcuts()
}*/

private fun renderComposeHtml() {
    renderComposable(rootElementId = "root") {
        HelloComposeHtml()
    }
}

@Composable
private fun HelloComposeHtml() {
    Div {
        Text("Hello from Compose Html")
    }
}

private fun setupShirtcuts() {
    document.addEventListener("keydown", { event ->
        val e = event as KeyboardEvent
        if (e.ctrlKey && e.shiftKey) {
            when (e.key.lowercase()) {
                "e" -> {
                    e.preventDefault()
                    makeTextElementsEditable()
                }

                "p" -> {
                    e.preventDefault()
                    disableEditableMode()
                }

                "s" -> {
                    e.preventDefault()
                    serializeAndDownload()
                }
            }
        }
    })

    document.addEventListener("click", { event ->
        val e = event as MouseEvent
        if (e.ctrlKey) {
            e.preventDefault()
            val target = e.target
            if (target is Element) {
                logStyles(target, "Initial (on Ctrl+Click)")
                var onAnimationOrTransitionEnd: ((Event) -> Unit)? = null
                onAnimationOrTransitionEnd = { event: Event ->
                    if (event.target == target) {
                        logStyles(target, "Final (after hover animation end)")
                        target.removeEventListener("transitionend", onAnimationOrTransitionEnd!!)
                        target.removeEventListener("animationend", onAnimationOrTransitionEnd)
                    }
                }
                var onMouseLeave: ((Event) -> Unit)? = null
                onMouseLeave = { event: Event ->
                    if (event.target == target) {
                        println("Mouse left element <${target.tagName.lowercase()} id='${target.id}'>. Waiting for animation end.")
                        target.addEventListener("transitionend", onAnimationOrTransitionEnd)
                        target.addEventListener("animationend", onAnimationOrTransitionEnd)
                        target.removeEventListener("mouseleave", onMouseLeave!!)
                    }
                }
                target.addEventListener("mouseleave", onMouseLeave)
            }
        }
    })
}

fun makeTextElementsEditable() {
    val elements = document.querySelectorAll("*").asList()

    elements.forEach { node ->
        if (node is Element && node.textContent?.trim()?.isNotEmpty() == true) {
            node.setAttribute("contenteditable", "true")
            node.style {
                editableElement()
            }
            addControlIcons(node)
        }
    }

    console.log("[Editable Mode] Applied contenteditable=true to all elements with visible text")
}

/**
 * Adds + and - control icons to the top right corner of the given element.
 * The - icon deletes the node, and the + icon duplicates it.
 */
private fun addControlIcons(element: Element) {
    // Check if the element already has control icons
    val existingContainer = element.querySelector("div[data-control-icons='true']")
    if (existingContainer != null) {
        // Remove existing control icons to avoid duplicates
        element.removeChild(existingContainer)
    }
    val iconContainer = element("div") {
        attribute("data-control-icons", "true")
        attribute("contenteditable", "false") // Make sure the icons are not editable
        style {
            iconContainer()
        }
    }
    val deleteIcon = element("div") {
        text("-")
        attribute("contenteditable", "false")
        style {
            controlIcon()
        }
        onClick { event ->
            event.stopPropagation()
            element.parentNode?.removeChild(element)
        }
    }
    val duplicateIcon = element("div") {
        text("+")
        attribute("contenteditable", "false")
        style {
            controlIcon()
        }
        onClick { event ->
            event.stopPropagation()
            val clone = element.cloneNode(true) as Element
            element.parentNode?.insertBefore(clone, element.nextSibling)
            addControlIcons(clone)
        }
    }

    iconContainer.appendChild(deleteIcon)
    iconContainer.appendChild(duplicateIcon)
    element.appendChild(iconContainer)
}

fun disableEditableMode() {
    val elements = document.querySelectorAll("[contenteditable='true']").asList()
    elements.forEach { node ->
        if (node is Element) {
            node.removeAttribute("contenteditable")
            node.removeAttribute("style")
            val controlIcons = node.querySelector("div[data-control-icons='true']")
            if (controlIcons != null) {
                node.removeChild(controlIcons)
            }
        }
    }

    console.log("[Preview Mode] Disabled editable mode for all elements")
}

fun serializeAndDownload() {
    disableEditableMode()
    val htmlContent = document.documentElement?.outerHTML ?: ""
    val cssContent = extractCSS()
    downloadFile(htmlContent, "page.html", "text/html")
    downloadFile(cssContent, "styles.css", "text/css")
    console.log("[Serialize] Downloaded HTML and CSS files")
}

fun extractCSS(): String {
    val cssContent = StringBuilder()
    val styleElements = document.querySelectorAll("style").asList()
    styleElements.forEach { element ->
        if (element is HTMLStyleElement) {
            cssContent.append(element.textContent)
            cssContent.append("\n\n")
        }
    }
    val elementsWithStyle = document.querySelectorAll("[style]").asList()
    elementsWithStyle.forEach { element ->
        if (element is Element) {
            val style = element.getAttribute("style")
            if (!style.isNullOrEmpty()) {
                val selector = generateSelector(element)
                cssContent.append("$selector {\n")
                cssContent.append("    $style\n")
                cssContent.append("}\n\n")
            }
        }
    }
    return cssContent.toString()
}

fun generateSelector(element: Element): String {
    val tagName = element.tagName.lowercase()
    val id = element.id
    val className = element.className

    return buildString {
        append(tagName)
        if (id.isNotEmpty()) {
            append("#$id")
        }
        if (className.isNotEmpty()) {
            val firstClass = className.split(" ").firstOrNull()
            if (!firstClass.isNullOrEmpty()) {
                append(".$firstClass")
            }
        }
    }
}

fun downloadFile(content: String, filename: String, mimeType: String) {
    val blob = Blob(arrayOf(content), BlobPropertyBag(type = mimeType))
    val url = URL.createObjectURL(blob)
    val link = document.createElement("a") as HTMLAnchorElement
    link.href = url
    link.download = filename
    document.body?.appendChild(link)
    link.click()
    document.body?.removeChild(link)
    URL.revokeObjectURL(url)
}

fun logStyles(element: Element, state: String) {
    val computed = window.getComputedStyle(element)
    val appliedStyles = mutableListOf<String>()

    for (i in 0 until computed.length) {
        val prop = computed.item(i)
        val value = computed.getPropertyValue(prop).trim()
        if (value.isNotEmpty()) {
            appliedStyles += "$prop: $value;"
        }
    }

    val elementIdentifier = buildString {
        append("<${element.tagName.lowercase()}>")
        if (element.id.isNotEmpty()) {
            append("#${element.id}")
        }
    }
    console.log("[$state computed styles for $elementIdentifier]:\n" + appliedStyles.joinToString("\n"))
}

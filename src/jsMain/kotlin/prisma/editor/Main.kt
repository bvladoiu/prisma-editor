package prisma.editor

import kotlinx.browser.document
import kotlinx.browser.window
import org.w3c.dom.HTMLDivElement

fun main() {
    window.onload = {
        val app = App()
        app.render()
    }
}

class App {
    fun render() {
        document.body?.let { body ->
            val container = document.createElement("div") as HTMLDivElement
            container.id = "app-container"

            container.append(
                page {
                    header {
                        text = "KotlinJS App"
                        style {
                            color = "white"
                            backgroundColor = "#336699"
                            padding = "20px"
                            textAlign = "center"
                            fontSize = "24px"
                        }
                    }

                    section {
                        text = "Welcome to the KotlinJS application!"
                        style {
                            padding = "20px"
                            textAlign = "center"
                        }

                        button {
                            text = "Click me!"
                            onClick = {
                                window.alert("Button clicked!")
                            }
                        }
                    }

                    footer {
                        text = "Created with Kotlin JS, CSS, and Browser wrappers"
                        style {
                            padding = "10px"
                            textAlign = "center"
                            fontSize = "12px"
                            color = "#666"
                            borderTop = "1px solid #eee"
                        }
                    }
                }
            )

            body.appendChild(container)
        }
    }
}
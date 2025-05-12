// :browser:src:jvmMain:JvmMain.kt
package prisma.editor

import com.microsoft.playwright.*
import java.io.File
import kotlin.concurrent.thread

fun main() {
    val playwright = Playwright.create()
    val browser = playwright.chromium().launch(
        BrowserType.LaunchOptions().setHeadless(false)
            .setArgs(listOf("--kiosk", "--disable-web-security", "--disable-features=IsolateOrigins,site-per-process"))
    )
    val context = browser.newContext()
    val page = context.newPage()

    setupCli(page)

    val devServerUrl = "http://localhost:8080"
    page.navigate(devServerUrl)

    Runtime.getRuntime().addShutdownHook(thread(start = false) {
        // TODO save everything in the Editor js context
    })
}


fun setupCli(page: Page) {
    page.onConsoleMessage { message: ConsoleMessage ->
        val fullCommand = message.text()
        val parts = fullCommand.split(":", limit = 2)
        val command = parts.getOrNull(0) // The command part (e.g., "save", "load")
        val tag = parts.getOrNull(1) // The tag part (e.g., "hero")

        when (command) {
            "save" -> {
                if (tag != null) {
                    val jsonData = message.args()[0].jsonValue().toString()
                    File("$tag.json").writeText(jsonData)
                }
            }

            "load" -> {
                if (tag != null) {
                    val jsonString = File("$tag.json").readText()
                    val dataToPass = mapOf(
                        "tag" to tag,
                        "jsonString" to jsonString
                    )
                    page.evaluate("(data) => receiveData(data.tag, data.jsonString)", dataToPass)
                }
            }

            else -> trace(message)
        }
    }
}

fun trace(message: ConsoleMessage) {
    val type = message.type().uppercase()
    val text = try {
        if (message.args().isNotEmpty()) {
            message.args().joinToString(" ") { arg ->
                try {
                    arg.jsonValue()?.toString() ?: "[JSHandle]"
                } catch (e: Exception) {
                    "[Error converting arg: ${e.message}]"
                }
            }
        } else {
            message.text()
        }
    } catch (e: Exception) {
        "[Error getting message text: ${e.message}]"
    }
    val location = message.location()
    println("[Browser $type @ $location]: $text")
}

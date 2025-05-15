package prisma.editor

import com.microsoft.playwright.*
import java.nio.file.Paths
import kotlin.concurrent.thread

fun main() {
    val playwright = Playwright.create()
    val browser = playwright.chromium().launch(
        BrowserType.LaunchOptions().setHeadless(false)
            .setArgs(listOf("--kiosk", "--disable-web-security", "--disable-features=IsolateOrigins,site-per-process"))
    )
    val context = browser.newContext()
    val page = context.newPage()

    EditorJvm.setupCli(page)

    page.addInitScript(Paths.get("src\\jsMain\\resources\\js\\prisma.js"))

    val indexHtmlPath = Paths.get("src\\jvmMain\\resources\\index.html").toAbsolutePath().toString()
    val fileUrl = "file:///" + indexHtmlPath.replace("\\", "/")

    page.navigate(fileUrl)

    Runtime.getRuntime().addShutdownHook(thread(start = false) {
    })
}

package prisma.editor.client

fun main() {
    console.log("Client module loaded successfully!")
}

// Simple function that can be called from the main project
@JsExport
fun greetFromClient(name: String): String {
    return "Hello, $name from the client module!"
}
package prisma.editor

import prisma.editor.Config

data class Link(
    val url: String,
    val names: Map<String, String>
) {
    val localizedName: String
        get() = names[Config.currentLanguage] ?: url // Fallback to url if localized name is not found

    companion object {
        const val TAG = "link"
    }
}
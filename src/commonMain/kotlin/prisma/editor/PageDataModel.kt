package prisma.editor

/**
 * Data class representing the metadata for a page, including its slug and localized names.
 * This model is used for managing pages independent of their content components.
 */
data class PageDataModel(
    val slug: String,
    val names: Map<String, String>
) {
    companion object {
        const val TAG = "pageDataModel"
    }
}
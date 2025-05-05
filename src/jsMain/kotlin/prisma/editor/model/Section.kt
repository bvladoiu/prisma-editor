package prisma.editor.model

data class Section(
    val title: String?,
    val isDivider: Boolean,
    val items: List<SectionItem>? = null,
    val keywordStrip: List<String>? = null,
    val updates: List<Update>? = null,
    val buttonText: String? = null
)
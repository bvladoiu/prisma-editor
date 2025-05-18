package prisma.editor.pages

import prisma.editor.component.SectionsInitialData
import kotlin.js.json

/**
 * Helper class for creating specific pages to be removed after pages are persisted by the jvm code
 */
object PagesInitialData {
    /**
     * Creates a home page with sample content.
     * @return The created home page instance.
     */
    fun createEnHomePage(): Page {
        val page = Page.create(Page.HOME_TAG, "Home")

        // Create sample component data for home page
        val components = json()

        // Hero section
        components["hero"] = json(
            "type" to "Hero",
            "name" to "Embedded Software Solutions — Secure for Every Industry",
            "description" to "Over 20 years of embedded expertise in mission-critical systems, from in-vehicle platforms to robust, cloud-connected applications. We focus on quality, compliance, and a user-first experience to ensure your products excel in competitive markets.",
            "buttonText" to "Get in Touch"
        )

        // Core Expertise section
        components["coreExpertise"] = json(
            "type" to "Section",
            "title" to "Our Core Expertise",
            "isDivider" to true,
            "sectionId" to "coreExpertise"
        )

        // Keyword Strip section
        components["keywordStrip"] = json(
            "type" to "Section",
            "title" to null,
            "isDivider" to false,
            "sectionId" to "keywordStrip"
        )

        // Why Work With Us section
        components["whyWorkWithUs"] = json(
            "type" to "Section",
            "title" to "Why Work With Us?",
            "isDivider" to true,
            "sectionId" to "whyWorkWithUs"
        )

        // Latest Updates section
        components["latestUpdates"] = json(
            "type" to "Section",
            "title" to "Latest Updates",
            "isDivider" to true,
            "sectionId" to "latestUpdates"
        )

        page.componentData = components
        return page
    }

    /**
     * Creates a home page with sample content in German.
     * @return The created home page instance.
     */
    fun createDeHomePage(): Page {
        val page = Page.create(Page.HOME_TAG, "Startseite")

        // Create sample component data for home page in German
        val components = json()

        // Hero section
        components["hero"] = json(
            "type" to "Hero",
            "name" to "Embedded Software Lösungen – Sicher für jede Branche", // Translated
            "description" to "Über 20 Jahre Embedded-Expertise in unternehmenskritischen Systemen, von Fahrzeugplattformen bis hin zu robusten Cloud-verbundenen Anwendungen. Wir konzentrieren uns auf Qualität, Compliance und eine benutzerorientierte Erfahrung, um sicherzustellen, dass Ihre Produkte auf wettbewerbsintensiven Märkten herausragen.", // Translated
            "buttonText" to "Kontaktieren Sie uns" // Translated
        )

        // Core Expertise section
        components["coreExpertise"] = json(
            "type" to "Section",
            "title" to "Unsere Kernkompetenzen", // Translated
            "isDivider" to true,
            "sectionId" to "coreExpertise"
        )

        // Keyword Strip section
        components["keywordStrip"] = json(
            "type" to "Section",
            "title" to null,
            "isDivider" to false,
            "sectionId" to "keywordStrip"
        )

        // Why Work With Us section
        components["whyWorkWithUs"] = json(
            "type" to "Section",
            "title" to "Warum mit uns arbeiten?", // Translated
            "isDivider" to true,
            "sectionId" to "whyWorkWithUs"
        )

        // Latest Updates section
        components["latestUpdates"] = json(
            "type" to "Section",
            "title" to "Neueste Updates", // Translated
            "isDivider" to true,
            "sectionId" to "latestUpdates"
        )

        page.componentData = components
        return page
    }

    /**
     * Creates a catalog page with sample content in English.
     * @return The created catalog page instance.
     */
    fun createEnCatalogPage(): Page {
        val page = Page.create(Page.CATALOG_TAG, "Catalog")

        // Create sample component data for catalog page in English
        val components = json()

        // Header section
        components["header"] = json(
            "type" to "Section",
            "title" to "Component Catalog",
            "isDivider" to false
        )

        // Add more components as needed

        page.componentData = components

        return page
    }

    /**
     * Creates a catalog page with sample content in German.
     * @return The created catalog page instance.
     */
    fun createDeCatalogPage(): Page {
        val page = Page.create(Page.CATALOG_TAG, "Katalog")

        // Create sample component data for catalog page in German
        val components = json()

        // Header section
        components["header"] = json(
            "type" to "Section",
            "title" to "Komponentenkatalog", // Already translated
            "isDivider" to false
        )

        // Add more components as needed

        page.componentData = components
        return page
    }
}

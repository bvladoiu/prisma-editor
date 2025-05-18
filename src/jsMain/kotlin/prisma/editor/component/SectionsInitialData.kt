package prisma.editor.component

import kotlinx.browser.document
import kotlinx.html.*
import kotlinx.html.dom.create
import org.w3c.dom.HTMLElement
import prisma.editor.css.Typography
import kotlin.js.json

/**
 * Helper class for creating specific sections to be used in pages
 * Based on content from resources/samples/home6.html
 */
object SectionsInitialData {
    /**
     * Creates a Core Expertise section with sample content.
     * @return The created section instance.
     */
    fun createCoreExpertiseSection(): Section {
        val section = Section(
            initialTitle = "Our Core Expertise",
            initialIsDivider = true
        )

        // Create Expertise components for grid items
        val automotiveExpertise = Expertise(
            initialName = "Automotive",
            initialDescription = "ISO 26262, ASPICE, AUTOSAR — We excel at functional safety, powertrain control, battery management, and ADAS. From OTA updates to secure diagnostics, our solutions combine precision and compliance for tomorrow's vehicles."
        )

        val medtechExpertise = Expertise(
            initialName = "MedTech & Industry 4.0",
            initialDescription = "With select experience in IEC 62304 and smart manufacturing, our team embeds connectivity (BLE, Wi-Fi, and industrial protocols) to drive secure, data-driven solutions. We help you meet strict compliance without sacrificing innovation."
        )

        val cloudExpertise = Expertise(
            initialName = "Cloud & Application Engineering",
            initialDescription = "We build multi-platform apps with Kotlin, Flutter, or React — offline-first, secure, and integrated with your backend. Leveraging CI/CD pipelines (Jenkins, GitLab, GitHub Actions), we ensure smooth builds, rapid deployment, and scalable cloud architectures."
        )

        // Add expertise components as grid items
        section.addGridItems(listOf(
            automotiveExpertise.buildHtml(),
            medtechExpertise.buildHtml(),
            cloudExpertise.buildHtml()
        ))

        return section
    }

    /**
     * Creates a Keyword Strip section with sample content.
     * @return The created section instance.
     */
    fun createKeywordStripSection(): Section {
        val section = Section(
            initialTitle = null,
            initialIsDivider = false
        )

        // Create keyword strip content
        val keywordContent = document.create.p {
            attributes[Typography.BODY] = ""
            style = "text-align: center; margin-top: 1rem; line-height: 1.8;"
            +"ISO 26262 • ASPICE • AUTOSAR • Model-Based Development • MATLAB/Simulink • TargetLink • IBM Rhapsody • Agile/V-Cycle • Kotlin • Flutter • React • Jenkins • GitLab • GitHub Actions • C/C++ • BLE, Wi-Fi, NFC • IEC 62304 • OTA Updates • Cryptography • Asynchronous Programming • Performance Optimization"
        }

        // Add keyword content
        section.addContent(keywordContent)

        return section
    }

    /**
     * Creates a Why Work With Us section with sample content.
     * @return The created section instance.
     */
    fun createWhyWorkWithUsSection(): Section {
        val section = Section(
            initialTitle = "Why Work With Us?",
            initialIsDivider = true
        )

        // Create Pitch components for grid items
        val expertisePitch = Pitch(
            "Proven Expertise",
            "20+ years delivering in complex compliance environments, with a deep record in automotive embedded."
        )

        val improvementPitch = Pitch(
            "Continuous Improvement",
            "Agile methodologies, TDD, code reviews, and tight feedback loops."
        )

        val perspectivePitch = Pitch(
            "End-to-End Perspective",
            "From firmware to mobile apps to cloud backends—our holistic approach keeps everything in sync."
        )

        val collaborationPitch = Pitch(
            "Scalable Collaboration",
            "Whether a feasibility study or multi-year engagement, we match your roadmap at every step."
        )

        // Add pitch components as grid items
        section.addGridItems(listOf(
            expertisePitch.preview(),
            improvementPitch.preview(),
            perspectivePitch.preview(),
            collaborationPitch.preview()
        ))

        // Add call-to-action button
        val ctaButton = document.create.button {
            attributes["class"] = "ui primary button"
            style = "margin-top: 2rem;"
            +"Let's Build Something Great"
        }
        section.addContent(ctaButton)

        return section
    }

    /**
     * Creates a Latest Updates section with sample content.
     * @return The created section instance.
     */
    fun createLatestUpdatesSection(): Section {
        val section = Section(
            initialTitle = "Latest Updates",
            initialIsDivider = true
        )

        // Create latest updates content
        val updatesList = document.create.div {
            attributes["class"] = "ui relaxed divided list"

            div {
                attributes["class"] = "item"
                div {
                    attributes["class"] = "content"
                    a {
                        attributes["class"] = "header"
                        attributes["href"] = "#"
                        +"Expanding Beyond Automotive"
                    }
                    div {
                        attributes["class"] = "description"
                        +"by David • Apr 3, 2024"
                    }
                }
            }

            div {
                attributes["class"] = "item"
                div {
                    attributes["class"] = "content"
                    a {
                        attributes["class"] = "header"
                        attributes["href"] = "#"
                        +"How Can Marketing Help Your Business?"
                    }
                    div {
                        attributes["class"] = "description"
                        +"by Allen • Apr 3, 2024"
                    }
                }
            }
        }

        // Add updates list content
        section.addContent(updatesList)

        return section
    }
}

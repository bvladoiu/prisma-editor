package prisma.editor.styles

import kotlinx.browser.document
import org.w3c.dom.HTMLElement
import org.w3c.dom.css.CSSStyleSheet

/**
 * Main stylesheet that aggregates all styles from the styles package.
 * This is used to provide a single stylesheet for the application.
 */
object Main {
    /**
     * Initializes the main stylesheet by aggregating all styles from the styles package.
     * This should be called once when the application starts.
     */
    fun initialize() {
        // Create a new stylesheet
        val styleSheet = document.createElement("style")
        document.head?.appendChild(styleSheet)
        
        // Add all styles to the stylesheet
        val cssRules = mutableListOf<String>()
        
        // Add styles from ButtonStyles
        cssRules.add(".button-primary { ${getCssFromStyle { ButtonStyles.applyPrimaryStyle(it) }} }")
        
        // Add styles from ExpertiseItemStyles
        cssRules.add(".expertise-item-container { ${getCssFromStyle { ExpertiseItemStyles.applyContainerStyle(it) }} }")
        cssRules.add(".expertise-item-title { ${getCssFromStyle { ExpertiseItemStyles.applyTitleStyle(it) }} }")
        cssRules.add(".expertise-item-description { ${getCssFromStyle { ExpertiseItemStyles.applyDescriptionStyle(it) }} }")
        
        // Add styles from FooterStyles
        cssRules.add(".footer-container { ${getCssFromStyle { FooterStyles.applyContainerStyle(it) }} }")
        cssRules.add(".footer-content { ${getCssFromStyle { FooterStyles.applyContentStyle(it) }} }")
        cssRules.add(".footer-link { ${getCssFromStyle { FooterStyles.applyLinkStyle(it) }} }")
        
        // Add styles from HeroStyles
        cssRules.add(".hero-container { ${getCssFromStyle { HeroStyles.applyContainerStyle(it) }} }")
        cssRules.add(".hero-title { ${getCssFromStyle { HeroStyles.applyTitleStyle(it) }} }")
        cssRules.add(".hero-description { ${getCssFromStyle { HeroStyles.applyDescriptionStyle(it) }} }")
        cssRules.add(".hero-button { ${getCssFromStyle { HeroStyles.applyButtonStyle(it) }} }")
        
        // Add styles from KeywordStripStyles
        cssRules.add(".keyword-strip-container { ${getCssFromStyle { KeywordStripStyles.applyContainerStyle(it) }} }")
        cssRules.add(".keyword-strip-paragraph { ${getCssFromStyle { KeywordStripStyles.applyParagraphStyle(it) }} }")
        cssRules.add(".keyword-strip-separator { ${getCssFromStyle { KeywordStripStyles.applySeparatorStyle(it) }} }")
        
        // Add styles from NavigationMenuStyles
        cssRules.add(".nav-container { ${getCssFromStyle { NavigationMenuStyles.applyContainerStyle(it) }} }")
        cssRules.add(".nav-content { ${getCssFromStyle { NavigationMenuStyles.applyContentStyle(it) }} }")
        cssRules.add(".nav-brand { ${getCssFromStyle { NavigationMenuStyles.applyBrandStyle(it) }} }")
        cssRules.add(".nav-list { ${getCssFromStyle { NavigationMenuStyles.applyNavListStyle(it) }} }")
        cssRules.add(".nav-item { ${getCssFromStyle { NavigationMenuStyles.applyNavItemStyle(it) }} }")
        cssRules.add(".nav-link { ${getCssFromStyle { NavigationMenuStyles.applyNavLinkStyle(it) }} }")
        
        // Add styles from PageStyles
        cssRules.add(".page-container { ${getCssFromStyle { PageStyles.applyContainerStyle(it) }} }")
        cssRules.add(".page-loading-container { ${getCssFromStyle { PageStyles.applyLoadingContainerStyle(it) }} }")
        cssRules.add(".page-error-container { ${getCssFromStyle { PageStyles.applyErrorContainerStyle(it) }} }")
        cssRules.add(".page-main-content { ${getCssFromStyle { PageStyles.applyMainContentStyle(it) }} }")
        cssRules.add(".page-button-container { ${getCssFromStyle { PageStyles.applyButtonContainerStyle(it) }} }")
        cssRules.add(".page-button { ${getCssFromStyle { PageStyles.applyButtonStyle(it) }} }")
        
        // Add styles from ReasonItemStyles
        cssRules.add(".reason-item-container { ${getCssFromStyle { ReasonItemStyles.applyContainerStyle(it) }} }")
        cssRules.add(".reason-item-title { ${getCssFromStyle { ReasonItemStyles.applyTitleStyle(it) }} }")
        
        // Add styles from SectionStyles
        cssRules.add(".section-container { ${getCssFromStyle { SectionStyles.applyContainerStyle(it, Theme.Spacing.rem2) }} }")
        cssRules.add(".section-article { ${getCssFromStyle { SectionStyles.applyArticleStyle(it) }} }")
        cssRules.add(".section-header-with-divider { ${getCssFromStyle { SectionStyles.applyHeaderWithDividerStyle(it) }} }")
        cssRules.add(".section-header-without-divider { ${getCssFromStyle { SectionStyles.applyHeaderWithoutDividerStyle(it) }} }")
        cssRules.add(".section-heading { ${getCssFromStyle { SectionStyles.applyHeadingStyle(it) }} }")
        
        // Add styles from UpdatesListStyles
        cssRules.add(".updates-list { ${getCssFromStyle { UpdatesListStyles.applyListStyle(it) }} }")
        cssRules.add(".updates-list-item { ${getCssFromStyle { UpdatesListStyles.applyListItemStyle(it) }} }")
        cssRules.add(".updates-link { ${getCssFromStyle { UpdatesListStyles.applyUpdateLinkStyle(it) }} }")
        cssRules.add(".updates-metadata { ${getCssFromStyle { UpdatesListStyles.applyMetadataStyle(it) }} }")
        
        // Add styles from Theme.Typography
        cssRules.add(".typography-h1 { ${getCssFromStyle { Theme.Typography.applyH1Style(it) }} }")
        cssRules.add(".typography-h2 { ${getCssFromStyle { Theme.Typography.applyH2Style(it) }} }")
        cssRules.add(".typography-body1 { ${getCssFromStyle { Theme.Typography.applyBody1Style(it) }} }")
        cssRules.add(".typography-body2 { ${getCssFromStyle { Theme.Typography.applyBody2Style(it) }} }")
        cssRules.add(".typography-caption { ${getCssFromStyle { Theme.Typography.applyCaptionStyle(it) }} }")
        
        // Add the CSS rules to the stylesheet
        val cssText = cssRules.joinToString("\n")
        (styleSheet as? HTMLElement)?.innerHTML = cssText
    }
    
    /**
     * Exports the main stylesheet as a CSS string.
     * This can be used to download the stylesheet.
     */
    fun exportStylesheet(): String {
        val cssRules = mutableListOf<String>()
        
        // Add styles from ButtonStyles
        cssRules.add(".button-primary { ${getCssFromStyle { ButtonStyles.applyPrimaryStyle(it) }} }")
        
        // Add styles from ExpertiseItemStyles
        cssRules.add(".expertise-item-container { ${getCssFromStyle { ExpertiseItemStyles.applyContainerStyle(it) }} }")
        cssRules.add(".expertise-item-title { ${getCssFromStyle { ExpertiseItemStyles.applyTitleStyle(it) }} }")
        cssRules.add(".expertise-item-description { ${getCssFromStyle { ExpertiseItemStyles.applyDescriptionStyle(it) }} }")
        
        // Add styles from FooterStyles
        cssRules.add(".footer-container { ${getCssFromStyle { FooterStyles.applyContainerStyle(it) }} }")
        cssRules.add(".footer-content { ${getCssFromStyle { FooterStyles.applyContentStyle(it) }} }")
        cssRules.add(".footer-link { ${getCssFromStyle { FooterStyles.applyLinkStyle(it) }} }")
        
        // Add styles from HeroStyles
        cssRules.add(".hero-container { ${getCssFromStyle { HeroStyles.applyContainerStyle(it) }} }")
        cssRules.add(".hero-title { ${getCssFromStyle { HeroStyles.applyTitleStyle(it) }} }")
        cssRules.add(".hero-description { ${getCssFromStyle { HeroStyles.applyDescriptionStyle(it) }} }")
        cssRules.add(".hero-button { ${getCssFromStyle { HeroStyles.applyButtonStyle(it) }} }")
        
        // Add styles from KeywordStripStyles
        cssRules.add(".keyword-strip-container { ${getCssFromStyle { KeywordStripStyles.applyContainerStyle(it) }} }")
        cssRules.add(".keyword-strip-paragraph { ${getCssFromStyle { KeywordStripStyles.applyParagraphStyle(it) }} }")
        cssRules.add(".keyword-strip-separator { ${getCssFromStyle { KeywordStripStyles.applySeparatorStyle(it) }} }")
        
        // Add styles from NavigationMenuStyles
        cssRules.add(".nav-container { ${getCssFromStyle { NavigationMenuStyles.applyContainerStyle(it) }} }")
        cssRules.add(".nav-content { ${getCssFromStyle { NavigationMenuStyles.applyContentStyle(it) }} }")
        cssRules.add(".nav-brand { ${getCssFromStyle { NavigationMenuStyles.applyBrandStyle(it) }} }")
        cssRules.add(".nav-list { ${getCssFromStyle { NavigationMenuStyles.applyNavListStyle(it) }} }")
        cssRules.add(".nav-item { ${getCssFromStyle { NavigationMenuStyles.applyNavItemStyle(it) }} }")
        cssRules.add(".nav-link { ${getCssFromStyle { NavigationMenuStyles.applyNavLinkStyle(it) }} }")
        
        // Add styles from PageStyles
        cssRules.add(".page-container { ${getCssFromStyle { PageStyles.applyContainerStyle(it) }} }")
        cssRules.add(".page-loading-container { ${getCssFromStyle { PageStyles.applyLoadingContainerStyle(it) }} }")
        cssRules.add(".page-error-container { ${getCssFromStyle { PageStyles.applyErrorContainerStyle(it) }} }")
        cssRules.add(".page-main-content { ${getCssFromStyle { PageStyles.applyMainContentStyle(it) }} }")
        cssRules.add(".page-button-container { ${getCssFromStyle { PageStyles.applyButtonContainerStyle(it) }} }")
        cssRules.add(".page-button { ${getCssFromStyle { PageStyles.applyButtonStyle(it) }} }")
        
        // Add styles from ReasonItemStyles
        cssRules.add(".reason-item-container { ${getCssFromStyle { ReasonItemStyles.applyContainerStyle(it) }} }")
        cssRules.add(".reason-item-title { ${getCssFromStyle { ReasonItemStyles.applyTitleStyle(it) }} }")
        
        // Add styles from SectionStyles
        cssRules.add(".section-container { ${getCssFromStyle { SectionStyles.applyContainerStyle(it, Theme.Spacing.rem2) }} }")
        cssRules.add(".section-article { ${getCssFromStyle { SectionStyles.applyArticleStyle(it) }} }")
        cssRules.add(".section-header-with-divider { ${getCssFromStyle { SectionStyles.applyHeaderWithDividerStyle(it) }} }")
        cssRules.add(".section-header-without-divider { ${getCssFromStyle { SectionStyles.applyHeaderWithoutDividerStyle(it) }} }")
        cssRules.add(".section-heading { ${getCssFromStyle { SectionStyles.applyHeadingStyle(it) }} }")
        
        // Add styles from UpdatesListStyles
        cssRules.add(".updates-list { ${getCssFromStyle { UpdatesListStyles.applyListStyle(it) }} }")
        cssRules.add(".updates-list-item { ${getCssFromStyle { UpdatesListStyles.applyListItemStyle(it) }} }")
        cssRules.add(".updates-link { ${getCssFromStyle { UpdatesListStyles.applyUpdateLinkStyle(it) }} }")
        cssRules.add(".updates-metadata { ${getCssFromStyle { UpdatesListStyles.applyMetadataStyle(it) }} }")
        
        // Add styles from Theme.Typography
        cssRules.add(".typography-h1 { ${getCssFromStyle { Theme.Typography.applyH1Style(it) }} }")
        cssRules.add(".typography-h2 { ${getCssFromStyle { Theme.Typography.applyH2Style(it) }} }")
        cssRules.add(".typography-body1 { ${getCssFromStyle { Theme.Typography.applyBody1Style(it) }} }")
        cssRules.add(".typography-body2 { ${getCssFromStyle { Theme.Typography.applyBody2Style(it) }} }")
        cssRules.add(".typography-caption { ${getCssFromStyle { Theme.Typography.applyCaptionStyle(it) }} }")
        
        return cssRules.joinToString("\n")
    }
    
    /**
     * Helper function to extract CSS from a style function.
     */
    private fun getCssFromStyle(styleFunction: (HTMLElement) -> Unit): String {
        val element = document.createElement("div") as HTMLElement
        styleFunction(element)
        
        val cssProperties = mutableListOf<String>()
        for (i in 0 until element.style.length) {
            val property = element.style.item(i)
            val value = element.style.getPropertyValue(property)
            cssProperties.add("$property: $value;")
        }
        
        return cssProperties.joinToString(" ")
    }
}
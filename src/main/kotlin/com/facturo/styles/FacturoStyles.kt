package com.facturo.styles

import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import tornadofx.*

class FacturoStyles : Stylesheet() {
    companion object {
        // Colors
        val primaryColor = c("#3498db")
        val secondaryColor = c("#2ecc71")
        val lightBackground = c("#f9f9f9")
        val darkText = c("#2c3e50")
        val lightText = c("#ecf0f1")
        
        // Style classes
        val mainHeader by cssclass()
        val subHeader by cssclass()
        val contentPane by cssclass()
        val actionButton by cssclass()
        val navButton by cssclass()
        val formField by cssclass()
        val tableHeader by cssclass()
    }
    
    init {
        root {
            fontSize = 14.px
            fontFamily = "Segoe UI"
            backgroundColor += lightBackground
            textFill = darkText
        }
        
        mainHeader {
            fontSize = 24.px
            fontWeight = FontWeight.BOLD
            padding = box(15.px)
            textFill = primaryColor
        }
        
        subHeader {
            fontSize = 18.px
            fontWeight = FontWeight.SEMI_BOLD
            padding = box(10.px)
            textFill = darkText
        }
        
        contentPane {
            padding = box(20.px)
            spacing = 10.px
            backgroundColor += Color.WHITE
            borderRadius += box(5.px)
            effect = DropShadowBuilder().color(Color.GRAY.deriveColor(0.0, 1.0, 1.0, 0.2)).build()
        }
        
        button {
            backgroundColor += primaryColor
            textFill = lightText
            borderRadius += box(3.px)
            padding = box(8.px, 15.px)
            
            and(hover) {
                backgroundColor += primaryColor.darker()
            }
        }
        
        actionButton {
            backgroundColor += secondaryColor
            
            and(hover) {
                backgroundColor += secondaryColor.darker()
            }
        }
        
        navButton {
            minWidth = 160.px
            alignment = Pos.CENTER_LEFT
            padding = box(12.px, 20.px)
            backgroundColor += Color.TRANSPARENT
            textFill = darkText
            
            and(hover) {
                backgroundColor += primaryColor.deriveColor(0.0, 1.0, 1.0, 0.1)
            }
        }
        
        formField {
            padding = box(8.px)
            fontSize = 14.px
        }
        
        tableView {
            borderColor += box(lightBackground)
            
            tableRowCell {
                and(selected) {
                    backgroundColor += primaryColor.deriveColor(0.0, 1.0, 1.0, 0.3)
                }
            }
        }
        
        tableHeader {
            backgroundColor += primaryColor.deriveColor(0.0, 1.0, 1.0, 0.1)
            fontWeight = FontWeight.SEMI_BOLD
        }
    }
}
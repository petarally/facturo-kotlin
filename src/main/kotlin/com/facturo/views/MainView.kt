package com.facturo.views

import com.facturo.styles.FacturoStyles
import javafx.geometry.Pos
import javafx.scene.control.ScrollPane
import javafx.scene.effect.DropShadow
import javafx.scene.layout.Priority
import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import tornadofx.*

class MainView : View("Facturo - Invoice Management") {
    override val root = borderpane {
        setPrefSize(1200.0, 800.0)
        
        left = vbox {
            minWidth = 200.0
            style {
                backgroundColor += Color.WHITE
                effect = DropShadow().apply {
                    color = Color.GRAY.deriveColor(0.0, 1.0, 1.0, 0.2)
                    radius = 10.0
                    spread = 0.0
                }
            }
            
            vbox {
                alignment = Pos.CENTER
                padding = insets(20)
                
                label("FACTURO") {
                    style {
                        fontSize = 20.px
                        fontWeight = FontWeight.BOLD
                        textFill = FacturoStyles.primaryColor
                    }
                }
                
                label("Invoice Management") {
                    style {
                        fontSize = 12.px
                        textFill = FacturoStyles.darkText
                    }
                }
            }
            
            separator()
            
            vbox {
                spacing = 5.0
                padding = insets(10)
                
                button("Dashboard") {
                    maxWidth = Double.MAX_VALUE
                    addClass(FacturoStyles.navButton)
                    graphic = label("🏠")
                    action {
                        replaceContent<DashboardView>()
                    }
                }
                
                button("Invoices") {
                    maxWidth = Double.MAX_VALUE
                    addClass(FacturoStyles.navButton)
                    graphic = label("📄")
                    action {
                        replaceContent<InvoiceListView>()
                    }
                }
                
                button("Clients") {
                    maxWidth = Double.MAX_VALUE
                    addClass(FacturoStyles.navButton)
                    graphic = label("👥")
                    action {
                        replaceContent<ClientListView>()
                    }
                }
                
                button("Company Info") {
                    maxWidth = Double.MAX_VALUE
                    addClass(FacturoStyles.navButton)
                    graphic = label("🏢")
                    action {
                        replaceContent<CompanySettingsView>()
                    }
                }
                
                button("Settings") {
                    maxWidth = Double.MAX_VALUE
                    addClass(FacturoStyles.navButton)
                    graphic = label("⚙️")
                    action {
                        replaceContent<SettingsView>()
                    }
                }
            }
            
            vbox {
                vgrow = Priority.ALWAYS
            }
            
            separator()
            
            vbox {
                padding = insets(10)
                alignment = Pos.CENTER
                
                label("Facturo v1.0") {
                    style {
                        fontSize = 12.px
                        textFill = Color.GRAY
                    }
                }
            }
        }
        
        center = scrollpane {
            isFitToWidth = true
            isFitToHeight = true
            hbarPolicy = ScrollPane.ScrollBarPolicy.NEVER
            
            add<DashboardView>()
        }
    }
    
    override fun onDock() {
        primaryStage.minWidth = 1000.0
        primaryStage.minHeight = 700.0
    }
    
    private inline fun <reified T: UIComponent> replaceContent() {
        val container = root.center as ScrollPane
        container.content = find<T>().root
    }
}
package com.facturo.views

import com.facturo.styles.FacturoStyles
import javafx.geometry.Pos
import javafx.scene.layout.Priority
import javafx.scene.paint.Color
import tornadofx.*

class MainView : View("Facturo - Invoice Management") {
    override val root = borderpane {
        left = vbox {
            style {
                backgroundColor += Color.WHITE
                effect = DropShadowBuilder().color(Color.GRAY.deriveColor(0.0, 1.0, 1.0, 0.2)).build()
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
                    addClass(FacturoStyles.navButton)
                    graphic = label("🏠")
                    action {
                        replaceWith<DashboardView>()
                    }
                }
                
                button("Invoices") {
                    addClass(FacturoStyles.navButton)
                    graphic = label("📄")
                    action {
                        replaceWith<InvoiceListView>()
                    }
                }
                
                button("Clients") {
                    addClass(FacturoStyles.navButton)
                    graphic = label("👥")
                    action {
                        replaceWith<ClientListView>()
                    }
                }
                
                button("Company Info") {
                    addClass(FacturoStyles.navButton)
                    graphic = label("🏢")
                    action {
                        replaceWith<CompanySettingsView>()
                    }
                }
                
                button("Settings") {
                    addClass(FacturoStyles.navButton)
                    graphic = label("⚙️")
                    action {
                        replaceWith<SettingsView>()
                    }
                }
            }
            
            vbox {
                vgrow = Priority.ALWAYS
            }
            
            separator()
            
            vbox {
                padding = insets(20)
                alignment = Pos.CENTER
                
                label("Facturo v1.0") {
                    style {
                        fontSize = 12.px
                        textFill = Color.GRAY
                    }
                }
            }
        }
        
        center {
            add<DashboardView>()
        }
    }
}
package com.facturo.views

import com.facturo.controllers.InvoiceController
import com.facturo.styles.FacturoStyles
import javafx.geometry.Pos
import javafx.scene.chart.CategoryAxis
import javafx.scene.chart.NumberAxis
import javafx.scene.paint.Color
import tornadofx.*

class DashboardView : View("Dashboard") {
    private val invoiceController: InvoiceController by inject()
    
    override val root = scrollpane {
        fitToWidth = true
        vbox {
            spacing = 20.0
            padding = insets(20)
            
            label("Dashboard") {
                addClass(FacturoStyles.mainHeader)
            }
            
            hbox {
                spacing = 20.0
                
                vbox {
                    addClass(FacturoStyles.contentPane)
                    hgrow = Priority.ALWAYS
                    
                    label("Invoice Statistics") {
                        addClass(FacturoStyles.subHeader)
                    }
                    
                    hbox {
                        spacing = 20.0
                        
                        vbox {
                            hgrow = Priority.ALWAYS
                            spacing = 10.0
                            
                            label("Total Invoices") {
                                style { textFill = Color.GRAY }
                            }
                            
                            label(invoiceController.invoices.size.toString()) {
                                style {
                                    fontSize = 28.px
                                    fontWeight = FontWeight.BOLD
                                }
                            }
                        }
                        
                        vbox {
                            hgrow = Priority.ALWAYS
                            spacing = 10.0
                            
                            label("Paid Invoices") {
                                style { textFill = Color.GRAY }
                            }
                            
                            label(invoiceController.invoices.count { it.paid }.toString()) {
                                style {
                                    fontSize = 28.px
                                    fontWeight = FontWeight.BOLD
                                    textFill = FacturoStyles.secondaryColor
                                }
                            }
                        }
                        
                        vbox {
                            hgrow = Priority.ALWAYS
                            spacing = 10.0
                            
                            label("Unpaid Invoices") {
                                style { textFill = Color.GRAY }
                            }
                            
                            label(invoiceController.invoices.count { !it.paid }.toString()) {
                                style {
                                    fontSize = 28.px
                                    fontWeight = FontWeight.BOLD
                                    textFill = Color.ORANGE
                                }
                            }
                        }
                    }
                }
            }
            
            hbox {
                spacing = 20.0
                
                vbox {
                    addClass(FacturoStyles.contentPane)
                    hgrow = Priority.ALWAYS
                    
                    label("Invoice Revenue") {
                        addClass(FacturoStyles.subHeader)
                    }
                    
                    barchart("Revenue by Month", CategoryAxis(), NumberAxis()) {
                        series("Revenue") {
                            data("Jan", 5000)
                            data("Feb", 7000)
                            data("Mar", 4500)
                            data("Apr", 8200)
                            data("May", 9100)
                            data("Jun", 6700)
                        }
                        
                        prefHeight = 300.0
                    }
                }
            }
            
            hbox {
                spacing = 20.0
                
                vbox {
                    addClass(FacturoStyles.contentPane)
                    hgrow = Priority.ALWAYS
                    
                    label("Recent Invoices") {
                        addClass(FacturoStyles.subHeader)
                    }
                    
                    tableview(invoiceController.invoices.sortedByDescending { it.date }.take(5).asObservable()) {
                        readonlyColumn("Invoice #", Invoice::invoiceNumber)
                        readonlyColumn("Client", Invoice::client) { it.value.name }
                        readonlyColumn("Date", Invoice::date)
                        readonlyColumn("Due Date", Invoice::dueDate)
                        readonlyColumn("Total", Invoice::total) { it.value.toString() + " €" }
                        readonlyColumn("Status", Invoice::paid) { 
                            if (it.value) "Paid" else "Unpaid" 
                        }
                        
                        prefHeight = 200.0
                    }
                    
                    button("View All Invoices") {
                        style {
                            marginTop = 10.px
                        }
                        action {
                            replaceWith<InvoiceListView>()
                        }
                    }
                }
            }
            
            hbox {
                spacing = 20.0
                
                vbox {
                    addClass(FacturoStyles.contentPane)
                    hgrow = Priority.ALWAYS
                    alignment = Pos.CENTER
                    
                    label("Quick Actions") {
                        addClass(FacturoStyles.subHeader)
                    }
                    
                    hbox {
                        spacing = 20.0
                        padding = insets(20)
                        alignment = Pos.CENTER
                        
                        button("New Invoice") {
                            addClass(FacturoStyles.actionButton)
                            prefWidth = 180.0
                            prefHeight = 50.0
                            graphic = label("📄")
                            action {
                                find<InvoiceEditorView>(mapOf("invoice" to invoiceController.createNewInvoice())).openWindow()
                            }
                        }
                        
                        button("New Client") {
                            addClass(FacturoStyles.actionButton)
                            prefWidth = 180.0
                            prefHeight = 50.0
                            graphic = label("👥")
                            action {
                                find<ClientEditorView>().openWindow()
                            }
                        }
                        
                        button("Company Settings") {
                            addClass(FacturoStyles.actionButton)
                            prefWidth = 180.0
                            prefHeight = 50.0
                            graphic = label("🏢")
                            action {
                                replaceWith<CompanySettingsView>()
                            }
                        }
                    }
                }
            }
        }
    }
}
package com.facturo.views

import com.facturo.controllers.InvoiceController
import com.facturo.models.Invoice
import com.facturo.styles.FacturoStyles
import javafx.geometry.Pos
import javafx.scene.chart.CategoryAxis
import javafx.scene.chart.NumberAxis
import javafx.scene.layout.Priority
import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import tornadofx.*

class DashboardView : View("Dashboard") {
    private val invoiceController: InvoiceController by inject()
    
    override val root = scrollpane {
        isFitToWidth = true
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
                            
                            label(invoiceController.invoices.count { invoice -> invoice.paid }.toString()) {
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
                            
                            label(invoiceController.invoices.count { invoice -> !invoice.paid }.toString()) {
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
                    
                    tableview<Invoice>(invoiceController.invoices.sortedByDescending { invoice -> invoice.date }.take(5).asObservable()) {
                        column("Invoice #", Invoice::invoiceNumber)
                        
                        column("Client", Invoice::client).cellFormat {
                            text = it.name
                        }
                        
                        column("Date", Invoice::date)
                        column("Due Date", Invoice::dueDate)
                        
                        column("Total", Invoice::total).cellFormat {
                            text = "$it €"
                        }
                        
                        column("Status", Invoice::paid).cellFormat {
                            text = if (it) "Paid" else "Unpaid"
                        }
                        
                        prefHeight = 200.0
                    }
                    
                    button("View All Invoices") {
                        style {
                            this.padding = box(10.px, 0.px, 0.px, 0.px)
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
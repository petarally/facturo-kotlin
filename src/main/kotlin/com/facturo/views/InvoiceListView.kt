package com.facturo.views

import com.facturo.controllers.InvoiceController
import com.facturo.models.Invoice
import com.facturo.styles.FacturoStyles
import javafx.geometry.Pos
import javafx.scene.control.TableView
import javafx.scene.layout.Priority
import tornadofx.*
import java.time.format.DateTimeFormatter

class InvoiceListView : View("Invoices") {
    private val invoiceController: InvoiceController by inject()
    private var tableView: TableView<Invoice> by singleAssign()
    
    override val root = vbox {
        spacing = 20.0
        padding = insets(20)
        
        hbox {
            alignment = Pos.CENTER_LEFT
            spacing = 20.0
            
            label("Invoices") {
                addClass(FacturoStyles.mainHeader)
            }
            
            region { hgrow = Priority.ALWAYS }
            
            button("Create New Invoice") {
                addClass(FacturoStyles.actionButton)
                action {
                    val invoice = invoiceController.createNewInvoice()
                    find<InvoiceEditorView>(mapOf("invoice" to invoice)).openWindow()
                    // When the window is closed, refresh the table
                    tableView.refresh()
                }
            }
        }
        
        vbox {
            addClass(FacturoStyles.contentPane)
            vgrow = Priority.ALWAYS
            
            tableView = tableview(invoiceController.invoices) {
                readonlyColumn("Invoice #", Invoice::invoiceNumber)
                readonlyColumn("Client", Invoice::client) { it.value.name }
                readonlyColumn("Date", Invoice::date) { 
                    it.value.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) 
                }
                readonlyColumn("Due Date", Invoice::dueDate) { 
                    it.value.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) 
                }
                readonlyColumn("Total", Invoice::total) { 
                    String.format("%.2f €", it.value) 
                }
                readonlyColumn("Status", Invoice::paid) { 
                    if (it.value) "Paid" else "Unpaid" 
                }
                
                contextmenu {
                    item("Edit") {
                        action {
                            val selectedInvoice = selectedItem
                            if (selectedInvoice != null) {
                                find<InvoiceEditorView>(mapOf("invoice" to selectedInvoice)).openWindow()
                                tableView.refresh()
                            }
                        }
                    }
                    item("Delete") {
                        action {
                            val selectedInvoice = selectedItem
                            if (selectedInvoice != null) {
                                confirmation(
                                    "Delete Invoice",
                                    "Are you sure you want to delete invoice ${selectedInvoice.invoiceNumber}?",
                                    actionFn = {
                                        invoiceController.delete(selectedInvoice)
                                        tableView.refresh()
                                    }
                                )
                            }
                        }
                    }
                    item("Mark as Paid") {
                        action {
                            val selectedInvoice = selectedItem
                            if (selectedInvoice != null && !selectedInvoice.paid) {
                                selectedInvoice.paid = true
                                invoiceController.update(selectedInvoice)
                                tableView.refresh()
                            }
                        }
                    }
                    item("Generate PDF") {
                        action {
                            val selectedInvoice = selectedItem
                            if (selectedInvoice != null) {
                                // TODO: Implement PDF generation
                                information("PDF Generation", "PDF generation will be implemented in a future version.")
                            }
                        }
                    }
                }
                
                vgrow = Priority.ALWAYS
                smartResize()
                
                onDoubleClick {
                    val selectedInvoice = selectedItem
                    if (selectedInvoice != null) {
                        find<InvoiceEditorView>(mapOf("invoice" to selectedInvoice)).openWindow()
                    }
                }
            }
        }
    }
}
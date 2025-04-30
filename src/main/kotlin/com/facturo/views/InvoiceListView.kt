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
                column("Invoice #", Invoice::invoiceNumber)
                
                column("Client", Invoice::client).cellFormat { client ->
                    text = client.name
                }
                
                column("Date", Invoice::date).cellFormat { date ->
                    text = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
                }
                
                column("Due Date", Invoice::dueDate).cellFormat { dueDate ->
                    text = dueDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
                }
                
                column("Total", Invoice::total).cellFormat { total ->
                    text = String.format("%.2f €", total)
                }
                
                column("Status", Invoice::paid).cellFormat { paid ->
                    text = if (paid) "Paid" else "Unpaid"
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
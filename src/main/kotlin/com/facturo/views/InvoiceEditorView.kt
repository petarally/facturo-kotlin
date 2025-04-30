package com.facturo.views

import com.facturo.controllers.ClientController
import com.facturo.controllers.InvoiceController
import com.facturo.models.Client
import com.facturo.models.Invoice
import com.facturo.models.InvoiceItem
import com.facturo.models.InvoiceItemModel
import com.facturo.models.InvoiceModel
import com.facturo.styles.FacturoStyles
import javafx.beans.property.SimpleObjectProperty
import javafx.collections.FXCollections
import javafx.geometry.Pos
import javafx.scene.control.TableView
import javafx.scene.layout.Priority
import tornadofx.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class InvoiceEditorView : View("Invoice Editor") {
    private val invoice: Invoice by param()
    private val model = InvoiceModel().apply { item = invoice }
    private val invoiceController: InvoiceController by inject()
    private val clientController: ClientController by inject()
    private val itemModel = InvoiceItemModel()
    private var itemsTable: TableView<InvoiceItem> by singleAssign()
    private val selectedClient = SimpleObjectProperty<Client>()
    
    override val root = vbox {
        padding = insets(20)
        spacing = 20.0
        
        label("Edit Invoice") {
            addClass(FacturoStyles.mainHeader)
        }
        
        scrollpane {
            fitToWidth = true
            vbox {
                padding = insets(20)
                spacing = 20.0
                
                form {
                    fieldset("Invoice Details") {
                        field("Invoice Number") {
                            textfield(model.invoiceNumber) {
                                addClass(FacturoStyles.formField)
                                isEditable = false  // Auto-generated
                            }
                        }
                        
                        hbox {
                            spacing = 20.0
                            
                            field("Date") {
                                datepicker(model.date) {
                                    addClass(FacturoStyles.formField)
                                }
                            }
                            
                            field("Due Date") {
                                datepicker(model.dueDate) {
                                    addClass(FacturoStyles.formField)
                                }
                            }
                        }
                        
                        field("Client") {
                            combobox<Client>(selectedClient) {
                                items = clientController.clients
                                cellFormat { text = it.name }
                                valueProperty().addListener { _, _, newValue ->
                                    if (newValue != null) {
                                        invoice.client = newValue
                                    }
                                }
                                
                                // Set initial selection if editing existing invoice
                                if (invoice.client.id > 0) {
                                    selectionModel.select(clientController.clients.find { it.id == invoice.client.id })
                                }
                                
                                addClass(FacturoStyles.formField)
                            }
                        }
                    }
                    
                    fieldset("Invoice Items") {
                        vbox {
                            itemsTable = tableview(invoice.items) {
                                readonlyColumn("Description", InvoiceItem::description)
                                readonlyColumn("Quantity", InvoiceItem::quantity)
                                readonlyColumn("Unit Price", InvoiceItem::unitPrice) { 
                                    String.format("%.2f €", it.value) 
                                }
                                readonlyColumn("Discount %", InvoiceItem::discount)
                                readonlyColumn("Total", InvoiceItem::total) { 
                                    String.format("%.2f €", it.value) 
                                }
                                
                                contextmenu {
                                    item("Edit Item") {
                                        action {
                                            val selectedItem = selectedItem
                                            if (selectedItem != null) {
                                                itemModel.item = selectedItem
                                                find<InvoiceItemEditorFragment>(mapOf("model" to itemModel)).openModal(
                                                    block = true
                                                )
                                                selectedItem.recalculate()
                                                invoice.recalculate()
                                                refresh()
                                            }
                                        }
                                    }
                                    item("Delete Item") {
                                        action {
                                            val selectedItem = selectedItem
                                            if (selectedItem != null) {
                                                invoice.items.remove(selectedItem)
                                                invoice.recalculate()
                                                refresh()
                                            }
                                        }
                                    }
                                }
                                
                                onDoubleClick {
                                    val selectedItem = selectedItem
                                    if (selectedItem != null) {
                                        itemModel.item = selectedItem
                                        find<InvoiceItemEditorFragment>(mapOf("model" to itemModel)).openModal(
                                            block = true
                                        )
                                        selectedItem.recalculate()
                                        invoice.recalculate()
                                        refresh()
                                    }
                                }
                                
                                prefHeight = 200.0
                            }
                            
                            hbox {
                                spacing = 10.0
                                padding = insets(10, 0)
                                
                                button("Add Item") {
                                    action {
                                        itemModel.item = InvoiceItem()
                                        find<InvoiceItemEditorFragment>(mapOf("model" to itemModel)).openModal(
                                            block = true
                                        )
                                        
                                        if (itemModel.isDirty) {
                                            val newItem = itemModel.item
                                            newItem.recalculate()
                                            invoice.items.add(newItem)
                                            invoice.recalculate()
                                            itemsTable.refresh()
                                        }
                                    }
                                }
                            }
                        }
                    }
                    
                    fieldset("Summary") {
                        hbox {
                            vbox {
                                field("Notes") {
                                    textarea(model.notes) {
                                        prefRowCount = 5
                                        addClass(FacturoStyles.formField)
                                    }
                                }
                            }
                            
                            vbox {
                                minWidth = 300.0
                                padding = insets(0, 0, 0, 20)
                                
                                hbox {
                                    label("Subtotal:")
                                    region { hgrow = Priority.ALWAYS }
                                    label(invoice.subtotal.toString()) {
                                        textProperty().bind(model.subtotal.stringBinding {
                                            String.format("%.2f €", it)
                                        })
                                    }
                                }
                                
                                hbox {
                                    padding = insets(10, 0)
                                    
                                    label("Tax Rate:")
                                    region { hgrow = Priority.ALWAYS }
                                    
                                    hbox {
                                        textfield(model.taxRate) {
                                            prefWidth = 60.0
                                            filterInput { it.controlNewText.isDouble() }
                                            textProperty().addListener { _, _, _ ->
                                                invoice.recalculate()
                                                model.taxAmount.value = invoice.taxAmount
                                                model.total.value = invoice.total
                                            }
                                        }
                                        label(" %")
                                    }
                                }
                                
                                hbox {
                                    label("Tax Amount:")
                                    region { hgrow = Priority.ALWAYS }
                                    label(invoice.taxAmount.toString()) {
                                        textProperty().bind(model.taxAmount.stringBinding {
                                            String.format("%.2f €", it)
                                        })
                                    }
                                }
                                
                                separator()
                                
                                hbox {
                                    padding = insets(10, 0)
                                    
                                    label("TOTAL:") {
                                        style {
                                            fontWeight = FontWeight.BOLD
                                        }
                                    }
                                    region { hgrow = Priority.ALWAYS }
                                    label(invoice.total.toString()) {
                                        style {
                                            fontWeight = FontWeight.BOLD
                                        }
                                        textProperty().bind(model.total.stringBinding {
                                            String.format("%.2f €", it)
                                        })
                                    }
                                }
                                
                                hbox {
                                    padding = insets(10, 0)
                                    alignment = Pos.CENTER_LEFT
                                    
                                    checkbox("Invoice Paid", model.paid)
                                }
                            }
                        }
                    }
                }
                
                hbox {
                    alignment = Pos.CENTER_RIGHT
                    spacing = 10.0
                    padding = insets(20, 0)
                    
                    button("Cancel") {
                        action {
                            close()
                        }
                    }
                    
                    button("Save Invoice") {
                        addClass(FacturoStyles.actionButton)
                        action {
                            model.commit()
                            
                            if (invoice.id == 0) {
                                invoiceController.add(invoice)
                            } else {
                                invoiceController.update(invoice)
                            }
                            
                            close()
                        }
                    }
                }
            }
        }
    }
}
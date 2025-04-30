package com.facturo.models

import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import tornadofx.*
import java.time.LocalDate

class Invoice(
    id: Int = 0,
    invoiceNumber: String = "",
    date: LocalDate = LocalDate.now(),
    dueDate: LocalDate = LocalDate.now().plusDays(15),
    client: Client = Client(),
    items: ObservableList<InvoiceItem> = FXCollections.observableArrayList(),
    notes: String = "",
    subtotal: Double = 0.0,
    taxRate: Double = 25.0,
    taxAmount: Double = 0.0,
    total: Double = 0.0,
    paid: Boolean = false
) {
    val idProperty = SimpleIntegerProperty(id)
    var id by idProperty
    
    val invoiceNumberProperty = SimpleStringProperty(invoiceNumber)
    var invoiceNumber by invoiceNumberProperty
    
    val dateProperty = SimpleObjectProperty(date)
    var date by dateProperty
    
    val dueDateProperty = SimpleObjectProperty(dueDate)
    var dueDate by dueDateProperty
    
    val clientProperty = SimpleObjectProperty(client)
    var client by clientProperty
    
    val itemsProperty = SimpleObjectProperty(items)
    var items by itemsProperty
    
    val notesProperty = SimpleStringProperty(notes)
    var notes by notesProperty
    
    val subtotalProperty = SimpleDoubleProperty(subtotal)
    var subtotal by subtotalProperty
    
    val taxRateProperty = SimpleDoubleProperty(taxRate)
    var taxRate by taxRateProperty
    
    val taxAmountProperty = SimpleDoubleProperty(taxAmount)
    var taxAmount by taxAmountProperty
    
    val totalProperty = SimpleDoubleProperty(total)
    var total by totalProperty
    
    val paidProperty = javafx.beans.property.SimpleBooleanProperty(paid)
    var paid by paidProperty
    
    fun recalculate() {
        subtotal = items.sumOf { it.total }
        taxAmount = subtotal * taxRate / 100
        total = subtotal + taxAmount
    }
}

class InvoiceModel : ItemViewModel<Invoice>() {
    val id = bind(Invoice::idProperty)
    val invoiceNumber = bind(Invoice::invoiceNumberProperty)
    val date = bind(Invoice::dateProperty)
    val dueDate = bind(Invoice::dueDateProperty)
    val client = bind(Invoice::clientProperty)
    val items = bind(Invoice::itemsProperty)
    val notes = bind(Invoice::notesProperty)
    val subtotal = bind(Invoice::subtotalProperty)
    val taxRate = bind(Invoice::taxRateProperty)
    val taxAmount = bind(Invoice::taxAmountProperty)
    val total = bind(Invoice::totalProperty)
    val paid = bind(Invoice::paidProperty)
}
package com.facturo.controllers

import com.facturo.models.Invoice
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import tornadofx.*
import java.time.LocalDate

class InvoiceController : Controller() {
    val clientController: ClientController by inject()
    val invoices: ObservableList<Invoice> = FXCollections.observableArrayList()
    
    init {
        // Sample data - would be loaded from database in a real app
        // We'll start with empty list for this example
    }
    
    fun createNewInvoice(): Invoice {
        val invoice = Invoice(
            id = invoices.size + 1,
            invoiceNumber = generateInvoiceNumber(),
            date = LocalDate.now(),
            dueDate = LocalDate.now().plusDays(15),
            items = FXCollections.observableArrayList()
        )
        return invoice
    }
    
    fun add(invoice: Invoice) {
        // In a real app, we would insert into database and get ID
        invoice.id = invoices.size + 1
        invoices.add(invoice)
    }
    
    fun update(invoice: Invoice) {
        val index = invoices.indexOfFirst { it.id == invoice.id }
        if (index >= 0) {
            invoices[index] = invoice
        }
    }
    
    fun delete(invoice: Invoice) {
        invoices.removeIf { it.id == invoice.id }
    }
    
    fun getById(id: Int): Invoice? {
        return invoices.find { it.id == id }
    }
    
    private fun generateInvoiceNumber(): String {
        val year = LocalDate.now().year
        val count = invoices.size + 1
        return "$year-${count.toString().padStart(4, '0')}"
    }
}
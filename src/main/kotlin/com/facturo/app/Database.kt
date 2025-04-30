package com.facturo.app

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.File

object Database {
    fun connect() {
        val dbFile = File("facturo.db")
        val dbExists = dbFile.exists()
        
        org.jetbrains.exposed.sql.Database.connect("jdbc:sqlite:facturo.db", "org.sqlite.JDBC")
        
        if (!dbExists) {
            // Create tables if database doesn't exist
            transaction {
                SchemaUtils.create(Clients, Companies, Invoices, InvoiceItems)
                
                // Insert default company data
                Companies.insert {
                    it[name] = "Your Company"
                    it[address] = "Company Address"
                    it[city] = "City"
                    it[postalCode] = "12345"
                    it[country] = "Country"
                    it[vatNumber] = "VAT123456789"
                    it[iban] = "IBAN123456789"
                    it[swift] = "SWIFT123"
                    it[email] = "info@yourcompany.com"
                    it[phone] = "+385 1 2345 678"
                }
            }
        }
    }
}

// Database tables
object Clients : Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 255)
    val address = varchar("address", 255)
    val city = varchar("city", 100)
    val postalCode = varchar("postal_code", 20)
    val country = varchar("country", 100)
    val vatNumber = varchar("vat_number", 50)
    val contactPerson = varchar("contact_person", 100)
    val email = varchar("email", 100)
    val phone = varchar("phone", 50)
    
    override val primaryKey = PrimaryKey(id)
}

object Companies : Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 255)
    val address = varchar("address", 255)
    val city = varchar("city", 100)
    val postalCode = varchar("postal_code", 20)
    val country = varchar("country", 100)
    val vatNumber = varchar("vat_number", 50)
    val iban = varchar("iban", 50)
    val swift = varchar("swift", 50)
    val email = varchar("email", 100)
    val phone = varchar("phone", 50)
    
    override val primaryKey = PrimaryKey(id)
}

object Invoices : Table() {
    val id = integer("id").autoIncrement()
    val invoiceNumber = varchar("invoice_number", 50)
    val date = varchar("date", 10) // Store as ISO format string
    val dueDate = varchar("due_date", 10) // Store as ISO format string
    val clientId = integer("client_id").references(Clients.id)
    val notes = text("notes")
    val subtotal = double("subtotal")
    val taxRate = double("tax_rate")
    val taxAmount = double("tax_amount")
    val total = double("total")
    val paid = bool("paid")
    
    override val primaryKey = PrimaryKey(id)
}

object InvoiceItems : Table() {
    val id = integer("id").autoIncrement()
    val invoiceId = integer("invoice_id").references(Invoices.id)
    val description = varchar("description", 255)
    val quantity = double("quantity")
    val unitPrice = double("unit_price")
    val discount = double("discount")
    val total = double("total")
    
    override val primaryKey = PrimaryKey(id)
}
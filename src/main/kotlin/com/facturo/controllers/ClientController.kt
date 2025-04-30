package com.facturo.controllers

import com.facturo.models.Client
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import tornadofx.*

class ClientController : Controller() {
    val clients: ObservableList<Client> = FXCollections.observableArrayList()
    
    init {
        // Sample data - would be loaded from database in a real app
        clients.addAll(
            Client(1, "ABC Corporation", "123 Main St", "New York", "10001", "USA", "US123456789", "John Smith", "john@abc.com", "+1 212-555-1234"),
            Client(2, "Global Industries", "456 Oak Ave", "London", "EC1A 1BB", "UK", "GB987654321", "Emma Johnson", "emma@global.com", "+44 20 7123 4567"),
            Client(3, "Tech Solutions", "789 Pine Rd", "Berlin", "10115", "Germany", "DE456789012", "Hans Schmidt", "hans@tech.com", "+49 30 987 6543")
        )
    }
    
    fun add(client: Client) {
        // In a real app, we would insert into database and get ID
        client.id = clients.size + 1
        clients.add(client)
    }
    
    fun update(client: Client) {
        val index = clients.indexOfFirst { it.id == client.id }
        if (index >= 0) {
            clients[index] = client
        }
    }
    
    fun delete(client: Client) {
        clients.removeIf { it.id == client.id }
    }
    
    fun getById(id: Int): Client? {
        return clients.find { it.id == id }
    }
}
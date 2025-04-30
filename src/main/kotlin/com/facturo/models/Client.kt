package com.facturo.models

import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*

class Client(
    id: Int = 0,
    name: String = "",
    address: String = "",
    city: String = "",
    postalCode: String = "",
    country: String = "",
    vatNumber: String = "",
    contactPerson: String = "",
    email: String = "",
    phone: String = ""
) {
    val idProperty = SimpleIntegerProperty(id)
    var id by idProperty
    
    val nameProperty = SimpleStringProperty(name)
    var name by nameProperty
    
    val addressProperty = SimpleStringProperty(address)
    var address by addressProperty
    
    val cityProperty = SimpleStringProperty(city)
    var city by cityProperty
    
    val postalCodeProperty = SimpleStringProperty(postalCode)
    var postalCode by postalCodeProperty
    
    val countryProperty = SimpleStringProperty(country)
    var country by countryProperty
    
    val vatNumberProperty = SimpleStringProperty(vatNumber)
    var vatNumber by vatNumberProperty
    
    val contactPersonProperty = SimpleStringProperty(contactPerson)
    var contactPerson by contactPersonProperty
    
    val emailProperty = SimpleStringProperty(email)
    var email by emailProperty
    
    val phoneProperty = SimpleStringProperty(phone)
    var phone by phoneProperty
}

class ClientModel : ItemViewModel<Client>() {
    val id = bind(Client::idProperty)
    val name = bind(Client::nameProperty)
    val address = bind(Client::addressProperty)
    val city = bind(Client::cityProperty)
    val postalCode = bind(Client::postalCodeProperty)
    val country = bind(Client::countryProperty)
    val vatNumber = bind(Client::vatNumberProperty)
    val contactPerson = bind(Client::contactPersonProperty)
    val email = bind(Client::emailProperty)
    val phone = bind(Client::phoneProperty)
}
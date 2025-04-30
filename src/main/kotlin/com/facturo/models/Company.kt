package com.facturo.models

import javafx.beans.property.SimpleStringProperty
import tornadofx.*

class Company(
    name: String = "",
    address: String = "",
    city: String = "",
    postalCode: String = "",
    country: String = "",
    vatNumber: String = "",
    iban: String = "",
    swift: String = "",
    email: String = "",
    phone: String = ""
) {
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
    
    val ibanProperty = SimpleStringProperty(iban)
    var iban by ibanProperty
    
    val swiftProperty = SimpleStringProperty(swift)
    var swift by swiftProperty
    
    val emailProperty = SimpleStringProperty(email)
    var email by emailProperty
    
    val phoneProperty = SimpleStringProperty(phone)
    var phone by phoneProperty
}

class CompanyModel : ItemViewModel<Company>() {
    val name = bind(Company::nameProperty)
    val address = bind(Company::addressProperty)
    val city = bind(Company::cityProperty)
    val postalCode = bind(Company::postalCodeProperty)
    val country = bind(Company::countryProperty)
    val vatNumber = bind(Company::vatNumberProperty)
    val iban = bind(Company::ibanProperty)
    val swift = bind(Company::swiftProperty)
    val email = bind(Company::emailProperty)
    val phone = bind(Company::phoneProperty)
}
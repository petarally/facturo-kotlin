package com.facturo.views

import com.facturo.models.Company
import com.facturo.models.CompanyModel
import com.facturo.styles.FacturoStyles
import javafx.geometry.Pos
import tornadofx.*

class CompanySettingsView : View("Company Settings") {
    private val company = Company(
        "Your Company",
        "Company Address",
        "City",
        "12345",
        "Country",
        "VAT123456789",
        "IBAN123456789",
        "SWIFT123",
        "info@yourcompany.com",
        "+385 1 2345 678"
    )
    
    private val model = CompanyModel().apply { item = company }
    
    override val root = vbox {
        spacing = 20.0
        padding = insets(20)
        
        label("Company Settings") {
            addClass(FacturoStyles.mainHeader)
        }
        
        vbox {
            addClass(FacturoStyles.contentPane)
            
            form {
                fieldset("Company Information") {
                    field("Company Name") {
                        textfield(model.name) {
                            addClass(FacturoStyles.formField)
                        }.required()
                    }
                    
                    hbox {
                        spacing = 20.0
                        
                        field("VAT Number") {
                            textfield(model.vatNumber) {
                                addClass(FacturoStyles.formField)
                            }
                        }
                    }
                }
                
                fieldset("Contact Information") {
                    hbox {
                        spacing = 20.0
                        
                        field("Email") {
                            textfield(model.email) {
                                addClass(FacturoStyles.formField)
                            }
                        }
                        
                        field("Phone") {
                            textfield(model.phone) {
                                addClass(FacturoStyles.formField)
                            }
                        }
                    }
                }
                
                fieldset("Address") {
                    field("Address") {
                        textfield(model.address) {
                            addClass(FacturoStyles.formField)
                        }
                    }
                    
                    hbox {
                        spacing = 20.0
                        
                        field("City") {
                            textfield(model.city) {
                                addClass(FacturoStyles.formField)
                            }
                        }
                        
                        field("Postal Code") {
                            textfield(model.postalCode) {
                                addClass(FacturoStyles.formField)
                            }
                        }
                    }
                    
                    field("Country") {
                        textfield(model.country) {
                            addClass(FacturoStyles.formField)
                        }
                    }
                }
                
                fieldset("Banking Information") {
                    hbox {
                        spacing = 20.0
                        
                        field("IBAN") {
                            textfield(model.iban) {
                                addClass(FacturoStyles.formField)
                            }
                        }
                        
                        field("SWIFT/BIC") {
                            textfield(model.swift) {
                                addClass(FacturoStyles.formField)
                            }
                        }
                    }
                }
                
                hbox {
                    alignment = Pos.CENTER_RIGHT
                    spacing = 10.0
                    padding = insets(20, 0)
                    
                    button("Save Settings") {
                        addClass(FacturoStyles.actionButton)
                        action {
                            model.commit()
                            // In a real app, we would save to database here
                            information("Settings Saved", "Your company settings have been saved successfully.")
                        }
                    }
                }
            }
        }
    }
}
package com.facturo.views

import com.facturo.controllers.ClientController
import com.facturo.models.Client
import com.facturo.models.ClientModel
import com.facturo.styles.FacturoStyles
import javafx.geometry.Pos
import tornadofx.*

class ClientEditorView : View("Client Editor") {
    private val clientController: ClientController by inject()
    private val client = params["client"] as? Client ?: Client()
    private val model = ClientModel().apply { item = client }
    
    override val root = vbox {
        padding = insets(20)
        spacing = 20.0
        
        label(if (client.id == 0) "Add New Client" else "Edit Client") {
            addClass(FacturoStyles.mainHeader)
        }
        
        scrollpane {
            isFitToWidth = true
            
            form {
                fieldset("Client Information") {
                    field("Company Name") {
                        textfield(model.name) {
                            addClass(FacturoStyles.formField)
                        }.required()
                    }
                    
                    field("VAT Number") {
                        textfield(model.vatNumber) {
                            addClass(FacturoStyles.formField)
                        }
                    }
                    
                    field("Contact Person") {
                        textfield(model.contactPerson) {
                            addClass(FacturoStyles.formField)
                        }
                    }
                }
                
                fieldset("Contact Information") {
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
                
                hbox {
                    alignment = Pos.CENTER_RIGHT
                    spacing = 10.0
                    padding = insets(20, 0)
                    
                    button("Cancel") {
                        action {
                            close()
                        }
                    }
                    
                    button("Save Client") {
                        addClass(FacturoStyles.actionButton)
                        action {
                            model.commit()
                            
                            if (client.id == 0) {
                                clientController.add(client)
                            } else {
                                clientController.update(client)
                            }
                            
                            close()
                        }
                    }
                }
            }
        }
    }
}
package com.facturo.views

import com.facturo.models.InvoiceItemModel
import tornadofx.*

class InvoiceItemEditorFragment : Fragment("Edit Invoice Item") {
    private val model: InvoiceItemModel by param()
    
    override val root = form {
        fieldset {
            field("Description") {
                textfield(model.description).required()
            }
            
            field("Quantity") {
                textfield(model.quantity) {
                    filterInput { it.controlNewText.isDouble() }
                    textProperty().addListener { _, _, newValue ->
                        if (newValue.isNotEmpty()) {
                            model.item.recalculate()
                        }
                    }
                }.required()
            }
            
            field("Unit Price") {
                textfield(model.unitPrice) {
                    filterInput { it.controlNewText.isDouble() }
                    textProperty().addListener { _, _, newValue ->
                        if (newValue.isNotEmpty()) {
                            model.item.recalculate()
                        }
                    }
                }.required()
            }
            
            field("Discount %") {
                textfield(model.discount) {
                    filterInput { it.controlNewText.isDouble() }
                    textProperty().addListener { _, _, newValue ->
                        if (newValue.isNotEmpty()) {
                            model.item.recalculate()
                        }
                    }
                }
            }
            
            field("Total") {
                label {
                    textProperty().bind(model.total.stringBinding { 
                        String.format("%.2f €", it ?: 0.0) 
                    })
                }
            }
        }
        
        buttonbar {
            button("Cancel") {
                action {
                    close()
                }
            }
            
            button("Save") {
                isDefaultButton = true
                action {
                    model.commit()
                    close()
                }
            }
        }
    }
}
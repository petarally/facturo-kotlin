package com.facturo.models

import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*

class InvoiceItem(
    id: Int = 0,
    description: String = "",
    quantity: Double = 1.0,
    unitPrice: Double = 0.0,
    discount: Double = 0.0,
    total: Double = 0.0
) {
    val idProperty = SimpleIntegerProperty(id)
    var id by idProperty
    
    val descriptionProperty = SimpleStringProperty(description)
    var description by descriptionProperty
    
    val quantityProperty = SimpleDoubleProperty(quantity)
    var quantity by quantityProperty
    
    val unitPriceProperty = SimpleDoubleProperty(unitPrice)
    var unitPrice by unitPriceProperty
    
    val discountProperty = SimpleDoubleProperty(discount)
    var discount by discountProperty
    
    val totalProperty = SimpleDoubleProperty(total)
    var total by totalProperty
    
    fun recalculate() {
        total = quantity * unitPrice * (1 - discount / 100)
    }
}

class InvoiceItemModel : ItemViewModel<InvoiceItem>() {
    val id = bind(InvoiceItem::idProperty)
    val description = bind(InvoiceItem::descriptionProperty)
    val quantity = bind(InvoiceItem::quantityProperty)
    val unitPrice = bind(InvoiceItem::unitPriceProperty)
    val discount = bind(InvoiceItem::discountProperty)
    val total = bind(InvoiceItem::totalProperty)
}
package com.facturo.views

import com.facturo.styles.FacturoStyles
import javafx.geometry.Pos
import tornadofx.*

class SettingsView : View("Settings") {
    override val root = vbox {
        spacing = 20.0
        padding = insets(20)
        
        label("Settings") {
            addClass(FacturoStyles.mainHeader)
        }
        
        vbox {
            addClass(FacturoStyles.contentPane)
            
            form {
                fieldset("Application Settings") {
                    field("Language") {
                        combobox<String> {
                            items = listOf("English", "Croatian", "German", "Italian").asObservable()
                            selectionModel.select("English")
                            addClass(FacturoStyles.formField)
                        }
                    }
                    
                    field("Theme") {
                        combobox<String> {
                            items = listOf("Light", "Dark", "System Default").asObservable()
                            selectionModel.select("Light")
                            addClass(FacturoStyles.formField)
                        }
                    }
                    
                    field("Currency") {
                        combobox<String> {
                            items = listOf("EUR (€)", "USD ($)", "GBP (£)", "HRK (kn)").asObservable()
                            selectionModel.select("EUR (€)")
                            addClass(FacturoStyles.formField)
                        }
                    }
                }
                
                fieldset("Invoice Settings") {
                    field("Default Tax Rate %") {
                        textfield("25") {
                            filterInput { it.controlNewText.isDouble() }
                            addClass(FacturoStyles.formField)
                        }
                    }
                    
                    field("Default Payment Terms (days)") {
                        textfield("15") {
                            filterInput { it.controlNewText.isInt() }
                            addClass(FacturoStyles.formField)
                        }
                    }
                    
                    field("Invoice Numbering Format") {
                        textfield("YYYY-NNNN") {
                            addClass(FacturoStyles.formField)
                        }
                    }
                }
                
                fieldset("PDF Export Settings") {
                    field("Include Logo") {
                        checkbox {
                            isSelected = true
                        }
                    }
                    
                    field("Paper Size") {
                        combobox<String> {
                            items = listOf("A4", "Letter", "Legal").asObservable()
                            selectionModel.select("A4")
                            addClass(FacturoStyles.formField)
                        }
                    }
                }
                
                hbox {
                    alignment = Pos.CENTER_RIGHT
                    spacing = 10.0
                    padding = insets(20, 0)
                    
                    button("Reset to Defaults") {
                        action {
                            confirmation("Reset Settings", "Are you sure you want to reset all settings to defaults?") {
                                // Reset code would go here
                            }
                        }
                    }
                    
                    button("Save Settings") {
                        addClass(FacturoStyles.actionButton)
                        action {
                            information("Settings Saved", "Your settings have been saved successfully.")
                        }
                    }
                }
            }
        }
    }
}
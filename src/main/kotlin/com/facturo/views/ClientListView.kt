package com.facturo.views

import com.facturo.controllers.ClientController
import com.facturo.models.Client
import com.facturo.styles.FacturoStyles
import javafx.geometry.Pos
import javafx.scene.control.TableView
import javafx.scene.layout.Priority
import tornadofx.*

class ClientListView : View("Clients") {
    private val clientController: ClientController by inject()
    private var tableView: TableView<Client> by singleAssign()
    
    override val root = vbox {
        spacing = 20.0
        padding = insets(20)
        
        hbox {
            alignment = Pos.CENTER_LEFT
            spacing = 20.0
            
            label("Clients") {
                addClass(FacturoStyles.mainHeader)
            }
            
            region { hgrow = Priority.ALWAYS }
            
            button("Add New Client") {
                addClass(FacturoStyles.actionButton)
                action {
                    find<ClientEditorView>().openWindow()
                    // When the window is closed, refresh the table
                    tableView.refresh()
                }
            }
        }
        
        vbox {
            addClass(FacturoStyles.contentPane)
            vgrow = Priority.ALWAYS
            
            tableView = tableview(clientController.clients) {
                readonlyColumn("Name", Client::name)
                readonlyColumn("Contact", Client::contactPerson)
                readonlyColumn("Email", Client::email)
                readonlyColumn("Phone", Client::phone)
                readonlyColumn("City", Client::city)
                readonlyColumn("Country", Client::country)
                
                contextmenu {
                    item("Edit") {
                        action {
                            val selectedClient = selectedItem
                            if (selectedClient != null) {
                                find<ClientEditorView>(mapOf("client" to selectedClient)).openWindow()
                                tableView.refresh()
                            }
                        }
                    }
                    item("Delete") {
                        action {
                            val selectedClient = selectedItem
                            if (selectedClient != null) {
                                confirmation(
                                    "Delete Client",
                                    "Are you sure you want to delete ${selectedClient.name}?",
                                    actionFn = {
                                        clientController.delete(selectedClient)
                                        tableView.refresh()
                                    }
                                )
                            }
                        }
                    }
                }
                
                vgrow = Priority.ALWAYS
                smartResize()
                
                onDoubleClick {
                    val selectedClient = selectedItem
                    if (selectedClient != null) {
                        find<ClientEditorView>(mapOf("client" to selectedClient)).openWindow()
                    }
                }
            }
        }
    }
}
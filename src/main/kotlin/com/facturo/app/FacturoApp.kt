package com.facturo.app

import com.facturo.styles.FacturoStyles
import com.facturo.views.MainView
import javafx.stage.Stage
import tornadofx.*

class FacturoApp : App(MainView::class, FacturoStyles::class) {
    
    override fun start(stage: Stage) {
        with(stage) {
            minWidth = 1200.0
            minHeight = 800.0
            super.start(this)
        }
        
        // Initialize database
        Database.connect()
    }
}
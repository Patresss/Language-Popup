package com.patres.languagepopup.gui.controller

import com.jfoenix.controls.JFXSnackbar
import com.jfoenix.controls.JFXSnackbar.SnackbarEvent
import com.jfoenix.controls.JFXTabPane
import com.patres.languagepopup.Main
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.layout.StackPane


class MainController {

    @FXML
    var root: StackPane? = null

    @FXML
    var tabPane: JFXTabPane? = null

    @FXML
    lateinit var fichesTab: StackPane

    @FXML
    lateinit var settingsTab: StackPane

    private lateinit var snackBar: JFXSnackbar

    // ================================================================================
    // Configuration methods
    // ================================================================================
    fun initialize() {
        initTabs()
        snackBar = JFXSnackbar(root)
    }


    private fun initTabs() {
        initFicheTab()
        initSettingsTab()
    }

    private fun initFicheTab() {
        val loader = FXMLLoader()
        loader.location = javaClass.getResource("/fxml/FichesTab.fxml")
        loader.resources = Main.bundle
        fichesTab.children.add(loader.load<StackPane>())
        loader.getController<FichesController>().mainController = this
    }

    private fun initSettingsTab() {
        val loader = FXMLLoader()
        loader.location = javaClass.getResource("/fxml/SettingsTab.fxml")
        loader.resources = Main.bundle
        settingsTab.children.add(loader.load<StackPane>())
        loader.getController<SettingsController>().mainController = this
    }

    fun setMessageToSnackBar(message: String) {
        snackBar.fireEvent(
                SnackbarEvent(message, "X",
                        2000,
                        false,
                        EventHandler { snackBar.close() }))
    }

}

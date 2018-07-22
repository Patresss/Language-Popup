package com.patres.languagepopup.gui.controller

import com.jfoenix.controls.JFXSlider
import com.jfoenix.controls.JFXTextField
import com.patres.languagepopup.Main
import com.patres.languagepopup.model.Settings
import com.patres.languagepopup.util.setIntegerFilter
import javafx.fxml.FXML
import java.awt.Point


class SettingsController {

    @FXML
    private lateinit var minutesTextField: JFXTextField

    @FXML
    private lateinit var positionXTextField: JFXTextField

    @FXML
    private lateinit var positionYTextField: JFXTextField

    @FXML
    private lateinit var fontSizeTextField: JFXTextField

    @FXML
    private lateinit var opacitySlider: JFXSlider

    var mainController: MainController? = null

    fun initialize() {
        reload()
        minutesTextField.setIntegerFilter()
        positionXTextField.setIntegerFilter()
        positionYTextField.setIntegerFilter()
        fontSizeTextField.setIntegerFilter()
    }

    @FXML
    fun reload() {
        minutesTextField.text = Settings.minutestWaitPopup.toString()
        positionXTextField.text = Settings.positionOfPopup.x.toString()
        positionYTextField.text = Settings.positionOfPopup.y.toString()
        fontSizeTextField.text = Settings.popupFontSize.toString()
        opacitySlider.value = Settings.opacityBackground.toDouble()

        mainController?.setMessageToSnackBar(Main.bundle.getString("action.reloaded"))
    }

    @FXML
    fun save() {
        val minutes = minutesTextField.text
        val minutesInt = minutes?.toIntOrNull()
        if (minutesInt == null) {
            mainController?.setMessageToSnackBar(Main.bundle.getString("error.mustBeNumber"))
            return
        }

        val positionX = positionXTextField.text
        val positionXInt = positionX?.toIntOrNull()
        if (positionXInt == null) {
            mainController?.setMessageToSnackBar(Main.bundle.getString("error.mustBeNumber"))
            return
        }

        val positionY = positionYTextField.text
        val positionYInt = positionY?.toIntOrNull()
        if (positionYInt == null) {
            mainController?.setMessageToSnackBar(Main.bundle.getString("error.mustBeNumber"))
            return
        }

        val fontSize = fontSizeTextField.text
        val fontSizeInt = fontSize?.toIntOrNull()
        if (fontSizeInt == null) {
            mainController?.setMessageToSnackBar(Main.bundle.getString("error.mustBeNumber"))
            return
        }

        val opacity = opacitySlider.value

        Settings.minutestWaitPopup = minutesInt
        Settings.positionOfPopup = Point(positionXInt, positionYInt)
        Settings.opacityBackground = opacity.toInt()
        Settings.popupFontSize = fontSizeInt
        Settings.save()
        mainController?.setMessageToSnackBar(Main.bundle.getString("action.saved"))
    }

}

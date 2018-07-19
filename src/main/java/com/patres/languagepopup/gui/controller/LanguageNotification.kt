package com.patres.languagepopup.gui.controller

import com.patres.languagepopup.gui.LanguageNotificationStageManager
import com.patres.languagepopup.model.Fiche
import com.patres.languagepopup.model.Settings
import javafx.fxml.FXML
import javafx.scene.control.Label
import javafx.scene.text.Font

class LanguageNotification {

    companion object {
        const val VERY_SMILE_LEVEL = "1"
        const val SMILE_LEVEL = "2"
        const val NEUTRAL_LEVEL = "3"
        const val SAD_LEVEL = "4"
        const val VERY_SAD_LEVEL = "5"
    }

    private var fiche: Fiche? = null

    var languageNotificationHandler: LanguageNotificationStageManager? = null

    @FXML
    private lateinit var englishLabel: Label

    @FXML
    private lateinit var polishLabel: Label

    fun initialize() {
        val fontSize = Settings.popupFontSize.toDouble()
        englishLabel.font = Font(fontSize)
        polishLabel.font = Font(fontSize)
    }


    @FXML
    fun saveVerySmileLevel() {
        languageNotificationHandler?.saveFicheLevel(VERY_SMILE_LEVEL)
    }

    @FXML
    fun saveSmileLevel() {
        languageNotificationHandler?.saveFicheLevel(SMILE_LEVEL)
    }

    @FXML
    fun saveNeutralLevel() {
        languageNotificationHandler?.saveFicheLevel(NEUTRAL_LEVEL)
    }

    @FXML
    fun saveSadLevel() {
        languageNotificationHandler?.saveFicheLevel(SAD_LEVEL)
    }

    @FXML
    fun saveVerySadLevel() {
        languageNotificationHandler?.saveFicheLevel(VERY_SAD_LEVEL)
    }


    fun loadFiche(fiche: Fiche) {
        this.fiche = fiche
        englishLabel.text = fiche.englishTextProperty.get()
        polishLabel.text = fiche.polishTextProperty.get()
    }
}
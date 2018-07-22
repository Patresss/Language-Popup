package com.patres.languagepopup.model

import com.patres.languagepopup.database.SettingsTextDatabaseConnector
import java.awt.Point
import java.util.*

object Settings {

    private const val KEY_MINUTES_WAIT_POPUP = "minutes-wait-popup"
    private const val KEY_POPUP_POSITION_X = "popup-position-x"
    private const val KEY_POPUP_POSITION_Y = "popup-position-y"
    private const val KEY_OPACITY_BACKGROUND = "opacity-background"
    private const val KEY_POPUP_FONT_SIZE = "popup-font-size"
    private const val DEFAULT_MINUTES_WAIT_POPUP = 30
    private const val DEFAULT_POPUP_POSITION_X = 50
    private const val DEFAULT_POPUP_POSITION_Y = 50
    private const val DEFAULT_OPACITY_BACKGROUND = 90
    private const val DEFAULT_POPUP_FONT_SIZE = 11

    var minutestWaitPopup: Int = DEFAULT_MINUTES_WAIT_POPUP
    var positionOfPopup: Point = Point(DEFAULT_POPUP_POSITION_X, DEFAULT_POPUP_POSITION_Y)
    var opacityBackground: Int = DEFAULT_OPACITY_BACKGROUND
    var popupFontSize: Int = DEFAULT_POPUP_FONT_SIZE

    init {
        load()
    }

    fun save() {
        val properties = loadPropertiesFromSettings()
        SettingsTextDatabaseConnector.saveSettings(properties)
    }

    private fun load() {
        val properties = SettingsTextDatabaseConnector.loadSettings()
        loadSettingsFromProperties(properties)
        SettingsTextDatabaseConnector.saveSettings(properties)
    }

    private fun loadSettingsFromProperties(properties: Properties) {
        val minutesFromProperties = properties[KEY_MINUTES_WAIT_POPUP] as String?
        minutestWaitPopup = minutesFromProperties?.toIntOrNull() ?: DEFAULT_MINUTES_WAIT_POPUP

        val positionXFromProperties = properties[KEY_POPUP_POSITION_X] as String?
        val positionX = positionXFromProperties?.toIntOrNull() ?: DEFAULT_POPUP_POSITION_X

        val positionYFromProperties = properties[KEY_POPUP_POSITION_Y] as String?
        val positionY = positionYFromProperties?.toIntOrNull() ?: DEFAULT_POPUP_POSITION_Y
        positionOfPopup = Point(positionX, positionY)

        val popupFontSizeFromProperties = properties[KEY_POPUP_FONT_SIZE] as String?
        popupFontSize = popupFontSizeFromProperties?.toIntOrNull() ?: DEFAULT_POPUP_FONT_SIZE

        val opacityBackgroundFromProperties = properties[KEY_OPACITY_BACKGROUND] as String?
        opacityBackground = opacityBackgroundFromProperties?.toIntOrNull() ?: DEFAULT_OPACITY_BACKGROUND
    }

    private fun loadPropertiesFromSettings(): Properties {
        val properties = Properties()
        properties[KEY_MINUTES_WAIT_POPUP] = minutestWaitPopup.toString()
        properties[KEY_POPUP_POSITION_X] = positionOfPopup.x.toString()
        properties[KEY_POPUP_POSITION_Y] = positionOfPopup.y.toString()
        properties[KEY_POPUP_FONT_SIZE] = popupFontSize.toString()
        properties[KEY_OPACITY_BACKGROUND] = opacityBackground.toString()
        return properties
    }


    fun createDefaultSettings(): Properties {
        val properties = Properties()
        properties[KEY_MINUTES_WAIT_POPUP] = DEFAULT_MINUTES_WAIT_POPUP.toString()
        properties[KEY_POPUP_POSITION_X] = DEFAULT_POPUP_POSITION_X.toString()
        properties[KEY_POPUP_POSITION_Y] = DEFAULT_POPUP_POSITION_Y.toString()
        properties[KEY_POPUP_FONT_SIZE] = DEFAULT_POPUP_FONT_SIZE.toString()
        properties[KEY_OPACITY_BACKGROUND] = DEFAULT_OPACITY_BACKGROUND.toString()
        return properties
    }
}
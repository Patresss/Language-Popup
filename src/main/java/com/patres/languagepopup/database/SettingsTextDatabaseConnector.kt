package com.patres.languagepopup.database

import com.patres.languagepopup.model.Settings
import org.slf4j.LoggerFactory
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.*


object SettingsTextDatabaseConnector {

    private const val PROPERTIES_FILE = "/settings.properties"

    fun saveSettings(properties: Properties) {
        val file = getFile()
        file.delete()
        file.createNewFile()
        val fileOutputStream = FileOutputStream(file)
        properties.store(fileOutputStream, "Language Popup Settings")
        fileOutputStream.close()
    }

    fun loadSettings(): Properties {
        val properties = Properties()
        val file = getFile()
        createSettingsIfNotExists(file)
        val inputStream = FileInputStream(file)
        properties.load(inputStream)
        inputStream.close()
        return properties
    }

    private fun createSettingsIfNotExists(file: File) {
        if (!file.exists()) {
            val defaultSettings = Settings.createDefaultSettings()
            file.createNewFile()
            val fileOutputStream = FileOutputStream(file)
            defaultSettings.store(fileOutputStream, "Language Popup Settings")
            fileOutputStream.close()
        }
    }

    private fun getFile(): File {
        val path = File(SettingsTextDatabaseConnector::class.java.protectionDomain.codeSource.location.toURI()).path
        val jarFile = File(path)
        val jarDir = jarFile.parentFile.path
        return File(jarDir, PROPERTIES_FILE)
    }

}
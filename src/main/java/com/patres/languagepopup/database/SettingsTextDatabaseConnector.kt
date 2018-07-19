package com.patres.languagepopup.database

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
        val inputStream = FileInputStream(file)
        properties.load(inputStream)
        inputStream.close()
        return properties
    }

    private fun getFile(): File {
        val path = File(SettingsTextDatabaseConnector::class.java.protectionDomain.codeSource.location.toURI()).path
        return File(path, PROPERTIES_FILE)
    }

}
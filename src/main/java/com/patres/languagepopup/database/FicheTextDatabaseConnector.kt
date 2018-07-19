package com.patres.languagepopup.database

import com.patres.languagepopup.excpetion.ApplicationException
import com.patres.languagepopup.model.Fiche
import java.io.File


object FicheTextDatabaseConnector {

    private const val LANGUAGE_FILE = "fichses.txt"
    private const val SPLIT_CHAR = ';'

    fun loadFiches(): List<Fiche> {
        val path = File(FicheTextDatabaseConnector::class.java.protectionDomain.codeSource.location.toURI()).path
        val file = File(path, LANGUAGE_FILE)
        file.createNewFile()

        val lines = file.readLines()
        return lines.map { readFiche(it) }
    }

    fun saveFiches(fiches: List<Fiche>): Boolean {
        var saved = false
        val path = File(FicheTextDatabaseConnector::class.java.protectionDomain.codeSource.location.toURI()).path
        val file = File(path, LANGUAGE_FILE)
        file.createNewFile()

        file.printWriter().use { out ->
            fiches.forEach {
                out.println("${it.englishTextProperty.get()};${it.polishTextProperty.get()};${it.levelOfEducationProperty.get()}")
            }
            saved = true
        }
        return saved
    }

    private fun readFiche(line: String): Fiche {
        val spitedValues = line.split(SPLIT_CHAR)
        if (spitedValues.size != 3) {
            throw ApplicationException("Somethong wrong with line: $line")
        } else {
            return Fiche(spitedValues[0], spitedValues[1], spitedValues[2])
        }
    }


}
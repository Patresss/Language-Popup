package com.patres.languagepopup.model

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject
import javafx.beans.property.SimpleStringProperty

class Fiche(
        englishText: String,
        polishText: String,
        levelOfEducation: String
) : RecursiveTreeObject<Fiche>() {

    var englishTextProperty = SimpleStringProperty(englishText)


    var polishTextProperty = SimpleStringProperty(polishText)


    var levelOfEducationProperty = SimpleStringProperty(levelOfEducation)


}
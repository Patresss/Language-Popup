package com.patres.languagepopup.util

import javafx.scene.control.TextField


fun TextField.setIntegerFilter() {
    textProperty().addListener { _, _, newValue ->
        if (!newValue.matches(Regex("\\d*"))) {
            text = newValue.replace(Regex("[^\\d]"), "")
        }
    }
}

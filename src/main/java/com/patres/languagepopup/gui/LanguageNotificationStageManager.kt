package com.patres.languagepopup.gui

import com.patres.languagepopup.Main
import com.patres.languagepopup.database.FicheTextDatabaseConnector
import com.patres.languagepopup.gui.controller.LanguageNotification
import com.patres.languagepopup.model.Fiche
import com.patres.languagepopup.model.Settings
import com.patres.languagepopup.util.RandomGenerator
import javafx.animation.KeyFrame
import javafx.animation.Timeline
import javafx.beans.property.SimpleBooleanProperty
import javafx.event.EventHandler
import javafx.fxml.FXMLLoader
import javafx.scene.Cursor
import javafx.scene.Node
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import javafx.stage.Stage
import javafx.stage.StageStyle
import javafx.util.Duration
import org.controlsfx.control.NotificationPane
import java.awt.Point


class LanguageNotificationStageManager {

    private val stage = Stage()
    private val layout = StackPane()
    private val fiches = FicheTextDatabaseConnector.loadFiches()
    private val fiche = generateFiche()

    init {
        setupStage()
        stage.show()
        makeDraggable(stage, layout)
        NotificationPane()
    }

    private fun generateFiche(): Fiche {
        return RandomGenerator(fiches).generateRandomFiche()
    }

    fun saveFicheLevel(level: String) {
        fiche.levelOfEducationProperty.set(level)
        FicheTextDatabaseConnector.saveFiches(fiches)
        stage.close()
    }

    private fun setupStage() {
        val content = setupController()

        stage.x = Settings.positionOfPopup.x.toDouble()
        stage.y = Settings.positionOfPopup.y.toDouble()
        val opacity = Settings.opacityBackground / 100.0

        layout.style = "-fx-background-color: rgba(255, 255, 255, $opacity)"
        layout.children.setAll(content)
        stage.scene = Scene(layout, Color.TRANSPARENT)
        stage.scene.setOnMouseClicked { event -> if (event.clickCount == 2) stage.close() }

        setStyle(stage.scene)
        stage.initStyle(StageStyle.TRANSPARENT)
        stage.isAlwaysOnTop = true
    }

    private fun setupController(): Parent {
        val loader = FXMLLoader()
        loader.location = LanguageNotificationStageManager::class.java.getResource("/fxml/LanguageNotification.fxml")
        val content = loader.load<Any>() as Parent
        val controller = loader.getController<LanguageNotification>()
        controller.loadFiche(fiche)
        controller.languageNotificationHandler = this
        return content
    }

    private fun setStyle(scene: Scene) {
        scene.stylesheets.add(Main::class.java.getResource("/css/style_day.css").toExternalForm())
    }

    private fun makeDraggable(stage: Stage, byNode: Node) {
        val dragDelta = Point()
        byNode.setOnMousePressed { mouseEvent ->
            dragDelta.x = (stage.x - mouseEvent.screenX).toInt()
            dragDelta.y = (stage.y - mouseEvent.screenY).toInt()

            byNode.cursor = Cursor.MOVE
        }
        val inDrag = SimpleBooleanProperty(false)

        byNode.setOnMouseReleased {
            byNode.cursor = Cursor.HAND
            if (inDrag.get()) {
                stage.hide()
                val pause = Timeline(KeyFrame(Duration.millis(50.0), EventHandler {
                    stage.show()
                }))
                pause.play()
            }

            inDrag.set(false)
        }
        byNode.setOnMouseDragged { mouseEvent ->
            stage.x = mouseEvent.screenX + dragDelta.x
            stage.y = mouseEvent.screenY + dragDelta.y


            Settings.positionOfPopup.x = stage.x.toInt()
            Settings.positionOfPopup.y = stage.y.toInt()
            Settings.save()

            inDrag.set(true)
        }
        byNode.setOnMouseEntered { mouseEvent ->
            if (!mouseEvent.isPrimaryButtonDown) {
                byNode.cursor = Cursor.HAND
            }
        }
        byNode.setOnMouseExited { mouseEvent ->
            if (!mouseEvent.isPrimaryButtonDown) {
                byNode.cursor = Cursor.DEFAULT
            }
        }
    }


}
package com.patres.languagepopup

import com.jfoenix.controls.JFXDecorator
import com.patres.languagepopup.gui.controller.MainController
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.scene.layout.Pane
import javafx.stage.Stage
import org.slf4j.LoggerFactory
import java.io.IOException
import java.util.*


class Main : Application() {

    companion object {
        val logger = LoggerFactory.getLogger(Main::class.java)!!
        const val tittle = "Language Popup"
        const val sceneWidth = 475
        const val sceneHeight = 700
        var bundle = ResourceBundle.getBundle("language/Bundle", Locale("pl"))!!
        var mainStage: Stage = Stage()
        lateinit var mainController: MainController

        @JvmStatic
        fun main(args: Array<String>) {
            launch(Main::class.java)
        }
    }

    override fun start(primaryStage: Stage) {
        try {
            mainStage = primaryStage
            mainStage.title = tittle
            mainStage.icons.add(Image("/image/icon.png"))
            mainStage.scene = createScene(loadMainPane())
            mainStage.minWidth = sceneWidth.toDouble()
            mainStage.minHeight = sceneHeight.toDouble()
            mainStage.show()
            LanguageNotificationManager()
        } catch (e: IOException) {
            logger.error("Error in start method - I/O Exception", e)
        }
    }

    @Throws(IOException::class)
    private fun loadMainPane(): Pane {
        val loader = FXMLLoader()
        loader.location = Main::class.java.getResource("/fxml/Main.fxml")
        loader.resources = bundle

        val mainPane = loader.load<Pane>()
        mainController = loader.getController<MainController>()
        return mainPane
    }

    private fun createScene(mainPane: Pane): Scene {
        val decorator = JFXDecorator(mainStage, mainPane, false, true, true)
        val scene = Scene(decorator, sceneWidth.toDouble(), sceneHeight.toDouble())
        setStyle(scene)
        return scene
    }

    private fun setStyle(scene: Scene) {
        scene.stylesheets.add(Main::class.java.getResource("/css/style_day.css").toExternalForm())
    }


}

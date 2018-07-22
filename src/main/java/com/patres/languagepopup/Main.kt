package com.patres.languagepopup

import com.jfoenix.controls.JFXDecorator
import com.patres.languagepopup.gui.controller.MainController
import javafx.application.Application
import javafx.application.Platform
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.scene.layout.Pane
import javafx.stage.Stage
import org.slf4j.LoggerFactory
import java.awt.*
import java.awt.Toolkit.getDefaultToolkit
import java.io.IOException
import java.util.*
import javax.imageio.ImageIO


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


            if (!SystemTray.isSupported()) {
                logger.error("SystemTray is not supported")
                Platform.exit()
            } else {
                createTryIcon()
            }
            Platform.setImplicitExit(false)
            LanguageNotificationManager()
        } catch (e: IOException) {
            logger.error("Error in start method - I/O Exception", e)
        }
    }

    private fun createTryIcon() {
        val popup = PopupMenu()
        val url = Main::class.java.getResource("/image/icon-small.png")
        val image = ImageIO.read(url)

        val trayIcon = TrayIcon(image)
        trayIcon.addActionListener { Platform.runLater { this.showStage() } }

        val tray = SystemTray.getSystemTray()

        val openItem = MenuItem(bundle.getString("button.open"))
        val exitItem = MenuItem(bundle.getString("button.exit"))

        openItem.addActionListener { Platform.runLater { this.showStage() } }
        exitItem.addActionListener {
            Platform.exit()
            tray.remove(trayIcon)
        }
        popup.add(openItem)
        popup.addSeparator()
        popup.add(exitItem)

        trayIcon.popupMenu = popup

        try {
            tray.add(trayIcon)
        } catch (e: AWTException) {
            logger.error("TrayIcon could not be added.")
        }
    }

    private fun showStage() {
        mainStage.show()
        mainStage.toFront()
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

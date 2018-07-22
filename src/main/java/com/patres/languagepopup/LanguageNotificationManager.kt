package com.patres.languagepopup

import com.patres.languagepopup.gui.LanguageNotificationStageManager
import com.patres.languagepopup.model.Settings
import javafx.application.Platform

class LanguageNotificationManager {

    private fun getMillis() = 1000 * 60 * Settings.minutestWaitPopup.toLong()
    private var thread: Thread? = null
    private var languageNotificationStageManager: LanguageNotificationStageManager? = null

    init {
        loadTask()
    }

    private fun loadTask() {
        thread?.interrupt()
        thread = Thread(Runnable {
            try {
                while (true) {
                    Thread.sleep(getMillis())
                    Platform.runLater {
                        if (languageNotificationStageManager == null || languageNotificationStageManager?.stageIsShowing() == false) {
                            languageNotificationStageManager = LanguageNotificationStageManager()
                        }
                    }
                }
            } catch (e: InterruptedException) {
                Thread.currentThread().interrupt()
            }
        })
        thread?.start()
    }

}
package com.patres.languagepopup.gui.controller

import com.jfoenix.controls.*
import com.jfoenix.controls.cells.editors.TextFieldEditorBuilder
import com.jfoenix.controls.cells.editors.base.GenericEditableTreeTableCell
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject
import com.patres.languagepopup.Main
import com.patres.languagepopup.database.FicheTextDatabaseConnector
import com.patres.languagepopup.model.Fiche
import javafx.beans.binding.Bindings
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.fxml.FXML
import javafx.scene.control.Label
import javafx.scene.control.TreeItem
import javafx.scene.control.TreeTableColumn
import javafx.scene.layout.StackPane
import javafx.util.Callback
import java.util.concurrent.Callable


class FichesController {

    companion object {
        val DEFAULT_PREF_SIZE = 150.0
    }

    @FXML
    private lateinit var ficheStackPane: StackPane

    @FXML
    private lateinit var searchTextField: JFXTextField

    @FXML
    private lateinit var searchSize: Label

    @FXML
    private lateinit var removeButton: JFXButton

    @FXML
    private lateinit var nodesList: JFXNodesList

    private lateinit var fichesTable: JFXTreeTableView<Fiche>


    private val englishTextColumn: JFXTreeTableColumn<Fiche, String> = JFXTreeTableColumn(Main.bundle.getString("english"))

    var mainController: MainController? = null

    private val polishTextColumn: JFXTreeTableColumn<Fiche, String> = JFXTreeTableColumn(Main.bundle.getString("polish"))

    private val levelOfEducationColumn: JFXTreeTableColumn<Fiche, String> = JFXTreeTableColumn(Main.bundle.getString("levelOfEducation"))

    fun initialize() {
        initEnglishTextColumn()
        initPolishTextColumn()
        initLevelOfEducationColumn()
        initTree()

        setupRemoveButton()
    }

    @FXML
    fun addNewFiche() {
        val fiche = Fiche(
                englishText = Main.bundle.getString("fiche.englishText.placeholder"),
                polishText = Main.bundle.getString("fiche.polishText.placeholder"),
                levelOfEducation = Main.bundle.getString("fiche.levelOfEducation.placeholder")
        )
        val ficheTreeElement = TreeItem(fiche)
        fichesTable.root.children.add(ficheTreeElement)
        fichesTable.selectionModel.select(ficheTreeElement)
        mainController?.setMessageToSnackBar(Main.bundle.getString("action.added"))
    }

    @FXML
    fun removeFiche() {
        var removed = false
        fichesTable.selectionModel.selectedItem?.let { ficheTreeItem ->
            fichesTable.root.children.remove(ficheTreeItem)
            removed = true
        }
        if (removed) {
            mainController?.setMessageToSnackBar(Main.bundle.getString("action.removed"))
        } else {
            mainController?.setMessageToSnackBar(Main.bundle.getString("error.unknown"))
        }
    }

    @FXML
    fun reload() {
        fichesTable.root = calculateRoot()
        mainController?.setMessageToSnackBar(Main.bundle.getString("action.reloaded"))
    }

    @FXML
    fun save() {
        val fiches = fichesTable.root.children.map { it.valueProperty().get() }
        val saved = FicheTextDatabaseConnector.saveFiches(fiches)
        if (saved) {
            mainController?.setMessageToSnackBar(Main.bundle.getString("action.saved"))
        } else {
            mainController?.setMessageToSnackBar(Main.bundle.getString("error.unknown"))
        }
    }

    private fun setupRemoveButton() {
        removeButton.isDisable = true
        fichesTable.selectionModel.selectedItemProperty().addListener { _, _, newSelection ->
            removeButton.isDisable = newSelection == null
        }
    }

    private fun initTree() {
        val root: TreeItem<Fiche> = calculateRoot()
        fichesTable = JFXTreeTableView<Fiche>(root)
        fichesTable.isShowRoot = false
        fichesTable.isEditable = true
        fichesTable.columns.setAll(englishTextColumn, polishTextColumn, levelOfEducationColumn)

        ficheStackPane.children.add(fichesTable)
        searchTextField.textProperty().addListener { _, _, newVal ->
            fichesTable.setPredicate { fiche ->
                (fiche.value.englishTextProperty.get().contains(newVal)
                        || fiche.value.polishTextProperty.get().contains(newVal)
                        || fiche.value.levelOfEducationProperty.get().contains(newVal))
            }
        }

        searchSize.textProperty().bind(Bindings.createStringBinding(Callable { "(${fichesTable.currentItemsCount})" }, fichesTable.currentItemsCountProperty()))
    }

    private fun calculateRoot(): RecursiveTreeItem<Fiche> {
        val fichesList = FicheTextDatabaseConnector.loadFiches()
        val fiches: ObservableList<Fiche> = FXCollections.observableArrayList(fichesList)
        return RecursiveTreeItem<Fiche>(fiches, Callback<RecursiveTreeObject<Fiche>, ObservableList<Fiche>> { it.children })
    }

    private fun initEnglishTextColumn() {
        englishTextColumn.prefWidth = DEFAULT_PREF_SIZE
        englishTextColumn.setCellValueFactory { param ->
            if (englishTextColumn.validateValue(param)) {
                param.value?.value?.englishTextProperty
            } else
                englishTextColumn.getComputedValue(param)
        }
        englishTextColumn.setCellFactory { GenericEditableTreeTableCell<Fiche, String>(TextFieldEditorBuilder()) }
        englishTextColumn.setOnEditCommit { treeTableColumn ->
            treeTableColumn.treeTableView.getTreeItem(treeTableColumn.treeTablePosition.row).value.englishTextProperty.set(treeTableColumn.newValue)
        }
    }

    private fun initPolishTextColumn() {
        polishTextColumn.prefWidth = DEFAULT_PREF_SIZE
        polishTextColumn.setCellValueFactory { param ->
            if (polishTextColumn.validateValue(param))
                param.value?.value?.polishTextProperty
            else
                polishTextColumn.getComputedValue(param)
        }
        polishTextColumn.setCellFactory { GenericEditableTreeTableCell<Fiche, String>(TextFieldEditorBuilder()) }
        polishTextColumn.setOnEditCommit { treeTableColumn: TreeTableColumn.CellEditEvent<Fiche, String> ->
            treeTableColumn.treeTableView.getTreeItem(treeTableColumn.treeTablePosition.row).value.polishTextProperty.set(treeTableColumn.newValue)
        }
    }

    private fun initLevelOfEducationColumn() {
        levelOfEducationColumn.prefWidth = DEFAULT_PREF_SIZE
        levelOfEducationColumn.setCellValueFactory { param ->
            if (levelOfEducationColumn.validateValue(param))
                param.value?.value?.levelOfEducationProperty
            else
                levelOfEducationColumn.getComputedValue(param)
        }
        levelOfEducationColumn.setCellFactory { GenericEditableTreeTableCell<Fiche, String>(TextFieldEditorBuilder()) }
        levelOfEducationColumn.setOnEditCommit { treeTableColumn ->
            treeTableColumn.treeTableView.getTreeItem(treeTableColumn.treeTablePosition.row).value.levelOfEducationProperty.set(treeTableColumn.newValue)
        }
    }


}
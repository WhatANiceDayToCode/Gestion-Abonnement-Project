package JavaFX.Controller.Abonnement;

import JavaFX.Application;
import JavaFX.Controller.Revue.RevueHolder;
import dao.AbonnementDAO;
import dao.DAOFactory;
import dao.Persistance;
import dao.RevueDAO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import metier.Abonnement;
import metier.Revue;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class MenuGeneralAbonnementController implements Initializable, ChangeListener<Abonnement>
{
    AbonnementDAO abonnementDAO = DAOFactory.getDAOFactory(Persistance.ListeMemoire).getAbonnementDAO();

    @FXML
    private Button btnAjouter;

    @FXML
    private Button btnModifier;

    @FXML
    private Button btnSupprimer;

    @FXML
    private TableColumn<Abonnement, LocalDate> colDateDebut;

    @FXML
    private TableColumn<Abonnement, LocalDate> colDateFin;

    @FXML
    private TableColumn<Abonnement, Integer> colId;

    @FXML
    private TableColumn<Abonnement, Integer> colIdClient;

    @FXML
    private TableColumn<Abonnement, Integer> colIdRevue;

    @FXML
    private TableView<Abonnement> tableViewAbonnement;

    @FXML
    void btnAjouterClick(ActionEvent event) throws IOException
    {
        //Charger la page que l'on veux afficher
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("Vue/Abonnement/creerAbonnement.fxml"));
        //Creer une Scene contenant cette page
        Scene scene = new Scene(fxmlLoader.load(), 600, 450);
        //Recuperer la Stage de l'ancienne page
        Stage stage = (Stage) tableViewAbonnement.getScene().getWindow();
        //Afficher la nouvelle Scene dans l'ancienne Stage
        stage.setScene(scene);
    }

    @FXML
    void btnModifierClick(ActionEvent event) throws IOException
    {
        Abonnement abonnementAModifier = this.tableViewAbonnement.getSelectionModel().getSelectedItem();
        sendData(abonnementAModifier);

        //Charger la page que l'on veux afficher
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("Vue/Abonnement/modifierAbonnement.fxml"));
        //Creer une Scene contenant cette page
        Scene scene = new Scene(fxmlLoader.load(), 600, 450);
        //Recuperer la Stage de l'ancienne page
        Stage stage = (Stage) tableViewAbonnement.getScene().getWindow();
        //Afficher la nouvelle Scene dans l'ancienne Stage
        stage.setScene(scene);
    }

    @FXML
    void btnSupprimerClick(ActionEvent event) throws SQLException
    {
        Abonnement abonnementASupprimer = this.tableViewAbonnement.getSelectionModel().getSelectedItem();
        /*
        Supprimer l'abonnement de la DAO utilisé
        */

        //code test
        abonnementDAO.delete(abonnementASupprimer);

        //Reset la liste
        genererListeRevue();
    }

    @Override
    public void changed(ObservableValue<? extends Abonnement> observableValue, Abonnement oldValue, Abonnement newValue)
    {
        /*
        Si l'item selectionné n'est pas nul, ca veux dire qu'une ligne est select
         */
        this.btnSupprimer.setVisible(!(newValue == null));
        this.btnModifier.setVisible(!(newValue == null));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDateDebut.setCellValueFactory(new PropertyValueFactory<>("dateDebut"));
        colDateFin.setCellValueFactory(new PropertyValueFactory<>("dateFin"));
        colIdClient.setCellValueFactory(new PropertyValueFactory<>("idClient"));
        colIdRevue.setCellValueFactory(new PropertyValueFactory<>("idRevue"));

        genererListeRevue();

        //Appliquer le listener sur la selection et desactiver les boutons
        this.tableViewAbonnement.getSelectionModel().selectedItemProperty().addListener(this);
        this.btnSupprimer.setVisible(false);
        this.btnModifier.setVisible(false);
    }

    public void genererListeRevue()
    {
        //Permet de supprimer tout les elements afficher dans le tableau
        //Pour ensuite re importer uniquement ceux dans la base
        this.tableViewAbonnement.getItems().clear();
        try
        {
            //A modifier par une variable DAO
            this.tableViewAbonnement.getItems().addAll(abonnementDAO.findAll());
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    private void sendData(Abonnement abonnement)
    {
        AbonnementHolder abonnementHolder = AbonnementHolder.getInstance();
        abonnementHolder.setAbonnement(abonnement);
    }
}
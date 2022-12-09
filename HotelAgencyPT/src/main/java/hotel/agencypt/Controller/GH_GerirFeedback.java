package hotel.agencypt.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * Classe pública do controlador GH_GerirFeedback.fxml
 */
public class GH_GerirFeedback {

    /**
     * Volta atrás para a View GestorHotel.fxml
     *
     * @param actionEvent
     */
    @FXML
    public void voltarAtras(ActionEvent actionEvent) {
        try {
            Singleton.open("GestorHotel", "Hotel >> Gestor de Hotel");
        } catch (Exception e) {
            System.out.println("Erro ao voltar atrás.");
        }
    }
}

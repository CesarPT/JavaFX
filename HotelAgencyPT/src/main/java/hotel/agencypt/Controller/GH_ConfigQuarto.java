package hotel.agencypt.Controller;

import Classes.DAO.QuartoDAO;
import Classes.Quarto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Classe pública do controlador GH_ConfigQuarto.fxml
 */
public class GH_ConfigQuarto implements Initializable {
    @FXML
    private ComboBox<String> comboBoxPisoID;
    @FXML
    private ComboBox<String> comboBoxQuartoID;
    @FXML
    private Button verificarQuartoID;
    @FXML
    private Button verificarPrecoID;
    @FXML
    private Button verDescricaoID;
    @FXML
    private Button verificarDescricao;
    @FXML
    private TextArea textPreco;
    @FXML
    private TextArea textAlterarPreco;
    QuartoDAO qDAO = new QuartoDAO();
    List<Quarto> arrayQuartos = new ArrayList<>();
    List<Quarto> arrayPreco = new ArrayList<>();

    /**
     * Insere valores nas listviews
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     *                  the root object was not localized.
     */
    public void initialize(URL location, ResourceBundle resources) {
        //Limpar tudo e inserir valores na combobox
        comboBoxPisoID.getSelectionModel().clearSelection();
        comboBoxPisoID.getItems().clear();

        comboBoxPisoID.getItems().addAll(
                "Piso 1",
                "Piso 2"
        );
    }

    /**
     * Verifica se selecionou o piso, se selecionou
     * limpa e coloca os quartos na ComboBox
     * se não, emite um aviso
     */
    public void verificarPiso() {
        if (Objects.equals(comboBoxPisoID.getSelectionModel().getSelectedItem(), "Piso 1")) {
            Controller.getInstance().setPiso(1);
            //Limpar combobox
            comboBoxQuartoID.getSelectionModel().clearSelection();
            comboBoxQuartoID.getItems().clear();
            //Pesquisar e Adicionar da base de dados para a combobox
            arrayQuartos = qDAO.findQuarto();
            for (Quarto q : arrayQuartos) {
                comboBoxQuartoID.getItems().add(
                        q.getDescricao()
                );
            }
            //Ativar combobox e botão
            comboBoxQuartoID.setDisable(false);
            verificarQuartoID.setDisable(false);
        } else if (Objects.equals(comboBoxPisoID.getSelectionModel().getSelectedItem(), "Piso 2")) {
            Controller.getInstance().setPiso(2);
            //Limpar combobox
            comboBoxQuartoID.getSelectionModel().clearSelection();
            comboBoxQuartoID.getItems().clear();
            //Pesquisar e Adicionar da base de dados para a combobox
            arrayQuartos = qDAO.findQuarto();
            for (Quarto q : arrayQuartos) {
                comboBoxQuartoID.getItems().add(
                        q.getDescricao()
                );
            }
            //Ativar combobox e botão
            comboBoxQuartoID.setDisable(false);
            verificarQuartoID.setDisable(false);

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aviso");
            alert.setHeaderText("Sem seleção");
            alert.setContentText("Selecione primeiro um piso.");
            alert.showAndWait();
        }
    }

    public void atualizar() {
        //Envia para o controlador a seleção de quarto
        String quartoEscolhido = comboBoxQuartoID.getValue();
        Controller.getInstance().setDescricaoQuarto(quartoEscolhido);
        //Se não selecionou um quarto
        int index = comboBoxQuartoID.getSelectionModel().getSelectedIndex();
        if (index == -1) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aviso");
            alert.setHeaderText("Sem seleção");
            alert.setContentText("Selecione primeiro um quarto.");
            alert.showAndWait();
        } else {
            arrayPreco = qDAO.findPreco();
            for (Quarto q : arrayPreco) {
                textPreco.setText(String.valueOf(q.getPreco()));
            }
            //Ativar botões e limpar texto na TextArea preço
            verificarPrecoID.setDisable(false);
            verDescricaoID.setDisable(false);
            verificarDescricao.setDisable(false);
            textAlterarPreco.clear();
            //Ativar textarea
            textAlterarPreco.setEditable(true);
        }
    }

    public void verificarAlterarPreco() {
        //Se o preço que inseriu é igual ao match de:
        //Qualquer dígito de 0 a 9 com 3 digitos no máximo
        //Com casas decimais de 1 minimo e 2 máximo
        //Possívies matches: 99.9, 99.99, 999.9, 999.99
        if (textAlterarPreco.getText().matches("^\\d+(,\\d{3})*(\\.\\d{1,2})?$")) {
            QuartoDAO daoQuarto = new QuartoDAO();
            Quarto quarto = new Quarto();

            //Update: Alterar preço na base de dados
            quarto.setPreco(Float.parseFloat(textAlterarPreco.getText()));
            daoQuarto.updatePreco(quarto);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Informação");
            alert.setHeaderText(null);
            alert.setContentText("Atualizou com sucesso.");
            alert.showAndWait();
            //Se não emite aviso
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aviso");
            alert.setHeaderText("Preço inválido");
            alert.setContentText("Exemplos de formatos de preços possíves:\n" +
                    "99.9\n" +
                    "99.99\n" +
                    "999.9\n" +
                    "999.99"
            );
            alert.showAndWait();
        }
    }

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

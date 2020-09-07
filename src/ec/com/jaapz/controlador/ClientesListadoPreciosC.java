package ec.com.jaapz.controlador;

import java.util.List;

import ec.com.jaapz.modelo.Rubro;
import ec.com.jaapz.modelo.RubroDAO;
import ec.com.jaapz.util.Context;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class ClientesListadoPreciosC {
	@FXML TableView<Rubro> tvDatos;
	@FXML TextField txtBuscar;
	RubroDAO materialDAO = new RubroDAO();
	public void initialize() {
		llenarDatosPreciosUnitarios("");
		tvDatos.setRowFactory(tv -> {
            TableRow<Rubro> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                	if(tvDatos.getSelectionModel().getSelectedItem() != null){
    					Context.getInstance().setRubro(tvDatos.getSelectionModel().getSelectedItem());
    					Context.getInstance().getStageModal().close();
    				}
                }
            });
            return row ;
        });
	}
	@SuppressWarnings("unchecked")
	void llenarDatosPreciosUnitarios(String buscar) {
		try {
			List<Rubro> listadoPreciosUnitarios = materialDAO.getRubro(buscar);
			tvDatos.getColumns().clear();
			
			ObservableList<Rubro> datos = FXCollections.observableArrayList();
			datos.setAll(listadoPreciosUnitarios);

			//llenar los datos en la tabla
			TableColumn<Rubro, String> idColum = new TableColumn<>("Codigo");
			idColum.setMinWidth(10);
			idColum.setPrefWidth(60);
			idColum.setCellValueFactory(new PropertyValueFactory<Rubro, String>("idRubro"));
			
			TableColumn<Rubro, String> descripcionColum = new TableColumn<>("Descripción");
			descripcionColum.setMinWidth(10);
			descripcionColum.setPrefWidth(200);
			descripcionColum.setCellValueFactory(new PropertyValueFactory<Rubro, String>("descripcion"));
			
			TableColumn<Rubro, String> precioColum = new TableColumn<>("Tipo Precio Unitario");
			precioColum.setMinWidth(10);
			precioColum.setPrefWidth(200);
			precioColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Rubro,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Rubro, String> param) {
					return new SimpleObjectProperty<String>(param.getValue().getTipoRubro().getDescripcion());
				}
			});
			
			TableColumn<Rubro, String> costoColum = new TableColumn<>("Costo");
			costoColum.setMinWidth(10);
			costoColum.setPrefWidth(100);
			costoColum.setCellValueFactory(new PropertyValueFactory<Rubro, String>("precio"));
			
			tvDatos.getColumns().addAll(idColum, descripcionColum,precioColum,costoColum);
			tvDatos.setItems(datos);
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	public void buscarPrecioUnitario() {
		llenarDatosPreciosUnitarios(txtBuscar.getText());
	}
}
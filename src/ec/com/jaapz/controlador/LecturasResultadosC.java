package ec.com.jaapz.controlador;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import ec.com.jaapz.modelo.AperturaLectura;
import ec.com.jaapz.modelo.Empresa;
import ec.com.jaapz.modelo.EmpresaDAO;
import ec.com.jaapz.modelo.Planilla;
import ec.com.jaapz.modelo.PlanillaDAO;
import ec.com.jaapz.util.Context;
import ec.com.jaapz.util.ControllerHelper;
import ec.com.jaapz.util.PrintReport;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.util.Callback;

public class LecturasResultadosC {
	@FXML private Button btnVisualizarInforme;
	@FXML private TableView<Planilla> tvResultados;
	@FXML private Label lblApertura;
	
	PlanillaDAO planillaDAO = new PlanillaDAO();
	AperturaLectura aperturaSeleccionada = new AperturaLectura();
	EmpresaDAO empresaDAO = new EmpresaDAO();
	
	public void initialize() {
		try {
			btnVisualizarInforme.setStyle("-fx-cursor: hand;");
			aperturaSeleccionada = Context.getInstance().getApertura();
			lblApertura.setText("Apertura seleccionada: mes " + aperturaSeleccionada.getMe().getDescripcion() + " del a�o " + aperturaSeleccionada.getAnio().getDescripcion());
			Context.getInstance().setApertura(null);
			cargarClientes();
			btnVisualizarInforme.setOnMouseClicked(new EventHandler<Event>() {
				@Override
				public void handle(Event event) {
					// TODO Auto-generated method stub
					try {
						Integer cantidad = 0;
						List<Empresa> listaEmpresa = empresaDAO.getEmpresa();
						if(listaEmpresa.size() > 0) {
							if(listaEmpresa.get(0).getInconsistencia() != null)
								cantidad = listaEmpresa.get(0).getInconsistencia();
						}
						SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", new Locale("MX"));
						Date fechaDate = new Date();
						String fechaSistema = formateador.format(fechaDate);
						String fecha = dateFormatter("yyyy-MM-dd hh:mm:ss","d 'de' MMMM 'del' yyyy", fechaSistema);
						Map<String, Object> param = new HashMap<String, Object>();
						param.put("FECHA", fecha);
						param.put("CANTIDAD", cantidad);
						param.put("ID_APERTURA", aperturaSeleccionada.getIdApertura());
						PrintReport reporte = new PrintReport();
						reporte.crearReporte("/recursos/informes/inconsistenciaMarcaciones.jasper", empresaDAO, param);
						reporte.showReport("Inconsistencia de marcaciones");
					}catch(Exception ex) {
						System.out.println(ex.getMessage());
					}
				}
			});
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	private void cargarClientes() {
		try {
			
			tvResultados.getItems().clear();
			tvResultados.getColumns().clear();
			
			List<Planilla> listaPlanilla = planillaDAO.getListaPlanillaApertura(aperturaSeleccionada.getIdApertura());
			ObservableList<Planilla> datos = FXCollections.observableArrayList();
			datos.setAll(listaPlanilla);

			//llenar los datos en la tabla
			TableColumn<Planilla, String> idColum = new TableColumn<>("Id Planilla");
			idColum.setMinWidth(10);
			idColum.setPrefWidth(80);
			idColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Planilla,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Planilla, String> param) {
					String idPlanilla = String.valueOf(param.getValue().getIdPlanilla());
					return new SimpleObjectProperty<String>(idPlanilla);
				}
			});

			TableColumn<Planilla, String> clienteColum = new TableColumn<>("Cliente");
			clienteColum.setMinWidth(10);
			clienteColum.setPrefWidth(300);
			clienteColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Planilla,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Planilla, String> param) {
					String nombre = param.getValue().getCuentaCliente().getCliente().getNombre();
					String apellido = param.getValue().getCuentaCliente().getCliente().getApellido();
					return new SimpleObjectProperty<String>(nombre + " " + apellido);
				}
			});
			TableColumn<Planilla, String> medidorColum = new TableColumn<>("Medidor");
			medidorColum.setMinWidth(10);
			medidorColum.setPrefWidth(100);
			medidorColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Planilla,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Planilla, String> param) {
					return new SimpleObjectProperty<String>(String.valueOf(param.getValue().getCuentaCliente().getMedidor().getCodigo()));
				}
			});
			TableColumn<Planilla, String> antColum = new TableColumn<>("Lec.  Anterior");
			antColum.setMinWidth(10);
			antColum.setPrefWidth(90);
			antColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Planilla,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Planilla, String> param) {
					return new SimpleObjectProperty<String>(String.valueOf(param.getValue().getLecturaAnterior()));
				}
			});

			TableColumn<Planilla, String> actColum = new TableColumn<>("Lec. Actual");
			actColum.setMinWidth(10);
			actColum.setPrefWidth(90);
			actColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Planilla,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Planilla, String> param) {
					return new SimpleObjectProperty<String>(String.valueOf(param.getValue().getLecturaActual()));
				}
			});
			TableColumn<Planilla, String> coordenadaLectColum = new TableColumn<>("Coordenada Lect.");
			coordenadaLectColum.setMinWidth(10);
			coordenadaLectColum.setPrefWidth(130);
			coordenadaLectColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Planilla,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Planilla, String> param) {
					String coordenada = "";
					if(param.getValue().getLatitud() != null)
						coordenada = param.getValue().getLatitud() + "," + param.getValue().getLongitud();
					return new SimpleObjectProperty<String>(coordenada);
				}
			});
			TableColumn<Planilla, String> coordenadaMedColum = new TableColumn<>("Coordenada Med.");
			coordenadaMedColum.setMinWidth(10);
			coordenadaMedColum.setPrefWidth(130);
			coordenadaMedColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Planilla,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Planilla, String> param) {
					String coordenada = "";
					if(param.getValue().getCuentaCliente().getLatitud() != null)
						coordenada = param.getValue().getCuentaCliente().getLatitud() + "," + param.getValue().getCuentaCliente().getLongitud();
					return new SimpleObjectProperty<String>(coordenada);
				}
			});
			TableColumn<Planilla, String> distanciaColum = new TableColumn<>("Distancia metros.");
			distanciaColum.setMinWidth(10);
			distanciaColum.setPrefWidth(130);
			distanciaColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Planilla,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Planilla, String> param) {
					String distancia = "Sin distancia";
					if(param.getValue().getCuentaCliente().getLatitud() != null)
						if(param.getValue().getLatitud() != null) {
							Double lat1 = param.getValue().getLatitud();
							Double lon1 = param.getValue().getLongitud();
							Double lat2 = param.getValue().getCuentaCliente().getLatitud();
							Double lon2 = param.getValue().getCuentaCliente().getLongitud();
							distancia = String.valueOf(ControllerHelper.distanciaCoordenadas(lat1, lon1, lat2, lon2));
						}
							
					return new SimpleObjectProperty<String>(distancia);
				}
			});
			tvResultados.getColumns().addAll(idColum,clienteColum,medidorColum,antColum,actColum,coordenadaLectColum,coordenadaMedColum,distanciaColum);
			tvResultados.setItems(datos);

		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	public static final Locale LOCALE_MX = new Locale("es", "MX");
	public static String dateFormatter(String inputFormat, String outputFormat, String inputDate){
	      //Define formato default de entrada.   
	      String input = inputFormat.isEmpty()? "yyyy-MM-dd hh:mm:ss" : inputFormat; 
	      //Define formato default de salida.
	      String output = outputFormat.isEmpty()? "d 'de' MMMM 'del' yyyy" : outputFormat;
	    String outputDate = inputDate;
	    try {
	        outputDate = new SimpleDateFormat(output, LOCALE_MX).format(new SimpleDateFormat(input, LOCALE_MX).parse(inputDate));
	    } catch (Exception e) {
	        System.out.println("dateFormatter(): " + e.getMessage());           
	    }
	    return outputDate;
	}
}

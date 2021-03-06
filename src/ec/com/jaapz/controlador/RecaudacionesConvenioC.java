package ec.com.jaapz.controlador;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import ec.com.jaapz.modelo.Convenio;
import ec.com.jaapz.modelo.ConvenioDAO;
import ec.com.jaapz.modelo.ConvenioDetalle;
import ec.com.jaapz.modelo.ConvenioPlanilla;
import ec.com.jaapz.modelo.CuentaCliente;
import ec.com.jaapz.modelo.Empresa;
import ec.com.jaapz.modelo.EmpresaDAO;
import ec.com.jaapz.modelo.Pago;
import ec.com.jaapz.modelo.Planilla;
import ec.com.jaapz.modelo.PlanillaDAO;
import ec.com.jaapz.modelo.PlanillaDetalle;
import ec.com.jaapz.util.Constantes;
import ec.com.jaapz.util.Context;
import ec.com.jaapz.util.ControllerHelper;
import ec.com.jaapz.util.PrintReport;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Callback;

public class RecaudacionesConvenioC {

	@FXML private Button btnGenerar;
	@FXML private TextField txtNumMeses;
	@FXML private TableView<ConvenioDetalle> tvDetalleConvenio;
	@FXML private TextField txtMedidor;
	@FXML private TextField txtTotalDeuda;
	@FXML private Button btnLimpiar;
	@FXML private TextField txtidCuenta;
	@FXML private TextField txtCliente;
	@FXML private TextField txtCedula;
	@FXML private Button btnGrabar;
	@FXML private Button btnBuscar;
	@FXML private TableView<Planilla> tvPlanillasImpagas;

	ControllerHelper helper = new ControllerHelper();
	CuentaCliente cuentaSeleccionada;
	ConvenioDAO convenioDAO = new ConvenioDAO();
	PlanillaDAO planillaDAO = new PlanillaDAO();
	EmpresaDAO empresaDao = new EmpresaDAO();
	DecimalFormat df = new DecimalFormat("0.00"); 
	
	public void initialize() {
		try {
			bloquear();
			btnBuscar.setStyle("-fx-cursor: hand;");
			btnGenerar.setStyle("-fx-cursor: hand;");
			btnGrabar.setStyle("-fx-cursor: hand;");
			btnLimpiar.setStyle("-fx-cursor: hand;");
			limpiar();
			
			//validar solo numeros
			txtNumMeses.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					if (newValue.matches("\\d*")) {
						//int value = Integer.parseInt(newValue);
					} else {
						txtNumMeses.setText(oldValue);
					}
				}
			});
			
			txtNumMeses.setOnKeyPressed(new EventHandler<KeyEvent>(){
				@Override
				public void handle(KeyEvent ke){
					if (ke.getCode().equals(KeyCode.ENTER)){
						if (Integer.parseInt(txtNumMeses.getText()) > 0){
							generarDetalleConvenio();
						}
						else {
							helper.mostrarAlertaAdvertencia("Verifique el valor ingresado", Context.getInstance().getStage());
							txtNumMeses.requestFocus();
						}
					}
				}
			});
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	void bloquear() {
		txtidCuenta.setEditable(false);
		txtMedidor.setEditable(false);
		txtCedula.setEditable(false);
		txtCliente.setEditable(false);
		txtTotalDeuda.setEditable(false);
	}
	
	public void buscarCliente() {
		try{
			helper.abrirPantallaModal("/recaudaciones/ConvenioListadoPlanillas.fxml","Facturas Sin Cobrar", Context.getInstance().getStage());
			if (Context.getInstance().getCuentaCliente() != null) {
				cuentaSeleccionada = Context.getInstance().getCuentaCliente();
				llenarDatos(cuentaSeleccionada);
				Context.getInstance().setCuentaCliente(null);				
			}
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
	@SuppressWarnings({ "unused", "unchecked" })
	void llenarDatos(CuentaCliente datoSeleccionado){
		try {
			tvPlanillasImpagas.getColumns().clear();
			tvPlanillasImpagas.getItems().clear();
			
			if(datoSeleccionado.getMedidor() != null)
				if(datoSeleccionado.getMedidor().getCodigo() == null)
					txtMedidor.setText("");
				else
					txtMedidor.setText(datoSeleccionado.getMedidor().getCodigo());

			if(datoSeleccionado.getIdCuenta() == null)
				txtidCuenta.setText("");
			else
				txtidCuenta.setText(String.valueOf(datoSeleccionado.getIdCuenta()));

			if(datoSeleccionado.getCliente().getCedula() == null)
				txtCedula.setText("");
			else
				txtCedula.setText(datoSeleccionado.getCliente().getCedula());

			if(datoSeleccionado.getCliente().getNombre() + " " + datoSeleccionado.getCliente().getApellido() == null)
				txtCliente.setText("");
			else
				txtCliente.setText(datoSeleccionado.getCliente().getNombre() + " " + datoSeleccionado.getCliente().getApellido());


			System.out.println("Planillas: " + datoSeleccionado.getPlanillas().size());
			
			if (datoSeleccionado.getPlanillas().size() > 0) {
				
				ObservableList<Planilla> planillas = FXCollections.observableArrayList();
				for(Planilla pla : datoSeleccionado.getPlanillas()) {
					if (pla.getCancelado() != null)
						if(pla.getIdentificadorProceso() == null) // quiere decir que no esta pendiente de procesar
							if (pla.getCancelado().equals(Constantes.EST_FAC_PENDIENTE)) 
								planillas.add(pla);
				}
				System.out.println(planillas.size());
				Collections.sort(planillas);

				TableColumn<Planilla, String> idColum = new TableColumn<>("Id");
				idColum.setMinWidth(10);
				idColum.setPrefWidth(80);
				idColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Planilla, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Planilla, String> param) {
						return new SimpleObjectProperty<String>(String.valueOf(param.getValue().getIdPlanilla()));
					}
				});

				TableColumn<Planilla, String> descripcionColum = new TableColumn<>("Descripci�n");
				descripcionColum.setMinWidth(10);
				descripcionColum.setPrefWidth(365);
				descripcionColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Planilla, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Planilla, String> param) {
						String variable = ""; 
						if (param.getValue().getAperturaLectura() != null) {
							variable = "Planilla mes de: " + String.valueOf(param.getValue().getAperturaLectura().getMe().getDescripcion()) + " A�o: " + param.getValue().getAperturaLectura().getAnio().getDescripcion();
						}else {
							if(param.getValue().getIdentInstalacion() != null) {
								if(param.getValue().getIdentInstalacion().equals(Constantes.IDENT_INSTALACION))
									variable = "Instalaci�n de Medidor";
							}
						}
						return new SimpleObjectProperty<String>(variable);
					}
				});

				TableColumn<Planilla, String> consumoColum = new TableColumn<>("Consumo");
				consumoColum.setMinWidth(10);
				consumoColum.setPrefWidth(150);
				consumoColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Planilla, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Planilla, String> param) {
						return new SimpleObjectProperty<String>(String.valueOf(param.getValue().getConsumo()));
					}
				});

				TableColumn<Planilla, String> valorPagoColum = new TableColumn<>("Valor a pagar");
				valorPagoColum.setMinWidth(10);
				valorPagoColum.setPrefWidth(150);
				valorPagoColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Planilla, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Planilla, String> param) {
						double total = 0.0;
						for(Pago pa : param.getValue().getPagos()){
							total = total + pa.getValor();
						}
						return new SimpleObjectProperty<String>(String.valueOf(df.format(param.getValue().getTotalPagar() - total).replace(",", ".")));
					}
				});

				tvPlanillasImpagas.getColumns().addAll(descripcionColum, consumoColum, valorPagoColum);
				tvPlanillasImpagas.setItems(planillas);
				sumarTotal();
			}
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	private void sumarTotal() {
		try {
			if (tvPlanillasImpagas.getItems().isEmpty()) {
				//	txtTotal.setText("0.00");
				//	txtSaldo.setText("0.00");
			}else {
				double total = 0.00;
				for(int i = 0 ; i < tvPlanillasImpagas.getItems().size(); i++) {
					Double valorTotal = new Double(tvPlanillasImpagas.getItems().get(i).getTotalPagar());
					total += valorTotal;
					double totalPagado = 0.00;
					for(Planilla pla : tvPlanillasImpagas.getItems()) {
						for(Pago pa : pla.getPagos()){
							if(pa.getEstado().equals(Constantes.ESTADO_ACTIVO))
								totalPagado = totalPagado + pa.getValor();
						}
					}
					//txtTotalDeuda.setText(String.valueOf(Double.valueOf(total - totalPagado)));
					txtTotalDeuda.setText(df.format(total - totalPagado).replace(",", "."));
				}
			}
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	@SuppressWarnings("unchecked")
	public void generarDetalleConvenio() {
		try {			
			if(Integer.parseInt(String.valueOf(txtNumMeses.getText())) <= 0 || txtNumMeses.getText().toString() == "") {
				helper.mostrarAlertaAdvertencia("El n�mero de meses debe se mayor a cero..!!", Context.getInstance().getStage());
				txtNumMeses.requestFocus();
				return;
			}
			if(validarDatos() == false) {
				return;
			}
			Optional<ButtonType> result = helper.mostrarAlertaConfirmacion("Se va a generar el detalle del convenio\ndesea continuar?",Context.getInstance().getStage());
			if(result.get() == ButtonType.OK){	
				ObservableList<ConvenioDetalle> convenioDetalle = FXCollections.observableArrayList();
				ConvenioDetalle detalle;
				Double cuota =  Double.parseDouble(txtTotalDeuda.getText()) / Double.parseDouble(String.valueOf(txtNumMeses.getText()));
				for(int i = 0 ; i < Integer.parseInt(txtNumMeses.getText()) ; i++){
					detalle = new ConvenioDetalle();
					detalle.setEstado(Constantes.ESTADO_ACTIVO);
					detalle.setIdConvenioDet(null);
					detalle.setNumLetra(i + 1);
					detalle.setValor(Double.valueOf(cuota));
					detalle.setDescripcion("Convenio Cuota No. " + (i + 1) + " con valor: " + df.format(cuota).replace(",", "."));
					convenioDetalle.add(detalle);
				}
				TableColumn<ConvenioDetalle, String> numColum = new TableColumn<>("No.");
				numColum.setMinWidth(10);
				numColum.setPrefWidth(120);
				numColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ConvenioDetalle, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<ConvenioDetalle, String> param) {
						return new SimpleObjectProperty<String>(String.valueOf(param.getValue().getNumLetra()));
					}
				});
				TableColumn<ConvenioDetalle, String> descripcionColum = new TableColumn<>("Descripci�n");
				descripcionColum.setMinWidth(10);
				descripcionColum.setPrefWidth(350);
				descripcionColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ConvenioDetalle, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<ConvenioDetalle, String> param) {
						return new SimpleObjectProperty<String>(String.valueOf(param.getValue().getDescripcion()));
					}
				});
				TableColumn<ConvenioDetalle, String> valorColum = new TableColumn<>("Valor");
				valorColum.setMinWidth(10);
				valorColum.setPrefWidth(150);
				valorColum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ConvenioDetalle, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<ConvenioDetalle, String> param) {
						return new SimpleObjectProperty<String>(df.format(param.getValue().getValor()));
					}
				});
				tvDetalleConvenio.getColumns().addAll(numColum, descripcionColum, valorColum);
				tvDetalleConvenio.setItems(convenioDetalle);
			}
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	private boolean validarDatos() {
		try {
			boolean bandera = false;
			if(tvPlanillasImpagas.getItems().size() <= 0) {
				helper.mostrarAlertaAdvertencia("No existen planillas sin pagar!!", Context.getInstance().getStage());
				bandera = false;
			}
			
			bandera = true;
			return bandera;
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
			return false;
		}
	}
	public void grabarConvenio() {
		try {
			if(validarDatosDeEntrada() == false)
				return;
			Optional<ButtonType> result = helper.mostrarAlertaConfirmacion("Grabar el convenio.. revise los par�metros antes de continuar\nDesea continuar?",Context.getInstance().getStage());
			if(result.get() == ButtonType.OK){	
				Double cuota = 0.00;
				Convenio convenio = new Convenio();
				convenio.setEstado(Constantes.ESTADO_ACTIVO);
				convenio.setFecha(new Date());
				convenio.setIdConvenio(null);
				convenio.setTotal(Double.parseDouble(String.valueOf(txtTotalDeuda.getText())));
				convenio.setNumLetras(tvDetalleConvenio.getItems().size());
				convenio.setCuentaCliente(cuentaSeleccionada);
				//detalle del convenio
				List<ConvenioDetalle> convenioDetalle = new ArrayList<ConvenioDetalle>();
				for(ConvenioDetalle detalle : tvDetalleConvenio.getItems()) {
					detalle.setConvenio(convenio);
					cuota = detalle.getValor();
					convenioDetalle.add(detalle);
				}
				convenio.setConvenioDetalles(convenioDetalle);
				//convenio planilla.. es para saber a q planillas se le hizo el convenio
				List<ConvenioPlanilla> convenioPlanilla = new ArrayList<ConvenioPlanilla>();
				for(Planilla planilla : tvPlanillasImpagas.getItems()) {
					ConvenioPlanilla conPlanilla = new ConvenioPlanilla();
					conPlanilla.setEstado(Constantes.ESTADO_ACTIVO);
					conPlanilla.setIdConvPlanilla(null);
					conPlanilla.setPlanilla(planilla);
					conPlanilla.setConvenio(convenio);
					convenioPlanilla.add(conPlanilla);
				}
				//enlace con el convenio
				convenio.setConvenioPlanillas(convenioPlanilla);
				convenioDAO.getEntityManager().getTransaction().begin();
				convenioDAO.getEntityManager().persist(convenio);
				convenioDAO.getEntityManager().getTransaction().commit();
				
				//poner las planillas que han sido pagadas
				convenioDAO.getEntityManager().getTransaction().begin();
				for(Planilla planilla : tvPlanillasImpagas.getItems()) {
					planilla.setCancelado(Constantes.EST_FAC_CANCELADO);
					planilla.setConvenio(Constantes.CONVENIO_SI);
					convenioDAO.getEntityManager().merge(planilla);
				}
				convenioDAO.getEntityManager().getTransaction().commit();
				
				List<Convenio> listaGuardada = convenioDAO.getConveniosAll();
				List<Planilla> noPlanillado = planillaDAO.getNoPlanillado(cuentaSeleccionada.getIdCuenta());
				
				//crear las planillas para realizar el cobro
				for(int i = 0 ; i < Integer.parseInt(String.valueOf(txtNumMeses.getText())) ; i ++) {
					Planilla planilla;
					if(noPlanillado.size() > i) {
						planilla = noPlanillado.get(i);
					}else {
						planilla = new Planilla();
						planilla.setIdPlanilla(null);
						planilla.setIdentificadorProceso(Constantes.IDENT_PROCESO);//con esta variable se identifica si se encuentra procesada
						planilla.setCuentaCliente(cuentaSeleccionada);
						planilla.setEstado(Constantes.ESTADO_ACTIVO);
					}
					
					//enlace entre detalle de planilla y planilla
					PlanillaDetalle detallePlanilla = new PlanillaDetalle();
					detallePlanilla.setIdPlanillaDet(null);
					detallePlanilla.setCantidad(1);
					detallePlanilla.setUsuarioCrea(Context.getInstance().getIdUsuario());
					detallePlanilla.setSubtotal(cuota);
					detallePlanilla.setDescripcion("CUOTA DE CONVENIO: " + (i + 1) + "/" + Integer.parseInt(String.valueOf(txtNumMeses.getText())));
					detallePlanilla.setIdentificadorOperacion(Constantes.IDENT_CONVENIO);
					detallePlanilla.setEstado(Constantes.ESTADO_ACTIVO);
					detallePlanilla.setCantidad(1);
					if(listaGuardada.size() > 0) 
						detallePlanilla.setConvenioDetalle(listaGuardada.get(0).getConvenioDetalles().get(i));
					
					List<PlanillaDetalle> detalles = new ArrayList<PlanillaDetalle>();
					detallePlanilla.setPlanilla(planilla);
					detalles.add(detallePlanilla);
					planilla.setPlanillaDetalles(detalles);
					convenioDAO.getEntityManager().getTransaction().begin();
					if(planilla.getIdPlanilla() != null)
						convenioDAO.getEntityManager().merge(planilla);
					else
						convenioDAO.getEntityManager().persist(planilla);
					convenioDAO.getEntityManager().getTransaction().commit();
				}
				helper.mostrarAlertaInformacion("Datos grabados!!", Context.getInstance().getStage());
				List<Empresa> empresa;
				empresa = empresaDao.getEmpresa();
				if(empresa.size() == 1){
					Context.getInstance().setEmpresaC(empresa.get(0));
				}
				
				PrintReport pr = new PrintReport();
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("id_convenio", convenio.getIdConvenio());
				param.put("presidente", empresa.get(0).getRepresentante());
				pr.crearReporte("/recursos/informes/convenio.jasper", convenioDAO, param);
				pr.showReport("Convenios de Pago");
				limpiar();
			}
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	boolean validarDatosDeEntrada() {
		try {
			if(txtidCuenta.getText().equals("")) {
				helper.mostrarAlertaAdvertencia("No existe ID de la cuenta del Cliente!!", Context.getInstance().getStage());
				txtidCuenta.requestFocus();
				return false;
			}
			
			if(txtCedula.getText().equals("")) {
				helper.mostrarAlertaAdvertencia("No existe c�dula de Cliente!!", Context.getInstance().getStage());
				txtCedula.requestFocus();
				return false;
			}
			
			if(txtNumMeses.getText().equals("")) {
				helper.mostrarAlertaAdvertencia("Ingrese n�mero de letras o cuotas a diferir!!", Context.getInstance().getStage());
				txtNumMeses.requestFocus();
				return false;
			}
						
			if(tvPlanillasImpagas.getItems().isEmpty()) {
				helper.mostrarAlertaAdvertencia("No contiene planillas impagas!!", Context.getInstance().getStage());
				tvPlanillasImpagas.requestFocus();
				return false;
			}
			
			if(tvDetalleConvenio.getItems().isEmpty()) {
				helper.mostrarAlertaAdvertencia("No existe el detalle del convenio a generar!!", Context.getInstance().getStage());
				tvDetalleConvenio.requestFocus();
				return false;
			}
			
			return true;
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
			return false;
		}
	}
	
	public void limpiarDatos() {
		try {
			limpiar();
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	private void limpiar() {
		try {
			txtNumMeses.setText("");
			tvDetalleConvenio.getItems().clear();
			tvDetalleConvenio.getColumns().clear();
			txtMedidor.setText("");
			txtTotalDeuda.setText("");
			txtidCuenta.setText("");
			txtCliente.setText("");
			txtCedula.setText("");
			tvPlanillasImpagas.getItems().clear();
			tvPlanillasImpagas.getColumns().clear();
			cuentaSeleccionada = null;
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}		
	}
}

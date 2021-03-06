package ec.com.jaapz.controlador;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import ec.com.jaapz.modelo.CuentaCliente;
import ec.com.jaapz.modelo.CuentaClienteDAO;
import ec.com.jaapz.modelo.SolInspeccionRep;
import ec.com.jaapz.modelo.SolInspeccionRepDAO;
import ec.com.jaapz.modelo.TipoSolicitud;
import ec.com.jaapz.modelo.TipoSolicitudDAO;
import ec.com.jaapz.util.Constantes;
import ec.com.jaapz.util.Context;
import ec.com.jaapz.util.ControllerHelper;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;

public class SolicitudReparacionC {
	@FXML private TextField txtCedula;
	@FXML private TextField txtCuenta;
	@FXML private TextField txtNumMedidor;
	@FXML private TextField txtNombres;
	@FXML private TextField txtApellidos;
	@FXML private TextField txtDireccion;
	@FXML private TextField txtTelefono;
	@FXML private TextField txtCodigo;

	@FXML private DatePicker dtpFechaRep;
	@FXML private TextField txtReferenciaIns;
	@FXML private TextField txtDireccionIns;
	@FXML private TextField txtContacto;
	@FXML private TextArea txtObservaciones;

	@FXML private Button btnGrabarIns;
	@FXML private Button btnNuevoIns;
	@FXML private Button btnBuscarIns;

	ControllerHelper helper = new ControllerHelper();
	CuentaClienteDAO cuentaClienteDao = new CuentaClienteDAO();
	TipoSolicitudDAO tipoSolicitudDAO = new TipoSolicitudDAO();
	SolInspeccionRepDAO reparacionDao = new SolInspeccionRepDAO();
	CuentaCliente cuentaRecuperada;
	CuentaCliente cuentaSeleccionada = new CuentaCliente();

	public void initialize() {
		try {
			btnBuscarIns.setStyle("-fx-cursor: hand;");
			btnGrabarIns.setStyle("-fx-cursor: hand;");
			btnNuevoIns.setStyle("-fx-cursor: hand;");
			
			dtpFechaRep.setValue(LocalDate.now());
			nuevo();
			Context.getInstance().setCuentaCliente(null);
			//solo letras mayusculas
			txtNombres.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					// TODO Auto-generated method stub
					String cadena = txtNombres.getText().toUpperCase();
					txtNombres.setText(cadena);
				}
			});

			//solo letras mayusculas
			txtApellidos.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					// TODO Auto-generated method stub
					String cadena = txtApellidos.getText().toUpperCase();
					txtApellidos.setText(cadena);
				}
			});

			//solo letras mayusculas
			txtDireccion.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					// TODO Auto-generated method stub
					String cadena = txtDireccion.getText().toUpperCase();
					txtDireccion.setText(cadena);
				}
			});

			//solo letras mayusculas
			txtReferenciaIns.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					// TODO Auto-generated method stub
					String cadena = txtReferenciaIns.getText().toUpperCase();
					txtReferenciaIns.setText(cadena);
				}
			});

			//solo letras mayusculas
			txtDireccionIns.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					// TODO Auto-generated method stub
					String cadena = txtDireccionIns.getText().toUpperCase();
					txtDireccionIns.setText(cadena);
				}
			});

			//solo letras mayusculas
			txtObservaciones.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					// TODO Auto-generated method stub
					String cadena = txtObservaciones.getText().toUpperCase();
					txtObservaciones.setText(cadena);
				}
			});

			//solo letras mayusculas
			txtNumMedidor.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					// TODO Auto-generated method stub
					String cadena = txtNumMedidor.getText().toUpperCase();
					txtNumMedidor.setText(cadena);
				}
			});

			//validar solo numeros
			txtCuenta.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, 
						String newValue) {
					if (newValue.matches("\\d*")) {
						//int value = Integer.parseInt(newValue);
					} else {
						txtCuenta.setText(oldValue);
					}
				}
			});

			//validar solo numeros
			txtTelefono.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, 
						String newValue) {
					if (newValue.matches("\\d*")) {
						//int value = Integer.parseInt(newValue);
					} else {
						txtTelefono.setText(oldValue);
					}
				}
			});

			//validar solo numeros
			txtContacto.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, 
						String newValue) {
					if (newValue.matches("\\d*")) {
						//int value = Integer.parseInt(newValue);
					} else {
						txtContacto.setText(oldValue);
					}
				}
			});
			
			//validar solo 10 valores
			txtContacto.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
					if (txtContacto.getText().length() > 10) {
						String s = txtContacto.getText().substring(0, 10);
						txtContacto.setText(s);
					}
				}
			});

			txtCuenta.setOnKeyPressed(new EventHandler<KeyEvent>(){
				@Override
				public void handle(KeyEvent ke){
					if (ke.getCode().equals(KeyCode.ENTER)){
						if (validarCuentaExiste() == false){
							helper.mostrarAlertaAdvertencia("N�mero de Cuenta no existe", Context.getInstance().getStage());
							txtCuenta.setText("");
							txtCuenta.requestFocus();
							nuevo();
						}else
							recuperarDatosCuenta(Integer.parseInt(txtCuenta.getText()));
					}
				}
			});

			txtNumMedidor.setOnKeyPressed(new EventHandler<KeyEvent>(){
				@Override
				public void handle(KeyEvent ke){
					if (ke.getCode().equals(KeyCode.ENTER)){
						if (validarCuentaExisteMedidor() == false){
							helper.mostrarAlertaAdvertencia("Cliente no existe", Context.getInstance().getStage());
							txtNumMedidor.setText("");
							txtNumMedidor.requestFocus();
							nuevo();
						}else
							recuperarDatosMedidor(txtNumMedidor.getText());
					}
				}
			});

			txtCedula.setEditable(false);
			txtCodigo.setVisible(false);
			txtNombres.setEditable(false);
			txtApellidos.setEditable(false);
			txtDireccion.setEditable(false);
			txtTelefono.setEditable(false);
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	private void recuperarDatosCuenta(Integer cuenta) {
		try {
			List<CuentaCliente> listaCuentaCliente = new ArrayList<CuentaCliente>();
			listaCuentaCliente = cuentaClienteDao.getExisteCuenta(cuenta);
			for(int i = 0 ; i < listaCuentaCliente.size() ; i ++) {
				//txtCodigo.setText(Integer.toString(listaCuentaCliente.get(i).getCliente().getIdCliente()));
				txtCedula.setText(listaCuentaCliente.get(i).getCliente().getCedula());
				txtNumMedidor.setText(listaCuentaCliente.get(i).getMedidor().getCodigo());
				txtNombres.setText(listaCuentaCliente.get(i).getCliente().getNombre());
				txtApellidos.setText(listaCuentaCliente.get(i).getCliente().getApellido());
				txtDireccion.setText(listaCuentaCliente.get(i).getDireccion());
				txtTelefono.setText(listaCuentaCliente.get(i).getCliente().getTelefono());
			}
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	private void recuperarDatosMedidor(String medidor) {
		try {
			List<CuentaCliente> listaCuentaCliente = new ArrayList<CuentaCliente>();
			listaCuentaCliente = cuentaClienteDao.getExisteCuentaMedidor(medidor);
			for(int i = 0 ; i < listaCuentaCliente.size() ; i ++) {
				//txtCodigo.setText(Integer.toString(listaCuentaCliente.get(i).getCliente().getIdCliente()));
				txtCuenta.setText(Integer.toString(listaCuentaCliente.get(i).getIdCuenta()));
				txtCedula.setText(listaCuentaCliente.get(i).getCliente().getCedula());
				txtNumMedidor.setText(listaCuentaCliente.get(i).getMedidor().getCodigo());
				txtNombres.setText(listaCuentaCliente.get(i).getCliente().getNombre());
				txtApellidos.setText(listaCuentaCliente.get(i).getCliente().getApellido());
				txtDireccion.setText(listaCuentaCliente.get(i).getDireccion());
				txtTelefono.setText(listaCuentaCliente.get(i).getCliente().getTelefono());
			}
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	boolean validarCuentaExiste() {
		try {
			List<CuentaCliente> listaCuentas;
			listaCuentas = cuentaClienteDao.getExisteCuenta(Integer.valueOf(txtCuenta.getText()));
			if(listaCuentas.size() != 0)
				return true;
			else
				return false;
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
			return false;
		}
	}

	boolean validarCuentaExisteMedidor() {
		try {
			List<CuentaCliente> listaCuentas;
			listaCuentas = cuentaClienteDao.getExisteCuentaMedidor(txtNumMedidor.getText());
			if(listaCuentas.size() != 0)
				return true;
			else
				return false;
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
			return false;
		}
	}

	void llenarDatos(CuentaCliente cuentaSeleccionada){
		try {
			//txtCodigo.setText(String.valueOf(cuentaSeleccionada.getCliente().getIdCliente()));
			if(cuentaSeleccionada.getIdCuenta() == null)
				txtCuenta.setText("");
			else
				txtCuenta.setText(Integer.toString(cuentaSeleccionada.getIdCuenta()));

			if(cuentaSeleccionada.getMedidor() == null)
				txtNumMedidor.setText("");
			else
				txtNumMedidor.setText(cuentaSeleccionada.getMedidor().getCodigo());

			if(cuentaSeleccionada.getCliente().getCedula() == null)
				txtCedula.setText("");
			else
				txtCedula.setText(cuentaSeleccionada.getCliente().getCedula());

			if(cuentaSeleccionada.getCliente().getNombre() == null)
				txtNombres.setText("");
			else
				txtNombres.setText(cuentaSeleccionada.getCliente().getNombre());

			if(cuentaSeleccionada.getCliente().getApellido() == null)
				txtApellidos.setText("");
			else
				txtApellidos.setText(cuentaSeleccionada.getCliente().getApellido());

			if(cuentaSeleccionada.getCliente().getTelefono() == null)
				txtTelefono.setText("");
			else
				txtTelefono.setText(cuentaSeleccionada.getCliente().getTelefono());

			if(cuentaSeleccionada.getDireccion() == null)
				txtDireccion.setText("");
			else
				txtDireccion.setText(cuentaSeleccionada.getDireccion());

		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	public void nuevo(){
		txtCedula.setText("");
		txtCodigo.setText("0");
		txtCuenta.setText("");
		txtNumMedidor.setText("");    	
		txtNombres.setText("");
		txtApellidos.setText("");
		txtDireccion.setText("");
		txtTelefono.setText("");
		//dtpFechaRep.setValue(null);
		txtReferenciaIns.setText("");
		txtDireccionIns.setText("");
		txtContacto.setText("");
		txtObservaciones.setText("");
	}

	public void buscarCuenta() {
		try{
			helper.abrirPantallaModal("/reparaciones/ReparacionesListadoCuentas.fxml","Listado de Clientes", Context.getInstance().getStage());
			if (Context.getInstance().getCuentaCliente() != null) {
				CuentaCliente datoSeleccionado = Context.getInstance().getCuentaCliente();
				cuentaSeleccionada = datoSeleccionado;
				txtNumMedidor.setText(cuentaSeleccionada.getMedidor().getCodigo());
				recuperarDatos(cuentaSeleccionada.getMedidor().getCodigo());
				Context.getInstance().setCuentaCliente(null);;
				/*CuentaCliente datoSeleccionado = Context.getInstance().getCuentaCliente();
				llenarDatos(datoSeleccionado);
				Context.getInstance().setCuentaCliente(null);*/
			}
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}

	private void recuperarDatos(String numMedidor) {
		try {
			List<CuentaCliente> listaCuentaCliente = new ArrayList<CuentaCliente>();
			listaCuentaCliente = cuentaClienteDao.getExisteCuentaMedidor(numMedidor);
			if(listaCuentaCliente.size() > 0) {
				txtCedula.setText(listaCuentaCliente.get(0).getCliente().getCedula());
				txtNombres.setText(listaCuentaCliente.get(0).getCliente().getNombre());
				txtApellidos.setText(listaCuentaCliente.get(0).getCliente().getApellido());
				txtDireccion.setText(listaCuentaCliente.get(0).getDireccion());
				txtTelefono.setText(listaCuentaCliente.get(0).getCliente().getTelefono());
				txtCuenta.setText(Integer.toString(listaCuentaCliente.get(0).getIdCuenta()));
				cuentaRecuperada = listaCuentaCliente.get(0);
			}else {
				helper.mostrarAlertaInformacion("Cliente no registrado.. debe ser registrado", Context.getInstance().getStage());
				txtNombres.requestFocus();
				cuentaRecuperada = new CuentaCliente();
			}
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	public void grabar() {
		try {
			java.util.Date utilDate = new java.util.Date(); 
			long lnMilisegundos = utilDate.getTime();
			java.sql.Time sqlTime = new java.sql.Time(lnMilisegundos);
			
			if(validarDatos() == false)
				return;
			Optional<ButtonType> result = helper.mostrarAlertaConfirmacion("Desea Grabar los Datos?",Context.getInstance().getStage());
			if(result.get() == ButtonType.OK){

				Date date = Date.from(dtpFechaRep.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());

				cuentaRecuperada.setIdCuenta(Integer.parseInt(txtCuenta.getText()));
				
				SolInspeccionRep reparacion = new SolInspeccionRep();

				List<TipoSolicitud> tipoSolicitud = tipoSolicitudDAO.getSolById(2);
				System.out.println(tipoSolicitud.size());
				if(tipoSolicitud.size() > 0)
					reparacion.setTipoSolicitud(tipoSolicitud.get(0));

				reparacion.setCuentaCliente(cuentaRecuperada);
				reparacion.setUsuarioCrea(Context.getInstance().getUsuariosC().getIdUsuario());
				reparacion.setFecha(date);
				reparacion.setEstadoInspecRep(Constantes.EST_INSPECCION_PENDIENTE);
				reparacion.setEstadoSolicitud(Constantes.EST_INSPECCION_PENDIENTE);
				reparacion.setObservacion(txtObservaciones.getText());
				reparacion.setHora(sqlTime);
				reparacion.setEstado(Constantes.ESTADO_ACTIVO);
				reparacion.setReferencia(txtReferenciaIns.getText());
				reparacion.setTelfContacto(txtContacto.getText());

				reparacionDao.getEntityManager().getTransaction().begin();
				if (txtCodigo.getText().equals("0")) {
					reparacion.setIdSolicitudRep(null);
					reparacionDao.getEntityManager().persist(reparacion);
				}else {
					reparacion.setIdSolicitudRep(Integer.parseInt(txtCodigo.getText()));
					reparacionDao.getEntityManager().merge(reparacion);
				}			
				reparacionDao.getEntityManager().getTransaction().commit();

				helper.mostrarAlertaInformacion("Datos Grabados Correctamente", Context.getInstance().getStage());
				nuevo();
			}
		}catch(Exception ex) {
			helper.mostrarAlertaError("Error al grabar", Context.getInstance().getStage());
			reparacionDao.getEntityManager().getTransaction().rollback();
			System.out.println(ex.getMessage());
		}
	}

	boolean validarDatos() {
		try {
			if(txtCuenta.getText().equals("")) {
				helper.mostrarAlertaAdvertencia("Debe ingresar cuenta", Context.getInstance().getStage());
				txtCuenta.requestFocus();
				return false;
			}
			if(txtNumMedidor.getText().equals("")) {
				helper.mostrarAlertaAdvertencia("Debe ingresar # Medidor", Context.getInstance().getStage());
				txtNumMedidor.requestFocus();
				return false;
			}
			if(txtNombres.getText().equals("")) {
				helper.mostrarAlertaAdvertencia("Debe ingresar Nombre de cliente", Context.getInstance().getStage());
				txtNombres.requestFocus();
				return false;	
			}
			if(txtApellidos.getText().equals("")) {
				helper.mostrarAlertaAdvertencia("Debe ingresar Apellidos de cliente", Context.getInstance().getStage());
				txtApellidos.requestFocus();
				return false;	
			}
			if(txtDireccion.getText().equals("")) {
				helper.mostrarAlertaAdvertencia("Debe ingresar Direcci�n de cliente", Context.getInstance().getStage());
				txtDireccion.requestFocus();
				return false;	
			}
			if(txtTelefono.getText().equals("")) {
				helper.mostrarAlertaAdvertencia("Debe ingresar Direcci�n de cliente", Context.getInstance().getStage());
				txtTelefono.requestFocus();
				return false;	
			}
			
			if(dtpFechaRep.getValue() == null) {
				helper.mostrarAlertaAdvertencia("Debe ingresar fecha de inspecci�n", Context.getInstance().getStage());
				return false;	
			}
			
			if(txtReferenciaIns.getText().equals("")) {
				helper.mostrarAlertaAdvertencia("Ingresar referencia domiciliaria", Context.getInstance().getStage());
				txtReferenciaIns.requestFocus();
				return false;	
			}
			
			if(txtDireccionIns.getText().equals("")) {
				helper.mostrarAlertaAdvertencia("Ingresar direcci�n", Context.getInstance().getStage());
				txtDireccionIns.requestFocus();
				return false;	
			}
			
			if(txtContacto.getText().equals("")) {
				helper.mostrarAlertaAdvertencia("Ingresar tel�fono de contacto", Context.getInstance().getStage());
				txtContacto.requestFocus();
				return false;	
			}
			
			if(txtObservaciones.getText().equals("")) {
				helper.mostrarAlertaAdvertencia("Describa el problema presentado", Context.getInstance().getStage());
				txtObservaciones.requestFocus();
				return false;	
			}
			
			if(verificarExistencia() == true) {
				helper.mostrarAlertaAdvertencia("El cliente tiene una solicitud en proceso", Context.getInstance().getStage());
				return false;	
			}
			return true;
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
			return false;
		}
	}
	
	boolean verificarExistencia() {
		try {
			boolean bandera = false;
			SolInspeccionRepDAO reparacionDAO = new SolInspeccionRepDAO();
			List<SolInspeccionRep> lista = reparacionDAO.getSolicitudesExistente(Integer.parseInt(txtCuenta.getText()));
			if(lista.size() > 0) 
				bandera = true;
			return bandera;
		}catch(Exception ex) {
			return false;
		}
	}
}
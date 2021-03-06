package ec.com.jaapz.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Time;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the sol_inspeccion_rep database table.
 * 
 */
@Entity
@Table(name="sol_inspeccion_rep")
@NamedQueries({
	@NamedQuery(name="SolInspeccionRep.findAll", query="SELECT r FROM SolInspeccionRep r"),
	//para cierre de inspeccion
	@NamedQuery(name="SolInspeccionRep.buscarInspeccionPerfilPendiente", query="SELECT r FROM SolInspeccionRep r "
		+ "where (lower(r.cuentaCliente.cliente.nombre) like lower(:patron) or lower(r.cuentaCliente.cliente.apellido) like lower(:patron) or lower(r.cuentaCliente.cliente.cedula) like lower(:patron)) "
		+ " and r.idUsuEncargado = :idPerfilUsuario and r.estadoInspecRep = 'PENDIENTE' and r.estado = 'A' order by r.idSolicitudRep desc"),
	//para asignar
	@NamedQuery(name="SolInspeccionRep.findAllPendiente", query="SELECT r FROM SolInspeccionRep r "
		+ "where (lower(r.cuentaCliente.cliente.apellido) like :patron  or lower(r.cuentaCliente.cliente.nombre) like :patron) "
		+ "and r.estadoInspecRep = 'PENDIENTE' and r.idUsuEncargado = null and r.estado = 'A' order by r.idSolicitudRep desc"),
	
	@NamedQuery(name="SolInspeccionRep.buscarInspeccionAsignada", query="SELECT r FROM SolInspeccionRep r "
		+ "where r.idUsuEncargado = :idPerfilUsuario and r.estado = 'A' order by r.idSolicitudRep desc"),
	
	//para cierre de inspeccion
	@NamedQuery(name="SolInspeccionRep.buscarListaRep", query="SELECT r FROM SolInspeccionRep r "
		+ "where (lower(r.cuentaCliente.cliente.nombre) like lower(:patron) or lower(r.cuentaCliente.cliente.apellido) like lower(:patron) or lower(r.cuentaCliente.cliente.cedula) like lower(:patron)) "
		+ "and r.estadoInspecRep = 'PENDIENTE' and r.estado = 'A' order by r.idSolicitudRep desc"),
	//para asignar
	@NamedQuery(name="SolInspeccionRep.buscarListaRepPerfil", query="SELECT r FROM SolInspeccionRep r "
			+ "where (lower(r.cuentaCliente.cliente.apellido) like :patron or lower(r.cuentaCliente.cliente.nombre) like :patron) "
			+ " and r.idUsuEncargado = :idPerfilUsuario and r.idUsuEncargado = null and r.estadoInspecRep = 'PENDIENTE' and r.estado = 'A' order by r.idSolicitudRep desc"),
	@NamedQuery(name="SolInspeccionRep.buscarSolicitudesNoAtendidas", query="SELECT r FROM SolInspeccionRep r "
			+ "where r.estado = 'A' and r.estadoSolicitud = 'PENDIENTE' order by r.idSolicitudRep desc"),
	
	@NamedQuery(name="SolInspeccionRep.buscarExistente", query="SELECT r FROM SolInspeccionRep r "
			+ "where r.estado = 'A' and r.estadoSolicitud = 'PENDIENTE' and r.cuentaCliente.idCuenta = :idCuenta")
})

public class SolInspeccionRep implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_solicitud_rep")
	private Integer idSolicitudRep;

	private String estado;
	
	private String referencia;
	
	@Column(name="telf_contacto")
	private String telfContacto;

	@Column(name="estado_inspec_rep")
	private String estadoInspecRep;

	@Temporal(TemporalType.DATE)
	private Date fecha;

	private Time hora;

	@Column(name="id_usu_encargado")
	private Integer idUsuEncargado;

	@Column(name="estado_solicitud")
	private String estadoSolicitud;
	
	private String observacion;

	@Column(name="usuario_crea")
	private Integer usuarioCrea;

	//bi-directional many-to-one association to Reparacion
	@OneToMany(mappedBy="solInspeccionRep", cascade = CascadeType.ALL)
	private List<Reparacion> reparacions;

	//bi-directional many-to-one association to CuentaCliente
	@ManyToOne
	@JoinColumn(name="id_cuenta")
	private CuentaCliente cuentaCliente;

	//bi-directional many-to-one association to Barrio
	@ManyToOne
	@JoinColumn(name="id_tipo_solicitud")
	private TipoSolicitud tipoSolicitud;

	public SolInspeccionRep() {
	}

	public Integer getIdSolicitudRep() {
		return this.idSolicitudRep;
	}

	public void setIdSolicitudRep(Integer idSolicitudRep) {
		this.idSolicitudRep = idSolicitudRep;
	}

	public String getReferencia() {
		return this.referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}
	
	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getEstadoInspecRep() {
		return this.estadoInspecRep;
	}

	public void setEstadoInspecRep(String estadoInspecRep) {
		this.estadoInspecRep = estadoInspecRep;
	}
	
	public String getTelfContacto() {
		return this.telfContacto;
	}

	public void setTelfContacto(String telfContacto) {
		this.telfContacto = telfContacto;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Time getHora() {
		return this.hora;
	}

	public void setHora(Time hora) {
		this.hora = hora;
	}

	public Integer getIdUsuEncargado() {
		return this.idUsuEncargado;
	}

	public void setIdUsuEncargado(Integer idUsuEncargado) {
		this.idUsuEncargado = idUsuEncargado;
	}

	public String getObservacion() {
		return this.observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Integer getUsuarioCrea() {
		return this.usuarioCrea;
	}

	public void setUsuarioCrea(Integer usuarioCrea) {
		this.usuarioCrea = usuarioCrea;
	}

	public List<Reparacion> getReparacions() {
		return this.reparacions;
	}

	public void setReparacions(List<Reparacion> reparacions) {
		this.reparacions = reparacions;
	}

	public Reparacion addReparacion(Reparacion reparacion) {
		getReparacions().add(reparacion);
		reparacion.setSolInspeccionRep(this);

		return reparacion;
	}

	public Reparacion removeReparacion(Reparacion reparacion) {
		getReparacions().remove(reparacion);
		reparacion.setSolInspeccionRep(null);

		return reparacion;
	}

	public CuentaCliente getCuentaCliente() {
		return this.cuentaCliente;
	}

	public void setCuentaCliente(CuentaCliente cuentaCliente) {
		this.cuentaCliente = cuentaCliente;
	}

	public TipoSolicitud getTipoSolicitud() {
		return tipoSolicitud;
	}

	public void setTipoSolicitud(TipoSolicitud tipoSolicitud) {
		this.tipoSolicitud = tipoSolicitud;
	}

	public String getEstadoSolicitud() {
		return estadoSolicitud;
	}

	public void setEstadoSolicitud(String estadoSolicitud) {
		this.estadoSolicitud = estadoSolicitud;
	}

}
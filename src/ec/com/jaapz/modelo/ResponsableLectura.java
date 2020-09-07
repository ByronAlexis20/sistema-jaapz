package ec.com.jaapz.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Time;
import java.util.Date;


/**
 * The persistent class for the responsable_lectura database table.
 * 
 */
@Entity
@Table(name="responsable_lectura")
@NamedQueries({
	@NamedQuery(name="ResponsableLectura.findAll", query="SELECT r FROM ResponsableLectura r"),
	@NamedQuery(name="ResponsableLectura.buscarPorResponsable", query="SELECT r FROM ResponsableLectura r where r.aperturaLectura.idApertura = :idApertura "
			+ "and r.estado = 'A' and r.segUsuario.idUsuario = :idUsuario")
})
public class ResponsableLectura implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_responsable")
	private Integer idResponsable;

	private String estado;

	@Temporal(TemporalType.DATE)
	private Date fecha;

	private Time hora;

	@Column(name="usuario_crea")
	private Integer usuarioCrea;

	//bi-directional many-to-one association to AperturaLectura
	@ManyToOne
	@JoinColumn(name="id_apertura")
	private AperturaLectura aperturaLectura;

	//bi-directional many-to-one association to Barrio
	@ManyToOne
	@JoinColumn(name="id_barrio")
	private Barrio barrio;

	//bi-directional many-to-one association to SegUsuario
	@ManyToOne
	@JoinColumn(name="id_usuario")
	private SegUsuario segUsuario;

	public ResponsableLectura() {
	}

	public Integer getIdResponsable() {
		return this.idResponsable;
	}

	public void setIdResponsable(Integer idResponsable) {
		this.idResponsable = idResponsable;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
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

	public Integer getUsuarioCrea() {
		return this.usuarioCrea;
	}

	public void setUsuarioCrea(Integer usuarioCrea) {
		this.usuarioCrea = usuarioCrea;
	}

	public AperturaLectura getAperturaLectura() {
		return this.aperturaLectura;
	}

	public void setAperturaLectura(AperturaLectura aperturaLectura) {
		this.aperturaLectura = aperturaLectura;
	}

	public Barrio getBarrio() {
		return this.barrio;
	}

	public void setBarrio(Barrio barrio) {
		this.barrio = barrio;
	}

	public SegUsuario getSegUsuario() {
		return this.segUsuario;
	}

	public void setSegUsuario(SegUsuario segUsuario) {
		this.segUsuario = segUsuario;
	}

}
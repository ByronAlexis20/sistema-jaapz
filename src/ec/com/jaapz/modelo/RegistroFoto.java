package ec.com.jaapz.modelo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the registro_fotos database table.
 * 
 */
@Entity
@Table(name="registro_fotos")
@NamedQuery(name="RegistroFoto.findAll", query="SELECT r FROM RegistroFoto r")
public class RegistroFoto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_registro")
	private Integer idRegistro;

	private String estado;

	private byte[] foto;

	@Column(name="nombre_foto")
	private String nombreFoto;

	@Column(name="usuario_crea")
	private Integer usuarioCrea;

	//bi-directional many-to-one association to PlanillaDetalle
	@ManyToOne
	@JoinColumn(name="id_planilla_det")
	private PlanillaDetalle planillaDetalle;

	public RegistroFoto() {
	}

	public Integer getIdRegistro() {
		return this.idRegistro;
	}

	public void setIdRegistro(Integer idRegistro) {
		this.idRegistro = idRegistro;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public byte[] getFoto() {
		return this.foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	public String getNombreFoto() {
		return this.nombreFoto;
	}

	public void setNombreFoto(String nombreFoto) {
		this.nombreFoto = nombreFoto;
	}

	public Integer getUsuarioCrea() {
		return this.usuarioCrea;
	}

	public void setUsuarioCrea(Integer usuarioCrea) {
		this.usuarioCrea = usuarioCrea;
	}

	public PlanillaDetalle getPlanillaDetalle() {
		return this.planillaDetalle;
	}

	public void setPlanillaDetalle(PlanillaDetalle planillaDetalle) {
		this.planillaDetalle = planillaDetalle;
	}

}
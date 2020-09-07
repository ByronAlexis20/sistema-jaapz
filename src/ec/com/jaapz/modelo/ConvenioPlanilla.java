package ec.com.jaapz.modelo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the convenio_planilla database table.
 * 
 */
@Entity
@Table(name="convenio_planilla")
@NamedQuery(name="ConvenioPlanilla.findAll", query="SELECT c FROM ConvenioPlanilla c")
public class ConvenioPlanilla implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_conv_planilla")
	private Integer idConvPlanilla;

	private String estado;

	//bi-directional many-to-one association to Convenio
	@ManyToOne
	@JoinColumn(name="id_convenio")
	private Convenio convenio;

	//bi-directional many-to-one association to Planilla
	@ManyToOne
	@JoinColumn(name="id_planilla")
	private Planilla planilla;

	public ConvenioPlanilla() {
	}

	public Integer getIdConvPlanilla() {
		return this.idConvPlanilla;
	}

	public void setIdConvPlanilla(Integer idConvPlanilla) {
		this.idConvPlanilla = idConvPlanilla;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Convenio getConvenio() {
		return this.convenio;
	}

	public void setConvenio(Convenio convenio) {
		this.convenio = convenio;
	}

	public Planilla getPlanilla() {
		return this.planilla;
	}

	public void setPlanilla(Planilla planilla) {
		this.planilla = planilla;
	}

}
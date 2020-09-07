package ec.com.jaapz.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the convenio_detalle database table.
 * 
 */
@Entity
@Table(name="convenio_detalle")
@NamedQuery(name="ConvenioDetalle.findAll", query="SELECT c FROM ConvenioDetalle c")
public class ConvenioDetalle implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_convenio_det")
	private Integer idConvenioDet;

	private String descripcion;

	private String estado;

	@Column(name="num_letra")
	private Integer numLetra;

	private double valor;

	//bi-directional many-to-one association to Convenio
	@ManyToOne
	@JoinColumn(name="id_convenio")
	private Convenio convenio;

	//bi-directional many-to-one association to PlanillaDetalle
	@OneToMany(mappedBy="convenioDetalle", cascade = CascadeType.ALL)
	private List<PlanillaDetalle> planillaDetalles;

	public ConvenioDetalle() {
	}

	public Integer getIdConvenioDet() {
		return this.idConvenioDet;
	}

	public void setIdConvenioDet(Integer idConvenioDet) {
		this.idConvenioDet = idConvenioDet;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Integer getNumLetra() {
		return this.numLetra;
	}

	public void setNumLetra(Integer numLetra) {
		this.numLetra = numLetra;
	}

	public double getValor() {
		return this.valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public Convenio getConvenio() {
		return this.convenio;
	}

	public void setConvenio(Convenio convenio) {
		this.convenio = convenio;
	}

	public List<PlanillaDetalle> getPlanillaDetalles() {
		return this.planillaDetalles;
	}

	public void setPlanillaDetalles(List<PlanillaDetalle> planillaDetalles) {
		this.planillaDetalles = planillaDetalles;
	}

	public PlanillaDetalle addPlanillaDetalle(PlanillaDetalle planillaDetalle) {
		getPlanillaDetalles().add(planillaDetalle);
		planillaDetalle.setConvenioDetalle(this);

		return planillaDetalle;
	}

	public PlanillaDetalle removePlanillaDetalle(PlanillaDetalle planillaDetalle) {
		getPlanillaDetalles().remove(planillaDetalle);
		planillaDetalle.setConvenioDetalle(null);

		return planillaDetalle;
	}

}
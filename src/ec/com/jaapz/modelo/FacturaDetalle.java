package ec.com.jaapz.modelo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the factura_detalle database table.
 * 
 */
@Entity
@Table(name="factura_detalle")
@NamedQuery(name="FacturaDetalle.findAll", query="SELECT f FROM FacturaDetalle f")
public class FacturaDetalle implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_detalle_fac")
	private Integer idDetalleFac;

	private String estado;

	private double subtotal;

	//bi-directional many-to-one association to Factura
	@ManyToOne
	@JoinColumn(name="id_factura")
	private Factura factura;

	//bi-directional many-to-one association to Planilla
	@ManyToOne
	@JoinColumn(name="id_planilla")
	private Planilla planilla;

	public FacturaDetalle() {
	}

	public Integer getIdDetalleFac() {
		return this.idDetalleFac;
	}

	public void setIdDetalleFac(Integer idDetalleFac) {
		this.idDetalleFac = idDetalleFac;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public double getSubtotal() {
		return this.subtotal;
	}

	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}

	public Factura getFactura() {
		return this.factura;
	}

	public void setFactura(Factura factura) {
		this.factura = factura;
	}

	public Planilla getPlanilla() {
		return this.planilla;
	}

	public void setPlanilla(Planilla planilla) {
		this.planilla = planilla;
	}

}
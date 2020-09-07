package ec.com.jaapz.modelo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ingreso_detalle database table.
 * 
 */
@Entity
@Table(name="ingreso_detalle")
@NamedQueries({
	@NamedQuery(name="IngresoDetalle.findAll", query="SELECT i FROM IngresoDetalle i"),
})
public class IngresoDetalle implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_ingreso_det")
	private Integer idIngresoDet;

	private Integer cantidad;

	private String estado;

	private double precio;

	private double total;

	//bi-directional many-to-one association to Ingreso
	@ManyToOne
	@JoinColumn(name="id_ingreso")
	private Ingreso ingreso;

	//bi-directional many-to-one association to Rubro
	@ManyToOne
	@JoinColumn(name="id_rubro")
	private Rubro rubro;

	public IngresoDetalle() {
	}

	public Integer getIdIngresoDet() {
		return this.idIngresoDet;
	}

	public void setIdIngresoDet(Integer idIngresoDet) {
		this.idIngresoDet = idIngresoDet;
	}

	public Integer getCantidad() {
		return this.cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public double getPrecio() {
		return this.precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public double getTotal() {
		return this.total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public Ingreso getIngreso() {
		return this.ingreso;
	}

	public void setIngreso(Ingreso ingreso) {
		this.ingreso = ingreso;
	}

	public Rubro getRubro() {
		return this.rubro;
	}

	public void setRubro(Rubro rubro) {
		this.rubro = rubro;
	}

}
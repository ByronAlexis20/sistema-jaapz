package ec.com.jaapz.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the seg_menu database table.
 * 
 */
@Entity
@Table(name="seg_menu")
@NamedQueries({
	@NamedQuery(name="SegMenu.findAll", query="SELECT s FROM SegMenu s ORDER BY s.posicion"),
	@NamedQuery(name="SegMenu.buscarMenu", query="SELECT s FROM SegMenu s where s.idMenuPadre <> 0 ORDER BY s.idMenuPadre"),
	@NamedQuery(name="SegMenu.BuscarPadre", query="SELECT s FROM SegMenu s where s.idMenuPadre = 0"),
	@NamedQuery(name="SegMenu.BuscarPadrePorId", query="SELECT s FROM SegMenu s where (s.idMenu = :patron) ORDER BY s.posicion")
})
public class SegMenu implements Serializable, Comparable<SegMenu> {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_menu")
	private Integer idMenu;

	private String descripcion;

	private String estado;

	@Column(name="fxml_asociado")
	private Boolean fxmlAsociado;

	private String icono;

	@Column(name="id_menu_padre")
	private Integer idMenuPadre;

	@Column(name="nombre_fxml")
	private String nombreFxml;

	private Integer posicion;

	//bi-directional many-to-one association to SegPermiso
	@OneToMany(mappedBy="segMenu", cascade = CascadeType.ALL)
	private List<SegPermiso> segPermisos;

	public SegMenu() {
	}

	public Integer getIdMenu() {
		return this.idMenu;
	}

	public void setIdMenu(Integer idMenu) {
		this.idMenu = idMenu;
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

	public Boolean getFxmlAsociado() {
		return this.fxmlAsociado;
	}

	public void setFxmlAsociado(Boolean fxmlAsociado) {
		this.fxmlAsociado = fxmlAsociado;
	}

	public String getIcono() {
		return this.icono;
	}

	public void setIcono(String icono) {
		this.icono = icono;
	}

	public Integer getIdMenuPadre() {
		return this.idMenuPadre;
	}

	public void setIdMenuPadre(Integer idMenuPadre) {
		this.idMenuPadre = idMenuPadre;
	}

	public String getNombreFxml() {
		return this.nombreFxml;
	}

	public void setNombreFxml(String nombreFxml) {
		this.nombreFxml = nombreFxml;
	}

	public Integer getPosicion() {
		return this.posicion;
	}

	public void setPosicion(Integer posicion) {
		this.posicion = posicion;
	}

	public List<SegPermiso> getSegPermisos() {
		return this.segPermisos;
	}

	public void setSegPermisos(List<SegPermiso> segPermisos) {
		this.segPermisos = segPermisos;
	}

	public SegPermiso addSegPermiso(SegPermiso segPermiso) {
		getSegPermisos().add(segPermiso);
		segPermiso.setSegMenu(this);

		return segPermiso;
	}

	public SegPermiso removeSegPermiso(SegPermiso segPermiso) {
		getSegPermisos().remove(segPermiso);
		segPermiso.setSegMenu(null);

		return segPermiso;
	}
	@Override
    public int compareTo(SegMenu o) {
        if (this.posicion < o.posicion) {
            return -1;
        }
        if (this.posicion  > o.posicion) {
            return 1;
        }
        return 0;
    }
}
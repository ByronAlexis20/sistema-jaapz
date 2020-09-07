package ec.com.jaapz.modelo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the seg_permiso database table.
 * 
 */
@Entity
@Table(name="seg_permiso")
@NamedQueries({
	@NamedQuery(name="SegPermiso.findAll", query="SELECT s FROM SegPermiso s"),
	@NamedQuery(name="SegPermiso.buscarPermisoPerfil", query="SELECT s FROM SegPermiso s "
			+ "where s.segPerfil.idPerfil = (:patron) and s.estado = 'A' and s.segMenu.estado = 'A' ORDER BY s.segMenu.posicion"),
	@NamedQuery(name="SegPermiso.buscarPermiso", query="SELECT s FROM SegPermiso s "
			+ "where s.segPerfil.idPerfil = (:patron) and s.estado = 'A' and s.segMenu.idMenuPadre <> 0")
})
public class SegPermiso implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_permiso")
	private Integer idPermiso;

	private String estado;

	//bi-directional many-to-one association to SegMenu
	@ManyToOne
	@JoinColumn(name="id_menu")
	private SegMenu segMenu;

	//bi-directional many-to-one association to SegPerfil
	@ManyToOne
	@JoinColumn(name="id_perfil")
	private SegPerfil segPerfil;

	public SegPermiso() {
	}

	public Integer getIdPermiso() {
		return this.idPermiso;
	}

	public void setIdPermiso(Integer idPermiso) {
		this.idPermiso = idPermiso;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public SegMenu getSegMenu() {
		return this.segMenu;
	}

	public void setSegMenu(SegMenu segMenu) {
		this.segMenu = segMenu;
	}

	public SegPerfil getSegPerfil() {
		return this.segPerfil;
	}

	public void setSegPerfil(SegPerfil segPerfil) {
		this.segPerfil = segPerfil;
	}

}
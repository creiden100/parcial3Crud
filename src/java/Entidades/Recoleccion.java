/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author SERGIO
 */
@Entity
@Table(name = "recoleccion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Recoleccion.findAll", query = "SELECT r FROM Recoleccion r"),
    @NamedQuery(name = "Recoleccion.findByIdRecoleccion", query = "SELECT r FROM Recoleccion r WHERE r.idRecoleccion = :idRecoleccion"),
    @NamedQuery(name = "Recoleccion.findByUbicacion", query = "SELECT r FROM Recoleccion r WHERE r.ubicacion = :ubicacion"),
    @NamedQuery(name = "Recoleccion.findByFecha", query = "SELECT r FROM Recoleccion r WHERE r.fecha = :fecha"),
    @NamedQuery(name = "Recoleccion.findByColmenaMadre", query = "SELECT r FROM Recoleccion r WHERE r.colmenaMadre = :colmenaMadre"),
    @NamedQuery(name = "Recoleccion.findByKilosDeMiel", query = "SELECT r FROM Recoleccion r WHERE r.kilosDeMiel = :kilosDeMiel"),
    @NamedQuery(name = "Recoleccion.findByPanelesConCera", query = "SELECT r FROM Recoleccion r WHERE r.panelesConCera = :panelesConCera"),
    @NamedQuery(name = "Recoleccion.findByPanelesConAlimento", query = "SELECT r FROM Recoleccion r WHERE r.panelesConAlimento = :panelesConAlimento"),
    @NamedQuery(name = "Recoleccion.findByRecolector", query = "SELECT r FROM Recoleccion r WHERE r.recolector = :recolector")})
public class Recoleccion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "Id_Recoleccion")
    private Integer idRecoleccion;
    @Size(max = 30)
    @Column(name = "Ubicacion")
    private String ubicacion;
    @Column(name = "Fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "ColmenaMadre")
    private Integer colmenaMadre;
    @Column(name = "KilosDeMiel")
    private Integer kilosDeMiel;
    @Column(name = "PanelesConCera")
    private Integer panelesConCera;
    @Column(name = "PanelesConAlimento")
    private Integer panelesConAlimento;
    @Size(max = 30)
    @Column(name = "Recolector")
    private String recolector;
    @JoinColumn(name = "Id_Colmena", referencedColumnName = "Id_Colmena")
    @ManyToOne
    private Colmena idColmena;

    public Recoleccion() {
    }

    public Recoleccion(Integer idRecoleccion) {
        this.idRecoleccion = idRecoleccion;
    }

    public Integer getIdRecoleccion() {
        return idRecoleccion;
    }

    public void setIdRecoleccion(Integer idRecoleccion) {
        this.idRecoleccion = idRecoleccion;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getColmenaMadre() {
        return colmenaMadre;
    }

    public void setColmenaMadre(Integer colmenaMadre) {
        this.colmenaMadre = colmenaMadre;
    }

    public Integer getKilosDeMiel() {
        return kilosDeMiel;
    }

    public void setKilosDeMiel(Integer kilosDeMiel) {
        this.kilosDeMiel = kilosDeMiel;
    }

    public Integer getPanelesConCera() {
        return panelesConCera;
    }

    public void setPanelesConCera(Integer panelesConCera) {
        this.panelesConCera = panelesConCera;
    }

    public Integer getPanelesConAlimento() {
        return panelesConAlimento;
    }

    public void setPanelesConAlimento(Integer panelesConAlimento) {
        this.panelesConAlimento = panelesConAlimento;
    }

    public String getRecolector() {
        return recolector;
    }

    public void setRecolector(String recolector) {
        this.recolector = recolector;
    }

    public Colmena getIdColmena() {
        return idColmena;
    }

    public void setIdColmena(Colmena idColmena) {
        this.idColmena = idColmena;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRecoleccion != null ? idRecoleccion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Recoleccion)) {
            return false;
        }
        Recoleccion other = (Recoleccion) object;
        if ((this.idRecoleccion == null && other.idRecoleccion != null) || (this.idRecoleccion != null && !this.idRecoleccion.equals(other.idRecoleccion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Recoleccion[ idRecoleccion=" + idRecoleccion + " ]";
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author SERGIO
 */
@Entity
@Table(name = "colmena")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Colmena.findAll", query = "SELECT c FROM Colmena c"),
    @NamedQuery(name = "Colmena.findByIdColmena", query = "SELECT c FROM Colmena c WHERE c.idColmena = :idColmena"),
    @NamedQuery(name = "Colmena.findByCantidadPoblacion", query = "SELECT c FROM Colmena c WHERE c.cantidadPoblacion = :cantidadPoblacion"),
    @NamedQuery(name = "Colmena.findByCalidadPoblacion", query = "SELECT c FROM Colmena c WHERE c.calidadPoblacion = :calidadPoblacion"),
    @NamedQuery(name = "Colmena.findByPresenciaDeReina", query = "SELECT c FROM Colmena c WHERE c.presenciaDeReina = :presenciaDeReina"),
    @NamedQuery(name = "Colmena.findByProduccionMiel", query = "SELECT c FROM Colmena c WHERE c.produccionMiel = :produccionMiel"),
    @NamedQuery(name = "Colmena.findByPanelesConCera", query = "SELECT c FROM Colmena c WHERE c.panelesConCera = :panelesConCera"),
    @NamedQuery(name = "Colmena.findByPanelesConAlimento", query = "SELECT c FROM Colmena c WHERE c.panelesConAlimento = :panelesConAlimento"),
    @NamedQuery(name = "Colmena.findByPanelesConCria", query = "SELECT c FROM Colmena c WHERE c.panelesConCria = :panelesConCria"),
    @NamedQuery(name = "Colmena.findByPanelesVacios", query = "SELECT c FROM Colmena c WHERE c.panelesVacios = :panelesVacios")})
public class Colmena implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "Id_Colmena")
    private Integer idColmena;
    @Size(max = 30)
    @Column(name = "CantidadPoblacion")
    private String cantidadPoblacion;
    @Size(max = 30)
    @Column(name = "CalidadPoblacion")
    private String calidadPoblacion;
    @Size(max = 30)
    @Column(name = "PresenciaDeReina")
    private String presenciaDeReina;
    @Size(max = 30)
    @Column(name = "ProduccionMiel")
    private String produccionMiel;
    @Column(name = "PanelesConCera")
    private Integer panelesConCera;
    @Column(name = "PanelesConAlimento")
    private Integer panelesConAlimento;
    @Column(name = "PanelesConCria")
    private Integer panelesConCria;
    @Column(name = "PanelesVacios")
    private Integer panelesVacios;
    @OneToMany(mappedBy = "idColmena")
    private Collection<Apiario> apiarioCollection;
    @OneToMany(mappedBy = "idColmena")
    private Collection<Revision> revisionCollection;
    @OneToMany(mappedBy = "idColmena")
    private Collection<Recoleccion> recoleccionCollection;

    public Colmena() {
    }

    public Colmena(Integer idColmena) {
        this.idColmena = idColmena;
    }

    public Integer getIdColmena() {
        return idColmena;
    }

    public void setIdColmena(Integer idColmena) {
        this.idColmena = idColmena;
    }

    public String getCantidadPoblacion() {
        return cantidadPoblacion;
    }

    public void setCantidadPoblacion(String cantidadPoblacion) {
        this.cantidadPoblacion = cantidadPoblacion;
    }

    public String getCalidadPoblacion() {
        return calidadPoblacion;
    }

    public void setCalidadPoblacion(String calidadPoblacion) {
        this.calidadPoblacion = calidadPoblacion;
    }

    public String getPresenciaDeReina() {
        return presenciaDeReina;
    }

    public void setPresenciaDeReina(String presenciaDeReina) {
        this.presenciaDeReina = presenciaDeReina;
    }

    public String getProduccionMiel() {
        return produccionMiel;
    }

    public void setProduccionMiel(String produccionMiel) {
        this.produccionMiel = produccionMiel;
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

    public Integer getPanelesConCria() {
        return panelesConCria;
    }

    public void setPanelesConCria(Integer panelesConCria) {
        this.panelesConCria = panelesConCria;
    }

    public Integer getPanelesVacios() {
        return panelesVacios;
    }

    public void setPanelesVacios(Integer panelesVacios) {
        this.panelesVacios = panelesVacios;
    }

    @XmlTransient
    public Collection<Apiario> getApiarioCollection() {
        return apiarioCollection;
    }

    public void setApiarioCollection(Collection<Apiario> apiarioCollection) {
        this.apiarioCollection = apiarioCollection;
    }

    @XmlTransient
    public Collection<Revision> getRevisionCollection() {
        return revisionCollection;
    }

    public void setRevisionCollection(Collection<Revision> revisionCollection) {
        this.revisionCollection = revisionCollection;
    }

    @XmlTransient
    public Collection<Recoleccion> getRecoleccionCollection() {
        return recoleccionCollection;
    }

    public void setRecoleccionCollection(Collection<Recoleccion> recoleccionCollection) {
        this.recoleccionCollection = recoleccionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idColmena != null ? idColmena.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Colmena)) {
            return false;
        }
        Colmena other = (Colmena) object;
        if ((this.idColmena == null && other.idColmena != null) || (this.idColmena != null && !this.idColmena.equals(other.idColmena))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Colmena[ idColmena=" + idColmena + " ]";
    }
    
}

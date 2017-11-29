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
@Table(name = "revision")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Revision.findAll", query = "SELECT r FROM Revision r"),
    @NamedQuery(name = "Revision.findByIdRevision", query = "SELECT r FROM Revision r WHERE r.idRevision = :idRevision"),
    @NamedQuery(name = "Revision.findByTecnico", query = "SELECT r FROM Revision r WHERE r.tecnico = :tecnico"),
    @NamedQuery(name = "Revision.findByFecha", query = "SELECT r FROM Revision r WHERE r.fecha = :fecha")})
public class Revision implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "Id_Revision")
    private Integer idRevision;
    @Size(max = 30)
    @Column(name = "Tecnico")
    private String tecnico;
    @Column(name = "Fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @JoinColumn(name = "Id_Colmena", referencedColumnName = "Id_Colmena")
    @ManyToOne
    private Colmena idColmena;

    public Revision() {
    }

    public Revision(Integer idRevision) {
        this.idRevision = idRevision;
    }

    public Integer getIdRevision() {
        return idRevision;
    }

    public void setIdRevision(Integer idRevision) {
        this.idRevision = idRevision;
    }

    public String getTecnico() {
        return tecnico;
    }

    public void setTecnico(String tecnico) {
        this.tecnico = tecnico;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
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
        hash += (idRevision != null ? idRevision.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Revision)) {
            return false;
        }
        Revision other = (Revision) object;
        if ((this.idRevision == null && other.idRevision != null) || (this.idRevision != null && !this.idRevision.equals(other.idRevision))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Revision[ idRevision=" + idRevision + " ]";
    }
    
}

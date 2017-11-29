/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author SERGIO
 */
@Entity
@Table(name = "oro_legal")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OroLegal.findAll", query = "SELECT o FROM OroLegal o"),
    @NamedQuery(name = "OroLegal.findByIdOrolegal", query = "SELECT o FROM OroLegal o WHERE o.idOrolegal = :idOrolegal")})
public class OroLegal implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_Oro_legal")
    private Integer idOrolegal;

    public OroLegal() {
    }

    public OroLegal(Integer idOrolegal) {
        this.idOrolegal = idOrolegal;
    }

    public Integer getIdOrolegal() {
        return idOrolegal;
    }

    public void setIdOrolegal(Integer idOrolegal) {
        this.idOrolegal = idOrolegal;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idOrolegal != null ? idOrolegal.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OroLegal)) {
            return false;
        }
        OroLegal other = (OroLegal) object;
        if ((this.idOrolegal == null && other.idOrolegal != null) || (this.idOrolegal != null && !this.idOrolegal.equals(other.idOrolegal))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.OroLegal[ idOrolegal=" + idOrolegal + " ]";
    }
    
}

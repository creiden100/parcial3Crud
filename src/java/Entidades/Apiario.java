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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author SERGIO
 */
@Entity
@Table(name = "apiario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Apiario.findAll", query = "SELECT a FROM Apiario a"),
    @NamedQuery(name = "Apiario.findByIdApiario", query = "SELECT a FROM Apiario a WHERE a.idApiario = :idApiario"),
    @NamedQuery(name = "Apiario.findByFabrica", query = "SELECT a FROM Apiario a WHERE a.fabrica = :fabrica")})
public class Apiario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_Apiario")
    private Integer idApiario;
    @Size(max = 30)
    @Column(name = "Fabrica")
    private String fabrica;
    @JoinColumn(name = "Id_Colmena", referencedColumnName = "Id_Colmena")
    @ManyToOne
    private Colmena idColmena;

    public Apiario() {
    }

    public Apiario(Integer idApiario) {
        this.idApiario = idApiario;
    }

    public Integer getIdApiario() {
        return idApiario;
    }

    public void setIdApiario(Integer idApiario) {
        this.idApiario = idApiario;
    }

    public String getFabrica() {
        return fabrica;
    }

    public void setFabrica(String fabrica) {
        this.fabrica = fabrica;
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
        hash += (idApiario != null ? idApiario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Apiario)) {
            return false;
        }
        Apiario other = (Apiario) object;
        if ((this.idApiario == null && other.idApiario != null) || (this.idApiario != null && !this.idApiario.equals(other.idApiario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Apiario[ idApiario=" + idApiario + " ]";
    }
    
}

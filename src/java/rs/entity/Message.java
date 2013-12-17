/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author kevin
 */
@Entity
@Table(name = "MESSAGE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Message.findAll", query = "SELECT m FROM Message m")})
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "Message")
    @TableGenerator(name = "Message", allocationSize = 1)
    @Column(name = "IDMESSAGE")
    private Integer idmessage;
    @Size(max = 255)
    @Column(name = "TITRE")
    private String titre;
    @Size(max = 8000)
    @Column(name = "CONTENU")
    private String contenu;
    @Column(name = "LU")
    private Integer lu;
    @Column(name = "DATEENVOI")
    @Temporal(TemporalType.DATE)
    private Date dateenvoi;
    @JoinColumn(name = "EXPEDITEUR", referencedColumnName = "IDUSER")
    @ManyToOne(optional = false)
    private Users expediteur;
    @JoinColumn(name = "DESTINATAIRE", referencedColumnName = "IDUSER")
    @ManyToOne(optional = false)
    private Users destinataire;

    public Message() {
    }

    public Message(Integer idmessage) {
        this.idmessage = idmessage;
    }

    public Integer getIdmessage() {
        return idmessage;
    }

    public void setIdmessage(Integer idmessage) {
        this.idmessage = idmessage;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Integer getLu() {
        return lu;
    }

    public void setLu(Integer lu) {
        this.lu = lu;
    }

    public Date getDateenvoi() {
        return dateenvoi;
    }

    public void setDateenvoi(Date dateenvoi) {
        this.dateenvoi = dateenvoi;
    }

    public Users getExpediteur() {
        return expediteur;
    }

    public void setExpediteur(Users expediteur) {
        this.expediteur = expediteur;
    }

    public Users getDestinataire() {
        return destinataire;
    }

    public void setDestinataire(Users destinataire) {
        this.destinataire = destinataire;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idmessage != null ? idmessage.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Message)) {
            return false;
        }
        Message other = (Message) object;
        if ((this.idmessage == null && other.idmessage != null) || (this.idmessage != null && !this.idmessage.equals(other.idmessage))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rs.entity.Message[ idmessage=" + idmessage + " ]";
    }

}

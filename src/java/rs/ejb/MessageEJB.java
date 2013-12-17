/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ejb;

import java.util.List;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import rs.entity.Message;
import rs.entity.Users;

/**
 *
 * @author kevin
 */
@Stateful
public class MessageEJB {

    @PersistenceContext(unitName = "RecetteScoutPU")
    private EntityManager em;

    public void persist(Object object) {
        em.persist(object);
    }

    public List<Message> getMessageRecu(Users u) {
        Query q = em.createQuery("SELECT m FROM Message m where m.destinataire.iduser = :dest");
        q.setParameter("dest", u.getIduser());
        List<Message> coll = q.getResultList();
        return coll;
    }

    public List<Message> getMessageEnvoye(Users u) {
        Query q = em.createQuery("SELECT m FROM Message m where m.expediteur.iduser = :exp");
        q.setParameter("exp", u.getIduser());
        List<Message> coll = q.getResultList();
        return coll;
    }

    public Message nvMessage(Message mess) {
        Message m = new Message();
        m.setDestinataire(mess.getDestinataire());
        m.setExpediteur(mess.getExpediteur());
        m.setDateenvoi(mess.getDateenvoi());
        m.setTitre(mess.getTitre());
        m.setContenu(mess.getContenu());
        m.setLu(mess.getLu());
        
        em.persist(m);
        return m;
    }

}

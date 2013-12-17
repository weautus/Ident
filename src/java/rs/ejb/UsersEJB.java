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
import rs.entity.Users;

/**
 *
 * @author kevin
 */
@Stateful
public class UsersEJB {

    @PersistenceContext(unitName = "RecetteScoutPU")
    private EntityManager em;

    public void persist(Object object) {
        em.persist(object);
    }

    public Users connecter(String login, String mdp) {
        Query q = em.createQuery("SELECT m FROM Users m WHERE m.login = :login");
        q.setParameter("login", login);
        if (!q.getResultList().isEmpty()) {
            Users m = (Users) q.getSingleResult();
            if (m.getMdp().equals(mdp)) {
                return m;
            }
        }
        return null;
    }

    public boolean loginPresent(String login) {
        Query q = em.createQuery("SELECT m FROM Users m WHERE m.login = :login");
        q.setParameter("login", login);
        return !q.getResultList().isEmpty();
    }

    public Users getUsers(String login) {
        Query q = em.createQuery("SELECT m FROM Users m WHERE m.login = :login");
        q.setParameter("login", login);
        return (Users) q.getSingleResult();
    }

    public Users inscrire(Users nouveau) {
        // Vérifie pas d'injection SQL (devrait le faire pour tous les champs ...)

        Query q = em.createQuery("SELECT m FROM Users m WHERE m.login = :login");
        q.setParameter("login", nouveau.getLogin());
        if (q.getResultList().isEmpty()) {
            Users m = new Users();
            m.setLogin(nouveau.getLogin());
            m.setMdp(nouveau.getMdp());
            m.setEmail(nouveau.getEmail());

            em.persist(m);
            return m;
        }
        return null;
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    public List<Users> allUsers() {
        Query q = em.createQuery("SELECT m FROM Users m");
        List<Users> userList = q.getResultList();
        return userList;
    }
}

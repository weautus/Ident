/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ManagedBeans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import rs.ejb.MessageEJB;
import rs.ejb.UsersEJB;
import rs.entity.Message;
import rs.entity.Users;

/**
 *
 * @author kevin
 */
@ManagedBean(name = "usersCtrl")
@SessionScoped
public class UsersController {

    @EJB
    private UsersEJB ejbUsers;

    @EJB
    private MessageEJB ejbMessage;

    private String login;
    private String mdp;
    private String erreur;

    //USERS
    private List<Users> allUsers;

    private Users userConnecte;
    private Users userACreer;
    private boolean estConnecte;

    //MESSAGES
    private List<Message> allMessagesRecu;
    private List<Message> allMessagesEnvoye;
    private Message selectedMes;
    private Message messageACreer;
    private String erreurMessage;

    int cc [][] = new int[4][4];
    
    //METHODES
    public String envoyerMessage() {
        messageACreer.setDateenvoi(new Date());
        messageACreer.setLu(0);
        messageACreer.setExpediteur(userConnecte);
        if (!ejbUsers.loginPresent(login)) {
            erreurMessage = "Login non valide";
            return "GOECRIREMES";
        }
        messageACreer.setDestinataire(ejbUsers.getUsers(login));
        Message test = ejbMessage.nvMessage(messageACreer);
        if (test == null) {
            erreurMessage = "Probleme de persistance";
            return "GOECRIREMES";
        }
        erreurMessage = null;
        allMessagesRecu = ejbMessage.getMessageRecu(userConnecte);
        allMessagesEnvoye = ejbMessage.getMessageEnvoye(userConnecte);
        selectedMes = null;
        return "GOBOITE";
    }

    public String goEcrireMessage() {
        selectedMes = null;
        login = null;
        allUsers = ejbUsers.allUsers();
        messageACreer = new Message();
        return "GOECRIREMES";
    }

    public String goLireMessage(Message mes) {
        selectedMes = mes;
        return "GOLIREMES";
    }

    public String goBoite() {
        allMessagesRecu = ejbMessage.getMessageRecu(userConnecte);
        allMessagesEnvoye = ejbMessage.getMessageEnvoye(userConnecte);
        selectedMes = null;
        return "GOBOITE";
    }

    public String seDeconnecter() {
        userConnecte = null;
        selectedMes = null;
        login = null;
        return "DECONNECTER";
    }

    public String identifier() {
        userConnecte = ejbUsers.connecter(login, mdp);
        if (userConnecte == null) {
            erreur = "Login ou mot de passe invalide.";
            return "LOGGER";
        }
        estConnecte = true;
        erreur = null;
        return "LOGGER";
    }

    public String goInscription() {
        userACreer = new Users();
        erreur = null;
        return "INSCRIPTION";
    }

    public String goInscriptionFin() {
        erreur = null;
        if (!ejbUsers.loginPresent(userACreer.getLogin())) {
            userConnecte = ejbUsers.inscrire(userACreer);
            if (userConnecte == null) {
                erreur = "Erreur lors de l'inscription : le pseudo est déjà utilisé";
                return "REINSCRIPTION";
            }
            userACreer = null;
            estConnecte = true;
            return "INSCRIPTIONFINOK";
        } else {
            erreur = "login déjà utilisé";
            return "REINSCRIPTION";
        }
    }

    public String goIndex() {
        return "GOINDEX";
    }

    /**
     * Get the value of erreur
     *
     * @return the value of erreur
     */
    public String getErreur() {
        return erreur;
    }

    /**
     * Set the value of erreur
     *
     * @param erreur new value of erreur
     */
    public void setErreur(String erreur) {
        this.erreur = erreur;
    }

    /**
     * Get the value of mdp
     *
     * @return the value of mdp
     */
    public String getMdp() {
        return mdp;
    }

    /**
     * Set the value of mdp
     *
     * @param mdp new value of mdp
     */
    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    /**
     * Get the value of login
     *
     * @return the value of login
     */
    public String getLogin() {
        return login;
    }

    /**
     * Set the value of login
     *
     * @param login new value of login
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Get the value of userConnecte
     *
     * @return the value of userConnecte
     */
    public Users getUserConnecte() {
        return userConnecte;
    }

    /**
     * Set the value of userConnecte
     *
     * @param userConnecte new value of userConnecte
     */
    public void setUserConnecte(Users userConnecte) {
        this.userConnecte = userConnecte;
    }

    /**
     * Get the value of estConnecte
     *
     * @return the value of estConnecte
     */
    public boolean isEstConnecte() {
        return estConnecte;
    }

    /**
     * Set the value of estConnecte
     *
     * @param estConnecte new value of estConnecte
     */
    public void setEstConnecte(boolean estConnecte) {
        this.estConnecte = estConnecte;
    }

    /**
     * Get the value of userACreer
     *
     * @return the value of userACreer
     */
    public Users getUserACreer() {
        return userACreer;
    }

    /**
     * Set the value of userACreer
     *
     * @param userACreer new value of userACreer
     */
    public void setUserACreer(Users userACreer) {
        this.userACreer = userACreer;
    }

    /**
     * Get the value of allMessages
     *
     * @return the value of allMessages
     */
    public List<Message> getAllMessagesRecu() {
        return allMessagesRecu;
    }

    /**
     * Set the value of allMessages
     *
     * @param allMessages new value of allMessages
     */
    public void setAllMessagesRecu(List<Message> allMessages) {
        this.allMessagesRecu = allMessages;
    }

    /**
     * Get the value of allMessagesEnvoye
     *
     * @return the value of allMessagesEnvoye
     */
    public List<Message> getAllMessagesEnvoye() {
        return allMessagesEnvoye;
    }

    /**
     * Set the value of allMessagesEnvoye
     *
     * @param allMessagesEnvoye new value of allMessagesEnvoye
     */
    public void setAllMessagesEnvoye(List<Message> allMessagesEnvoye) {
        this.allMessagesEnvoye = allMessagesEnvoye;
    }

    /**
     * Get the value of selectedMes
     *
     * @return the value of selectedMes
     */
    public Message getSelectedMes() {
        return selectedMes;
    }

    /**
     * Set the value of selectedMes
     *
     * @param selectedMes new value of selectedMes
     */
    public void setSelectedMes(Message selectedMes) {
        this.selectedMes = selectedMes;
    }

    /**
     * Get the value of messageACreer
     *
     * @return the value of messageACreer
     */
    public Message getMessageACreer() {
        return messageACreer;
    }

    /**
     * Set the value of messageACreer
     *
     * @param messageACreer new value of messageACreer
     */
    public void setMessageACreer(Message messageACreer) {
        this.messageACreer = messageACreer;
    }

    /**
     * Get the value of allUsers
     *
     * @return the value of allUsers
     */
    public List<Users> getAllUsers() {
        return allUsers;
    }

    /**
     * Set the value of allUsers
     *
     * @param allUsers new value of allUsers
     */
    public void setAllUsers(List<Users> allUsers) {
        this.allUsers = allUsers;
    }

    /**
     * Get the value of erreurMessage
     *
     * @return the value of erreurMessage
     */
    public String getErreurMessage() {
        return erreurMessage;
    }

    /**
     * Set the value of erreurMessage
     *
     * @param erreurMessage new value of erreurMessage
     */
    public void setErreurMessage(String erreurMessage) {
        this.erreurMessage = erreurMessage;
    }

    /**
     * Creates a new instance of UsersController
     */
    public UsersController() {
    }

}

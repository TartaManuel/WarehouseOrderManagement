package bll;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import bll.validators.ClientAgeValidator;
import bll.validators.EmailValidator;
import bll.validators.Validator;
import dao.ClientDAO;
import model.Client;

/**
 * Clasa care face validarile si apeleaza toate metodele pentru client
 * Face operatiile cu baza de date doar daca datele pentru Client sunt valide
 */

public class ClientBLL {

    private List<Validator<Client>> validators;
    private ClientDAO clientDAO;

    public ClientBLL() {
        validators = new ArrayList<Validator<Client>>();
        validators.add(new EmailValidator()); //cei 2 validatori, email si age
        validators.add(new ClientAgeValidator());

        clientDAO = new ClientDAO();
    }

    public List<Client> findALlClientsBLL() { //functia care ia toti clientii din baza de date
        List<Client> st = clientDAO.findAllClients();//apeleaza metoda din DAO care foloseste metoda abstracta pe care
        //o face pe Client
        return st;
    }

    public Client findClientByIdBLL(int id) { //cauta un client anume cu un anumit Id
        Client st = clientDAO.findClientById(id);
        if (st == null) {//daca e null, nu l-am gasit si aruncam o exceptie
            throw new NoSuchElementException("The client with id =" + id + " was not found!");
        }
        return st;
    }

    public int insertClientBLL(Client client) {//metoda care insereaza un client in baza de date
        for (Validator<Client> v : validators) {
            v.validate(client);//prima data facem validarile, validate da throw la o exceptie daca nu e bine
        }
        ClientDAO c=new ClientDAO();
        return c.insertClient(client);//apelam metoda din DAO care foloseste metoda abstracta de inserare
    }

    public void updateClientBLL(Client client) { //metoda care updateaza un client anume
        //primeste ca parametru Clientul ca obiect, ii ia ID-ul si actualizeaza datele la acel id in bd
        for (Validator<Client> v : validators) {
            v.validate(client);//prima data facem validarile, validate da throw la o exceptie daca nu e bine
        }
        ClientDAO c=new ClientDAO();
        c.updateClient(client);
    }

    public void deleteClientBLL(int id) {
        clientDAO.deleteClient(id); //metoda care sterge un client de la un anumit id
    }

}

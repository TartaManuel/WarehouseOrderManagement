package dao;

import model.Client;

import java.util.List;
import java.util.NoSuchElementException;

public class ClientDAO extends AbstractDAO<Client>{

    public Client findClientById(int id) {
        return findById(id);
    }

    public List<Client> findAllClients(){
        return findAll();
    }

    public int insertClient(Client c){
        return insert(c);
    }

    public void deleteClient(int id){
       delete(id);
    }

    public void updateClient(Client c){
        update(c);

    }
}

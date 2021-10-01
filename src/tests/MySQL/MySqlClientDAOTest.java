package tests.MySQL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import dao.ClientDAO;
import dao.DAOFactory;
import dao.Persistance;
import metier.Client;

public class MySqlClientDAOTest 
{
    private DAOFactory daof;
    private ClientDAO clientDAO;

    @Before
    public void setUp()
    {
        daof = DAOFactory.getDAOFactory(Persistance.MYSQL);
        clientDAO = daof.getClientDAO();
    }


    @Test
    public void testCreate() throws SQLException
    {
        Client client = new Client("Nom", "Prenom", "1", "ma rue", "01234", "Ma ville", "France");

        clientDAO.create(client);

        Client clientRead = clientDAO.getByNomPrenom(client.getNom(), client.getPrenom()).get(0);

        client.setId(clientRead.getId());

        assertTrue(client.equals(clientRead));

        clientDAO.delete(clientRead);
    }

    @Test
    public void testUpdate() throws SQLException
    {
        Client client = new Client("Nom", "Prenom", "1", "ma rue", "01234", "Ma ville", "France");

        clientDAO.create(client);

        Client clientUpdate = new Client("nouveau Nom", "nouveau Prenom", "19", "ma nouvelle rue", "01234", "Ma nouvelle ville", "France");

        clientDAO.update(clientUpdate);

        Client clientRead = clientDAO.getByNomPrenom(client.getNom(), client.getPrenom()).get(0);

        client.setId(clientRead.getId());

        assertTrue(client.equals(clientRead));

        clientDAO.delete(clientRead);
    }

    @Test
    public void testDelete() throws SQLException
    {
        Client client = new Client("Code4589652", "Prenom", "1", "ma rue", "12548", "Ma ville", "France");

        clientDAO.create(client);

        Client clientRead = clientDAO.getByNomPrenom(client.getNom(), client.getPrenom()).get(0);

        client.setId(clientRead.getId());

        clientDAO.delete(clientRead);

        List<Client> liste = new ArrayList<>();

        liste = clientDAO.getByNomPrenom(client.getNom(), client.getPrenom());

        assertEquals(0, liste.size());
    }
}
package listememoire;

import dao.AbonnementDAO;
import dao.DAOFactory;
import dao.Persistance;
import metier.Abonnement;
import metier.Client;
import metier.Revue;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class ListeMemoireDAOAbonnementTest
{

    private DAOFactory daof;
    private AbonnementDAO abonnementDAO;

    @Before
    public void setUp() 
    {
        daof = DAOFactory.getDAOFactory(Persistance.ListeMemoire);
        abonnementDAO = daof.getAbonnementDAO();
    }

    @Test
    public void testCreate() throws SQLException
    {
        Abonnement abonnementAVerif = new Abonnement(3, LocalDate.parse("30/07/2347", DateTimeFormatter.ofPattern("dd/MM/yyyy")), LocalDate.parse("12/03/2347", DateTimeFormatter.ofPattern("dd/MM/yyyy")), new Client(1), new Revue(1));

        abonnementDAO.create(abonnementAVerif);

        Abonnement abonnementRead = abonnementDAO.getById(abonnementAVerif.getId());

        assertEquals(abonnementAVerif, abonnementRead);

        abonnementDAO.delete(abonnementRead);
    }

    @Test
    public void testUpdate() throws SQLException
    {
        Abonnement abonnementAVerif = new Abonnement(3, LocalDate.parse("30/07/2347", DateTimeFormatter.ofPattern("dd/MM/yyyy")), LocalDate.parse("12/03/2347", DateTimeFormatter.ofPattern("dd/MM/yyyy")), new Client(1), new Revue(1));

        abonnementDAO.create(abonnementAVerif);

        Abonnement abonnementUpdate = new Abonnement(3, LocalDate.parse("30/07/2347", DateTimeFormatter.ofPattern("dd/MM/yyyy")), LocalDate.parse("12/03/2347", DateTimeFormatter.ofPattern("dd/MM/yyyy")), new Client(1), new Revue(1));

        abonnementDAO.update(abonnementUpdate);

        Abonnement abonnementRead = abonnementDAO.getById(abonnementUpdate.getId());
        
        assertEquals(abonnementUpdate, abonnementRead);

        abonnementDAO.delete(abonnementRead);
    }

    @Test
    public void testDelete() throws SQLException
    {
        Abonnement abonnementAVerif = new Abonnement(3, LocalDate.parse("30/07/2347", DateTimeFormatter.ofPattern("dd/MM/yyyy")), LocalDate.parse("12/03/2347", DateTimeFormatter.ofPattern("dd/MM/yyyy")), new Client(1), new Revue(1));

        abonnementDAO.create(abonnementAVerif);

        abonnementDAO.delete(abonnementAVerif);

        assertFalse(abonnementDAO.findAll().contains(abonnementAVerif));
    }
}

package dao.mysql;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import connexion.Connexion;
import dao.AbonnementDAO;
import metier.Abonnement;
import metier.Client;
import metier.Revue;

public class MySqlAbonnementDAO implements AbonnementDAO
{
    private Connexion maBD;
    private Connection laConnexion;

    private static MySqlAbonnementDAO instance;
    
    /* @return une instance de MySqlDao en singleton */
    public static AbonnementDAO getInstance() 
    {
        if (instance == null) {
            instance = new MySqlAbonnementDAO();
        }
        return instance;
    }

    private MySqlAbonnementDAO() 
    {
        maBD = Connexion.getInstance();
        laConnexion = maBD.creeConnexion();
    }

    @Override
    public boolean create(Abonnement objet) throws SQLException
    {
        maBD = Connexion.getInstance();
        laConnexion = maBD.creeConnexion();

        PreparedStatement requete = laConnexion.prepareStatement("INSERT INTO Abonnement(date_debut, date_fin, id_client, id_revue) Values (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        requete.setDate(1, java.sql.Date.valueOf(objet.getDateDebut()));
        requete.setDate(2, java.sql.Date.valueOf(objet.getDateFin()));
        requete.setInt(3, objet.getIdClient());
        requete.setInt(4, objet.getIdRevue());

        int nbLignes = requete.executeUpdate();
        ResultSet res = requete.getGeneratedKeys();

        if (res.next())
        {
            objet.setId(res.getInt(1));
        }
        if (laConnexion != null)
            laConnexion.close();

        return (nbLignes == 1);
    }

    @Override
    public boolean update(Abonnement objet) throws SQLException 
    {
        maBD = Connexion.getInstance();
        laConnexion = maBD.creeConnexion();

        PreparedStatement requete = laConnexion.prepareStatement("UPDATE Abonnement SET date_debut = ?, date_fin = ?, id_client = ?, id_revue = ? WHERE id_abonnement =  ?");
        requete.setDate(1, java.sql.Date.valueOf(objet.getDateDebut()));
        requete.setDate(2, java.sql.Date.valueOf(objet.getDateFin()));
        requete.setInt(3, objet.getIdClient());
        requete.setInt(4, objet.getIdRevue());
        requete.setInt(5, objet.getId());

        int nbLignes = requete.executeUpdate();

        if (laConnexion != null)
            laConnexion.close();

        return (nbLignes == 1);
    }

    @Override
    public boolean delete(Abonnement objet) throws SQLException 
    {
        maBD = Connexion.getInstance();
        laConnexion = maBD.creeConnexion();

        PreparedStatement requete = laConnexion.prepareStatement("DELETE FROM Abonnement WHERE id_abonnement = ?");
        requete.setInt(1, objet.getId());

        int nbLignes = requete.executeUpdate();

        if (laConnexion != null)
            laConnexion.close();

        return (nbLignes == 1);
    }

    @Override
    public Abonnement getById(int i) throws SQLException 
    {
        maBD = Connexion.getInstance();
        laConnexion = maBD.creeConnexion();

        PreparedStatement requete = laConnexion.prepareStatement("SELECT * FROM Abonnement WHERE id_abonnement = ?");
        requete.setInt(1, i);

        ResultSet res = requete.executeQuery();
        
        res.next();

        Abonnement abonnement = new Abonnement(res.getInt(1), res.getDate(2).toLocalDate(), res.getDate(3).toLocalDate(), new Client(res.getInt(4)), new Revue(res.getInt(5)));

        if (laConnexion != null)
            laConnexion.close();

        return abonnement;
    }
    
    @Override
    public List<Abonnement> getByDate(LocalDate dateDebut, LocalDate dateFin) throws SQLException 
    {
        List<Abonnement> listeAbonnement = new ArrayList<Abonnement>();

        maBD = Connexion.getInstance();
        laConnexion = maBD.creeConnexion();

        PreparedStatement requete = laConnexion.prepareStatement("SELECT * FROM Abonnement WHERE date_debut = ? AND date_fin = ?");
        requete.setDate(1, java.sql.Date.valueOf(dateDebut));
        requete.setDate(2, java.sql.Date.valueOf(dateFin));

        ResultSet res = requete.executeQuery();

        while (res.next()) 
        {
            listeAbonnement.add(new Abonnement(res.getInt(1), res.getDate(2).toLocalDate(), res.getDate(3).toLocalDate(), new Client(res.getInt(4)), new Revue(res.getInt(5))));
        }
        
        if (laConnexion != null)
            laConnexion.close();

        return listeAbonnement;
    }

    @Override
    public List<Abonnement> getByNomPrenom(String nom, String prenom) throws SQLException 
    {
        List<Abonnement> listeAbonnement = new ArrayList<Abonnement>();

        maBD = Connexion.getInstance();
        laConnexion = maBD.creeConnexion();

        PreparedStatement requete = laConnexion.prepareStatement("SELECT * FROM Abonnement a, Client c WHERE a.id_client = c.id_client AND nom = ? AND prenom = ?");
        requete.setString(1, nom);
        requete.setString(2, prenom);

        ResultSet res = requete.executeQuery();

        while (res.next()) 
        {
            listeAbonnement.add(new Abonnement(res.getInt(1), res.getDate(2).toLocalDate(), res.getDate(3).toLocalDate(), new Client(res.getInt(4)), new Revue(res.getInt(5))));
        }

        if (laConnexion != null)
        laConnexion.close();

        return listeAbonnement;
    }
    
    @Override
    public List<Abonnement> findAll() throws SQLException 
    {
        List<Abonnement> listeAbonnement = new ArrayList<Abonnement>();

        maBD = Connexion.getInstance();
        laConnexion = maBD.creeConnexion();

        PreparedStatement requete = laConnexion.prepareStatement("SELECT * FROM Abonnement");
        
        ResultSet res = requete.executeQuery();

        while (res.next()) 
        {
            listeAbonnement.add(new Abonnement(res.getInt(1), res.getDate(2).toLocalDate(), res.getDate(3).toLocalDate(), new Client(res.getInt(4)), new Revue(res.getInt(5))));
        }

        if (laConnexion != null)
        laConnexion.close();

        return listeAbonnement;
    }
}

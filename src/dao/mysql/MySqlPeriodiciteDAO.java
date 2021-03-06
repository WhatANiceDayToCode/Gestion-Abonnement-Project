package dao.mysql;

import connexion.Connexion;
import dao.PeriodiciteDAO;
import metier.Periodicite;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;



public class MySqlPeriodiciteDAO implements PeriodiciteDAO
{
    private static MySqlPeriodiciteDAO instance;
    Connexion maBD;
    Connection laConnexion;

    public static PeriodiciteDAO getInstance()
    {
        if (instance == null)
        {
            instance = new MySqlPeriodiciteDAO();
        }
        return instance;
    }

    private MySqlPeriodiciteDAO()
    {
        maBD = Connexion.getInstance();
        laConnexion = maBD.creeConnexion();
    }

    @Override
    public boolean create(Periodicite objet) throws SQLException
    {
        maBD = Connexion.getInstance();
        laConnexion = maBD.creeConnexion();

        PreparedStatement requete = laConnexion.prepareStatement("INSERT INTO Periodicite(libelle) Values (?)", Statement.RETURN_GENERATED_KEYS);
        requete.setString(1, objet.getLibelle());

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
    public boolean update(Periodicite objet) throws SQLException
    {
        maBD = Connexion.getInstance();
        laConnexion = maBD.creeConnexion();

        PreparedStatement requete = laConnexion
                .prepareStatement("UPDATE Periodicite SET libelle = ? WHERE id_periodicite = ?");
        requete.setString(1, objet.getLibelle());
        requete.setInt(2, objet.getId());

        int res = requete.executeUpdate();

        if (laConnexion != null)
            laConnexion.close();

        return (res == 1);
    }

    @Override
    public boolean delete(Periodicite objet) throws SQLException
    {
        maBD = Connexion.getInstance();
        laConnexion = maBD.creeConnexion();

        PreparedStatement requete = laConnexion.prepareStatement("DELETE FROM Periodicite WHERE id_periodicite = ?");
        requete.setInt(1, objet.getId());

        int res = requete.executeUpdate();

        if (laConnexion != null)
            laConnexion.close();

        return (res == 1);
    }

    @Override
    public Periodicite getById(int i) throws SQLException
    {
        maBD = Connexion.getInstance();
        laConnexion = maBD.creeConnexion();

        PreparedStatement requete = laConnexion.prepareStatement("SELECT * FROM Periodicite WHERE id_periodicite = ?");
        requete.setInt(1, i);

        ResultSet res = requete.executeQuery();

        res.next();
        
        Periodicite periodicite = new Periodicite(res.getInt(1), res.getString(2));

        if (laConnexion != null)
            laConnexion.close();

        return periodicite;
    }

    @Override
    public List<Periodicite> getByLibelle(String libelle) throws SQLException
    {
        List<Periodicite> listePeriodicite = new ArrayList<Periodicite>();

        maBD = Connexion.getInstance();
        laConnexion = maBD.creeConnexion();

        PreparedStatement requete = laConnexion.prepareStatement("SELECT * FROM Periodicite WHERE libelle = ?");
        requete.setString(1, libelle);
        
        ResultSet res = requete.executeQuery();

        while (res.next()) 
        {
            listePeriodicite.add(new Periodicite(res.getInt(1), res.getString(2)));
        }

        if (laConnexion != null)
        {
            laConnexion.close();
        }
        
        return listePeriodicite;
    }

    @Override
    public List<Periodicite> findAll() throws SQLException
    {
        List<Periodicite> listePeriodicite = new ArrayList<Periodicite>();

        maBD = Connexion.getInstance();
        laConnexion = maBD.creeConnexion();

        PreparedStatement requete = laConnexion.prepareStatement("SELECT * FROM Periodicite");

        ResultSet res = requete.executeQuery();

        while (res.next())
        {
            listePeriodicite.add(new Periodicite(res.getInt(1), res.getString(2)));
        }

        if (laConnexion != null)
            laConnexion.close();

        return listePeriodicite;
    }
}
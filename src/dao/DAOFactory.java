package dao;

public abstract class DAOFactory
{
    public static DAOFactory getDAOFactory(Persistance cible)
    {
        DAOFactory daoF = null;
        switch (cible) 
        {
            case MYSQL:
                daoF = new MySQLDAOFactory();
                break;
                
            case ListeMemoire:
                daoF = new ListeMemoireDAOFactory();
                break;
        }
        return daoF;
    }
    public abstract AbonnementDAO getAbonnementDAO();
    public abstract ClientDAO getClientDAO();
    public abstract PeriodiciteDAO getPeriodiciteDAO();
    public abstract RevueDAO getRevueDAO();
}
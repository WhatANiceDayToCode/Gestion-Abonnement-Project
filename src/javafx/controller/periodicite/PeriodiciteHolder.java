package javafx.controller.periodicite;

//import JavaFX.Controller.Revue.RevueHolder;
import metier.Periodicite;
// transmet l'element sélectionné entre le menuGeneral et le modifier
public class PeriodiciteHolder {

    private Periodicite periodicite;

    private final static PeriodiciteHolder INSTANCE = new PeriodiciteHolder();

    public static PeriodiciteHolder getInstance()
    {
        return INSTANCE;
    }

    public Periodicite getPeriodicite()
    {
        return this.periodicite;
    }

    public void setPeriodicite(Periodicite periodicite) {this.periodicite = periodicite;}
}

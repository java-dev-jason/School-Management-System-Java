public class SchuelerHinzu2 {
    private MainWindow mainWindow;

    public SchuelerHinzu2(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    public void Start() {
        mainWindow.Signalerhalten();
    }
    public void Signal2() {
        mainWindow.SignalErhalten_Klasse();
    }
}
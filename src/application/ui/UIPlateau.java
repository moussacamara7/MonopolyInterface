package application.ui;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.regex.Pattern;

public class UIPlateau {

    final static String PLATEAU = "image/Plateau2.jpg";
    final static String COORDONNEES = "image/CoordonneesCases.csv";
    private static final int NOMBRE_CASES = 40;
    private static final String REGEX_COORDONNEES = "[0-9]+(;[0-9]+){4}";
    private HashMap<Integer, UICase> cases = new HashMap<Integer, UICase>();
    private Image imagePlateau = null;
    private HashMap<String, Image> imagesPions = new HashMap<String, Image>();


    public UIPlateau(/* paramètres ? */) {

        for (int i = 0; i < 41; i++)
            cases.put(i, new UICase());

        initImagePlateau(PLATEAU);
        initCoordonnees(COORDONNEES);
        initImagesPions();
    }

    private void initImagesPions() {
        imagesPions.put("Chien", new Image(getClass().getResourceAsStream("image/Chien.png")));
        imagesPions.put("Bateau", new Image(getClass().getResourceAsStream("image/Bateau.png")));
        imagesPions.put("Brouette", new Image(getClass().getResourceAsStream("image/Brouette.png")));
        imagesPions.put("Chapeau", new Image(getClass().getResourceAsStream("image/Chapeau.png")));
        imagesPions.put("Chat", new Image(getClass().getResourceAsStream("image/Chat.png")));
        imagesPions.put("Chaussure", new Image(getClass().getResourceAsStream("image/Chaussure.png")));
        imagesPions.put("DeACoudre", new Image(getClass().getResourceAsStream("image/DeACoudre.png")));
        imagesPions.put("Voiture", new Image(getClass().getResourceAsStream("image/Voiture.png")));
    }

    public Image getImage() {
        return imagePlateau;
    }

    private void initImagePlateau(String nomFichierPlateau) {
        imagePlateau = new Image(getClass().getResourceAsStream(nomFichierPlateau));
    }

    public UICase getCase(int numCase) {
        if (numCase < 0 || numCase > NOMBRE_CASES)
            throw new IllegalArgumentException("Le numéro de la case est incorrect");

        return cases.get(numCase);
    }

    /**
     * initCase lit un fichier au format .csv. Une ligne doit avoir le format "n;x1;y1;x2;y2;" où n,x1,y1,x2,y2
     * sont des entiers. En cas de non respect de ce format, le programme est interrompu.
     *
     * @param nf nom du fichier contenant les coordonnées des cases du plateau. Ces coordonnées
     *           sont celles pour le plateau 800x800 pixels
     *           <p>
     *           YL : --> A remplacer avec vos parser !!! ça c'est moche et ça doit disparaitre !!!
     */
    private void initCoordonnees(String nf) {
        BufferedReader f = null;
        String ligne;

        f = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(nf)));

        try {
            while ((ligne = f.readLine()) != null) {
                parserCoordonnees(ligne);
            }

            f.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * La méthode parserCoordonnées vérifie que la ligne à parser respecte le bon format. Sinon arrêt
     * du programme en lançant une exception du type Error
     *
     * @param ligne La ligne à parser
     */
    private void parserCoordonnees(String ligne) {
        if (!Pattern.matches(REGEX_COORDONNEES, ligne))
            throw new Error("Format des coordonnées non respecté");


        // Tout est ok pour parser
        int numCase, x1, y1, x2, y2;

        String[] mots = ligne.split(";");
        numCase = Integer.parseInt(mots[0]);
        x1 = Integer.parseInt(mots[1]);
        y1 = Integer.parseInt(mots[2]);
        x2 = Integer.parseInt(mots[3]);
        y2 = Integer.parseInt(mots[4]);

        UICase c = cases.get(numCase);
        c.setCoordonnees(x1, y1, x2, y2);
    }

    public void dessiner(Canvas grillePane) {
        for (int i = 0; i <= NOMBRE_CASES; i++) {
            cases.get(i).vider();
        }


        grillePane.getGraphicsContext2D().drawImage(imagePlateau, 0, 0);
        for (int i = 0; i <= NOMBRE_CASES; i++) {
            UICase c = cases.get(i);
            for (int p = 0; p < c.getNombrePion(); p++) {
                Image imagePion = imagesPions.get(c.getListePions().get(p).getNom());
                grillePane.getGraphicsContext2D().drawImage(imagePion, c.x1 + 30 * (p % 2), c.y1 + 30 * (p / 2));
            }
        }

    }
}

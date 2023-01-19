import javafx.animation.AnimationTimer;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class Earth extends Group
{
    private ArrayList<Sphere> yellowSphere;
    private Rotate ry = new Rotate();

    public Sphere getEarth() {
        return sph;
    }

    private Sphere sph;

    public Earth()
    {
        yellowSphere = new ArrayList<Sphere>();
        sph = new Sphere(300);//taille sphere

        PhongMaterial skin = new PhongMaterial();
        skin.setDiffuseMap(new Image("file:./data/earth_lights_4800.png"));//appell du fichier utiliser
        skin.setSelfIlluminationMap(new Image("file:./data/earth_lights_4800.png"));//appell du fichier utiliser
        sph.setMaterial(skin);

        this.getChildren().add(sph);
        this.getTransforms().add(ry);

        //animation pour faire tourner la terre
        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                ry.setAxis(new Point3D(0, 1, 0));//v2 = angle de vue
                ry.setAngle(l/70000000);//Vitessede rotation
            }
        };
        animationTimer.start();//Execution de la rotation
    }

    //création de la nouvelle sphere pour afficher l'aéroport le plus proche
    public Sphere createSphere(Aeroport a, Color color) {
        PhongMaterial col = new PhongMaterial();
        col.setSpecularColor(color);
        col.setDiffuseColor(color);

        //Récupération des coordonnées de l'aéroport le plus proche
        double latitude = Math.toRadians(a.getLatitude() * 0.65);//Reécupération de la la latitude
        double longitude = Math.toRadians(a.getLongitude());//Récupération de la longitude

        Sphere coloredSphere = new Sphere(5);//taille de la sphere
        coloredSphere.setMaterial(col);

        //calcule des coordonnées de la sphere
        Translate tz = new Translate(
                300 * Math.cos(latitude) * Math.sin(longitude),//calcul en axe x
                -300 * Math.sin(latitude),//calcul en Y
                -300 * Math.cos(latitude) * Math.cos(longitude)//calcul en axe Z
        );

        coloredSphere.getTransforms().add(tz);
        //Rotate(V1,V2,V3,Point3D)
        //V = angle
        //V1 = pivot X
        //V2 = pivot Y
        //V3 = pivot Z
        Rotate rTheta = new Rotate(40, 0, 0, 300, Rotate.X_AXIS);
        Rotate rPhi = new Rotate(73, 0, 0, 300, Rotate.Y_AXIS);

        //coloredSphere.getTransforms().add(rTheta);
        //coloredSphere.getTransforms().add(rPhi);

        return coloredSphere;
    }

    //affichage de la sphere pour indiquer l'aéroport le plus proche
    public void displayRedSphere(Aeroport a)
    {
        this.getChildren().add(createSphere(a, Color.ORANGE));
    }

    //affichage de l'aéroport de départ
    public void displayYellowSphere(ArrayList<Flight> list)
    {
        PhongMaterial yellow = new PhongMaterial();
        //coloc.Yellow permet de dire que la sphere sera jaune
        yellow.setSpecularColor(Color.YELLOW);
        yellow.setDiffuseColor(Color.YELLOW);
        yellowSphere.clear();
        for (Flight f : list)
        {
            //si trouve un aeroport de départ affiche la sphere jaune a sont emplacment
            if (f.getDeparture() != null)
            {
                //création de la sphere sur les coordonnes de l'aéroport de départ
                Sphere current = createSphere(f.getDeparture(), Color.YELLOW);
                this.getChildren().add(current);
            }
        }
    }
}
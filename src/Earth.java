import javafx.animation.AnimationTimer;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;

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

    public Earth() {
        yellowSphere = new ArrayList<Sphere>();
        sph = new Sphere(300);

        PhongMaterial skin = new PhongMaterial();
        skin.setDiffuseMap(new Image("file:./data/earth_lights_4800.png"));
        skin.setSelfIlluminationMap(new Image("file:./data/earth_lights_4800.png"));
        sph.setMaterial(skin);
        this.getChildren().add(sph);
        this.getTransforms().add(ry);

        //animation pour faire tourner la terre
        AnimationTimer animationTimer = new AnimationTimer()
        {
            @Override
            public void handle(long time)
            {
                //System.out.println("Valeur de time : " + time);
                ry.setAngle( time/(100_000_000_000_000.0 * 15)  );
                ry.setAxis(new Point3D(0,-1,0));
                sph.getTransforms().add(ry);

            }
        };
        animationTimer.start();

    }
}
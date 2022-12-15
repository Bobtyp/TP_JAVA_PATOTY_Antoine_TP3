import javafx.animation.AnimationTimer;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;

import java.io.File;
import java.util.ArrayList;

public class Earth extends Group
{
    private Rotate ry;
    private Sphere sph;
    private ArrayList<Sphere> yellowSphere;
    private PhongMaterial material;

    public Earth()
    {

        this.ry = new Rotate();
        this.sph = new Sphere(300);
        this.yellowSphere = new ArrayList<Sphere>();
        this.material = new PhongMaterial();
        this.getChildren().add(sph);

        try {
            material.setDiffuseMap(new Image(new File("data/earth_lights_4800.png").toURI().toURL().toString()));
            sph.setMaterial(material);
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long time) {
                //System.out.println("Valeur de time : " + time);
                ry.setAngle( time/(100_000_000_000_000.0 * 15)  );
                ry.setAxis(new Point3D(0,-1,0));
                sph.getTransforms().add(ry);

            }
        };
        animationTimer.start();

    }
}
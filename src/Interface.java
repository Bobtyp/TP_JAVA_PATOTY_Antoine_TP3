import javafx.application.Application;
import javafx.scene.PerspectiveCamera;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
public class Interface extends Application
{
    double startDragX;
    double startDragY;
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        World w = new World ("Data/airport-codes_no_comma.csv");
        //creation de la sphere
        primaryStage.setTitle("Hello world");
        Earth root = new Earth();
        Pane pane = new Pane(root);
        Scene theScene = new Scene(pane, 600, 400, false);//taille page blanche
        primaryStage.setScene(theScene);
        primaryStage.show();

        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setTranslateZ(-1000);//position cam en Z
        camera.setNearClip(0.1);
        camera.setFarClip(2000.0);
        camera.setFieldOfView(35);//ZOOM de la sphere
        theScene.setCamera(camera);

        //creationevent pour savoir ou cliquer
        theScene.addEventHandler(MouseEvent.ANY, event -> {
            if (event.getEventType() == MouseEvent.MOUSE_PRESSED)//action suite au click de la souris
            {
                startDragX = event.getSceneX();//récupérer l'axe en X
                startDragY = event.getSceneY();//récupérer l'axe en Y
                System.out.println("votre souris à cliquer à la position : ( en X = " + startDragX +
                        ", en Y =  " + startDragY + ")");//afficher les coordonnée en X et Y
            }
            if (event.getEventType() == MouseEvent.MOUSE_DRAGGED)//actione mener après lachement du click de la souris
            {
                double newTZ = camera.getTranslateZ() + (event.getSceneY() - startDragY);
                if (newTZ > -2000 && newTZ < -500)
                {
                    camera.setTranslateZ(newTZ);
                }
                startDragY = event.getSceneY();
            }
        });
        //action demander sur clic droit
        theScene.addEventHandler(MouseEvent.ANY, event ->
        {
            if (event.getButton() == MouseButton.SECONDARY && event.getEventType() == MouseEvent.MOUSE_CLICKED)
            {
                PickResult pickResult = event.getPickResult();
                if (pickResult.getIntersectedNode() != null)
                {
                    //Récupération des coordonnée en X et Y
                    startDragX = pickResult.getIntersectedTexCoord().getX();//récupérer l'axe en X
                    startDragY = pickResult.getIntersectedTexCoord().getY();//récupérer l'axe en Y

                    //transforation des données
                    double longitude=360*(startDragX-0.5);
                    double latitude=2*Math.toDegrees(Math.atan(Math.exp((0.5-startDragY)/0.2678))-(Math.PI/4));

                    //recherche de l'aeroport le plus proche en fonction de la ou on clic
                    System.out.println(w.findNearestAirport(longitude,latitude));
                }

            }
        });
    }
    public static void main(String[] args)
    {
       launch(args);
    }

}

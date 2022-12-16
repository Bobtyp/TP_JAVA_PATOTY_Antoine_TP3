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
        //creation de la sphere
        primaryStage.setTitle("Hello world");
        Earth root = new Earth();
        Pane pane = new Pane(root);
        Scene theScene = new Scene(pane, 600, 400,true);//taille page blanche
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
            if (event.getEventType() == MouseEvent.MOUSE_PRESSED)
            {
                startDragX = event.getSceneX();
                startDragY = event.getSceneY();
                System.out.println("Clicked on : (" + startDragX + ", " + startDragY + ")");
            }
            if (event.getEventType() == MouseEvent.MOUSE_DRAGGED)
            {
                double newTZ = camera.getTranslateZ() + (event.getSceneY() - startDragY);
                if (newTZ > -2000 && newTZ < -500) camera.setTranslateZ(newTZ);
                startDragY = event.getSceneY();
            }
        });
        theScene.addEventHandler(MouseEvent.ANY, event ->
        {
            //if pour récupérer les coordonnées sur click de la souris
            if(event.getButton() == MouseButton.SECONDARY && event.getEventType() == MouseEvent.MOUSE_CLICKED)
            {
                PickResult pickResult = event.getPickResult();
                if (pickResult.getIntersectedNode() != null)
                {
                    System.out.println(pickResult.getIntersectedNode());
                }
            }
        });
    }
    public static void main(String[] args)
    {
        launch(args);
    }

}

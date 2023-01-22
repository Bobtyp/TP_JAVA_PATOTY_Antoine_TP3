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

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class Interface extends Application
{
    //déclaration tableau des vols
    ArrayList<Flight> listOfFlight = new ArrayList<Flight>();
    double startDragX;
    double startDragY;
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        World w = new World ("Data/airport-codes_no_comma.csv");
        //creation de la sphere
        primaryStage.setTitle("Hello world");//nom de la pop up
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

        //action associer à l'évenement d'un clic de souris
        theScene.addEventHandler(MouseEvent.ANY, event ->
        {
            if (event.getEventType() == MouseEvent.MOUSE_PRESSED)//action suite au click de la souris
            {
                startDragX = event.getSceneX();//récupérer l'axe en X
                startDragY = event.getSceneY();//récupérer l'axe en Y
                System.out.println("votre souris à cliquer à la position : ( en X = " + startDragX +
                        ", en Y =  " + startDragY + ")");//afficher les coordonnée en X et Y
            }
            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            if (event.getEventType() == MouseEvent.MOUSE_DRAGGED)//actione mener après lachement du click de la souris
            {
                //indication de la translation du zoom selon un axe donnée
                double newTZ = camera.getTranslateZ() + (event.getSceneY() - startDragY);
                if (newTZ > -2000 && newTZ < -500)
                {
                    camera.setTranslateZ(newTZ);//permet de faire le zoom sur la terre sur un axe donné
                }
                startDragY = event.getSceneY();
            }
        });
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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

                    //affichage de la sphere de laéroport
                    Aeroport aeroport = w.findNearestAirport(longitude,latitude);
                    root.displayRedSphere(aeroport);

                    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                    try
                    {
                        //execution de la requète internet
                        HttpClient client = HttpClient.newHttpClient();

                        //création de la requète vers le site aviationstack avec une limite de 1 pour éviter de surcharger le PC
                        HttpRequest request = HttpRequest.newBuilder()
                                .uri(URI.create("http://api.aviationstack.com/v1/flights?access_key=369bf67204b8823ff563ab599670b59f&arr_iata="
                                        +aeroport.getIATA()+"&limit=1"))
                                .build();

                        HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
                        JsonFlightFiller json = new JsonFlightFiller(response.body().toString(),w);
                        listOfFlight = json.getList();
                        json.displayFlight();
                        //affichage des point jaune sur la sphere
                        root.displayYellowSphere(listOfFlight);

                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        });
        primaryStage.setScene(theScene);

        primaryStage.show();

    }
    public static void main(String[] args)
    {
       launch(args);
    }

}

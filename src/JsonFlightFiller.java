import javafx.scene.shape.Sphere;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME;


public class JsonFlightFiller
{
    private ArrayList<Flight> list = new ArrayList<Flight>();//déclaration liste des vols

    public ArrayList<Flight> getList()
    {
        return list;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public JsonFlightFiller(String jsonString,World w)
    {
        try
        {
            InputStream is = new ByteArrayInputStream(jsonString.getBytes(StandardCharsets.UTF_8));
            JsonReader rdr = Json.createReader(is);
            JsonObject obj = rdr.readObject();
            JsonArray results = obj.getJsonArray("data");

            for (JsonObject result : results.getValuesAs(JsonObject.class))
            {
                try
                {
                    //récupération des informations
                    //on intéroge la chaîne de caractère pour récupérer des éléments spécifique

                    //récupération de la date
                    String date = result.getJsonString("flight_date").getString();

                    //récupération de la compagnie
                    String airlineName = result.getJsonObject("airline").getString("name");

                    //récupération du code IATA
                    String airlineCode = result.getJsonObject("airline").getString("iata");

                    Integer number = Integer.parseInt(result.getJsonObject("flight").getString("number"));

                    String iataCodeDeparture = result.getJsonObject("departure").getString("iata");

                    Aeroport depart = w.findByCode(iataCodeDeparture);
                    String iataCodeArrival = result.getJsonObject("arrival").getString("iata");

                    Aeroport arrival = w.findByCode(iataCodeArrival);
                    String heure = result.getJsonObject("departure").getString("scheduled");

                    LocalDateTime departTime = null;

                    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                    //récupération des heures
                    if (heure != null)
                    {
                        departTime = LocalDateTime.parse(heure, ISO_OFFSET_DATE_TIME);
                    }

                    heure = result.getJsonObject("arrival").getString("estimated");
                    LocalDateTime arrivalTime = null;

                    if (heure != null)
                    {
                        arrivalTime = LocalDateTime.parse(heure, ISO_OFFSET_DATE_TIME);
                    }

                    //récupération des éléments issue de la liste de la classe FLIGHT
                    list.add(new Flight(depart, arrival, airlineName, airlineCode, number, departTime, arrivalTime));
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void displayFlight()
    {
        for (Flight f : list) System.out.println(f);
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //main issue du sujet de TP
    public static void main (String[] args)
    {
        try
        {
            //chemin d'accès pour trouver le fichier .csv
            World w = new World ("Data/airport-codes_no_comma.csv");

            //chemin d'accès pour touver le fichier jsonOrlyY.txt
            BufferedReader br = new BufferedReader(new FileReader("Data/JsonOrly.txt"));
            String test = br.readLine();
            JsonFlightFiller JsonFlightFiller = new JsonFlightFiller(test,w);
            System.out.println(JsonFlightFiller.getList().get(0));//affichage de la ligne du fichier .txt

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}


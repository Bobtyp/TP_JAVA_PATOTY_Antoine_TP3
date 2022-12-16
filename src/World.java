import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class World {
    private ArrayList<Aeroport> list;

    public World(String fileName)
    {
        list = new ArrayList<Aeroport>();
        try {
            BufferedReader buf = new BufferedReader(new FileReader(fileName));
            String s = buf.readLine();
            while (s != null)
            {
                s = s.replaceAll("\"", "");
                //Enleve les guillemets qui separent les champs de donnees GPS.
                String fields[] = s.split(",");
                // Une bonne idee : placer un point d
                //arr^et ici pour du debuggage.
                if (fields[1].equals("large_airport"))
                {
                    String IATA = fields[9];
                    String Name = fields[2];
                    String country = fields[5];
                    double lat = Double.parseDouble(fields[12]);
                    double lon = Double.parseDouble(fields[11]);
                    list.add(new Aeroport(IATA, Name, country, lat, lon));
                }
                s = buf.readLine();
            }
        } catch (Exception e)
        {
            System.out.println("Maybe the file isn't there ?");
            System.out.println(list.get(list.size() - 1));
            e.printStackTrace();
        }

    }

    //Retourne la distance enter 2 points gps
    public double calcul_Distance(double longitude1,double latitude1, double longitude2, double latitude2)
    {
        //utilisation de la formule de l'énoncer pour calculer la position
        return Math.pow((latitude2 - latitude1), 2.0) + Math.pow((longitude2 - longitude1) * Math.cos((latitude2 - latitude1) / 2.0), 2.0);
    }

    //calcul pour savoir quelle Aeroport est le plus proche
    public Aeroport findNearestAirport(double longitude,double latitude)//calcul pour comparer les distances
    {
        double result = 999999999.9;//résultat de réference
        double result1;
        int index = 0;
        for (Aeroport Aero: list)
        {
            result1 = this.calcul_Distance(longitude,latitude,Aero.getLongitude(),Aero.getLatitude());//calcul de la distance pour un aeroport
            if(result > result1)//comparaison des résltats pour connaitre le plus proche
            {
                result = result1;//si condition confirmer inscription du nouveau résultat
                index = list.indexOf(Aero);
            }
        }
        return list.get(index);
    }
    //recherche dans tableua quelle aeropoert correspond au code
    public Aeroport findByCode(String code)
    {
        for (Aeroport aero : list)
        {
            //on traverse le tableau pour comparer chaque IATA pour savoir a qui correspond le code
            if (aero.getIATA().equals(code))
            {
                return aero;//renvoie l'aeroport correspondant
            }
        }
        return null;
    }

    public ArrayList<Aeroport> getList()
    {
        return list;
    }
}

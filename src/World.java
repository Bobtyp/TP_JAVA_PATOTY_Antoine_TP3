import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class World {
    private ArrayList<Aeroport> list;

    public World(String fileName) {
        list = new ArrayList<Aeroport>();
        try {
            BufferedReader buf = new BufferedReader(new FileReader(fileName));
            String s = buf.readLine();
            while (s != null) {
                s = s.replaceAll("\"", "");
                //Enleve les guillemets qui separent les champs de donnees GPS.
                String fields[] = s.split(",");
                // Une bonne idee : placer un point d
                //arr^et ici pour du debuggage.
                if (fields[1].equals("large_airport")) {
                    String IATA = fields[9];
                    String Name = fields[2];
                    String country = fields[5];
                    double lat = Double.parseDouble(fields[12]);
                    double lon = Double.parseDouble(fields[11]);
                    list.add(new Aeroport(IATA, Name, country, lat, lon));
                }
                s = buf.readLine();
            }
        } catch (Exception e) {
            System.out.println("Maybe the file isn't there ?");
            System.out.println(list.get(list.size() - 1));
            e.printStackTrace();
        }

    }

    //Retourne la distance enter 2 points gps
    public double calcul_Distance(double longitude1,double latitude1, double longitude2, double latitude2)
    {
        return Math.pow((latitude2 - latitude1), 2.0) + Math.pow((longitude2 - longitude1) * Math.cos((latitude2 - latitude1) / 2.0), 2.0);
    }

    public Aeroport findNearestAirport(double longitude,double latitude)
    {
        double result = 999999999.9;
        double result1;
        int index = 0;
        for (Aeroport Aero: list)
        {
            result1 = this.calcul_Distance(longitude,latitude,Aero.getLongitude(),Aero.getLatitude());
            if(result > result1)
            {
                result = result1;
                index = list.indexOf(Aero);
            }
        }
        return list.get(index);
    }

    public Aeroport findByCode(String code)
    {
        for (Aeroport aero : list) {
            if (aero.getIATA().equals(code)) return aero;
        }
        return null;
    }

    public ArrayList<Aeroport> getList()
    {
        return list;
    }
}

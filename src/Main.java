public class Main {
    public static void main(String[] args)
    {

        System.out.println("Hello world!");
        /*Aeroport Aeroport1 = new Aeroport("toto","paris","france",34,45);
        System.out.println(Aeroport1);*/

        //test pour savoir si les 2 fonctions sont correct ou non
        World w = new World ("Data/airport-codes_no_comma.csv");
        System.out.println("Found "+w.getList().size()+" airports.");
        Aeroport paris = w.findNearestAirport(2.316,48.866);
        Aeroport cdg = w.findByCode("CDG");
        double distance = w.calcul_Distance(2.316,48.866,paris.getLongitude(),paris.getLatitude());
        System.out.println(paris);
        System.out.println(distance);
        double distanceCDG = w.calcul_Distance(2.316,48.866,cdg.getLongitude(),cdg.getLatitude());
        System.out.println(cdg);
        System.out.println(distanceCDG);
    }
}
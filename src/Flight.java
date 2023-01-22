import java.time.LocalDateTime;

import java.time.LocalDateTime;

public class Flight
{
    //creation des instances indiquer dans l'enoncé
    private Aeroport departure;
    private Aeroport arrival;
    private String airlineName;
    private String airLineCode;
    private int number;
    private LocalDateTime arrivalTime;

    public Aeroport getDeparture()
    {
        return departure;
    }

    private LocalDateTime departureTime;

    public Aeroport getArrival()
    {
        return arrival;
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //instanciation des instances de Flight
    public Flight(Aeroport departure, Aeroport arrival, String airlineName,
                  String airLineCode, int number, LocalDateTime arrivalTime,
                  LocalDateTime departureTime)
    {
        this.departure = departure;
        this.arrival = arrival;
        this.airlineName = airlineName;
        this.airLineCode = airLineCode;
        this.number = number;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //surcharge
    @Override
    public String toString()
    {
        return "Flight{" +
                "departure=" + departure +
                ", arrival=" + arrival +
                ", airline='" + airlineName + '\'' +
                ", number=" + number +
                ", arrivalTime=" + arrivalTime +
                ", departureTime=" + departureTime +
                '}';
    }
}
package trabajoFinal.SitioWeb;
import java.util.PriorityQueue;

public class ReservaCondicional {
	 private PriorityQueue<Reserva> pqReservas = new PriorityQueue<Reserva>();
	 
	 public PriorityQueue<Reserva> getPQReservas(){
		 return pqReservas;
	 }
}

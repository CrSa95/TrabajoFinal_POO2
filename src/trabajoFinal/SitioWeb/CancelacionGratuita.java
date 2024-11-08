package trabajoFinal.SitioWeb;

import java.time.LocalDate;

public class CancelacionGratuita implements PoliticaDeCancelacion {

	@Override
	public void darResarcimiento(Reserva reserva) {
		
		if (LocalDate.now().isEqual(reserva.getFechaDeIngreso().minusDays(10)) || 
			LocalDate.now().isBefore(reserva.getFechaDeIngreso().minusDays(10))	) {
			reserva.getInmueble().getPropietario().recibirResarcimiento(0);
		}
		else {
			reserva.getInmueble().getPropietario()
								 .recibirResarcimiento(reserva.getInmueble().getPrecioPorDia() * 2);
		}
	}

}

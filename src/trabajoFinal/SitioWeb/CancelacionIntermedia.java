package trabajoFinal.SitioWeb;

import java.time.LocalDate;

public class CancelacionIntermedia implements PoliticaDeCancelacion {

	@Override
	public void darResarcimiento(Reserva reserva) {
		
		if (LocalDate.now().isBefore(reserva.getFechaDeIngreso().minusDays(19))) {
			reserva.getInmueble().getPropietario().recibirResarcimiento(0);
		} else if (LocalDate.now().isBefore(reserva.getFechaDeIngreso().minusDays(10)) && LocalDate.now().isAfter(reserva.getFechaDeIngreso().minusDays(19))) {
			reserva.getInmueble().getPropietario().recibirResarcimiento(reserva.getInmueble().getPrecioTotalCalculado() * 0.5);
		} else {
			reserva.getInmueble().getPropietario().recibirResarcimiento(reserva.getInmueble().getPrecioTotalCalculado());
		}
	}
}
package trabajoFinal.SitioWeb;

import java.time.LocalDate;

public class CancelacionIntermedia implements PoliticaDeCancelacion {

	@Override
	public void darResarcimiento(Reserva reserva) {
		
		//Cancelacion Intermedia: Hasta 20 días antes es gratuita, entre el día 19 anterior y el día 10 anterior paga el 50 %, después del 10mo día paga la totalidad.

		if (LocalDate.now().isBefore(reserva.getFechaDeIngreso().minusDays(20))) {
			reserva.getInmueble().getPropietario().recibirResarcimiento(0);
		}
		else if (LocalDate.now().isBefore(reserva.getFechaDeIngreso().minusDays(10))) {
			reserva.getInmueble().getPropietario()
								 .recibirResarcimiento(reserva.getInmueble().getPrecioTotalCalculado() / 2);
		}
		else {
			reserva.getInmueble().getPropietario().recibirResarcimiento(reserva.getInmueble().getPrecioTotalCalculado());
		}
		
	}

}

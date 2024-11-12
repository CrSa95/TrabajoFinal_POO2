package trabajoFinal.SitioWeb;

import java.time.LocalDate;

public class CancelacionGratuita implements PoliticaDeCancelacion {

	@Override

	//Cancelación gratuita hasta 10 días antes de la fecha de inicio de la ocupación y luego abona el equivalente a dos días de reserva.

	public void darResarcimiento(Reserva reserva) {

		if (LocalDate.now().isBefore(reserva.getFechaDeIngreso().minusDays(10))	) {
			reserva.getInmueble().getPropietario().recibirResarcimiento(0);
		}
		else {
			reserva.getInmueble().getPropietario()
								 .recibirResarcimiento(reserva.getInmueble().getPrecioPorDia() * 2);
		}
	}

}

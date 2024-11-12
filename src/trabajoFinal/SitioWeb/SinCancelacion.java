package trabajoFinal.SitioWeb;

public class SinCancelacion implements PoliticaDeCancelacion {

	@Override
	public void darResarcimiento(Reserva reserva) {

		reserva.getInmueble().getPropietario().recibirResarcimiento(reserva.getInmueble().calcularPrecioTotal(reserva.getFechaDeIngreso(), reserva.getFechaDeEgreso()));

	}

}

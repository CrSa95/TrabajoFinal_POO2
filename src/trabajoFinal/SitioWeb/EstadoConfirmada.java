package trabajoFinal.SitioWeb;

import java.util.Optional;


public class EstadoConfirmada implements EstadoDeReserva {

	@Override
	public void cancelar(Reserva reserva) {	
		Inmueble inmueble = reserva.getInmueble();

		inmueble.getPoliticaDeCancelacion().darResarcimiento(reserva);
		inmueble.eliminarReserva(reserva);
		inmueble.getPropietario().recibirMail("La reserva fue cancelada");
		reserva.getInquilino().eliminarReserva(reserva);
		reserva.setEstadoDeReserva(new EstadoCancelada());
		
		Optional<Reserva> reservaCondicional = reserva.obtenerReservaCondicional(inmueble);

		if (reservaCondicional.isPresent()) {
			reservaCondicional.get().evaluarReserva();
		}
	}

	@Override
	public void finalizar(Reserva reserva) {
		reserva.getInmueble().eliminarReserva(reserva);
		reserva.setEstadoDeReserva(new EstadoFinalizada());
	}

	@Override
	public boolean esFinalizado(){
		return  false;
	}

	@Override
	public void rankearAUnPropietario(Usuario usuario, Categoria categoria, int puntaje) throws Exception {
		throw new Exception("Error: La Reserva aun no finalizo.");
	}

	@Override
	public void rankearAUnInquilino(Usuario usuario, Categoria categoria, int puntaje) throws Exception {
		throw new Exception("Error: La Reserva aun no finalizo.");
	}

	@Override
	public void rankearUnInmueble(Inmueble inmueble, Categoria categoria, int puntaje) throws Exception {
		throw new Exception("Error: La Reserva aun no finalizo.");
	}

	@Override
	public void registrarComentarioParaElPropietario(Usuario usuario, String comentario) throws Exception {
		throw new Exception("Error: La Reserva aun no finalizo.");
	}

	@Override
	public void registrarComentarioParaElInquilino(Usuario usuario, String comentario) throws Exception {
		throw new Exception("Error: La Reserva aun no finalizo.");
	}

	@Override
	public void registrarComentarioParaElInmueble(Inmueble inmueble, String comentario) throws Exception {
		throw new Exception("Error: La Reserva aun no finalizo.");
	}
} 
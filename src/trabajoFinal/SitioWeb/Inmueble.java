package trabajoFinal.SitioWeb;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class Inmueble {
	
	private List<String> comentarios = new ArrayList<String>();
	private SolicitudDeReserva solicitudDeReserva; 
	
	
	
	public void setComentario(String comentario){
		comentarios.add(comentario);
	}
	
	public List<String> getComentarios(){
		return comentarios;
	}
	
	public void realizarReservaDelInmueble(Usuario inquilino, FormaDePago formaDePago, LocalDateTime fechaDeIngreso, LocalDateTime fechaDeEgreso) {
		this.solicitudDeReserva = new SolicitudDeReserva(this, inquilino, formaDePago, fechaDeIngreso, fechaDeEgreso);
		this.solicitudDeReserva.solicitarReserva();
	}
	
}

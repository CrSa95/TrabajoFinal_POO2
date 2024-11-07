package trabajoFinal.SitioWeb;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class Inmueble {
	
	private Optional<String> tipoInmueble;
	private int superficie;
	private String pais;
	private String ciudad;
	private String direccion;
	private List<String> tiposDeServicios;
	private int capacidad; 
	private String fotos; 
	private LocalDate checkIn;
	private LocalDate checkOut; 
	private List<FormaDePago> todasLasFormasDePago;
	private int precioPorDia;
	private int cantidadVecesAlquilado;
	private Usuario usuario;
	private int cantidadDeVecesAlquilado;
	//private GestorPrecio gestorPrecio;
	private PoliticaDeCancelacion politicaDeCancelacion;
	private List<String> comentarios = new ArrayList<String>();
	private List<Rankeo> rankeosInmueble = new ArrayList<Rankeo>();
	
	public Inmueble(Optional<String> tipoDeInmueble, int superficie, String pais, String ciudad, 
			 String direccion, List<String> tiposDeServicios, int capacidad, 
			 String fotos, LocalDate checkIn, LocalDate checkOut, List<FormaDePago> todasLasFormasDePago, 
			 int precioPorDia) {
		
		this.tipoInmueble = tipoDeInmueble;
		this.superficie = superficie;
		this.pais = pais;
		this.ciudad = ciudad;
		this.direccion = direccion;
		this.tiposDeServicios = tiposDeServicios;
		this.capacidad = capacidad;
		this.fotos = fotos;
		this.checkIn = checkIn;
		this.checkOut = checkOut; //duda
		this.todasLasFormasDePago = todasLasFormasDePago;
		this.precioPorDia = precioPorDia; //duda
		
	}
	
	public void setComentario(String comentario){
		comentarios.add(comentario);
	}
	
	public void setRankeosInmueble(Rankeo rankeo){
		this.rankeosInmueble.add(rankeo);
	}
	
	public List<String> getComentarios(){
		return comentarios;
	}
	
	public List<Rankeo> getRankeosInmueble(){
		return rankeosInmueble;
	}
	
	public Optional<FormaDePago> seleccionarFormaDePago(FormaDePago formaDePagoSeleccionada) {
		return this.todasLasFormasDePago.stream()
										.filter(formaDePago -> formaDePago == formaDePagoSeleccionada)  // Filtra por igualdad de enum
                						.findFirst();
	}
	
}

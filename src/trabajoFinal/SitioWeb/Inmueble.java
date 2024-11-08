package trabajoFinal.SitioWeb;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Inmueble {
	
	private TipoDeInmueble tipoInmueble;
	private int superficie;
	private String pais;
	private String ciudad;
	private String direccion;
	private List<TipoDeServicio> tiposDeServicios;
	private int capacidad; 
	private List<Foto> cincoFotos; 
	private LocalTime checkIn;
	private LocalTime checkOut; 
	private int precioPorDia;
	private int cantidadVecesAlquilado;
	private Usuario usuario;
	private int cantidadDeVecesAlquilado;
	private PoliticaDeCancelacion politicaDeCancelacion;
	private List<String> comentarios = new ArrayList<String>();
	private List<Rankeo> rankeosInmueble = new ArrayList<Rankeo>();
	private List<FormaDePago> todasLasFormasDePago = new ArrayList<FormaDePago>();
	private List<FormaDePago> formasDePagoSeleccionadas;
	private Optional<FormaDePago> formaDePagoSeleccionada; 
	private Usuario propietario;
	private int precioTotalCalculado;
	private int cantidadDeDiasAlquilado;
	private List<PrecioEspecifico> preciosEspecificos;
	private LocalTime fechaInicial;
	private LocalTime fechaFinal;
	private LocalTime fechaIngreso;
	private LocalTime fechaEgreso;
	
	public Inmueble(Usuario propietario, int superficie, String pais, String ciudad, String direccion, int capacidad, 
			 		LocalTime checkIn, LocalTime checkOut, int precioPorDia) {
		
		this.propietario = propietario;
		this.superficie = superficie;
		this.pais = pais;
		this.ciudad = ciudad;
		this.direccion = direccion;
		this.capacidad = capacidad;
		this.checkIn = checkIn;
		this.checkOut = checkOut; 
		this.precioPorDia = precioPorDia; 
		
	}
	
	public void setDisponibilidad(LocalTime fechaInicial, LocalTime fechaFinal) {
		
		this.fechaInicial = fechaInicial;
		this.fechaFinal = fechaFinal;
	}
	
	public void calcularPrecio() {
		this.precioTotalCalculado = precioTotalCalculado;
	}
	
	public void setFechaIngresoYEgreso(LocalTime fechaIngreso, LocalTime fechaEgreso) {
		this.fechaIngreso = fechaIngreso;
		this.fechaEgreso = fechaEgreso;
	}
	
	public int getPrecioTotalCalculado() {
		return this.precioTotalCalculado;
	}
	
	public int getPrecioPorDia() {
		return this.precioTotalCalculado / this.cantidadDeDiasAlquilado;
	}
	
	public void setCantidadDeDiasAlquilado(int cantidadDeDias) {
		this.cantidadDeDiasAlquilado = cantidadDeDias;
	}
	
	public Usuario getPropietario() {
		return this.propietario;
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
	
	public void seleccionarFormaDePago(FormaDePago formaDePagoSeleccionada) {
		
		this.formaDePagoSeleccionada = this.todasLasFormasDePago.stream()
																.filter(formaDePago -> formaDePago == formaDePagoSeleccionada)  // Filtra por igualdad de enum
																.findFirst();
	}
	
	public void setFormasDePago(List<FormaDePago> formasDePago){
		
		this.formasDePagoSeleccionadas = this.todasLasFormasDePago.stream()
                												  .filter(formasDePago::contains)
                												  .collect(Collectors.toList());
	}
	
	public String getCiudad() {
		
		return this.ciudad;
	}
	
	public void setTipoDeInmueble(TipoDeInmueble tipoDeInmueble) {
		
		this.tipoInmueble = tipoDeInmueble;
	}
	
	public void setTipoDeServicios(List<TipoDeServicio> servicios) {
		
		this.tiposDeServicios = servicios;
	}
	
	public void setFotos(List<Foto> fotos) throws CantidadDeFotosIncorrectaException {
		
		if (fotos.size() > 5) {
			this.cincoFotos = fotos;
		}
		else {
			throw new CantidadDeFotosIncorrectaException("Error: La cantidad de fotos permitidas es hasta 5.");
		}
	}
	
	public void setPoliticaDeCancelacion(PoliticaDeCancelacion politicaDeCancelacion) {
		
		this.politicaDeCancelacion = politicaDeCancelacion;
	}
	
	public void setPreciosEspecificos(List<PrecioEspecifico> preciosEspecificos) {
		this.preciosEspecificos = preciosEspecificos;
	}

}

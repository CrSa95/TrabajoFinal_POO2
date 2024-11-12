package trabajoFinal.SitioWeb;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
	private int cantidadDeVecesAlquilado;
	private Usuario usuario;
	private PoliticaDeCancelacion politicaDeCancelacion;
	private List<String> comentarios = new ArrayList<String>();
	private List<Rankeo> rankeosInmueble = new ArrayList<Rankeo>();
	private List<FormaDePago> todasLasFormasDePago = new ArrayList<FormaDePago>();
	private List<FormaDePago> formasDePagoSeleccionadas;
	private Optional<FormaDePago> formaDePagoSeleccionada; 
	private List<Reserva> reservasDelInmueble = new ArrayList<Reserva>();
	private Usuario propietario;
	private double precioTotalCalculado;
	private int cantidadDeDiasAlquilado;
	private List<PrecioEspecifico> preciosEspecificos;
	private LocalDate fechaInicial;
	private LocalDate fechaFinal;
	private double precioBase;
	private int promedioPuntajeTotal;
	
	public Inmueble(Usuario propietario, int superficie, String pais, String ciudad, String direccion, int capacidad, 
			 		LocalTime checkIn, LocalTime checkOut, List<PrecioEspecifico> preciosEspecificos, double precioBase) {
		
		this.propietario = propietario;
		this.superficie = superficie;
		this.pais = pais;
		this.ciudad = ciudad;
		this.direccion = direccion;
		this.capacidad = capacidad;
		this.checkIn = checkIn;
		this.checkOut = checkOut; 
		this.precioBase = precioBase; 
		this.preciosEspecificos = preciosEspecificos;
		
	}
	
	public void calcularPromedioTotal() {
		
		double sumaTotal = 0;
        for (Rankeo rank : rankeosInmueble) {
            sumaTotal += rank.getPuntaje(); 
        }

        this.promedioPuntajeTotal = (int) (sumaTotal / rankeosInmueble.size());
	}
	
	public int getPromedioPuntajeTotal() {
		return promedioPuntajeTotal;
	}
	
	public void agregarReserva(Reserva reserva) {
		this.reservasDelInmueble.add(reserva);
	}
	
	public void eliminarReserva(Reserva reserva) {
		this.reservasDelInmueble.remove(reserva);
	}
	
	public Boolean estaReservado(Reserva reserva) {
	    return reservasDelInmueble.stream()
	            .anyMatch(unaReserva ->
	                !unaReserva.getFechaDeEgreso().isBefore(reserva.getFechaDeIngreso()) &&
	                !unaReserva.getFechaDeIngreso().isAfter(reserva.getFechaDeEgreso())
	            );
	}
	
	public void modificarPrecioBase(double precioBase) {
		this.precioBase = precioBase;
	}
	
	public double getPrecioBase() {
		return this.precioBase;
	}
	
	public void setDisponibilidad(LocalDate fechaInicial, LocalDate fechaFinal) {
		
		this.fechaInicial = fechaInicial;
		this.fechaFinal = fechaFinal;
	}
	
	public LocalDate getFechaInicial(){
		return this.fechaInicial;
	}
	
	public LocalDate getFechaFinal(){
		return this.fechaFinal;
	}
	
	public void calcularPrecio(LocalDate inicioReserva, LocalDate finReserva) {
	 
	}
	
	public double getPrecioTotalCalculado() {
		return this.precioTotalCalculado;
	}
	
	public double getPrecioPorDia() {
		return this.precioTotalCalculado / this.cantidadDeDiasAlquilado;
	}
	
	public void setCantidadDeDiasAlquilado(int cantidadDeDias) {
		this.cantidadDeDiasAlquilado = cantidadDeDias;
	}
	
	public Usuario getPropietario() {
		return this.propietario;
	}
	private SolicitudDeReserva solicitudDeReserva; 
	
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
	
	public TipoDeInmueble getTipoInmueble() {
		return this.tipoInmueble;
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
	
	public PoliticaDeCancelacion getPoliticaDeCancelacion() {
		return politicaDeCancelacion;
	}
  
	public void realizarReservaDelInmueble(Usuario inquilino, FormaDePago formaDePago, LocalDate fechaDeIngreso, LocalDate fechaDeEgreso) {
		this.solicitudDeReserva = new SolicitudDeReserva(this, inquilino, formaDePago, fechaDeIngreso, fechaDeEgreso);
		this.solicitudDeReserva.solicitarReserva();
	}

	public int getCapacidad() {
		// TODO Auto-generated method stub
		return this.capacidad;
	}

	public void sumarCantidadDeVecesAlquilado() {
        this.cantidadDeVecesAlquilado += 1 ; 
    }

    public void restarCantidadDeVecesAlquilado() {
        this.cantidadDeVecesAlquilado -= 1 ; 
    }

    public int getCantidadDeVecesAlquilado() {
        return this.cantidadDeVecesAlquilado; 
    }

}

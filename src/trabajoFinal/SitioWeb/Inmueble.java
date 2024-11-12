package trabajoFinal.SitioWeb;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


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
	private int cantidadDeVecesAlquilado = 0;
	private PoliticaDeCancelacion politicaDeCancelacion;
	private List<String> comentarios = new ArrayList<>();
	private List<Rankeo> rankeosInmueble = new ArrayList<>();
	private List<FormaDePago> formasDePago = new ArrayList<FormaDePago>();
	private List<Reserva> reservasDelInmueble = new ArrayList<>();
	private Usuario propietario;
	private List<PrecioEspecifico> preciosEspecificos = new ArrayList<>();
	private LocalDate fechaInicial;
	private LocalDate fechaFinal;
	private double precioBase;
	private int promedioPuntajeTotal;
	private SolicitudDeReserva solicitudDeReserva;
	private Manager manager;

	public Inmueble(Usuario propietario, int superficie, String pais, String ciudad, String direccion, int capacidad,
			 		LocalTime checkIn, LocalTime checkOut, double precioBase) {
		
		this.propietario = propietario;
		this.superficie = superficie;
		this.pais = pais;
		this.ciudad = ciudad;
		this.direccion = direccion;
		this.capacidad = capacidad;
		this.checkIn = checkIn;
		this.checkOut = checkOut;
		this.precioBase = precioBase; 
	}
	
	public void datosDelInmueble() {
		this.getSuperficie();
		this.getPais();
		this.getCiudad();
		this.getDireccion(); 
		this.getCapacidad();
		this.getFotos();
		this.getTiposDeServicios();
		this.getCheckIn();
		this.getCheckOut();
		this.getComentarios();
		this.getRankeosInmueble();
		this.calcularPromedioTotal();
	}

	private List<TipoDeServicio> getTiposDeServicios() {
		return this.tiposDeServicios;
		
	}

	public List<Foto> getFotos(){
		return this.cincoFotos;
	}
	
	public LocalTime getCheckIn() {
		return this.checkIn;
	}

	public LocalTime getCheckOut() {
		return this.checkOut;
	}

	public String getDireccion() {
		return this.direccion;
	}

	public String getPais() {
		return this.pais;
	}

	public int getSuperficie() {
		return this.superficie;
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


	public int calcularPromedioTotal() {

		double sumaTotal = 0;
        for (Rankeo rank : rankeosInmueble) {
            sumaTotal += rank.getPuntaje();
        }

        return (int) (sumaTotal / rankeosInmueble.size());
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
		if (this.precioBase > precioBase) {
			this.manager.bajaDePrecio(this);
		}
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

	public double calcularPrecioTotal(LocalDate inicioReserva, LocalDate finReserva) {
		return inicioReserva.datesUntil(finReserva.plusDays(1))
	            			.mapToDouble(fechaActual -> this.preciosEspecificos.stream()
	            			.filter(precio -> (fechaActual.isEqual(precio.getFechaInicial()) || fechaActual.isAfter(precio.getFechaInicial())) &&
	                                          (fechaActual.isEqual(precio.getFechaFinal()) || fechaActual.isBefore(precio.getFechaFinal())))
	            			.findFirst()
	            			.map(PrecioEspecifico::getPrecioPorPeriodo)
	            			.orElse(this.precioBase))
	            			.sum();
	}

	public Usuario getPropietario() {
		return this.propietario;
	}

	public void setComentario(String comentario){
		comentarios.add(comentario);
	}

	public void setRankeosInmueble(Rankeo rankeo) {
		this.rankeosInmueble.add(rankeo);
	}

	public void actualizarListaDeRaneko(Rankeo rankeo) {

		if (this.estaElRank(rankeo)) {

			this.actualizarPuntajeDeRankeo(rankeo);
		}
		else {

			this.setRankeosInmueble(rankeo);
		}
	}

	public Boolean estaElRank(Rankeo rankeo) {
		return this.rankeosInmueble.stream()
				.anyMatch(rank -> rank.getCategoria().nombreCategoria().equals
						 (rankeo.getCategoria().nombreCategoria()));
	}

	public void actualizarPuntajeDeRankeo(Rankeo rankeo) {

		Optional<Rankeo> rankeoViejo = this.rankeosInmueble.stream()
			    									   .filter(rank -> rank.getCategoria().nombreCategoria().equals(rankeo.getCategoria().nombreCategoria())).findFirst();

		this.rankeosInmueble.stream()
	    				.filter(rank -> rank.getCategoria().nombreCategoria().equals(rankeo.getCategoria().nombreCategoria()))
	    				.forEach(rank -> rank.setPuntaje((int) ((rankeoViejo.get().getPuntaje() + rankeo.getPuntaje()) / 2)));
	}
	
	public List<String> getComentarios(){
		return comentarios;
	}

	public List<Rankeo> getRankeosInmueble(){
		return rankeosInmueble;
	}

	public String getCiudad() {

		return this.ciudad;
	}

	public void setTipoDeInmueble(Optional<TipoDeInmueble> tipoDeInmueble) throws Exception {
		if (tipoDeInmueble.isEmpty()) {
			throw new Exception("Error: El tipo de inmueble seleccionado es incorrecto.");

		}
		else {
			this.tipoInmueble = tipoDeInmueble.get();
		}
	}

	public TipoDeInmueble getTipoInmueble() {
		return this.tipoInmueble;
	}

	public void setTipoDeServicios(List<TipoDeServicio> servicios) {

		this.tiposDeServicios = servicios;
	}

	public void setFotos(List<Foto> fotos) throws Exception {

		if (fotos.size() > 5) {
			this.cincoFotos = fotos;
		}
		else {
			throw new Exception("Error: La cantidad de fotos permitidas es hasta 5.");
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
		return this.capacidad;
	}
	
	public void modificarPreciosEspecificos(LocalDate fechaInicial, LocalDate fechaFinal, double precio) throws Exception{
		PrecioEspecifico fechaEspecifica = new PrecioEspecifico(fechaInicial, fechaFinal, precio);
		if(estaFechaEspecifica(fechaInicial, fechaFinal)) {
			this.preciosEspecificos.add(fechaEspecifica);
		}
		else {
			throw new Exception("Las fechas ingresadas ya estan declaradas");
		}
	}
	
	public Boolean estaFechaEspecifica(LocalDate fechaInicial, LocalDate fechaFinal) {
		return this.preciosEspecificos.stream().anyMatch(especifico -> !especifico.getFechaInicial().isAfter(fechaInicial) 
																	&& !especifico.getFechaFinal().isBefore(fechaFinal));
	}
	
	public void modificarFormasDePago(FormaDePago formaDePago) {
		this.formasDePago.add(formaDePago);
	}

    public void setManager(Manager manager) {
		this.manager = manager;
	}
	
	public Manager getManager() {
		return this.manager;
	}
}

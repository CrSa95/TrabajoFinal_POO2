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
	private PoliticaDeCancelacion politicaDeCancelacion;
	private List<String> comentarios = new ArrayList<String>();
	private List<Rankeo> rankeosInmueble = new ArrayList<Rankeo>();
	private List<FormaDePago> formasDePago = new ArrayList<FormaDePago>();
	private List<Reserva> reservasDelInmueble = new ArrayList<Reserva>();
	private Usuario propietario;
	private List<PrecioEspecifico> preciosEspecificos = new ArrayList<PrecioEspecifico>();
	private LocalDate fechaInicial;
	private LocalDate fechaFinal;
	private double precioBase;
	private Manager manager;
	private SitioWeb sitioWeb;

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
		this.setSitioWeb(propietario.getSitioWeb());
	}
	
	public void setSitioWeb(SitioWeb sitioWeb) {
		this.sitioWeb = sitioWeb;
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

	public List<TipoDeServicio> getTiposDeServicios() {
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

	public int calcularPromedioTotal() {

		double sumaTotal = 0;
        for (Rankeo rank : rankeosInmueble) {
            sumaTotal += rank.getPuntaje();
        }

        return (int) (sumaTotal / rankeosInmueble.size());
	}

	public void agregarReserva(Reserva reserva) {
		this.reservasDelInmueble.add(reserva);
	}

	public void eliminarReserva(Reserva reserva) {
		this.reservasDelInmueble.remove(reserva);
	}

	public List<Reserva> getReservas(){
		return this.reservasDelInmueble;
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
	
	public void modificarPreciosEspecificos(LocalDate fechaInicial, LocalDate fechaFinal, double precio) throws Exception{
		PrecioEspecifico fechaEspecifica = new PrecioEspecifico(fechaInicial, fechaFinal, precio);
		if(estaFechaEspecifica(fechaInicial, fechaFinal)) {
			throw new Exception("Las fechas ingresadas ya estan declaradas");
		}
		else {
			this.preciosEspecificos.add(fechaEspecifica);
		}
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
	
	public void dejarUnComentarioAlInmueble(Reserva reserva, String comentario) throws Exception {

		reserva.getEstadoDeReserva().finalizoLaReserva(reserva);
		comentarios.add(comentario);
	
	}
	
	public void rankearUnInmueble(Reserva reserva, Categoria categoria, int puntaje) throws Exception  {

		reserva.getEstadoDeReserva().finalizoLaReserva(reserva);
		this.sitioWeb.estaCategoriaEspecificaInmueble(categoria);
		this.actualizarListaDeRaneko(new Rankeo(categoria, puntaje));
	}
	
	public void actualizarListaDeRaneko(Rankeo rankeo) {

		if (this.estaElRank(rankeo)) {

			this.actualizarPuntajeDeRankeo(rankeo);
		}
		else {

			this.rankeosInmueble.add(rankeo);
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

		if (fotos.size() <= 5) {
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
	
	public SolicitudDeReserva realizarReservaDelInmueble(Usuario inquilino, FormaDePago formaDePago, LocalDate fechaDeIngreso, LocalDate fechaDeEgreso) {
		return new SolicitudDeReserva(this, inquilino, formaDePago, fechaDeIngreso, fechaDeEgreso);
	}

	public void solicitarReserva(SolicitudDeReserva solicitud) {
		solicitud.solicitarReserva();
	}
	
	public int getCapacidad() {
		return this.capacidad;
	}
	
	public Boolean estaFechaEspecifica(LocalDate fechaInicial, LocalDate fechaFinal) {
		return this.preciosEspecificos.stream().anyMatch(especifico -> !especifico.getFechaInicial().isAfter(fechaInicial) 
																	&& !especifico.getFechaFinal().isBefore(fechaFinal));
	}
	
	public void modificarFormasDePago(FormaDePago formaDePago) {
		this.formasDePago.add(formaDePago);
	}
	
	public List<FormaDePago> getFormasDePago(){
		return this.formasDePago;
	}
	
    public void setManager(Manager manager) {
		this.manager = manager;
	}
	
	public Manager getManager() {
		return this.manager;
	}

	public Boolean estaAlquiladoActualmente() {

		return this.reservasDelInmueble.size() != 0;
	}
}

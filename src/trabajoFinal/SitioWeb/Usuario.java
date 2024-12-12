package trabajoFinal.SitioWeb;

import java.time.LocalDate; 
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Usuario implements UsuarioInquilino,UsuarioPropietario {

	private String nombreCompleto;
	private String mail;
	private int telefono;
	private LocalDate fechaRegistro;
	private List<String> comentariosInquilino = new ArrayList<>();
	private List<String> comentariosPropietario = new ArrayList<>();
	private List<Rankeo> rankeosInquilino = new ArrayList<>();
	private List<Rankeo> rankeosPropietario = new ArrayList<>();
	private List<Reserva> reservas = new ArrayList<>();
	private SitioWeb sitioWeb;
	private String contenidoMail;

	public Usuario (String nombreCompleto, String mail, int telefono) {

		this.nombreCompleto = nombreCompleto;
		this.mail = mail;
		this.telefono = telefono;

	}
	
	// Get
	
	@Override
	public SitioWeb getSitioWeb() { 
		return this.sitioWeb;
	}

	@Override
	public String getNombre() {
		return this.nombreCompleto; 
	}
	
	@Override
	public String getMail() {
		return mail;
	}
	
	@Override
	public int getTelefono() {
		return telefono;
	}
	
	@Override
	public List<Rankeo> getRankeosInquilino(){
		return this.rankeosInquilino;
	}

	@Override
	public List<String> getComentariosInquilino(){
		return comentariosInquilino; 
	}

	public List<Reserva> getReservas(){
		return this.reservas;
	}
	
	@Override
	public List<String> getComentariosPropietario(){
		return this.comentariosPropietario;
	}

	@Override
	public List<Rankeo> getRankeosPropietario(){
		return this.rankeosPropietario;
	}

	public void registrarEnSitioWeb(SitioWeb sitioWeb) {
		this.sitioWeb = sitioWeb;
		this.fechaRegistro = LocalDate.now();
	}

	public int cantidadTiempoRegistrado(){
		return (int) ChronoUnit.DAYS.between(fechaRegistro, LocalDate.now());
	}

	public void recibirMail(String contenidoMail){
		this.contenidoMail = contenidoMail;
	}
	
	public void enviarMail(String contenidoDelMail, Usuario usuario){ 
		usuario.recibirMail(contenidoDelMail);
	}

	@Override
	public void registrarReserva(Reserva reserva) {
		this.reservas.add(reserva);
	}
	
	@Override
	public void eliminarReserva(Reserva reserva) {
		this.reservas.remove(reserva);
	}

	@Override
	public int calcularPromedioTotal(List<Rankeo> rankeos) {

		double sumaTotal = 0;
        for (Rankeo rank : rankeos) {
            sumaTotal += rank.getPuntaje();
        }

        return (int) (sumaTotal / rankeos.size());
	}

	@Override
	public List<Inmueble> inmueblesAlquilados() {
	    return this.getReservas().stream()
	            .filter(reserva -> reserva.getEstadoDeReserva().esFinalizado())
	            .map(Reserva::getInmueble)
	            .distinct()
	            .collect(Collectors.toList());
	}
	
	@Override
	public void recibirSolicitudReserva(SolicitudDeReserva solicitudDeReserva) {
		solicitudDeReserva.aprobarSolicitud();
	}
	
	@Override
	public void cancelarReserva(Reserva reserva) throws Exception{

		if (this.getReservas().stream().anyMatch(reser -> reser.equals(reserva))){
			reserva.cancelarReserva();
			this.eliminarReserva(reserva);
		}
		else {
			throw new Exception("La Reserva no le pertenece");
		}
	}

	@Override
	public double recibirResarcimiento(double dineroResarcido) {
		return dineroResarcido;
	}

	// Comentarios
	@Override
	public void agregarUnComentarioAlPropietario(Reserva reserva, String comentario) throws Exception{
		reserva.getEstadoDeReserva().registrarComentarioPropietario(this, comentario);
	}

	@Override
	public void agregarUnComentarioAlInqulino(Reserva reserva, String comentario) throws Exception{
		reserva.getEstadoDeReserva().registrarComentarioInquilino(this, comentario);
	}
	
	// Rankeo
	@Override
	public void rankearUnPropietario(Reserva reserva, Categoria categoria, int puntaje) throws Exception {
		reserva.getEstadoDeReserva().rankearPropietario(this, categoria, puntaje);
	}
	

	@Override
	public void rankearUnInquilino(Reserva reserva, Categoria categoria, int puntaje) throws Exception {
		reserva.getEstadoDeReserva().rankearInquilino(this, categoria, puntaje);
	}

	@Override
	public void actualizarListaDeRankeoInquilino(Rankeo rankeo) {
		if (this.estaElRankeo(rankeo,this.getRankeosInquilino())) {
			this.actualizarPuntajeDeRankeo(rankeo, this.getRankeosInquilino());
		}
		else {
			this.getRankeosInquilino().add(rankeo);
		}
		
	}
	
	@Override
	public void actualizarListaDeRankeoPropietario(Rankeo rankeo) {
		if (this.estaElRankeo(rankeo,this.getRankeosPropietario())) {
			this.actualizarPuntajeDeRankeo(rankeo,this.getRankeosPropietario());
		}
		else {
			this.getRankeosPropietario().add(rankeo);
		}
	}
	
	public Boolean estaElRankeo(Rankeo rankeo, List<Rankeo> listaDeRankeo) {
		return listaDeRankeo.stream()
				.anyMatch(rank -> rank.getCategoria().nombreCategoria().equals
						 (rankeo.getCategoria().nombreCategoria()));
	}

	@Override
	public void actualizarPuntajeDeRankeo(Rankeo rankeo, List<Rankeo> listaDeRankeo) {

		Optional<Rankeo> rankeoViejo = listaDeRankeo.stream()
			    									   .filter(rank -> rank.getCategoria().nombreCategoria().equals(rankeo.getCategoria().nombreCategoria())).findFirst();

		listaDeRankeo.stream()
	    				.filter(rank -> rank.getCategoria().nombreCategoria().equals(rankeo.getCategoria().nombreCategoria()))
	    				.forEach(rank -> rank.setPuntaje((int)((rankeoViejo.get().getPuntaje() + rankeo.getPuntaje()) / 2)));
	}
}
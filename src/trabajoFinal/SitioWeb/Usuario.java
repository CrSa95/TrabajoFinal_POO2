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
	
	public SitioWeb getSitioWeb() { 
		return this.sitioWeb;
	}

	public LocalDate getFechaDeRegistro() {
		return this.fechaRegistro;
	}

	public String getNombre() {
		return this.nombreCompleto; 
	}
	
	public String getMail() {
		return mail;
	}
	
	public int getTelefono() {
		return telefono;
	}
	
	public String getContenidoMail() {
		return this.contenidoMail; 
	}
	
	public List<Rankeo> getRankeosInquilino(){
		return this.rankeosInquilino;
	}

	public List<String> getComentariosInquilino(){
		return comentariosInquilino; 
	}

	public List<Reserva> getReservas(){
		return this.reservas;
	}
	
	public List<String> getComentariosPropietario(){
		return this.comentariosPropietario;
	}

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
	
	public void registrarReserva(Reserva reserva) {
		this.reservas.add(reserva);
	}
	
	public void eliminarReserva(Reserva reserva) {
		this.reservas.remove(reserva);
	}

	public int calcularPromedioTotal(List<Rankeo> rankeos) {

		double sumaTotal = 0;
        for (Rankeo rank : rankeos) {
            sumaTotal += rank.getPuntaje();
        }

        return (int) (sumaTotal / rankeos.size());
	}

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
	public void dejarUnComentarioAlPropietario(Reserva reserva, String comentario) throws Exception{
		reserva.getEstadoDeReserva().registrarComentarioParaElPropietario(this, comentario);
	}

	@Override
	public void dejarUnComentarioAlInqulino(Reserva reserva, String comentario) throws Exception{
		reserva.getEstadoDeReserva().registrarComentarioParaElInquilino(this, comentario);
	}
	
	// Rankeo
	@Override
	public void rankearAPropietario(Reserva reserva, Categoria categoria, int puntaje) throws Exception {
		reserva.getEstadoDeReserva().rankearAUnPropietario(this, categoria, puntaje);
	}
	

	@Override
	public void rankearAInquilino(Reserva reserva, Categoria categoria, int puntaje) throws Exception {
		reserva.getEstadoDeReserva().rankearAUnInquilino(this, categoria, puntaje);
	}

	@Override
	public void actualizarListaDeRankeoInquilino(Rankeo rankeo) {
		if (this.estaElRank(rankeo,this.getRankeosInquilino())) {
			this.actualizarPuntajeDeRankeo(rankeo, this.getRankeosInquilino());
		}
		else {
			this.getRankeosInquilino().add(rankeo);
		}
		
	}
	
	@Override
	public void actualizarListaDeRankeoPropietario(Rankeo rankeo) {
		if (this.estaElRank(rankeo,this.getRankeosPropietario())) {
			this.actualizarPuntajeDeRankeo(rankeo,this.getRankeosPropietario());
		}
		else {
			this.getRankeosPropietario().add(rankeo);
		}
	}
	
	public Boolean estaElRank(Rankeo rankeo, List<Rankeo> listaDeRankeo) {
		return listaDeRankeo.stream()
				.anyMatch(rank -> rank.getCategoria().nombreCategoria().equals
						 (rankeo.getCategoria().nombreCategoria()));
	}

	public void actualizarPuntajeDeRankeo(Rankeo rankeo, List<Rankeo> listaDeRankeo) {

		Optional<Rankeo> rankeoViejo = listaDeRankeo.stream()
			    									   .filter(rank -> rank.getCategoria().nombreCategoria().equals(rankeo.getCategoria().nombreCategoria())).findFirst();

		listaDeRankeo.stream()
	    				.filter(rank -> rank.getCategoria().nombreCategoria().equals(rankeo.getCategoria().nombreCategoria()))
	    				.forEach(rank -> rank.setPuntaje((int)((rankeoViejo.get().getPuntaje() + rankeo.getPuntaje()) / 2)));
	}
	
	@Override
	public void datosDelPropietario(Inmueble inmueble) {
		this.getNombre();
		this.getMail();
		this.getTelefono();
		this.getComentariosPropietario();
		this.getRankeosPropietario();
		this.calcularPromedioTotal(this.getRankeosPropietario());
		this.cantidadTiempoRegistrado();
		this.sitioWeb.cantidadDeVecesQueUnPropietarioAlquiloUnInmueble(inmueble, this);
		this.sitioWeb.cantidadDeVecesQueAlquiloUnPropietarioSusInmuebles(this);
		this.inmueblesAlquilados();
	}
	
	@Override
	public void datosDelInquilino() {
		this.getNombre();
		this.getMail();
		this.getTelefono();
		this.getComentariosInquilino();
		this.getRankeosInquilino();
		this.calcularPromedioTotal(this.getRankeosInquilino());
		this.cantidadTiempoRegistrado();
	}
}
package trabajoFinal.SitioWeb;

public class OtroSitioWeb implements Suscriptor{

	private HomePagePublisher homePagePublisher;

	public OtroSitioWeb(HomePagePublisher homePagePublisher) {
		this.homePagePublisher = homePagePublisher;
	}

	@Override
	public void bajaDePrecio(Inmueble inmueble) {
		this.homePagePublisher.publish( "No te pierdas sta oferta: Un inmueble" + inmueble.getTipoInmueble()
											+ "a tan sólo" + inmueble.getPrecioBase() + "pesos");
	}

	@Override
	public void cancelacionDeReserva(Inmueble inmueble) {

	}

	@Override
	public void altaDeReserva() {

	}

}

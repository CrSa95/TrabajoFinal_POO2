package trabajoFinal.SitioWeb;

public class AppMobile implements Listener {

	private PopUpWindow popUpWindow;
	
	public AppMobile(PopUpWindow popUpWindow) {
		this.popUpWindow = popUpWindow;
	}
	
	@Override
	public void bajaDePrecio(Inmueble inmueble) {
		
	}

	@Override
	public void cancelacionDeReserva(Inmueble inmueble) {
		this.popUpWindow.popUp( "El/la " + inmueble.getTipoInmueble().getTipoDeInmueble() + " que te interesa se ha liberado! Corre a reservarlo!", "Rojo", 10);
	}

	@Override
	public void altaDeReserva() {
		
	}

}

package trabajoFinal.SitioWeb;

public interface Listener {

	public abstract void bajaDePrecio(Inmueble inmueble);
	public abstract void cancelacionDeReserva(Inmueble inmueble);
	public abstract void altaDeReserva();
}

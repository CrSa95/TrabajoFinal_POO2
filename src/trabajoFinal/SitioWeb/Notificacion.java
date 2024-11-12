package trabajoFinal.SitioWeb;

import java.util.ArrayList;
import java.util.List;

public class Notificacion implements Suscriptor{
	
	private List<Suscriptor> listeners;
	
	public Notificacion() {
		listeners = new ArrayList<Suscriptor>();
	}
	
	public void addListener(Suscriptor listener) {
		this.listeners.add(listener);
	}
	
	public void removeListener(Suscriptor listener) {
		this.listeners.remove(listener);
	}

	@Override
	public void bajaDePrecio(Inmueble inmueble) {
		this.listeners.stream()
							.forEach(l -> l.bajaDePrecio(inmueble));
	}

	@Override
	public void cancelacionDeReserva(Inmueble inmueble) {
		this.listeners.stream()
							.forEach(l -> l.cancelacionDeReserva(inmueble));
		
	}

	@Override 
	public void altaDeReserva() {
		this.listeners.stream()
							.forEach(l -> l.altaDeReserva());
		
	}
	
	public List<Suscriptor> getListeners(){
		return this.listeners;
	}
	
}
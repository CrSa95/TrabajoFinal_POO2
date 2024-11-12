package trabajoFinal.SitioWeb;

import java.util.ArrayList;
import java.util.List;

public class Manager implements Listener{
	
	private List<Listener> listeners;
	
	public Manager() {
		listeners = new ArrayList<Listener>();
	}
	
	public void addListener(Listener listener) {
		this.listeners.add(listener);
	}
	
	public void removeListener(Listener listener) {
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
	
	public List<Listener> getListeners(){
		return this.listeners;
	}	
}
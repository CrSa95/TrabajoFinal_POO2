package trabajoFinal.SitioWeb;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

class ManagerTestCase {
	
    private Manager manager;
    private Listener listenerMock1;
    private Listener listenerMock2;

    @BeforeEach
    public void setUp() {
        manager = new Manager();
        listenerMock1 = mock(Listener.class);
        listenerMock2 = mock(Listener.class);
     
    }

    @Test
    public void testAddListener() {
        manager.addListener(listenerMock1);

        assertEquals(1, manager.getListeners().size());
        assertTrue(manager.getListeners().contains(listenerMock1));
    }

    @Test
    public void testRemoveListener() {
        manager.addListener(listenerMock1);
        manager.addListener(listenerMock2);
        List<Listener> listeners = manager.getListeners();
        
        assertEquals(2, listeners.size());
        
        manager.removeListener(listenerMock1);

        assertEquals(1, listeners.size());
        assertFalse(listeners.contains(listenerMock1));
        assertTrue(listeners.contains(listenerMock2));
    }
    
    @Test
    public void testBajaDePrecioNotificaListeners() {
        Inmueble inmuebleMock = mock(Inmueble.class);
        manager.addListener(listenerMock1);
        manager.addListener(listenerMock2);

        manager.bajaDePrecio(inmuebleMock);

        verify(listenerMock1).bajaDePrecio(inmuebleMock);
        verify(listenerMock2).bajaDePrecio(inmuebleMock);
    }
    
    @Test
    public void testCancelacionDeReservaNotificaListeners() {
        Inmueble inmuebleMock = mock(Inmueble.class);
        manager.addListener(listenerMock1);
        manager.addListener(listenerMock2);

        manager.cancelacionDeReserva(inmuebleMock);

        verify(listenerMock1).cancelacionDeReserva(inmuebleMock);
        verify(listenerMock2).cancelacionDeReserva(inmuebleMock);
    }
    
    @Test
    public void testAltaDeReservaNotificaListeners() {
        manager.addListener(listenerMock1);
        manager.addListener(listenerMock2);

        manager.altaDeReserva();

        verify(listenerMock1).altaDeReserva();
        verify(listenerMock2).altaDeReserva();
    }
}

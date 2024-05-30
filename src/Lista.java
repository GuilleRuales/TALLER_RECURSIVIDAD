import java.util.ArrayList;
import java.util.List;

public class Lista {

    private List<Paqueteria> serviEntrega;
    public Lista() {
        serviEntrega = new ArrayList<Paqueteria>();
    }

    public List<Paqueteria> getServiEntrega() {
        return serviEntrega;
    }

    public void adicionarElementos(Paqueteria p) throws Exception {
        if (serviEntrega.isEmpty())
            serviEntrega.add(p);
        else {
            for (Paqueteria pa : serviEntrega)
                if(pa.getTracking() == p.getTracking())
                    throw new Exception("Paquete ya Existe");
            serviEntrega.add(p);
        }
    }

    public String listarPaquetes(){
        String mensaje = "";
        for (Paqueteria p:serviEntrega)
            mensaje += p+"\n";
        return mensaje;
    }


    public int sumarTotalPaquetes (){
        return totalPaquetes(0);
    }
    public int totalPaquetes (int indice){
        if(serviEntrega.size()==indice)
            return 0;
        else{
            return 1+totalPaquetes(indice+1);
        }
    }


    public Double sumarTotalPeso(){
        return totalPeso(0);
    }
    public double totalPeso(int indice){
        if(serviEntrega.size()==indice)
            return 0;
        else{
            return  serviEntrega.get(indice).getPeso()+totalPeso(indice+ 1);
        }
    }


    public double sumarTotalPesoCiudad(String ciudad) {
        return totalPesoCiudad(0, ciudad);
    }
    public double totalPesoCiudad(int indice, String ciudad) {
        if (serviEntrega.size() == indice)
            return 0;
        else {
            double pesoActual = 0;
            if (serviEntrega.get(indice).getCiudadRecepcion().equals(ciudad))
                return serviEntrega.get(indice).getPeso() + totalPesoCiudad(indice + 1, ciudad);
            else
                return totalPesoCiudad(indice+1,ciudad);
        }
    }


    public int sumarTotalPaquetesEstado(String estado) {
        return totalPaquetesEstado(0, estado);
    }
    public int totalPaquetesEstado(int indice, String estado) {
        if (serviEntrega.size() == indice)
            return 0;
        else {
            if (serviEntrega.get(indice).getEstado().equals(estado)) {
                return 1 + totalPaquetesEstado(indice + 1, estado);
            } else {
                return totalPaquetesEstado(indice + 1, estado);
            }
        }
    }
    public String estadoPaquete(int tracking) throws Exception {
        Paqueteria paqueteria = buscarPaquete(tracking);
        if (paqueteria != null) {
            String estadoActual = paqueteria.getEstado();
            switch (estadoActual) {
                case "Receptado":
                    paqueteria.setEstado("Enviado");
                    return "Enviado";
                case "Enviado":
                    paqueteria.setEstado("Recibido");
                    return "Recibido";
                case "Recibido":
                    return "Finalizado";
            }
        } else {
            throw new Exception("No se encontró ningún paquete con el tracking especificado: " + tracking);
        }
        return null;
    }


    public void editar(int tracking, double nuevoPeso, String nuevaCiudadRecepcion, String nuevaCiudadEntrega, String nuevaCedula)  throws Exception {
        Paqueteria paqueteria = buscarPaquete(tracking);
        if (paqueteria != null) {
            paqueteria.setPeso(nuevoPeso);
            paqueteria.setCiudadRecepcion(nuevaCiudadRecepcion);
            paqueteria.setCiudadEntrega(nuevaCiudadEntrega);
            paqueteria.setCedulaReceptor(nuevaCedula);
        } else {
            throw new Exception("No se encontró ningún paquete con el tracking especificada: " + tracking);
        }
    }
    public Paqueteria buscarPaquete(int tracking) {
        for (Paqueteria p : serviEntrega)
            if (p.getTracking() == tracking)
                return p;
        return null;
    }


    public List<Paqueteria> ordenarPaquetesPorTracking() {
        List<Paqueteria> lista = new ArrayList<>(serviEntrega);
        for (int i = 0; i < lista.size() - 1; i++) {
            for (int j = 0; j < lista.size() - i - 1; j++) {
                if (lista.get(j + 1).getTracking() < lista.get(j).getTracking()) {
                    Paqueteria aux = lista.get(j + 1);
                    lista.set(j + 1, lista.get(j));
                    lista.set(j, aux);
                }
            }
        }
        return lista;
    }

    public List<Paqueteria> ordenarPaquetesPorPeso() {
        List<Paqueteria> lista = new ArrayList<>(serviEntrega);
        for (int p = 1; p < lista.size(); p++) {
            Paqueteria aux = lista.get(p);
            int j = p - 1;
            while (j >= 0 && aux.getPeso() < lista.get(j).getPeso()) {
                lista.set(j + 1, lista.get(j));
                j--;
            }
            lista.set(j + 1, aux);
        }
        return lista;
    }

}

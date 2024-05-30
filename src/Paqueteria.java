public class Paqueteria {

    private int tracking;
    private double peso;
    private String ciudadRecepcion;
    private String ciudadEntrega;
    private String cedulaReceptor;
    private String estado;

    public Paqueteria(int tracking, double peso, String ciudadRecepcion, String ciudadEntrega, String cedulaReceptor) {
        this.tracking = tracking;
        this.peso = peso;
        this.ciudadEntrega = ciudadEntrega;
        this.ciudadRecepcion = ciudadRecepcion;
        this.cedulaReceptor = cedulaReceptor;
        this.estado = "Receptado";
    }

    public int getTracking() {
        return tracking;
    }

    public void setTracking(int tracking) {
        this.tracking = tracking;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public String getCiudadEntrega() {
        return ciudadEntrega;
    }

    public void setCiudadEntrega(String ciudadEntrega) {
        this.ciudadEntrega = ciudadEntrega;
    }

    public String getCiudadRecepcion() {
        return ciudadRecepcion;
    }

    public void setCiudadRecepcion(String ciudadRecepcion) {
        this.ciudadRecepcion = ciudadRecepcion;
    }

    public String getCedulaReceptor() {
        return cedulaReceptor;
    }

    public void setCedulaReceptor(String cedulaReceptor) {
        this.cedulaReceptor = cedulaReceptor;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "\nNumero de Tracking: " + tracking +
                "\n  Peso: " + peso +
                "\n  Ciudad de Recepcion: " + ciudadRecepcion +
                "\n  Ciudad de Entrega: "+ ciudadEntrega +
                "\n  Cedula del Receptor: "+cedulaReceptor+
                "\n  Estado: "+estado;
    }
}

package com.example.shop.Modelo.Pedido;

public class Pedido {

    private int id;
    private String latitude;
    private String longitude;
    private String state;
    private int cantidad;
    private double total_pagar;
    private int producto_id;
    private int usuario_id;

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCantidad() { return cantidad;  }

    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public double getTotal_pagar() { return total_pagar; }

    public void setTotal_pagar(int total_pagar) { this.total_pagar = total_pagar; }

    public int getProducto_id() { return producto_id; }

    public void setProducto_id(int producto_id) {this.producto_id = producto_id; }

    public int getUsuario_id() { return usuario_id; }

    public void setUsuario_id(int usuario_id) { this.usuario_id = usuario_id; }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models.OD;



/**
 *
 * @author SCOTT
 */
public class ComentarioOD {
    
    private int id_c;
    private String objectid;
    private String texto; 
    private String fecha;
    private int privacidad;
    private int id_u;
    private int padre;
    private int gusta;
    private int nogusta;




    
    public ComentarioOD( String texto, String fecha, int id_u,int padre,int privacidad,int gusta, int nogusta) {
        
        this.texto = texto;
        this.fecha = fecha;
        this.id_u = id_u;
        this.padre = padre;
        this.gusta = 0;
        this.nogusta = 0;
        this.privacidad = privacidad;
    }
    
    public ComentarioOD(int id_c,String texto, String fecha, int id_u,int padre,int privacidad) {
        this.id_c = id_c;
        this.texto = texto;
        this.fecha = fecha;
        this.id_u = id_u;
        this.padre = padre;
        this.gusta = 0;
        this.nogusta = 0;
        this.privacidad = privacidad;
    }

    public int getPrivacidad() {
        return privacidad;
    }

    public void setPrivacidad(int privacidad) {
        this.privacidad = privacidad;
    }
    
    
    
    public ComentarioOD(){}

    public int getPadre() {
        return padre;
    }

    public void setPadre(int padre) {
        this.padre = padre;
    }
    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getId_c() {
        return id_c;
    }

    public void setId_c(int id_c) {
        this.id_c = id_c;
    }

    public int getId_u() {
        return id_u;
    }

    public void setId_u(int id_u) {
        this.id_u = id_u;
    }

    public String getObjectid() {
        return objectid;
    }

    public void setObjectid(String objectid) {
        this.objectid = objectid;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
     public int getGusta() {
        return gusta;
    }

    public void setGusta(int gusta) {
        this.gusta = gusta;
    }

    public int getNogusta() {
        return nogusta;
    }

    public void setNogusta(int nogusta) {
        this.nogusta = nogusta;
    }

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models.OD;

/**
 *
 * @author SCOTT
 */
public class TokenOD {
    
    private String _id;
    private int id_u;
    private int token; 
    private String fecha;
    private String ip;

    
    public TokenOD(){}
    
    public TokenOD(int id_u,int token, String fecha,String ip) {
        this.id_u = id_u;
        this.fecha = fecha;
        this.token = token;
        this.ip = ip;
    
    }
  
    public TokenOD(int id_u, String fecha) {
        this.fecha = fecha;
        this.token = id_u;
    }
    
    
    public int getToken() {
        return token;
    }

    public void setToken(int token) {
        this.token = token;
    }

    

    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getId_u() {
        return id_u;
    }

    public void setId_u(int id_u) {
        this.id_u = id_u;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
    	
        this.ip = ip;
    }
}

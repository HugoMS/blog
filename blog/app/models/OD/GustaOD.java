/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models.OD;

/**
 *
 * @author SCOTT
 */
public class GustaOD {
    private int id_c;
    private int gusta;
    private int id_u;

    public GustaOD(int id_c, int gusta, int id_u) {
        this.id_c = id_c;
        this.gusta = gusta;
        this.id_u = id_u;
    }

    public GustaOD() {
		
	}

	public int getGusta() {
        return gusta;
    }

    public void setGusta(int gusta) {
        this.gusta = gusta;
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
    
    
    
    
    
    
}

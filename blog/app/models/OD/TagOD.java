/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models.OD;

/**
 *
 * @author SCOTT
 */
public class TagOD {
     private int id_t;
    private String nombre;

    public TagOD(String nombre) {
       
        this.nombre = nombre;
    }

    public TagOD() {
	}

	public int getId_t() {
        return id_t;
    }

    public void setId_t(int id_t) {
        this.id_t = id_t;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}

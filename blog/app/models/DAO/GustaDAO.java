/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models.DAO;

import models.OD.GustaOD;

/**
 *
 * @author SCOTT
 */
public interface GustaDAO {
    
     public void insertar(GustaOD Gusta);
     public boolean permisodDeRepeticion(GustaOD Gusta);
     public GustaOD buscar(GustaOD Gusta);
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models.DAO;

import models.OD.TagOD;

/**
 *
 * @author SCOTT
 */
public interface TagDAO {
    public void insertar(TagOD Tag);
    public TagOD buscar(TagOD Tag);
    
}

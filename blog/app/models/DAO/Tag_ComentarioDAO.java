/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models.DAO;

import models.OD.ComentarioOD;
import models.OD.TagOD;
import models.OD.Tag_ComentarioOD;

/**
 *
 * @author SCOTT
 */
public interface Tag_ComentarioDAO {
    
    public void insertar(Tag_ComentarioOD Tag_Coemntario);
    public void eliminarComentario(ComentarioOD comentario);
    public void eliminarTag(TagOD tag);
    
    
}

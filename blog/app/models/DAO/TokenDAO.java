/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models.DAO;

import models.DAO.MongoDB.TokenMongoDB;
import models.OD.TokenOD;
import models.OD.UsuarioOD;

/**
 *
 * @author SCOTT
 */
public interface TokenDAO {
     public void insertar(TokenOD Token);
     public void eliminar(TokenOD Token);
     public TokenOD buscar(TokenOD Token);
     public TokenOD buscarPorUsuario(UsuarioOD Usuario);
     public TokenOD buscarIP(TokenOD Token);
}

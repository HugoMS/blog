/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models.DAO;

import com.mongodb.DBObject;
import java.util.List;
import models.OD.UsuarioOD;

/**
 *
 * @author SCOTT
 */
public interface UsuarioDAO {
    
    public void insertar(UsuarioOD Usuario); 
    public void insertarModificar(UsuarioOD Usuario);
    public void eliminar(UsuarioOD Usuario);
    public void eliminarModificar(UsuarioOD Usuario);
    public void modificar(UsuarioOD Nuevo);
    public UsuarioOD buscar(UsuarioOD Usuario);
    public UsuarioOD construir(DBObject obj);
    public List<UsuarioOD> listar();
    public UsuarioOD buscarID(UsuarioOD Usuario);
    
}
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models.DAO;

import java.util.List;


import models.OD.ComentarioOD;

/**
 *
 * @author SCOTT
 */
public interface ComentarioDAO {
    public void insertar(ComentarioOD comentario);
    public void eliminar(ComentarioOD comentario);
    public ComentarioOD buscar(ComentarioOD Comentario);
    public void ActualizaGustar(ComentarioOD Comentario, int gusta);
    public void Modificar(ComentarioOD comentario);
    public List<ComentarioOD> listar();
    public List<ComentarioOD> listarHijos(ComentarioOD Comentario);
    public List<ComentarioOD> listarPorUsuario(ComentarioOD Comentario);
    public List<ComentarioOD> listarPortag(int id_t);
    public int Buscarid();
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models.DAO.MongoDB;

import com.mongodb.*;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.OD.ComentarioOD;
import models.OD.TagOD;
import models.OD.Tag_ComentarioOD;

/**
 *
 * @author SCOTT
 */
public class Tag_ComentarioMongoDB implements models.DAO.Tag_ComentarioDAO{
    
        
     public Tag_ComentarioMongoDB(){
    }

    public DBCollection conectarMongo(){
        Mongo m;
        try {
            m = new Mongo();
            DB db = m.getDB( "blog" );
            DBCollection coleccionUsuario = db.getCollection("tag_comentario");
        return coleccionUsuario;
        } catch (UnknownHostException ex) {
            Logger.getLogger(UsuarioMongoDB.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (MongoException ex) {
            Logger.getLogger(UsuarioMongoDB.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
    }
    
    public void insertar(Tag_ComentarioOD Tag_Coemntario) {
        
        
        DBCollection coleccionTG = conectarMongo();
        
        if(coleccionTG!=null){
            
            BasicDBObject tag_comentario = new BasicDBObject();
            tag_comentario.put("id_c", Tag_Coemntario.getId_c());
            tag_comentario.put("id_t", Tag_Coemntario.getId_t());
            
            coleccionTG.insert(tag_comentario);
            
        }
        else
        {
            System.out.println("coleccion no existente");
        }
    }
    
        public void eliminarComentario(ComentarioOD comentario) {
        
        DBCollection coleccionTG = conectarMongo();
        
        BasicDBObject query = new BasicDBObject();
        query.put("id_c", comentario.getId_c());
        DBCursor cur = coleccionTG.find(query);
        
        while(cur.hasNext()) {
            coleccionTG.remove(cur.next());
            //System.out.println(cur.next());
        } 
    }
    
        
        public void eliminarTag(TagOD tag) {
        
        DBCollection coleccionTG = conectarMongo();
        
        BasicDBObject query = new BasicDBObject();
        query.put("id_t", tag.getId_t());
        DBCursor cur = coleccionTG.find(query);
        
        while(cur.hasNext()) {
            coleccionTG.remove(cur.next());
            //System.out.println(cur.next());
        } 
    }
}

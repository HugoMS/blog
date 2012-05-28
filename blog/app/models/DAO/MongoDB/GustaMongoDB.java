/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models.DAO.MongoDB;

import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.OD.ComentarioOD;
import models.OD.GustaOD;
import models.OD.UsuarioOD;

/**
 *
 * @author SCOTT
 */
public class GustaMongoDB implements models.DAO.GustaDAO {
    
    public DBCollection conectarMongo(){
        Mongo m;
        try {
            m = new Mongo();
            DB db = m.getDB( "blog" );
            DBCollection coleccionUsuario = db.getCollection("gusta");
        return coleccionUsuario;
        } catch (UnknownHostException ex) {
            Logger.getLogger(UsuarioMongoDB.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (MongoException ex) {
            Logger.getLogger(UsuarioMongoDB.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
        
        
        
     public void insertar(GustaOD Gusta){
     
        DBCollection coleccionGusta = conectarMongo();
        
        if(coleccionGusta!=null){
            
            BasicDBObject gusta = new BasicDBObject();
            gusta.put("id_c",Gusta.getId_c());
            gusta.put("gusta",Gusta.getGusta());
            gusta.put("id_u",Gusta.getId_u());
            coleccionGusta.insert(gusta);
            
        }
        else
        {
            System.out.println("coleccion no existente");
        }
        
        
     }
        public boolean permisodDeRepeticion(GustaOD Gusta){
     
        DBObject obj = null;
        DBCollection coleccionGusta = conectarMongo();
        BasicDBObject query = new BasicDBObject();
        query.put("id_u",Gusta.getId_u());
        query.put("id_u",Gusta.getId_c());
        DBCursor cur = coleccionGusta.find(query);
        
        while(cur.hasNext()) {
            //System.out.println(cur.next());
            obj = cur.next();
            
        }
        if(obj==null){
        return true;
        }else{return false;}
    }
        
        
     
        public GustaOD construir(DBObject obj){
            GustaOD gusta = new GustaOD();
            gusta.setId_u((Integer.parseInt(obj.get("id_u").toString())));
            gusta.setId_u((Integer.parseInt(obj.get("id_c").toString())));
            gusta.setId_u((Integer.parseInt(obj.get("gusta").toString())));
         
            
        return gusta;
        }
     
        
        
        
        
        public GustaOD buscar(GustaOD Gusta){
        	
        	
        	DBObject obj = null ;
            GustaOD beta = null;
            DBCollection coleccionGusta = conectarMongo();
            BasicDBObject query = new BasicDBObject();
            query.put("id_u",Gusta.getId_u());
            query.put("id_c",Gusta.getId_c());
            DBCursor cur = coleccionGusta.find(query);
               
            
            if(cur.count() != 0){
            
            while(cur.hasNext()){
                
            	obj = cur.next();
            	beta = construir(obj);
            	return beta;
                }  		
         
            return null;
            }else{return null;}
        }
        
        
        
}
    
    
    
    
    


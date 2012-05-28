/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models.DAO.MongoDB;

import com.mongodb.*;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import play.db.ebean.Model;
import models.OD.TokenOD;
import models.OD.UsuarioOD;

/**
 *
 * @author SCOTT
 */
public class TokenMongoDB implements models.DAO.TokenDAO {
    
    
      public TokenMongoDB(){
    }

    public DBCollection conectarMongo(){
        Mongo m;
        try {
            m = new Mongo();
            DB db = m.getDB( "blog" );
            DBCollection coleccionUsuario = db.getCollection("token");
        return coleccionUsuario;
        } catch (UnknownHostException ex) {
            Logger.getLogger(UsuarioMongoDB.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (MongoException ex) {
            Logger.getLogger(UsuarioMongoDB.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
    }
    
    
    public void insertar(TokenOD Token) {
        
        
        DBCollection coleccionToken = conectarMongo();
          if(coleccionToken!=null){
            
            BasicDBObject token = new BasicDBObject();
            token.put("id_u",Token.getId_u());
            token.put("fecha", Token.getFecha());
            token.put("token", Token.getToken());
            token.put("ip", Token.getIp());
            coleccionToken.insert(token);
            
        }
        else
        {
            System.out.println("coleccion no existente");
        }
    }
    
    public void eliminar(TokenOD Token) {
        
        DBCollection coleccionToken = conectarMongo();
        
        BasicDBObject query = new BasicDBObject();
        query.put("token", Token.getToken());
        DBCursor cur = coleccionToken.find(query);
        
        while(cur.hasNext()) {
            coleccionToken.remove(cur.next());
            //System.out.println(cur.next());
        } 
    }
    
    public TokenOD buscar(TokenOD Token){
        
        TokenOD beta = null;
        DBObject obj = null;
        DBCollection coleccionUsuario = conectarMongo();
        BasicDBObject query = new BasicDBObject();
        query.put("token",Token.getToken());
        System.out.println("3paso");
        DBCursor cur = coleccionUsuario.find(query);
      
        if(cur.count() != 0)
        {
        
        	while(cur.hasNext()) {
        	
            obj = cur.next();
 
            
        }
        beta = construir(obj);
      //  System.out.println(beta.getFecha());
           
        return beta;
        }
        else
        {
        	return null;
        }
    }
    
 
    
    public TokenOD buscarPorUsuario(UsuarioOD Usuario){
        
        TokenOD beta = null;
        DBObject obj = null;
        DBCollection coleccionUsuario = conectarMongo();
        BasicDBObject query = new BasicDBObject();
        query.put("id_u",Usuario.getId_u());
        DBCursor cur = coleccionUsuario.find(query);
      
        if(cur.count() != 0){
        
        while(cur.hasNext()) {
        	
            obj = cur.next();
 
            
        }
        beta = construir(obj);
      //  System.out.println(beta.getFecha());
           
        return beta;
        }else{return null;}
    }
 
     public TokenOD construir(DBObject obj){
        TokenOD token = new TokenOD();
        token.setId(obj.get("_id").toString());
        token.setId_u((Integer.parseInt(obj.get("id_u").toString())));
        token.setFecha(obj.get("fecha").toString());
        token.setIp(obj.get("ip").toString());
        token.setToken((Integer.parseInt(obj.get("token").toString())));
        
    return token;
    }
    
    	    
    	    
     public TokenOD buscarIP(TokenOD Token){
         
         TokenOD beta = null;
         DBObject obj = null;
         DBCollection coleccionUsuario = conectarMongo();
         BasicDBObject query = new BasicDBObject();
         query.put("ip",Token.getIp());
         DBCursor cur = coleccionUsuario.find(query);
       
         if(cur.count() != 0){
         
         while(cur.hasNext()) {
         	
             obj = cur.next();
  
             
         }
         beta = construir(obj);
       //  System.out.println(beta.getFecha());
            
         return beta;
         }else{return null;}
     }  
    	

}
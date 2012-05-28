/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models.DAO.MongoDB;

import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.OD.TagOD;
import models.OD.UsuarioOD;


/**
 *
 * @author SCOTT
 */
public class TagMongoDB implements models.DAO.TagDAO{
     public TagMongoDB(){
    }

    public DBCollection conectarMongo()
    {
        Mongo m;
        try 
        {
            m = new Mongo();
            DB db = m.getDB( "blog" );
            DBCollection coleccionUsuario = db.getCollection("tag");
            return coleccionUsuario;
        } catch (UnknownHostException ex) {
            Logger.getLogger(UsuarioMongoDB.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (MongoException ex) {
            Logger.getLogger(UsuarioMongoDB.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
    }
    
    
    
    public void insertar(TagOD Tag) 
    {       
        DBCollection coleccionTag = conectarMongo();        
        if(coleccionTag!=null)
        {            
            BasicDBObject tag = new BasicDBObject();
            tag.put("id_t",crearId());
            tag.put("nombre", Tag.getNombre());
            System.out.println(Tag.getNombre()+"NOMBRE DEL TAG");           
            coleccionTag.insert(tag);            
        }
        else
        {
            System.out.println("coleccion no existente");
        }
    }
    
    public int crearId()
    {
    	DBObject obj = null;
        DBCollection coleccionUsuario = conectarMongo();
        DBCursor cur = coleccionUsuario.find();
        int mayor = 0;
        while(cur.hasNext()) 
        {
            //System.out.println(cur.next());
            obj = cur.next();
            if(Integer.parseInt(obj.get("id_t").toString())>mayor)
            {
                mayor = Integer.parseInt(obj.get("id_t").toString());
            }
        }
        //System.out.println(obj.get("nombre"));
        if(obj!=null)
        {
        	return  mayor + 1;
        }
        else
        {
        	return 1;
        }
        
    }
    
       
    public void eliminar(TagOD Tag) 
    {
    	DBCollection coleccionTG = conectarMongo();
        
        BasicDBObject query = new BasicDBObject();
        query.put("id_t", Tag.getId_t());
        
        DBCursor cur = coleccionTG.find(query);
        
        while(cur.hasNext())
        {
            coleccionTG.remove(cur.next());
            //System.out.println(cur.next());
        } 
    }
        



    public TagOD construirTag(DBObject obj)
    {
    	TagOD Tag = new TagOD();
        Tag.setId_t((Integer.parseInt(obj.get("id_t").toString())));
        Tag.setNombre(obj.get("nombre").toString());
        return Tag;
    }
       
        
        
        
        
    public TagOD buscar(TagOD Tag)
    {       	
    	DBObject obj = null ;
        TagOD beta = null;
        DBCollection coleccionTag = conectarMongo();
        BasicDBObject query = new BasicDBObject();
        query.put("nombre",Tag.getNombre());
        DBCursor cur = coleccionTag.find(query);
        if(cur.count() != 0)
        {
        	while(cur.hasNext())
        	{
        		obj = cur.next();
            	beta = construirTag(obj);
            	System.out.println("encuentro algo");
            	return beta;
            }  	     
        	System.out.println("NO encuentro algo");
            return null;
        }
        else
        {
        	System.out.println("NO encuentro algo");
        	return null;
        }
    }
        
        
        
}

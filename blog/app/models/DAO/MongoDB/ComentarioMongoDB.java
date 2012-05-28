
package models.DAO.MongoDB;

import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.DAO.ComentarioDAO;
import models.OD.ComentarioOD;
import models.OD.Tag_ComentarioOD;


/**
 *
 * @author SCOTT
 */
public class ComentarioMongoDB implements models.DAO.ComentarioDAO{

    public ComentarioMongoDB() {
    }
   
    public DBCollection conectarMongo(){
        Mongo m;
        
        try {
            m = new Mongo();
            DB db = m.getDB( "blog" );
            DBCollection coleccionUsuario = db.getCollection("comentario");
        return coleccionUsuario;
        } catch (UnknownHostException ex) {
            Logger.getLogger(UsuarioMongoDB.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (MongoException ex) {
            Logger.getLogger(UsuarioMongoDB.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
    }
  
    public DBCollection conectarMongo2(){
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
    
    /**
     * Se encargada de insertar en persistencia (Mongodb) el comentario que le llega a la funcion
     * como parametro conviertiendolo en un objeti tipo mongo para luego insertatlo
     * @param Comentario
     */
    public void insertar(ComentarioOD Comentario){
     
        DBCollection coleccionComentario = conectarMongo();
        
        if(coleccionComentario!=null){
            
            BasicDBObject comentario = new BasicDBObject();
            comentario.put("id_c",crearId());
            comentario.put("texto", Comentario.getTexto());
            comentario.put("id_u", Comentario.getId_u());
            comentario.put("fecha", Comentario.getFecha());
            comentario.put("padre",Comentario.getPadre());
            comentario.put("gusta",Comentario.getGusta());
            comentario.put("nogusta",Comentario.getNogusta());
            comentario.put("privacidad",Comentario.getPrivacidad());
            
            coleccionComentario.insert(comentario);
            
            
            
            
            
        }
        else
        {
        	
            System.out.println("coleccion no existente");
           
        }
        
    }
   
    
    /**
     * Se encarga de eliminar los comentarios en persistencia(Mongodb) pasando el un objeto  del tipo comentario en el
     * cual sol
     *
     */
    public void eliminar(ComentarioOD comentario) {
        
        DBCollection coleccionComentario = conectarMongo();
        DBObject obj = null;
        BasicDBObject query = new BasicDBObject();
        query.put("padre", comentario.getId_c());
        DBCursor cur = coleccionComentario.find(query);
        
       
          	   
    	   
       
        while(cur.hasNext()) {  
            obj = cur.next();
            int id_c = Integer.parseInt(obj.get("id_c").toString());
            int padre = Integer.parseInt(obj.get("padre").toString());
            ComentarioOD comentarioR = new ComentarioOD(id_c,null, null, 0,padre,0);
            eliminar(comentarioR);
           
        coleccionComentario.remove(obj); 
            //System.out.println(cur.next());
        }
        eliminarOrigen(comentario);
            
    }
    
    
    
    public void eliminarOrigen(ComentarioOD comentario) {
        System.out.println(comentario.getId_c());
        DBCollection coleccionComentario = conectarMongo();
        BasicDBObject query = new BasicDBObject();
        query.put("id_c", comentario.getId_c());
        coleccionComentario.remove(query);
         
    }
    
    
    
     public ComentarioOD construir(DBObject obj){
        ComentarioOD comentario = new ComentarioOD();
       // comentario.setObjectid(obj.get("_id").toString());
        comentario.setId_c((Integer.parseInt(obj.get("id_c").toString())));
        comentario.setId_u((Integer.parseInt(obj.get("id_u").toString())));
        comentario.setFecha(obj.get("fecha").toString());
        comentario.setTexto(obj.get("texto").toString());
        comentario.setGusta((Integer.parseInt(obj.get("gusta").toString())));
        comentario.setPadre((Integer.parseInt(obj.get("padre").toString())));
        comentario.setNogusta((Integer.parseInt(obj.get("nogusta").toString())));
        comentario.setPrivacidad((Integer.parseInt(obj.get("privacidad").toString())));
        
    return comentario;
    }
     
     
     public ComentarioOD buscar(ComentarioOD comentario)
     {    	
     	 DBObject obj = null ;
         ComentarioOD beta = null;
         DBCollection coleccionUsuario = conectarMongo();
         BasicDBObject query = new BasicDBObject();
         query.put("id_c",comentario.getId_c());
         DBCursor cur = coleccionUsuario.find(query);
            
         if(cur.count() != 0)
         {
        	 while(cur.hasNext())
        	 {             
        		 obj = cur.next();
        		 beta = construir(obj);
        		 return beta;
             }      
        	 return null;
         }
         else
         {
        	 return null;
         }
         
   }
     
     
  

 
    public void ActualizaGustar(ComentarioOD Comentario, int gusta){
     
        
       eliminarOrigen(Comentario); 
       
       
       if(gusta==1){
   
        Comentario.setGusta(Comentario.getGusta() + 1);
        Modificar(Comentario);
      }
       
       if(gusta==0){
        Comentario.setNogusta(Comentario.getNogusta() + 1);
        Modificar(Comentario);
       
      }
        
    }
    
        public int crearId(){
            DBObject obj = null;
            DBCollection coleccionComentario = conectarMongo();
            DBCursor cur = coleccionComentario.find();
            int mayor = 0;
        while(cur.hasNext()) {
            //System.out.println(cur.next());
            obj = cur.next();
            if(Integer.parseInt(obj.get("id_c").toString())>mayor){
            mayor = Integer.parseInt(obj.get("id_c").toString());
            }
        }
        //System.out.println(obj.get("nombre"));
          
          if(obj!=null){
              return  mayor + 1;
          }else{return 1;}
        
        }
        
        
        public int Buscarid(){
            DBObject obj = null;
            DBCollection coleccionComentario = conectarMongo();
            DBCursor cur = coleccionComentario.find();
            int mayor = 0;
        while(cur.hasNext()) {
            //System.out.println(cur.next());
            obj = cur.next();
            if(Integer.parseInt(obj.get("id_c").toString())>mayor){
            mayor = Integer.parseInt(obj.get("id_c").toString());
            }
        }
        //System.out.println(obj.get("nombre"));
          
          if(obj!=null){
              return  mayor ;
          }else{return 1;}
        
        }
        
        public List<ComentarioOD> listar() {
            
            List<ComentarioOD> lista = new ArrayList<ComentarioOD>();
            DBObject obj = null;
            ComentarioOD beta = new ComentarioOD();
            DBCollection coleccionComentario = conectarMongo();
            DBCursor cur = coleccionComentario.find();
           
            while(cur.hasNext()) {
                //System.out.println(cur.next());
                obj = cur.next();
                beta = construir(obj);
                
                lista.add(beta);
            }
            
            return lista;
        }
   
   
        public List<ComentarioOD> listarHijos(ComentarioOD Comentario) {
            
            List<ComentarioOD> lista = new ArrayList<ComentarioOD>();
            DBObject obj = null;
            ComentarioOD beta = new ComentarioOD();
            BasicDBObject query = new BasicDBObject();
            DBCollection coleccionComentario = conectarMongo();
            query.put("padre",Comentario.getId_c());
            DBCursor cur = coleccionComentario.find(query);
          
           
            while(cur.hasNext()) {
                //System.out.println(cur.next());
                obj = cur.next();
                beta = construir(obj);
                lista.add(beta);
            }
            
            return lista;
        }
      
        public List<ComentarioOD> listarPorUsuario(ComentarioOD Comentario) {
	            
	            List<ComentarioOD> lista = new ArrayList<ComentarioOD>();
	            DBObject obj = null;
	            ComentarioOD beta = new ComentarioOD();
	            BasicDBObject query = new BasicDBObject();
	            DBCollection coleccionComentario = conectarMongo();
	            query.put("id_u",Comentario.getId_u());
	            DBCursor cur = coleccionComentario.find(query);
	          
	           
	            while(cur.hasNext()) {
	                //System.out.println(cur.next());
	                obj = cur.next();
	                beta = construir(obj);
	                lista.add(beta);
	            }
	            
	            return lista;
	        }
        
        public List<ComentarioOD> listarPortag(int id_t){
        	
			System.out.println("listar comentario por tag en comentario mongo db " + id_t);
		    List<ComentarioOD> lista = new ArrayList<ComentarioOD>();
		    DBObject obj = null;  
		    Tag_ComentarioOD beta = new Tag_ComentarioOD();
		    BasicDBObject query = new BasicDBObject();
		    DBCollection coleccionComentario = conectarMongo2();
		    query.put("id_t",id_t);
		    System.out.println(query);
		    DBCursor cur = coleccionComentario.find(query);
		  
		   
		    while(cur.hasNext()) {
		        obj = cur.next();
		        beta = construirtag(obj);
		        System.out.println(beta.getId_c()+" id del comentario a buscar para menter en la lista");
		        ComentarioOD buscar = new ComentarioOD();
		        buscar.setId_c(beta.getId_c());
		        ComentarioDAO traer = new ComentarioMongoDB();
		        buscar = traer.buscar(buscar);
		        
		        lista.add(buscar);
		    }
		    
		    return lista;
		
		}


        public Tag_ComentarioOD construirtag(DBObject obj){
		 
        	Tag_ComentarioOD tag = new  Tag_ComentarioOD();
		    tag.setId_c((Integer.parseInt(obj.get("id_c").toString())));
		    tag.setId_t((Integer.parseInt(obj.get("id_t").toString())));
		
		    
		return tag;
		}

		@Override
		public void Modificar(ComentarioOD comentario) {
			// TODO Auto-generated method stub
			
		}


}

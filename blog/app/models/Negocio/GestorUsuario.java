package models.Negocio;

import java.awt.List;

import play.Logger;

import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

import models.DAO.UsuarioDAO;
import models.DAO.MongoDB.TokenMongoDB;
import models.DAO.MongoDB.UsuarioMongoDB;
import models.OD.TokenOD;
import models.OD.UsuarioOD;

public class GestorUsuario {
	
	public GestorUsuario(){}
	
//funciones basicas	
	/**
	 * Se encarga de insertar el usuario en persistencia
	 * @param Usuario
	 * @return
	 */
	
	public boolean insertar(UsuarioOD Usuario)
	{
		if (validarNick(Usuario))
		{
			System.out.print(Usuario.getNick());
			UsuarioDAO Persona = new UsuarioMongoDB();
			Persona.insertar(Usuario);
			Logger.info("Usuario Insertado en sistema "+ Usuario.getNick());
			return true; 
		}
		else 
		{
			System.out.print("validar nick dijo que no");
			Logger.error("Nick ya existente");
			return false;
		}		
	}


	/**
	 * se encarga de buscar un usuario en especifico por  
	 * @param Usuario
	 * @return
	 */
	
	public UsuarioOD usuarioespecifico (UsuarioOD Usuario)
	{
		UsuarioDAO usuario = new UsuarioMongoDB();
		UsuarioOD result = usuario.buscar(Usuario);
		if (result != null)
		{
			return result;
		}
		else
		{
			return null;
		}

	}
	
	/**
	 * se encarga de eliminar  un usuaio en persistencia
	 * @param usuario
	 * @return
	 */
	public boolean eliminar(UsuarioOD usuario)
	{
		boolean estado = false;
		UsuarioDAO c = new UsuarioMongoDB();
	    c.eliminar(usuario);
	    Logger.info("Usuario Eliminado");
	    estado = true;
	    return estado;
	}

	/**
	 * se encarga de modificar los datos del usuario en persistencia
	 * @param Usuario
	 * @return
	 */
	public boolean modificar(UsuarioOD Usuario)
	{
		GestorToken gestort = new GestorToken();
		TokenOD Token = new TokenOD();
		Token.setToken(Usuario.getToken());
		if (gestort.validartoken(Token))
		{
			UsuarioDAO usuario = new UsuarioMongoDB();
			usuario.modificar(Usuario);//entonces primero busco y despues mando los dos //id_u debe ser el mismo
			Logger.info("Ha sido modificaco el usuario "+ Usuario.getNick());
			return true;
		}
		else 
		{
			Logger.error("No se puedo modificar el usuario, token vencido o no valido");
			return false;
		}
	 
}
	
	
	//funciones de validacion
	/**
	 * se encarga de validar el nick verificando si existe en persistencia
	 * @param Usuario
	 * @return
	 */
	public boolean validarNick(UsuarioOD Usuario)
	{	
		UsuarioDAO usuario = new UsuarioMongoDB();
		UsuarioOD result = usuario.buscar(Usuario);		
		if (result == null)
		{
			Logger.info("Usuario valido "+Usuario.getNick());
			return true;				
		}
		else
		{
			Logger.error("Usuario no exixtente");
			return false;
		}
	
	}
	
	/**
	 * se encarga de validar si la contrasena del usuario es valida
	 * @param Usuario
	 * @return
	 */

	public boolean validarPass(UsuarioOD Usuario)
	{		
		UsuarioDAO usuario = new UsuarioMongoDB();
		UsuarioOD result = usuario.buscar(Usuario);
		if ((result != null)&&(result.getClave().equals(Usuario.getClave())))
		{
			Logger.info("Contrasena valida");
			return true;				
		}
		else
		{
			Logger.error("Contrasena invalida");
			return false;
		}
	
	}


	/**
	 * se encarga de validar el nick y la contrasena del usuario sean  validas y que no se conecte desde la misma computadora 2 veces, la unica forma de volverte a conectar 
	 * es que el token este vencido
	 * @param Usuario
	 * @param IP
	 * @return
	 */
	public UsuarioOD Login(UsuarioOD Usuario,String IP)
	{
		if (!validarNick(Usuario))		//la funcion validarNick devuelve false si si encuenta al nick en la base de datos
		{
			if (validarPass(Usuario))
			{
				GestorToken gestort = new GestorToken();
				UsuarioDAO usuario = new UsuarioMongoDB();
				UsuarioOD result = usuario.buscar(Usuario);
				TokenOD Token = new TokenOD();
				System.err.println("este es el ip que llega "+IP);
				Token.setIp(IP);
				System.err.println("este es el ip que manda a encontara "+Token.getIp());
				Token = gestort.BuscarIP(Token);
				// System.err.println(Token.getFecha());
				  
				if(!(Token!=null)||(!gestort.validartoken(Token)))
				{
					TokenOD token  = gestort.insertar(result,IP);
					Usuario = result;
					Usuario.setToken(token.getToken());
					Logger.info("Asignandole un token: "+ Usuario.getToken() + "al usuario " +Usuario.getNick());
					return Usuario;				  
				 }
				else 
				{
					//System.out.print("No se puede Iniciar Sesion desde la misma pc"); 
					Usuario.setNombre("error");
					Usuario.setNombres("No se puede Iniciar Sesion desde la misma pc");
					Logger.error("Error debido a  estar conectado desde la misma pc");
					return Usuario;
				}
			}
			else
			{
				//System.out.print("validar pass dijo que no"); 
				Usuario.setNombre("error");
				Usuario.setNombres("Validar pass dijo que no");
				Logger.error("Contrasena invalida");
				return Usuario;
			}
		}
		else 
		{
			//System.out.print("validar nick dijo que no");  
			Usuario.setNombre("error");
			Usuario.setNombres("Validar nick dijo que no");
			Logger.error("Nick Invalido");
			return Usuario;
		}
		  
		
	}
		/**
		 * se encarga de buscar los usuario por id_u
		 * @param Usuario
		 * @return
		 */
	public UsuarioOD Buscar(UsuarioOD Usuario)
	{
		 UsuarioDAO usuario = new UsuarioMongoDB();
		 UsuarioOD result = usuario.buscarID(Usuario);
		 Usuario = result;
		 return result;
	}
	
}
	
	
	
	
	

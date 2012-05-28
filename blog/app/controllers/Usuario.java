package controllers;

import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.List;
import org.w3c.dom.Document;
import models.DAO.UsuarioDAO;
import models.DAO.MongoDB.UsuarioMongoDB;
import models.Negocio.GestorUsuario;
import models.OD.UsuarioOD;
import play.Logger;
import play.libs.XPath;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class Usuario  extends Controller 
{
	public static Result index(){
		return ok("Proyecto de servicios webs");
	}	
	
	/**
	 * se encarga de listar todos los usuarios que esten registrados en el sistema(persistencia Mongodb)
	 * @return
	 */
	
	
	public static Result listar()
	{
		UsuarioDAO Persona = new UsuarioMongoDB();      
	    List<UsuarioOD> list = Persona.listar();
	    XStream xstream = new XStream(new DomDriver());
	    String xml = xstream.toXML(list);
	    return  ok(xml);
	}
	  
	
	
	/**
	 *Esta funcion devuelve true si una fecha es menor a la fecha del sistema y false si es mayor 
	 * @param fecha
	 * @return
	 */
	
	public static boolean validarFecha(String fecha)
	{
		Calendar calendario = Calendar.getInstance();
	 	int hora,minutos,segundos,dia,mes,ano;
	  	hora =calendario.get(Calendar.HOUR_OF_DAY);
	  	minutos = calendario.get(Calendar.MINUTE);
	  	segundos = calendario.get(Calendar.SECOND);	  	
	  	
	  	dia = calendario.get(Calendar.DAY_OF_MONTH);
	  	mes = calendario.get(Calendar.MONTH);
	  	mes=mes+1;
		ano = calendario.get(Calendar.YEAR);		
		String diaFecha;
		String mesFecha;
		String anoFecha = null;
		
		int pos = 0;
		pos = fecha.indexOf('/');
		System.out.println("la posicion del primer slash es: "+pos);
		diaFecha = fecha.substring(0,pos);
		String resto = fecha.substring(pos+1,fecha.length());
		int pos2 = 0;
		pos2 = resto.indexOf('/');
		mesFecha = resto.substring(0,pos2);
		String resto2 = resto.substring(pos2+1,resto.length());//resto 3 es el ano		
		System.out.println("la fecha es:"+diaFecha+","+mesFecha+","+resto2);		
		int diaInt,mesInt,anoInt;
		diaInt=Integer.parseInt(diaFecha);
		mesInt=Integer.parseInt(mesFecha);
		anoInt=Integer.parseInt(resto2);		
		System.out.println("fechaSistema:"+dia+"/"+mes+"/"+ano);
		System.out.println("fechaEntrada:"+diaInt+"/"+mesInt+"/"+anoInt);
		if ((anoInt<ano))
		{
			System.out.println("fecha de entrada es menor");
			return true;
		}
		else
		{
			if (mesInt<mes)
			{				
				System.out.println("fecha de entrada es menor");
				return true;				
			}
			else
			{
				if (diaInt < dia)
				{
					System.out.println("fecha de entrada es menor");
					return true;
				}
			}
			return false;
		}
 	
	}
	
	

	  /**
	   * se encarga de recibir el xml del servicio web y extraer todo los datos y validar que no esten vacios y se crea un objeto 
	   * tipo UsuarioOD para despues llamar a la capa de negocio para insertar el usuario a persistencia(Mongodb) 
	   * @return
	   */
	
	// falta validar si no mandas nada ni xml
	  @BodyParser.Of(BodyParser.Xml.class)
	  public static Result insertar() 
	  {
		  Document dom = request().body().asXml();
		  if(dom == null) 
		  {
			    return ok("<error>Esperando Un XML</error>");
		  }
		  else
		  {
			  System.out.println("Insertando Usuario");  
			  String nombre = XPath.selectText("//nombre", request().body().asXml());
			  String nombres = XPath.selectText("//nombres", request().body().asXml());
			  String apellido = XPath.selectText("//apellido", request().body().asXml());
			  String apellidos = XPath.selectText("//apellidos", request().body().asXml());
			  String email = XPath.selectText("//email", request().body().asXml());
			  String fecha = XPath.selectText("//fecha", request().body().asXml());
			  String nick = XPath.selectText("//nick", request().body().asXml());
			  String pais = XPath.selectText("//pais", request().body().asXml());
			  String biografia = XPath.selectText("//biografia", request().body().asXml());
			  String sexo = XPath.selectText("//sexo", request().body().asXml());
			  String clave = XPath.selectText("//clave", request().body().asXml());
	  	
	  	
	  	
			  if(nick == null) 
			  {
				  return ok("<mensaje> Falta el nick </mensaje>");
			  }
			  else if(nombre == null)
			  {
				  return ok("<mensaje> Falta el nombre </mensaje>");
			  }
			  else if(nombres == null)
			  {
				  return ok("<mensaje> Falta el segundo nombre </mensaje>");
			  }
			  else if(apellido == null)
			  {
				  return ok("<mensaje> Falta el apellido </mensaje>");
			  }
			  else if(apellidos == null)
			  {
				  return ok("<mensaje> Falta el segundo apellido</mensaje>");
			  }
			  else if(email == null)
			  {
				  return ok("<mensaje> Falta el email </mensaje>");
			  }
			  else if (fecha == null )
			  {
				  return ok("<mensaje> Falta la fecha </mensaje>");
			  }	  	 
			  else if (pais == null )
			  {
				  return ok("<mensaje> Falta el pais de nacimiento </mensaje>");
			  }	  	 
			  else if (biografia == null )
			  {
				  return ok("<mensaje> Falta la biografia </mensaje>");
			  }	  	 
			  else if (sexo == null )
			  {
				  return ok("<mensaje> Falta la sexualidad </mensaje>");
			  }	  	 
			  else if(clave == null)
			  {
				  return ok("<mensaje> Falta la clave [nick]</mensaje>");
			  }
			  //validaciones de Strings sin numeros
			  else if(nombre.matches ("^.*\\d.*$"))
			  {	  		
				  return ok("<mensaje> El nombre no puede contener numeros </mensaje>");
			  }	  	
			  else if(nombres.matches ("^.*\\d.*$"))
			  {	  		
				  return ok("<mensaje> El segundo nombre no puede contener numeros </mensaje>");
			  }
			  else if(apellido.matches ("^.*\\d.*$"))
			  {	  		
				  return ok("<mensaje> El apellido no puede contener numeros </mensaje>");
			  }
			  else if(apellidos.matches ("^.*\\d.*$"))
			  {	  		
				  return ok("<mensaje> El segundo apellido no puede contener numeros </mensaje>");
			  }
			  else if(pais.matches ("^.*\\d.*$"))
			  {	  		
				  return ok("<mensaje> El pais no puede contener numeros </mensaje>");
			  }
			  else if(sexo.matches ("^.*\\d.*$"))
			  {	  		
				  return ok("<mensaje> El sexo no puede contener numeros </mensaje>");
			  }
			  else if(sexo.length() > 1)
			  {	  		
				  return ok("<mensaje> El sexo es solo una letra </mensaje>");
			  }
			  else if(!sexo.equals("M") && !sexo.equals("F"))
			  {
				  return ok("<mensaje> El sexo solo puede ser M o F </mensaje>");
			  }
			  else if(validarFecha(fecha)==false)
			  {
				  return ok("<mensaje> Error con la fecha de Nacimiento: fecha posterior o fecha invalida </mensaje>");
			  }	
			  else 
			  {
				  boolean existe = false;
				  UsuarioOD usuario = new UsuarioOD(nombre, nombres, apellido, apellidos, email, nick, pais, biografia, sexo, fecha, clave);	  	
				  GestorUsuario nuevo =  new GestorUsuario();
				  existe = nuevo.insertar(usuario);
				  usuario = nuevo.usuarioespecifico(usuario);
				  if (existe == true)
				  {	  		
					  XStream xstream = new XStream(new DomDriver());
					  xstream.alias("usuario", UsuarioOD.class);
					  String xml = xstream.toXML(usuario);	
					  Logger.info("Usuario insertado exitosamente con nick: "+usuario.getNick());
					  return ok(xml);
				  }	  		
				  else 
				  {
					  Logger.error("Este usuario no se puede agregar debido a que el nick ya existe");
					  return ok("<mensaje>Nickname ya existente</mensaje>");
				  }
			  }
	  	
		  }
	  }
	  
	  
	  /**
	   * Esta funcion que devuelve un boolean: devuelve true si el string pasado como parametro es un numero y devuelve un 
	   * false si esta cadena contiene caracteres no numericos 
	   * @param cadena
	   * @return
	   */
	  
	  private static boolean isNumeric(String cadena)
	  {
		  try 
		  {
			  Integer.parseInt(cadena);
			  return true;
		  } catch (NumberFormatException nfe)
		  {
			  return false;
		  }
	  }
	  
	  
	  /**
	   * Esta funcion asigna una foto a un usuario
	   * 
	   * @param id_u
	   * @return
	   */
	  
	  public static Result insertarfoto(String id_u) 
	  {
		  int id_usuario;
		  if (isNumeric(id_u) == true)
		  {
			   id_usuario = Integer.parseInt(id_u);
		  }
		  else
		  {
			  return ok("<mensaje> El id del usuario debe se un numero sin letras</mensaje>");
		  }		  
		  System.out.println("Insertando Usuario foto ");  
		  if(id_u == null) 
		  {
			  return badRequest("Missing parameter [nombre]");
		  } 
		  else 
		  {
			  UsuarioOD usuario = new UsuarioOD();
			  usuario.setId_u(id_usuario);
	  		  GestorUsuario nuevo =  new GestorUsuario();
	  		  usuario = nuevo.Buscar(usuario);
	  		  XStream xstream = new XStream(new DomDriver());
			  String xml = xstream.toXML(usuario);
			  Logger.info("Foto asignada al usuario: "+usuario.getNick());
			  return ok(xml);
		  }
	  }
  
  /**
   * se encarga de eliminar el usuario de persistencia pasanado como parametro su nick
   * @param nick
   * @return
   */
	  
	  public static Result eliminarUsuario(String nick) 
	  {
		  //if (nick.isEmpty())
			  //return ok("<mensaje>Introduzca un nick </mensaje>");
		  //else
		  //{
			  System.out.println("Eliminando Usuario");
			  GestorUsuario beta =new GestorUsuario();
			  UsuarioOD alfa = new UsuarioOD(null, null, null, null, null, nick, null, null, null, null, null);
		 
			  boolean eliminado = beta.eliminar(alfa);
			  if (eliminado == true)
			  {
				  Logger.info("Eliminado el usuario: "+nick);
				  return ok("<mensaje>Usuario Eliminado</mensaje>");
			  }
			  else
			  {
				  Logger.error("No se pudo eliminar al usuario: "+nick);
				  return ok("<mensaje>No se pudo eliminar al usuario</mensaje>");
			  }
			  
		  //}
	  }
	  
	  
	  /**
	   * se encargade modificar algun dato del usuario 
	   * @param token
	   * @return
	   */
	  @BodyParser.Of(BodyParser.Xml.class)
	  public static Result modificarUsuario(String token) 
	  {
		    if (isNumeric(token)==false)
		    	return ok("<mensaje>Token invalido, deben ser puros numeros</mensaje>");
		    else
		    {
		    	System.out.println("Modificando Usuario");
		   
		    	int tokens = (Integer) Integer.parseInt(token);
		    	System.out.println(tokens);
		    	String id_u = XPath.selectText("//id_u", request().body().asXml());
		    	String nombre = XPath.selectText("//nombre", request().body().asXml());
		    	String nombres = XPath.selectText("//nombres", request().body().asXml());
		    	String apellido = XPath.selectText("//apellido", request().body().asXml());
		    	String apellidos = XPath.selectText("//apellidos", request().body().asXml());
		    	String email = XPath.selectText("//email", request().body().asXml());
		    	String fecha = XPath.selectText("//fecha", request().body().asXml());
		    	String nick = XPath.selectText("//nick", request().body().asXml());
		    	String pais = XPath.selectText("//pais", request().body().asXml());
		    	String biografia = XPath.selectText("//biografia", request().body().asXml());
		    	String sexo = XPath.selectText("//sexo", request().body().asXml());
		    	String clave = XPath.selectText("//clave", request().body().asXml());
		  	
		    	if(nick == null) {
		    		return ok("<mensaje> Falta el nick </mensaje>");
		    	}
		    	else if(nombre == null)
		    	{
		    		return ok("<mensaje> Falta el nombre </mensaje>");
		    	}
		    	else if(id_u == null)
		    	{
		    		return ok("<mensaje> Falta el id del usuario </mensaje>");
		    	}
		    	else if(nombres == null)
		    	{
		    		return ok("<mensaje> Falta el segundo nombre </mensaje>");
		    	}
		    	else if(apellido == null)
		    	{
		    		return ok("<mensaje> Falta el apellido </mensaje>");
		    	}
		    	else if(apellidos == null)
		    	{
		    		return ok("<mensaje> Falta el segundo apellido</mensaje>");
		    	}
		    	else if(email == null)
		    	{
		    		return ok("<mensaje> Falta el email </mensaje>");
		    	}
		    	else if (fecha == null )
		    	{
		    		return ok("<mensaje> Falta la fecha </mensaje>");
		    	}	  	 
		    	else if (pais == null )
		    	{
		    		return ok("<mensaje> Falta el pais de nacimiento </mensaje>");
		    	}	  	 
		    	else if (biografia == null )
		    	{
		    		return ok("<mensaje> Falta la biografia </mensaje>");
		    	}	  	 
		    	else if (sexo == null )
		    	{
		    		return ok("<mensaje> Falta la sexualidad </mensaje>");
		    	}	  	 
		    	else if(clave == null)
		    	{
		    		return ok("<mensaje> Falta la clave [nick]</mensaje>");
		    	}
		    	//validaciones de Strings sin numeros
		    	else if(nombre.matches ("^.*\\d.*$"))
		    	{	  		
		    		return ok("<mensaje> El nombre no puede contener numeros </mensaje>");
		    	}	  	
		    	else if(nombres.matches ("^.*\\d.*$"))
		    	{	  		
		    		return ok("<mensaje> El segundo nombre no puede contener numeros </mensaje>");
		    	}
		    	else if(apellido.matches ("^.*\\d.*$"))
		    	{	  		
		    		return ok("<mensaje> El apellido no puede contener numeros </mensaje>");
		    	}
		    	else if(apellidos.matches ("^.*\\d.*$"))
		    	{	  		
		    		return ok("<mensaje> El segundo apellido no puede contener numeros </mensaje>");
		    	}
		    	else if(pais.matches ("^.*\\d.*$"))
		    	{	  		
		    		return ok("<mensaje> El pais no puede contener numeros </mensaje>");
		    	}
		    	else if(sexo.matches ("^.*\\d.*$"))
		    	{	  		
		    		return ok("<mensaje> El sexo no puede contener numeros </mensaje>");
		    	}
		    	else if(sexo.length() > 1)
		    	{	  		
		    		return ok("<mensaje> El sexo es solo una letra </mensaje>");
		    	}
		    	else if(!sexo.equals("M") && !sexo.equals("F"))
		    	{
		    		return ok("<mensaje> El sexo solo puede ser M o F </mensaje>");
		    	}
		    	else if(validarFecha(fecha)==false)
		    	{
		    		return ok("<mensaje> Error con la fecha de Nacimiento: fecha posterior o fecha invalida </mensaje>");
		    	}	
		    	else if(isNumeric(id_u)==false)
		    	{
		    		return ok("<mensaje> id de usuario debe ser un numero </mensaje>");
		    	}
		    	
		    	UsuarioOD usuario = new UsuarioOD(Integer.parseInt(id_u), nombre, nombres, apellido, apellidos, email, nick, pais, biografia, sexo, fecha, clave,tokens);
		    	GestorUsuario beta = new GestorUsuario();
		    	boolean modifico = beta.modificar(usuario);
		    	if(modifico == true)
		    	{		    		
		    		XStream xstream = new XStream(new DomDriver());
		    	    String xml = xstream.toXML(usuario);
		    	    Logger.info("Los datos del usuario: "+usuario.getNick()+" han sido modificados exitosamente");
		    	    return ok(xml);
		    	}
		    	else 
		    	{
		    		XStream xstream = new XStream(new DomDriver());
		    		String xml = xstream.toXML("<mensaje>No se pudo modificar</mensaje> ");
		    		Logger.error("No se pudieron modificar los datos del usuario: "+usuario.getNick());
		    		return ok(xml);
		    	}
		    }
	  
	  }
	  
	  
	  
	  /**
	   * se encagar de recibir el xml del servico web para la validacion del usuario en el que se pasa el nick y su clave para llamar 
	   * a la capa de negocio parfa que se encargeu de validar el usuario
	   * @return
	   */
	  @BodyParser.Of(BodyParser.Xml.class)
	  public static Result validarUsuario() //cambie aqui, y en gestorUsuario, si hay un error, lo setea en nombre y nombres para poder mostrar error personalizado
	  {
		  UsuarioOD validarusuario= null;
		  System.out.println("Validando Usuario");
		  String nick = XPath.selectText("//nick", request().body().asXml());
		  String clave = XPath.selectText("//clave", request().body().asXml());
		  System.out.println("el nick es: "+nick);
		  System.out.println("el pass es: "+clave);
		  UsuarioOD usuario = new UsuarioOD(nick,clave);
		  GestorUsuario validar = new GestorUsuario();
		  String IP = null;
			  
		  try 
		  {
			  IP = java.net.InetAddress.getLocalHost().getHostAddress();
			  validarusuario = validar.Login(usuario,IP);
			  if(!validarusuario.getNombre().equals("error"))	//antes era validarusuario!=null, lo cambie y si no lo encuentra, en el nombre, el gestor guarda el tipo de error
			  {					
				  XStream xstream = new XStream(new DomDriver());		
				  String xml = xstream.toXML(validarusuario);
			  	  xstream.alias("usuario", UsuarioOD.class);
			  	  Logger.info("El usuario: "+nick+ " ha iniciado sesion");
			  	  return ok(xml);
			  }		
		  } catch (UnknownHostException e) 
		  {
			  e.printStackTrace();			  
		  }
		  String error = validarusuario.getNombres();
		  System.out.println("el mega error es: "+error);
		  Logger.error("Error: "+error+" del usuario "+nick);
		  return ok("<error>"+error+"</error> ");	 	
	  }
		  	
	  /**
	   * se encarga de mostrar o devolver toda la informacion de un usuario en especifico pasandole como parametro el nick name 
	   * @param nick
	   * @return
	   */

	  public static Result especifico(String nick)
	  {
		  UsuarioOD erestu = null;
		  System.out.println("Buscando Usuario");
		  GestorUsuario beta =new GestorUsuario();
		  UsuarioOD alfa = new UsuarioOD(null, null, null, null, null, nick, null, null, null, null, null);
		  erestu = beta.usuarioespecifico(alfa);
		  if (erestu != null)
		  {
			  XStream xstream = new XStream(new DomDriver());
			  String xml = xstream.toXML(erestu);
			  return  ok(xml);
		  }
		  else
			 return  ok("<error>Usuario no encontrado</error>"); 
	  	}
	  
	  
	  
}

package models.Negocio;

import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import com.thoughtworks.xstream.io.binary.Token.Formatter;



import models.DAO.TokenDAO;
import models.DAO.UsuarioDAO;
import models.DAO.MongoDB.TokenMongoDB;
import models.DAO.MongoDB.UsuarioMongoDB;
import models.OD.TokenOD;
import models.OD.UsuarioOD;


public class GestorToken {

	public GestorToken(){}
	
	public TokenOD insertar (UsuarioOD Usuario,String IP )
	{
			Date fecha = new Date();
			Random randomGenerator = new Random();
		    int t = randomGenerator.nextInt(999999999);
		    
		    System.out.println("este es el ip a insertar"+IP);
		    TokenOD Token = new TokenOD(Usuario.getId_u(),t,fecha.toString(),IP); 
			TokenDAO token = new TokenMongoDB();
			token.insertar(Token);
			

			return Token;
		
	}
	
	public boolean validartoken(TokenOD Token) 
	{
	
		
		
		TokenDAO token = new TokenMongoDB();
		TokenOD aux = token.buscar(Token);
		if(aux!=null){ 
				System.out.println(aux.getFecha());
				
				Date fechaS = new Date();
				Date fechaG = new Date();
				try {
					//Date today = df.parse(aux.getFecha());
					String fecha = aux.getFecha().split(" ")[3];
					int hh = Integer.parseInt(fecha.split(":")[0]);
					int mm = Integer.parseInt(fecha.split(":")[1]);
					int ss = Integer.parseInt(fecha.split(":")[2]);
					//Date fechaG = new Date();
					fechaG.setHours(hh);
					fechaG.setMinutes(mm);
					fechaG.setSeconds(ss);
					
				} catch (Exception e) {
		
					e.printStackTrace();
				}
				
				
				Long dif = fechaS.getTime() - fechaG.getTime(); 
				System.out.println("diferencia"+dif);
				if(dif<=300000){
					
				
					
					return true;}
					
				
				}else {return false;}
		return false;
			
		
		}
		
		
	
		
	
	
	public TokenOD BuscarIP(TokenOD Token) 
	{
		 System.err.println("este es el ip que llega a encontar IP"+Token.getIp());
		TokenDAO token = new TokenMongoDB();
		TokenOD result = token.buscarIP(Token);
		if(result!=null){
			
			
			System.err.println("Ip que regresa de la vase de dato  "+Token.getToken());
			
			System.err.println("Ip que regresa de la vase de dato  "+Token.getIp());
			return token.buscarIP(Token);
		}else { System.err.println("No encontre nada "); return null;}

	
	}
	
	
	
}

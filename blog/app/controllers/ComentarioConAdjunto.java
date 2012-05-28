package controllers;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import play.Logger;
import play.mvc.Result;

public class ComentarioConAdjunto extends HttpServlet{
	public static final String repositorioFoto = "C:/play/blog/adjuntos/";
	
	
	 public void comentarioAdjunto(HttpServletRequest request, HttpServletResponse response) throws FileUploadException 
	 {
		 System.out.println("entrando");
		 
		 boolean isMultipart = ServletFileUpload.isMultipartContent(request);

		 

		 FileItemFactory factory = new DiskFileItemFactory();

		 ServletFileUpload upload = new ServletFileUpload(factory);

		 List  items = upload.parseRequest(request);
		
		 
		 if (!isMultipart)
		 {
			 System.out.println("error");
			 Logger.info("No es un formato de multipart");
		 }
		 else
		 {
			 saveAttachedFile(request);
		 }
		 
		 
		
		 
	 }
	 
	 
	 
	 
	 
	 private void saveAttachedFile(HttpServletRequest request) {

	        File filePath = new File(repositorioFoto);
	        
	        int maxFileSize = 5000 * 1024;
	        
	        int maxMemSize = 4 * 1024;
	        
	        File file;
	        
	        DiskFileItemFactory factory = new DiskFileItemFactory();
	        
	        // maximum size that will be stored in memory
	        factory.setSizeThreshold(maxMemSize);
	        
	        // Location to save data that is larger than maxMemSize.
	        factory.setRepository(filePath);
	        
	        // Create a new file upload handler
	        ServletFileUpload upload = new ServletFileUpload(factory);
	        
	        // maximum file size to be uploaded.
	        upload.setSizeMax(maxFileSize);
	        
	        try {
	            // Parse the request to get file items.
	            List fileItems = upload.parseRequest(request);

	            // Process the uploaded file items
	            Iterator i = fileItems.iterator();

	            while (i.hasNext()) {
	                FileItem fi = (FileItem) i.next();

	                if (!fi.isFormField()) {
	                    // Get the uploaded file parameters
	                    String fieldName = fi.getFieldName();

	                    String fileName = fi.getName();
	                    System.out.println(fileName);

	                    String contentType = fi.getContentType();

	                    boolean isInMemory = fi.isInMemory();

	                    long sizeInBytes = fi.getSize();

	                    // Write the file
	                    if (fileName.lastIndexOf("\\") >= 0) {
	                        file = new File(filePath,
	                                fileName.substring(fileName.lastIndexOf("\\")));
	                    } else {
	                        file = new File(filePath,
	                                fileName.substring(fileName.lastIndexOf("\\") + 1));
	                    }

	                    fi.write(file);

	                    Logger.info("Uploaded Filename: " + fileName);
	                }
	            }

	        } catch (Exception ex) {
	            Logger.error("Error al procesar archivo adjunto", ex);
	        }
	    }


}

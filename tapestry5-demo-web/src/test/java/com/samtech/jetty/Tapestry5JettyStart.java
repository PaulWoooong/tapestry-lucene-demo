package com.samtech.jetty;

//import org.eclipse.jetty.server.Connector;
//import org.eclipse.jetty.server.Server;
//import org.eclipse.jetty.server.bio.SocketConnector;
//import org.eclipse.jetty.webapp.WebAppContext;


/*import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.ajp.Ajp13SocketConnector;
import org.mortbay.jetty.bio.SocketConnector;
import org.mortbay.jetty.webapp.WebAppContext;*/

public class Tapestry5JettyStart {

	public static void main(String[] args) throws Exception {/*
		Server server = new Server();
		
		SocketConnector connector =new SocketConnector();// new Ajp13SocketConnector();
		
		// Set some timeout options to make debugging easier.
		connector.setMaxIdleTime(1000 * 60 * 60);
		connector.setSoLingerTime(-1);
		connector.setPort(8068);
		//connector.setConfidentialPort(8443);
		//connector.setConfidentialScheme("https");
	
		server.setConnectors(new Connector[] { connector});
		
		Class clazz=org.slf4j.Logger.class;
		WebAppContext bb = new WebAppContext();
		//bb.setClassLoader(TapestryFilter.class.getClassLoader());
		bb.setServer(server);
		bb.setContextPath("/");
		bb.setWar("tapestry5-demo-web/src/main/webapp");
		
		
		
		
		ServletHolder holder=new ServletHolder(new JspServlet());
        bb.addServlet(holder, "*.jsp");
        
		server.setHandler(bb);
		//server.addHandler(bb);

		try {
			System.out.println(">>> STARTING EMBEDDED JETTY SERVER, PRESS ANY KEY TO STOP");
			server.start();
			System.in.read();
			System.out.println(">>> STOPPING EMBEDDED JETTY SERVER"); 
            // while (System.in.available() == 0) {
			//   Thread.sleep(5000);
			// }
			server.stop();
			server.join();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(100);
		}
	*/}
}

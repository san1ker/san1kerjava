package itji.example.jetty.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHandler;

public class MyServer {

	public static void main(String[] args) throws Exception {
		new MyServer().start();
	}

	private void start() throws Exception{
    	// 1. Web Server, Server Connector 생성
		Server server = new Server();
		ServerConnector httpConector = new ServerConnector(server);
		httpConector.setHost("127.0.0.1");
		httpConector.setPort(8080);
		server.addConnector(httpConector);
		
        // 2. Servlet Handler 매핑
		ServletHandler servletHandler = new ServletHandler();
		servletHandler.addServletWithMapping(MyServlet.class, "/");
		server.setHandler(servletHandler);
		
        // 3. Web Server start
		server.start();
		server.join();
		
	}
	
}



package itji.example.jetty.server;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class MyServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		res.setStatus(200);
		res.getWriter().write("Welcome to my server.");
	}
}

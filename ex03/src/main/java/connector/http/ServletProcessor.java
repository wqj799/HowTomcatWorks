package connector.http;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;
import javax.servlet.Servlet;

import connector.http.Constants;
import connector.http.HttpRequest;
import connector.http.HttpRequestFacade;
import connector.http.HttpResponse;
import connector.http.HttpResponseFacade;

public class ServletProcessor {

  public void process(HttpRequest request, HttpResponse response) {

    String uri = request.getRequestURI();
    String servletName = uri.substring(uri.lastIndexOf("/") + 1);
    URLClassLoader loader = null;
    try {
      // create a URLClassLoader
      URL[] urls = new URL[1];
      URLStreamHandler streamHandler = null;
      File classPath = new File(Constants.WEB_ROOT);
      String repository = (new URL("file", null, classPath.getCanonicalPath() + File.separator)).toString();
      urls[0] = new URL(null, repository, streamHandler);
      loader = new URLClassLoader(urls);
    } catch (IOException e) {
      System.out.println(e.toString());
    }
    Class myClass = null;
    try {
      myClass = loader.loadClass(servletName);
    } catch (ClassNotFoundException e) {
      System.out.println(e.toString());
    }

    Servlet servlet = null;

    try {
      servlet = (Servlet) myClass.newInstance();
      HttpRequestFacade requestFacade = new HttpRequestFacade(request);
      HttpResponseFacade responseFacade = new HttpResponseFacade(response);
      String ResponseHead = "HTTP/1.1 200 OK\r\n" +
          "Content-Type:text/html\r\n" +
          "\r\n";
      responseFacade.getWriter().write(ResponseHead);
      servlet.service(requestFacade, responseFacade);
      ((HttpResponse) response).finishResponse();
    } catch (Exception e) {
      e.printStackTrace();
    } catch (Throwable e) {
      e.printStackTrace();
    }
  }
}
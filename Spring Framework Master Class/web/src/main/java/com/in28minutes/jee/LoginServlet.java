package com.in28minutes.jee;

//import java.io.IOException;
//import java.io.PrintWriter;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import com.in28minutes.jee.LoginService;
//
//@WebServlet(urlPatterns = "/login.do")
//public class LoginServlet extends HttpServlet{
//	
//	private LoginService service = new LoginService();
//
//	@Override
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		// TODO Auto-generated method stub
//		// Servlet에서 HTML을 그냥 쌩으로 전달하는 방법
////		PrintWriter printWriter = response.getWriter();
////		printWriter.println("<html>");
////		printWriter.println("<head>");
////		printWriter.println("<title>Yahoo!!!!!!!!</title>");
////		printWriter.println("</head>"); 
////		printWriter.println("<body>");
////		printWriter.println("My First Servlet");
////		printWriter.println("</body>");
////		printWriter.println("</html>");
//		System.out.println(request.getParameter("name"));
////		String name = request.getParameter("name");
//		
////		request.setAttribute("name", name);
//		
//		request.setAttribute("name", request.getParameter("name"));
//		request.setAttribute("password", request.getParameter("password"));
//		
//		//JSP를 사용하는 방법. 경로는 WEB-INF부터 찾는다
//		request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request,response);
//	}
//	
//	@Override
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		// TODO Auto-generated method stub
//		String name = request.getParameter("name"); 
//		String pw = request.getParameter("password");
//		if(service.isUserValid(name,pw)) {
//			request.setAttribute("name", request.getParameter("name"));
//			request.setAttribute("password", request.getParameter("password"));
//			request.getRequestDispatcher("/WEB-INF/views/welcome.jsp").forward(request,response);
//		} else {
//			request.setAttribute("msg", "Invalid User!!!!");
//			request.setAttribute("name", request.getParameter("name"));
//			request.setAttribute("password", request.getParameter("password"));
//			request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request,response);
//		}
//		
//	}
//	
//}
//

public class LoginServlet{
	
}
package com.in28minutes;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/login.do")
public class LoginServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// Servlet에서 HTML을 그냥 쌩으로 전달하는 방법
//		PrintWriter printWriter = arg1.getWriter();
//		printWriter.println("<html>");
//		printWriter.println("<head>");
//		printWriter.println("<title>Yahoo!!!!!!!!</title>");
//		printWriter.println("</head>");
//		printWriter.println("<body>");
//		printWriter.println("My First Servlet");
//		printWriter.println("</body>");
//		printWriter.println("</html>");
		
		//JSP를 사용하는 방법. 경로는 WEB-INF부터 찾는다
		arg0.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(arg0,arg1);
	}
	
}

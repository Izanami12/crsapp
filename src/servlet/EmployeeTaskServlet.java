package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/employeeTask")
public class EmployeeTaskServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    public EmployeeTaskServlet() {
        super();
    }
    
    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        
        final RequestDispatcher dispatcher //
                = this.getServletContext()//
                        .getRequestDispatcher("/WEB-INF/views/employeeTaskView.jsp");
        
        dispatcher.forward(request, response);
    }
    
    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        
        doGet(request, response);
    }
    
}
package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.UserAccount;
import utils.AppUtils;
import utils.DataDAO;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    public LoginServlet() {
        super();
    }
    
    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        
        final RequestDispatcher dispatcher //
                = this.getServletContext().getRequestDispatcher("/WEB-INF/views/loginView.jsp");
        
        dispatcher.forward(request, response);
    }
    
    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        
        final String userName = request.getParameter("userName");
        final String password = request.getParameter("password");
        final UserAccount userAccount = DataDAO.findUser(userName, password);
        
        if (userAccount == null) {
            final String errorMessage = "Invalid userName or Password";
            
            request.setAttribute("errorMessage", errorMessage);
            
            final RequestDispatcher dispatcher //
                    = this.getServletContext().getRequestDispatcher("/WEB-INF/views/loginView.jsp");
            
            dispatcher.forward(request, response);
            return;
        }
        
        AppUtils.storeLoginedUser(request.getSession(), userAccount);
        
        //
        int redirectId = -1;
        try {
            redirectId = Integer.parseInt(request.getParameter("redirectId"));
        } catch (final Exception e) {
        }
        final String requestUri = AppUtils.getRedirectAfterLoginUrl(request.getSession(), redirectId);
        if (requestUri != null) {
            response.sendRedirect(requestUri);
        } else {
            // ѕо умолчанию после успешного входа в систему
            // перенаправить на страницу /userInfo
            response.sendRedirect(request.getContextPath() + "/userInfo");
        }
        
    }
    
}

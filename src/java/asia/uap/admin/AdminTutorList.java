package asia.uap.admin;

import asia.uap.model.AdminRequestBean;
import asia.uap.model.TutorsIO;
import asia.uap.model.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "AdminTutorList", urlPatterns = {"/admin/do.AdminTutorList"})
public class AdminTutorList extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String search = request.getParameter("search");
        Boolean hassearch = true;
        if(search == null || search.isEmpty()){
            hassearch = false;
        }
        HttpSession session = request.getSession();
        ArrayList<AdminRequestBean> requests = new ArrayList();
        ArrayList<User> users = new ArrayList();
        User user = (User) session.getAttribute("current");
        TutorsIO io = new TutorsIO();   
        Integer random = io.generateRandom();
        try {
            requests = io.getRequests();
            users = io.getUsers();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        if(hassearch){
            ArrayList<User> searchResults = new ArrayList<>();
            try{
                searchResults = io.searchUsers(search);
            }catch(SQLException ex){
                ex.printStackTrace();
            }
            users = searchResults;
        }
        String uri = "/WEB-INF/admintutorlist.jsp";
        request.setAttribute("users", users);
        request.setAttribute("requests",requests);
        request.setAttribute("random", random);
        RequestDispatcher rd = request.getRequestDispatcher(uri);
        rd.forward(request, response);
        
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Admin Tutor List Servlet";
    }
}

/*
 * Use and copying for commercial purposes
 * only with the author's permission
 */
package ru.kentyku.reportnet.reports;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ru.kentyku.reportnet.requests.Equipment;
import ru.kentyku.reportnet.requests.RequestsToBase;

/**
 *
 * @author kentyku
 */
public class TakeAdminForm extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            String addLink = null;
            addLink = request.getParameter("addLink");
            if (addLink.equals("addLink")) {
                saveList();
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Ссылка на отчет</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<div align=\"center\"><h1>Кликните на ссылку, чтобы скачать отчет:</h1>");
                out.println("<br><h2>");
                out.println(generateLink());
                out.println("</h2></div>");
                out.println("</body>");
                out.println("</html>");
            }

        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(TakeAdminForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TakeAdminForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(TakeAdminForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TakeAdminForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    void saveList() throws SQLException, ClassNotFoundException {
        RequestsToBase requestDB = new RequestsToBase();
        ArrayList<String> list = requestDB.showAllList();
//        for (String item : list) {
//            System.out.println(item);
//        }

        String filename = "report";//имя файла
        try {
            FileWriter fwriter = new FileWriter("C:\\Program Files\\Apache Software Foundation\\Apache Tomcat 8.0.27\\webapps\\manager\\files\\" + filename + ".csv", false);
            for (String item : list) {
                fwriter.write(item);
            }
            fwriter.flush();
            fwriter.close();
            System.out.println("********Запись файла прошла успешно");
        } catch (IOException ex) {
            System.out.println("Ошибка записи!\n" + ex.getMessage());
        }
    }

    String generateLink() {
        String link = "<a href=\\manager\\files\\report.csv > Скачать общий отчет за все время </a>";
        return link;
    }
}

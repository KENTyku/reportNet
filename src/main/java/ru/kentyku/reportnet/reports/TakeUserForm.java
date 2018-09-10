/*
 * Use and copying for commercial purposes
 * only with the author's permission
 */
package ru.kentyku.reportnet.reports;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Date;
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
public class TakeUserForm extends HttpServlet {

    RequestsToBase rtb = new RequestsToBase();
    String isComplete = "Доставлен на сервер";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws java.lang.ClassNotFoundException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        Equipment equipment = new Equipment();

        equipment.setDateRequest(request.getParameter("dateRequest"));
        equipment.setNumberRequest(request.getParameter("numberRequest"));
        equipment.setPersonRequest(request.getParameter("person"));
        equipment.setRegionEquipment(request.getParameter("region"));
        equipment.setStatusEquipment(request.getParameter("statusEquipment"));
        equipment.setStatusMoneyBox(request.getParameter("statusMoneyBox"));
        equipment.setStatusPaper(request.getParameter("statusPaper"));
        equipment.setTypeEquipment(request.getParameter("typeequipment"));

        RequestsToBase requestToBase = new RequestsToBase();
        try {
            requestToBase.addReport(equipment);
        } catch (SQLException ex) {
            isComplete = "Проблемы с доставкой. Попробуйте позднее или"
                    + " свяжитесь с адмнистратором";
            Logger.getLogger(TakeUserForm.class.getName()).log(Level.SEVERE, null, ex);
        }
//        System.out.println("REPORT:");
//        System.out.println("DateRequest=" + equipment.getDateRequest());
//        System.out.println("NumberRequest=" + equipment.getNumberRequest());
//        System.out.println("Person=" + equipment.getPersonRequest());
//        System.out.println(equipment.getRegionEquipment());
//        System.out.println(equipment.getStatusEquipment());
//        System.out.println(equipment.getStatusMoneyBox());
//        System.out.println(equipment.getStatusPaper());
//        System.out.println(equipment.getTypeEquipment());
//        System.out.println(equipment.toString());

        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<meta charset=\"UTF-8\">");
            out.println("<title>Статус отчета</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1 align=\"center\">Статус отчета:</h1>");
            out.println("<br>");
            out.println("<br>");
            out.println("<h2 align=\"center\">" + isComplete + "</h2>");
            out.println("</body>");
            out.println("</html>");
        }
        rtb.connect();
        rtb.disconnect();

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
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TakeUserForm.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TakeUserForm.class.getName()).log(Level.SEVERE, null, ex);
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

}

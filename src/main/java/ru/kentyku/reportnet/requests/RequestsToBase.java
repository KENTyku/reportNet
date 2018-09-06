/*
 * Use and copying for commercial purposes
 * only with the author's permission
 */
package ru.kentyku.reportnet.requests;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author kentyku
 */
public class RequestsToBase {

    Statement stmt;
    PreparedStatement pstmt;
    Connection connection;
    ResultSet rs;
    ArrayList<Equipment> equipmentList = new ArrayList<Equipment>();

    /**
     * Метод, отвечающий за подключение к БД
     *
     * @throws SQLException, ClassNotFoundException
     */
    void connect() throws ClassNotFoundException {
        try {
            Class.forName("com.mysql.jdbc.Driver");//требуется чтобы 
            /*
                когда используете JDBC не забывайте загружать драйвера таким 
                образом т.к. DriverManager.getConnection() ищет драйверы среди
                загруженных классов, а не пытается загрузить их сам.
             */

            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/report?useSSL=no&serverTimezone=UTC", "root", "123456");
            stmt = connection.createStatement();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    /**
     * Метод, отвечающий за отключение от БД
     */
    void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
        }
    }
//     void showAllListRequest() throws SQLException {
//        this.pstmt = connection.prepareStatement("SELECT  city FROM country "
//                + "INNER JOIN city on country.idcountry=city.idcountry "
//                + "WHERE country RLIKE ? ORDER BY country "
//                + "limit ?,5;");
//        pstmt.setString(1, request);
//        pstmt.setInt(2, index);
//        rs = pstmt.executeQuery();
//        //обрабатываем результат запроса
//        this.cities.clear();
//        while (this.rs.next()) {
//            city = new City();
//            city.setNameCity(this.rs.getString(1));
//            this.cities.add(city);
//        }

    private void addReportQuery(Equipment item) throws SQLException {
        this.stmt.executeUpdate("INSERT INTO PAGES (URL, SITE_ID, FOUND)"
                + " VALUES ('" + item.getDateReport() + "','" + item.getDateRequest()
                + "','" + item.getRegionEquipment() + "')");
    }

    public ArrayList<Equipment> addReport(Equipment item) throws ClassNotFoundException, SQLException {
        connect();
        addReportQuery(item);
        disconnect();
        return equipmentList;
    }

    /**
     * Метод, считывающий из БД весь отчет
     *
     * @throws Exception
     */
    void showAllListRequest() throws SQLException {
        rs = stmt.executeQuery("SELECT country FROM country ORDER BY country;");
        while (rs.next()) {
            Equipment equipment = new Equipment();
            equipment.setRegionEquipment(rs.getString(1));
            equipment.setTypeEquipment(rs.getString(2));
            equipmentList.add(equipment);

        }
    }

    public ArrayList<Equipment> showAllList() throws ClassNotFoundException, SQLException {
        connect();
        showAllListRequest();
        disconnect();
        return equipmentList;
    }

}

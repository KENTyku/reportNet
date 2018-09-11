/*
 * Use and copying for commercial purposes
 * only with the author's permission
 */
package ru.kentyku.reportnet.requests;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

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
    Equipment equipment = new Equipment();

    /**
     * Метод, отвечающий за подключение к БД
     *
     * @throws SQLException, ClassNotFoundException
     */
    public void connect() throws ClassNotFoundException {
        try {
            Class.forName("com.mysql.jdbc.Driver");//требуется чтобы 
            /*
                когда используете JDBC не забывайте загружать драйвера таким 
                образом т.к. DriverManager.getConnection() ищет драйверы среди
                загруженных классов, а не пытается загрузить их сам.
             */

            Properties properties = new Properties();
            properties.setProperty("user", "root");
            properties.setProperty("password", "Txfjq123");
            properties.setProperty("serverTimezone", "UTC");
            properties.setProperty("characterEncoding", "UTF-8");
            properties.setProperty("useSSL", "no");

            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/reports", properties);
//            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/reports?useSSL=no&useUnicode=true&serverTimezone=UTC", "root", "123456");
            stmt = connection.createStatement();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    /**
     * Метод, отвечающий за отключение от БД
     */
    public void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
        }
    }

    private void addReportQuery(Equipment item) throws SQLException {
        System.out.println(item.getRegionEquipment() + "*******");
//        this.pstmt = connection.prepareStatement("insert into reports.report  \n"
//                + "set \n"
//                + "id_region=(select id_region from reports.region where region='?'),\n"
//                + "id_equipment=(select id_equipment from reports.equipment where equipment_col='?'),\n"
//                + "id_status_paper=(select id_status_paper from reports.statuspaper where status_paper_col='?'),\n"
//                + "id_status_money_box=(select id_status_money_box from reports.statusmoneybox where status_money_box_col='?'),\n"
//                + "date_request='?',\n"
//                + "number_request='?',\n"
//                + "name_person='?',\n"
//                + "date_rowl=DATE_FORMAT(now(), '%Y-%m-%d' );");
//        pstmt.setString(1, item.getRegionEquipment());
//        pstmt.setString(2, item.getTypeEquipment());
//        pstmt.setString(3, item.getStatusPaper());
//        pstmt.setString(4, item.getStatusMoneyBox());
//        pstmt.setString(5, item.getDateRequest());
//        pstmt.setString(6, item.getNumberRequest());
//        pstmt.setString(7, item.getPersonRequest());
//        pstmt.executeUpdate();

        this.stmt.executeUpdate("insert into reports.report  "
                + "set "
                + "id_region=(select id_region from reports.region where region='" + item.getRegionEquipment() + "'),"
                + "id_equipment=(select id_equipment from reports.equipment where equipment_col='" + item.getTypeEquipment() + "'),"
                + "id_status_equipment=(select id_status_equipment from reports.status_equipment where status_col='" + item.getStatusEquipment() + "'),"
                + "id_status_paper=(select id_status_paper from reports.statuspaper where status_paper_col='" + item.getStatusPaper() + "'),"
                + "id_status_money_box=(select id_status_money_box from reports.statusmoneybox where status_money_box_col='" + item.getStatusMoneyBox() + "'),"
                + "date_request='" + item.getDateRequest() + "',"
                + "number_request='" + item.getNumberRequest() + "',"
                + "name_person='" + item.getPersonRequest() + "',"
                + "date_rowl=DATE_FORMAT(now(), '%Y-%m-%d' );");

//        this.stmt.executeUpdate("UPDATE `reports`.`report` SET `name_person`='Юрий' "
//                + "WHERE `number`='14';");
    }

    public void addReport(Equipment item) throws ClassNotFoundException, SQLException {
        connect();
        addReportQuery(item);
        disconnect();

    }

    /**
     * Метод, считывающий из БД весь отчет
     *
     * @throws Exception
     */
    ArrayList<String> showAllListRequest() throws SQLException {
        ArrayList<String> list = new ArrayList<String>();
        this.pstmt = connection.prepareStatement("SELECT "
                + "report.number, "
                + "report.date_rowl,"
                + "region.id_region,"
                + "region.region,"
                + "equipment.equipment_col,"
                + "status_equipment.status_col,"
                + "statuspaper.status_paper_col,"
                + "statusmoneybox.status_money_box_col,"
                + "report.date_request,"
                + "report.number_request,"
                + "report.name_person "
                + "FROM reports.report "
                + "inner join region on report.id_region=region.id_region "
                + "inner join equipment on report.id_equipment=equipment.id_equipment "
                + "inner join status_equipment on report.id_status_equipment=status_equipment.id_status_equipment "
                + "inner join statuspaper on report.id_status_paper=statuspaper.id_status_paper "
                + "inner join statusmoneybox on report.id_status_money_box=statusmoneybox.id_status_money_box LIMIT 100;");
//        pstmt.setString(1, request);
//        pstmt.setInt(2, index);
        rs = pstmt.executeQuery();
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        //собираем строку заголовков столбцов
        ArrayList<String> nameColums = new ArrayList<String>();
        for (int i = 1; i < columnCount + 1; i++) {
            String nameitem = rsmd.getColumnLabel(i);
//            System.out.println(rsmd.getColumnLabel(i));
            nameColums.add(nameitem);
        }
        String str = "";
        for (String item : nameColums) {
            str = str.concat(item + ";");
        }
        str = str.substring(0, str.length() - 1) + "\r\n";
        //добавляем строку заголовков столбцов список
        list.add(str);
        //продолжаем собирать уже данные        
        while (rs.next()) {
            String itemString = "";
            for (int i = 1; i < columnCount + 1; i++) {
                itemString = itemString.concat(rs.getString(i) + ";");
            }

            list.add(itemString + "\r\n");
        }
        return list;
    }

    public ArrayList<String> showAllList() throws ClassNotFoundException, SQLException {
        ArrayList<String> list = new ArrayList<String>();
        connect();
        list = showAllListRequest();
        disconnect();
        return list;
    }

}

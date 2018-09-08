/*
 * Use and copying for commercial purposes
 * only with the author's permission
 */
package ru.kentyku.reportnet.requests;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author kentyku
 */
public class Equipment {

    private String typeEquipment;
    private String regionEquipment;
    private String statusEquipment;
    private String statusPaper;
    private String statusMoneyBox;
    private String dateRequest;
    private String numberRequest;
    private String personRequest;
    private String dateReport;

    public Equipment() {
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy.MM.dd");
        this.dateReport = formatForDateNow.format(new Date());
    }

    /**
     * @return the typeEquipment
     */
    public String getTypeEquipment() {
        return typeEquipment;
    }

    /**
     * @param typeEquipment the typeEquipment to set
     */
    public void setTypeEquipment(String typeEquipment) {
        if (typeEquipment == null) {
            this.typeEquipment = "нет данных";
        } else {
            this.typeEquipment = typeEquipment;
        }
    }

    /**
     * @return the regionEquipment
     */
    public String getRegionEquipment() {
        return regionEquipment;
    }

    /**
     * @param regionEquipment the regionEquipment to set
     */
    public void setRegionEquipment(String regionEquipment) {
        this.regionEquipment = regionEquipment;
    }

    /**
     * @return the statusEquipment
     */
    public String getStatusEquipment() {
        return statusEquipment;
    }

    /**
     * @param statusEquipment the statusEquipment to set
     */
    public void setStatusEquipment(String statusEquipment) {
        if (statusEquipment == null) {
            this.statusEquipment = "нет данных";
        } else {
            this.statusEquipment = statusEquipment;
        }
    }

    /**
     * @return the statusPaper
     */
    public String getStatusPaper() {
        return statusPaper;
    }

    /**
     * @param statusPaper the statusPaper to set
     */
    public void setStatusPaper(String statusPaper) {
        if (statusPaper == null) {
            this.statusPaper = "нет данных";
        } else {
            this.statusPaper = statusPaper;
        }
    }

    /**
     * @return the statusMoneyBox
     */
    public String getStatusMoneyBox() {
        return statusMoneyBox;
    }

    /**
     * @param statusMoneyBox the statusMoneyBox to set
     */
    public void setStatusMoneyBox(String statusMoneyBox) {
        if (statusMoneyBox == null) {
            this.statusMoneyBox = "нет данных";
        } else {
            this.statusMoneyBox = statusMoneyBox;
        }
    }

    /**
     * @return the dateRequest
     */
    public String getDateRequest() {
        return dateRequest;
    }

    /**
     * @param dateRequest the dateRequest to set
     */
    public void setDateRequest(String dateRequest) {
        if (dateRequest.equals("")) {
            this.dateRequest = "не введено";
        } else {
            this.dateRequest = dateRequest;
        }
    }

    /**
     * @return the numberRequest
     */
    public String getNumberRequest() {
        return numberRequest;
    }

    /**
     * @param numberRequest the numberRequest to set
     */
    public void setNumberRequest(String numberRequest) {
        if (numberRequest.equals("")) {
            this.numberRequest = "не введено";
        } else {
            this.numberRequest = numberRequest;
        }
    }

    /**
     * @return the personRequest
     */
    public String getPersonRequest() {
        return personRequest;
    }

    /**
     * @param personRequest the personRequest to set
     */
    public void setPersonRequest(String personRequest) {
        if (personRequest.equals("")) {
            this.personRequest = "не введено";
        } else {
            this.personRequest = personRequest;
        }
    }

    /**
     * @return the dateReport
     */
    public String getDateReport() {
        return dateReport;
    }

    /**
     * @param dateReport the dateReport to set
     */
    public void setDateReport(String dateReport) {
        this.dateReport = dateReport;
    }

}

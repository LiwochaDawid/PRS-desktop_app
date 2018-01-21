package prs.models;

import javax.persistence.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.Time;

public class ConfigurationModel {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="configuration_id", unique = true, nullable = false)
    private int configurationID;
    @Column(name="mo_work_start")
    private Time moWorkStart;
    @Column(name="mo_work_end")
    private Time moWorkEnd;
    @Column(name="mo_work_start")
    private Time tuWorkStart;
    @Column(name="tu_work_end")
    private Time tuWorkEnd;
    @Column(name="we_work_start")
    private Time weWorkStart;
    @Column(name="we_work_end")
    private Time weWorkEnd;
    @Column(name="th_work_start")
    private Time thWorkStart;
    @Column(name="th_work_end")
    private Time thWorkEnd;
    @Column(name="fr_work_start")
    private Time frWorkStart;            ;
    @Column(name="fr_work_end")
    private Time frWorkEnd;
    @Column(name="sa_work_start")
    private Time saWorkStart;
    @Column(name="sa_work_end")
    private Time saWorkEnd;
    @Column(name="su_work_start")
    private Time suWorkStart;
    @Column(name="su_work_end")
    private Time suWorkEnd;
    @Column(name="registration_term")
    private Date registrationTerm;
    @Column (name="max_visits")
    private int maxVisits;

    public int getMaxVisits() {
        return maxVisits;
    }

    public void setMaxVisits(int maxVisits) {
        this.maxVisits = maxVisits;
    }

    public Time getWeWorkEnd() {
        return weWorkEnd;
    }

    public void setWeWorkEnd(Time weWorkEnd) {
        this.weWorkEnd = weWorkEnd;
    }

    public Time getThWorkStart() {
        return thWorkStart;
    }

    public void setThWorkStart(Time thWorkStart) {
        this.thWorkStart = thWorkStart;
    }

    public Time getThWorkEnd() {
        return thWorkEnd;
    }

    public void setThWorkEnd(Time thWorkEnd) {
        this.thWorkEnd = thWorkEnd;
    }

    public Time getFrWorkStart() {
        return frWorkStart;
    }

    public void setFrWorkStart(Time frWorkStart) {
        this.frWorkStart = frWorkStart;
    }

    public Time getFrWorkEnd() {
        return frWorkEnd;
    }

    public void setFrWorkEnd(Time frWorkEnd) {
        this.frWorkEnd = frWorkEnd;
    }

    public Time getSaWorkStart() {
        return saWorkStart;
    }

    public void setSaWorkStart(Time saWorkStart) {
        this.saWorkStart = saWorkStart;
    }

    public Time getSaWorkEnd() {
        return saWorkEnd;
    }

    public void setSaWorkEnd(Time saWorkEnd) {
        this.saWorkEnd = saWorkEnd;
    }

    public Time getSuWorkStart() {
        return suWorkStart;
    }

    public void setSuWorkStart(Time suWorkStart) {
        this.suWorkStart = suWorkStart;
    }

    public Time getSuWorkEnd() {
        return suWorkEnd;
    }

    public void setSuWorkEnd(Time suWorkEnd) {
        this.suWorkEnd = suWorkEnd;
    }

    public Date getRegistrationTerm() {
        return registrationTerm;
    }

    public void setRegistrationTerm(Date registrationTerm) {
        this.registrationTerm = registrationTerm;
    }



    public int getConfigurationID() {
        return configurationID;
    }

    public void setConfigurationID(int configurationID) {
        this.configurationID = configurationID;
    }

    public Time getMoWorkStart() {
        return moWorkStart;
    }

    public void setMoWorkStart(Time moWorkStart) {
        this.moWorkStart = moWorkStart;
    }

    public Time getMoWorkEnd() {
        return moWorkEnd;
    }

    public void setMoWorkEnd(Time moWorkEnd) {
        this.moWorkEnd = moWorkEnd;
    }

    public Time getTuWorkStart() {
        return tuWorkStart;
    }

    public void setTuWorkStart(Time tuWorkStart) {
        this.tuWorkStart = tuWorkStart;
    }

    public Time getTuWorkEnd() {
        return tuWorkEnd;
    }

    public void setTuWorkEnd(Time tuWorkEnd) {
        this.tuWorkEnd = tuWorkEnd;
    }

    public Time getWeWorkStart() {
        return weWorkStart;
    }

    public void setWeWorkStart(Time weWorkStart) {
        this.weWorkStart = weWorkStart;
    }
}

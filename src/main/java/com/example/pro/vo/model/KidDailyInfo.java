package com.example.pro.vo.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "ksp_kid_daily")
public class KidDailyInfo {

    @Id
    private String dailyId;

    private String kindergartenId;

    private String kidId;

    private Date attendDate;

    private Integer attendState;

    private Date createTime;

    private String createPerson;

    private Date editTime;

    private String editPerson;

    public String getDailyId() {
        return dailyId;
    }

    public void setDailyId(String dailyId) {
        this.dailyId = dailyId == null ? null : dailyId.trim();
    }

    public String getKindergartenId() {
        return kindergartenId;
    }

    public void setKindergartenId(String kindergartenId) {
        this.kindergartenId = kindergartenId == null ? null : kindergartenId.trim();
    }

    public String getKidId() {
        return kidId;
    }

    public void setKidId(String kidId) {
        this.kidId = kidId == null ? null : kidId.trim();
    }

    public Date getAttendDate() {
        return attendDate;
    }

    public void setAttendDate(Date attendDate) {
        this.attendDate = attendDate;
    }

    public Integer getAttendState() {
        return attendState;
    }

    public void setAttendState(Integer attendState) {
        this.attendState = attendState;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreatePerson() {
        return createPerson;
    }

    public void setCreatePerson(String createPerson) {
        this.createPerson = createPerson == null ? null : createPerson.trim();
    }

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }

    public String getEditPerson() {
        return editPerson;
    }

    public void setEditPerson(String editPerson) {
        this.editPerson = editPerson == null ? null : editPerson.trim();
    }
}
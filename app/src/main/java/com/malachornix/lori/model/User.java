
package com.malachornix.lori.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable, Parcelable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("active")
    @Expose
    private String active;
    @SerializedName("changePasswordAtNextLogon")
    @Expose
    private Object changePasswordAtNextLogon;
    @SerializedName("createTs")
    @Expose
    private String createTs;
    @SerializedName("createdBy")
    @Expose
    private Object createdBy;
    @SerializedName("deleteTs")
    @Expose
    private Object deleteTs;
    @SerializedName("deletedBy")
    @Expose
    private Object deletedBy;
    @SerializedName("email")
    @Expose
    private Object email;
    @SerializedName("firstName")
    @Expose
    private Object firstName;
    @SerializedName("ipMask")
    @Expose
    private Object ipMask;
    @SerializedName("language")
    @Expose
    private Object language;
    @SerializedName("lastName")
    @Expose
    private Object lastName;
    @SerializedName("login")
    @Expose
    private String login;
    @SerializedName("loginLowerCase")
    @Expose
    private String loginLowerCase;
    @SerializedName("middleName")
    @Expose
    private Object middleName;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("password")
    @Expose
    private Object password;
    @SerializedName("position")
    @Expose
    private Object position;
    @SerializedName("timeZone")
    @Expose
    private Object timeZone;
    @SerializedName("timeZoneAuto")
    @Expose
    private Object timeZoneAuto;
    @SerializedName("updateTs")
    @Expose
    private Object updateTs;
    @SerializedName("updatedBy")
    @Expose
    private Object updatedBy;
    @SerializedName("workHoursForWeek")
    @Expose
    private String workHoursForWeek;
    public final static Parcelable.Creator<User> CREATOR = new Creator<User>() {


        @SuppressWarnings({
                "unchecked"
        })
        public User createFromParcel(Parcel in) {
            User instance = new User();
            instance.id = ((String) in.readValue((String.class.getClassLoader())));
            instance.active = ((String) in.readValue((String.class.getClassLoader())));
            instance.changePasswordAtNextLogon = ((Object) in.readValue((Object.class.getClassLoader())));
            instance.createTs = ((String) in.readValue((String.class.getClassLoader())));
            instance.createdBy = ((Object) in.readValue((Object.class.getClassLoader())));
            instance.deleteTs = ((Object) in.readValue((Object.class.getClassLoader())));
            instance.deletedBy = ((Object) in.readValue((Object.class.getClassLoader())));
            instance.email = ((Object) in.readValue((Object.class.getClassLoader())));
            instance.firstName = ((Object) in.readValue((Object.class.getClassLoader())));
            instance.ipMask = ((Object) in.readValue((Object.class.getClassLoader())));
            instance.language = ((Object) in.readValue((Object.class.getClassLoader())));
            instance.lastName = ((Object) in.readValue((Object.class.getClassLoader())));
            instance.login = ((String) in.readValue((String.class.getClassLoader())));
            instance.loginLowerCase = ((String) in.readValue((String.class.getClassLoader())));
            instance.middleName = ((Object) in.readValue((Object.class.getClassLoader())));
            instance.name = ((String) in.readValue((String.class.getClassLoader())));
            instance.password = ((Object) in.readValue((Object.class.getClassLoader())));
            instance.position = ((Object) in.readValue((Object.class.getClassLoader())));
            instance.timeZone = ((Object) in.readValue((Object.class.getClassLoader())));
            instance.timeZoneAuto = ((Object) in.readValue((Object.class.getClassLoader())));
            instance.updateTs = ((Object) in.readValue((Object.class.getClassLoader())));
            instance.updatedBy = ((Object) in.readValue((Object.class.getClassLoader())));
            instance.workHoursForWeek = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public User[] newArray(int size) {
            return (new User[size]);
        }

    };
    private final static long serialVersionUID = 4800509440027541135L;

    /**
     * No args constructor for use in serialization
     */
    public User() {
    }

    /**
     * @param position
     * @param middleName
     * @param lastName
     * @param deletedBy
     * @param timeZoneAuto
     * @param workHoursForWeek
     * @param updateTs
     * @param createTs
     * @param timeZone
     * @param changePasswordAtNextLogon
     * @param password
     * @param updatedBy
     * @param id
     * @param createdBy
     * @param email
     * @param ipMask
     * @param name
     * @param deleteTs
     * @param active
     * @param login
     * @param loginLowerCase
     * @param language
     * @param firstName
     */
    public User(String id, String active, Object changePasswordAtNextLogon, String createTs, Object createdBy, Object deleteTs, Object deletedBy, Object email, Object firstName, Object ipMask, Object language, Object lastName, String login, String loginLowerCase, Object middleName, String name, Object password, Object position, Object timeZone, Object timeZoneAuto, Object updateTs, Object updatedBy, String workHoursForWeek) {
        super();
        this.id = id;
        this.active = active;
        this.changePasswordAtNextLogon = changePasswordAtNextLogon;
        this.createTs = createTs;
        this.createdBy = createdBy;
        this.deleteTs = deleteTs;
        this.deletedBy = deletedBy;
        this.email = email;
        this.firstName = firstName;
        this.ipMask = ipMask;
        this.language = language;
        this.lastName = lastName;
        this.login = login;
        this.loginLowerCase = loginLowerCase;
        this.middleName = middleName;
        this.name = name;
        this.password = password;
        this.position = position;
        this.timeZone = timeZone;
        this.timeZoneAuto = timeZoneAuto;
        this.updateTs = updateTs;
        this.updatedBy = updatedBy;
        this.workHoursForWeek = workHoursForWeek;
    }

    /**
     * @return The id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return The active
     */
    public String getActive() {
        return active;
    }

    /**
     * @param active The active
     */
    public void setActive(String active) {
        this.active = active;
    }

    /**
     * @return The changePasswordAtNextLogon
     */
    public Object getChangePasswordAtNextLogon() {
        return changePasswordAtNextLogon;
    }

    /**
     * @param changePasswordAtNextLogon The changePasswordAtNextLogon
     */
    public void setChangePasswordAtNextLogon(Object changePasswordAtNextLogon) {
        this.changePasswordAtNextLogon = changePasswordAtNextLogon;
    }

    /**
     * @return The createTs
     */
    public String getCreateTs() {
        return createTs;
    }

    /**
     * @param createTs The createTs
     */
    public void setCreateTs(String createTs) {
        this.createTs = createTs;
    }

    /**
     * @return The createdBy
     */
    public Object getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy The createdBy
     */
    public void setCreatedBy(Object createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return The deleteTs
     */
    public Object getDeleteTs() {
        return deleteTs;
    }

    /**
     * @param deleteTs The deleteTs
     */
    public void setDeleteTs(Object deleteTs) {
        this.deleteTs = deleteTs;
    }

    /**
     * @return The deletedBy
     */
    public Object getDeletedBy() {
        return deletedBy;
    }

    /**
     * @param deletedBy The deletedBy
     */
    public void setDeletedBy(Object deletedBy) {
        this.deletedBy = deletedBy;
    }

    /**
     * @return The email
     */
    public Object getEmail() {
        return email;
    }

    /**
     * @param email The email
     */
    public void setEmail(Object email) {
        this.email = email;
    }

    /**
     * @return The firstName
     */
    public Object getFirstName() {
        return firstName;
    }

    /**
     * @param firstName The firstName
     */
    public void setFirstName(Object firstName) {
        this.firstName = firstName;
    }

    /**
     * @return The ipMask
     */
    public Object getIpMask() {
        return ipMask;
    }

    /**
     * @param ipMask The ipMask
     */
    public void setIpMask(Object ipMask) {
        this.ipMask = ipMask;
    }

    /**
     * @return The language
     */
    public Object getLanguage() {
        return language;
    }

    /**
     * @param language The language
     */
    public void setLanguage(Object language) {
        this.language = language;
    }

    /**
     * @return The lastName
     */
    public Object getLastName() {
        return lastName;
    }

    /**
     * @param lastName The lastName
     */
    public void setLastName(Object lastName) {
        this.lastName = lastName;
    }

    /**
     * @return The login
     */
    public String getLogin() {
        return login;
    }

    /**
     * @param login The login
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * @return The loginLowerCase
     */
    public String getLoginLowerCase() {
        return loginLowerCase;
    }

    /**
     * @param loginLowerCase The loginLowerCase
     */
    public void setLoginLowerCase(String loginLowerCase) {
        this.loginLowerCase = loginLowerCase;
    }

    /**
     * @return The middleName
     */
    public Object getMiddleName() {
        return middleName;
    }

    /**
     * @param middleName The middleName
     */
    public void setMiddleName(Object middleName) {
        this.middleName = middleName;
    }

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The password
     */
    public Object getPassword() {
        return password;
    }

    /**
     * @param password The password
     */
    public void setPassword(Object password) {
        this.password = password;
    }

    /**
     * @return The position
     */
    public Object getPosition() {
        return position;
    }

    /**
     * @param position The position
     */
    public void setPosition(Object position) {
        this.position = position;
    }

    /**
     * @return The timeZone
     */
    public Object getTimeZone() {
        return timeZone;
    }

    /**
     * @param timeZone The timeZone
     */
    public void setTimeZone(Object timeZone) {
        this.timeZone = timeZone;
    }

    /**
     * @return The timeZoneAuto
     */
    public Object getTimeZoneAuto() {
        return timeZoneAuto;
    }

    /**
     * @param timeZoneAuto The timeZoneAuto
     */
    public void setTimeZoneAuto(Object timeZoneAuto) {
        this.timeZoneAuto = timeZoneAuto;
    }

    /**
     * @return The updateTs
     */
    public Object getUpdateTs() {
        return updateTs;
    }

    /**
     * @param updateTs The updateTs
     */
    public void setUpdateTs(Object updateTs) {
        this.updateTs = updateTs;
    }

    /**
     * @return The updatedBy
     */
    public Object getUpdatedBy() {
        return updatedBy;
    }

    /**
     * @param updatedBy The updatedBy
     */
    public void setUpdatedBy(Object updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * @return The workHoursForWeek
     */
    public String getWorkHoursForWeek() {
        return workHoursForWeek;
    }

    /**
     * @param workHoursForWeek The workHoursForWeek
     */
    public void setWorkHoursForWeek(String workHoursForWeek) {
        this.workHoursForWeek = workHoursForWeek;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(active);
        dest.writeValue(changePasswordAtNextLogon);
        dest.writeValue(createTs);
        dest.writeValue(createdBy);
        dest.writeValue(deleteTs);
        dest.writeValue(deletedBy);
        dest.writeValue(email);
        dest.writeValue(firstName);
        dest.writeValue(ipMask);
        dest.writeValue(language);
        dest.writeValue(lastName);
        dest.writeValue(login);
        dest.writeValue(loginLowerCase);
        dest.writeValue(middleName);
        dest.writeValue(name);
        dest.writeValue(password);
        dest.writeValue(position);
        dest.writeValue(timeZone);
        dest.writeValue(timeZoneAuto);
        dest.writeValue(updateTs);
        dest.writeValue(updatedBy);
        dest.writeValue(workHoursForWeek);
    }

    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", active='" + active + '\'' +
                ", changePasswordAtNextLogon=" + changePasswordAtNextLogon +
                ", createTs='" + createTs + '\'' +
                ", createdBy=" + createdBy +
                ", deleteTs=" + deleteTs +
                ", deletedBy=" + deletedBy +
                ", email=" + email +
                ", firstName=" + firstName +
                ", ipMask=" + ipMask +
                ", language=" + language +
                ", lastName=" + lastName +
                ", login='" + login + '\'' +
                ", loginLowerCase='" + loginLowerCase + '\'' +
                ", middleName=" + middleName +
                ", name='" + name + '\'' +
                ", password=" + password +
                ", position=" + position +
                ", timeZone=" + timeZone +
                ", timeZoneAuto=" + timeZoneAuto +
                ", updateTs=" + updateTs +
                ", updatedBy=" + updatedBy +
                ", workHoursForWeek='" + workHoursForWeek + '\'' +
                '}';
    }
}
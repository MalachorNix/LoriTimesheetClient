package com.malachornix.lori.model;

import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Task implements Serializable, Parcelable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("project")
    @Expose
    private Project project;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("createTs")
    @Expose
    private String createTs;
    @SerializedName("createdBy")
    @Expose
    private String createdBy;
    @SerializedName("deleteTs")
    @Expose
    private Object deleteTs;
    @SerializedName("deletedBy")
    @Expose
    private Object deletedBy;
    @SerializedName("description")
    @Expose
    private Object description;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("updateTs")
    @Expose
    private String updateTs;
    @SerializedName("updatedBy")
    @Expose
    private Object updatedBy;
    public final static Parcelable.Creator<Task> CREATOR = new Creator<Task>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Task createFromParcel(Parcel in) {
            Task instance = new Task();
            instance.id = ((String) in.readValue((String.class.getClassLoader())));
            instance.code = ((String) in.readValue((String.class.getClassLoader())));
            instance.createTs = ((String) in.readValue((String.class.getClassLoader())));
            instance.createdBy = ((String) in.readValue((String.class.getClassLoader())));
            instance.deleteTs = ((Object) in.readValue((Object.class.getClassLoader())));
            instance.deletedBy = ((Object) in.readValue((Object.class.getClassLoader())));
            instance.description = ((Object) in.readValue((Object.class.getClassLoader())));
            instance.name = ((String) in.readValue((String.class.getClassLoader())));
            instance.status = ((String) in.readValue((String.class.getClassLoader())));
            instance.updateTs = ((String) in.readValue((String.class.getClassLoader())));
            instance.updatedBy = ((Object) in.readValue((Object.class.getClassLoader())));
            instance.project = (Project) in.readValue(Project.class.getClassLoader());
            return instance;
        }

        public Task[] newArray(int size) {
            return (new Task[size]);
        }

    };
    private final static long serialVersionUID = 8497180115331834427L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCreateTs() {
        return createTs;
    }

    public void setCreateTs(String createTs) {
        this.createTs = createTs;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Object getDeleteTs() {
        return deleteTs;
    }

    public void setDeleteTs(Object deleteTs) {
        this.deleteTs = deleteTs;
    }

    public Object getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(Object deletedBy) {
        this.deletedBy = deletedBy;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUpdateTs() {
        return updateTs;
    }

    public void setUpdateTs(String updateTs) {
        this.updateTs = updateTs;
    }

    public Object getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Object updatedBy) {
        this.updatedBy = updatedBy;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(code);
        dest.writeValue(createTs);
        dest.writeValue(createdBy);
        dest.writeValue(deleteTs);
        dest.writeValue(deletedBy);
        dest.writeValue(description);
        dest.writeValue(name);
        dest.writeValue(status);
        dest.writeValue(updateTs);
        dest.writeValue(updatedBy);
    }

    public int describeContents() {
        return 0;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
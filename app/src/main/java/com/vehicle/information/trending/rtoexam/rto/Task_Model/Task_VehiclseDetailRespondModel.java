package com.vehicle.information.trending.rtoexam.rto.Task_Model;

import android.os.Parcel;
import android.os.Parcelable;
import java.io.Serializable;


public class Task_VehiclseDetailRespondModel implements Serializable, Parcelable {
    public static final Creator<Task_VehiclseDetailRespondModel> CREATOR = new Creator<Task_VehiclseDetailRespondModel>() {

        @Override
        public Task_VehiclseDetailRespondModel createFromParcel(Parcel parcel) {
            return new Task_VehiclseDetailRespondModel(parcel);
        }


        @Override
        public Task_VehiclseDetailRespondModel[] newArray(int i) {
            return new Task_VehiclseDetailRespondModel[i];
        }
    };
    private Task_VehiclesDetailModel details;
    private boolean extra;
    private int statusCode;
    private String statusMessage;

    @Override
    public int describeContents() {
        return 0;
    }

    public Task_VehiclseDetailRespondModel(int i, String str, Task_VehiclesDetailModel m_rtoVehiclesDetail) {
        this.statusCode = i;
        this.statusMessage = str;
        this.details = m_rtoVehiclesDetail;
    }

    public Task_VehiclseDetailRespondModel(int i, String str, Task_VehiclesDetailModel m_rtoVehiclesDetail, boolean z) {
        this.statusCode = i;
        this.statusMessage = str;
        this.details = m_rtoVehiclesDetail;
        this.extra = z;
    }

    protected Task_VehiclseDetailRespondModel(Parcel parcel) {
        this.details = (Task_VehiclesDetailModel) parcel.readParcelable(Task_VehiclesDetailModel.class.getClassLoader());
        this.extra = parcel.readByte() != 0;
        this.statusCode = parcel.readInt();
        this.statusMessage = parcel.readString();
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public void setStatusCode(int i) {
        this.statusCode = i;
    }

    public String getStatusMessage() {
        return this.statusMessage;
    }

    public Task_VehiclesDetailModel getDetails() {
        return this.details;
    }

    public boolean isExtra() {
        return this.extra;
    }

    public String toString() {
        return "VehicleDetails{statusCode=" + this.statusCode + ", statusMessage='" + this.statusMessage + "', details=" + this.details + ", extra=" + this.extra + '}';
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.details, i);
        parcel.writeByte(this.extra ? (byte) 1 : (byte) 0);
        parcel.writeInt(this.statusCode);
        parcel.writeString(this.statusMessage);
    }
}

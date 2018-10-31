package com.mealapp.retrofit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LocationResp {

@SerializedName("id")
@Expose
private Integer id;
@SerializedName("streetAddress")
@Expose
private String streetAddress;
@SerializedName("postalCode")
@Expose
private String postalCode;
@SerializedName("city")
@Expose
private String city;
@SerializedName("stateProvince")
@Expose
private String stateProvince;

public Integer getId() {
return id;
}

public void setId(Integer id) {
this.id = id;
}

public String getStreetAddress() {
return streetAddress;
}

public void setStreetAddress(String streetAddress) {
this.streetAddress = streetAddress;
}

public String getPostalCode() {
return postalCode;
}

public void setPostalCode(String postalCode) {
this.postalCode = postalCode;
}

public String getCity() {
return city;
}

public void setCity(String city) {
this.city = city;
}

public String getStateProvince() {
return stateProvince;
}

public void setStateProvince(String stateProvince) {
this.stateProvince = stateProvince;
}

}
package com.mealapp.retrofit.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserDetail {

@SerializedName("id")
@Expose
private Integer id;
@SerializedName("login")
@Expose
private String login;
@SerializedName("firstName")
@Expose
private String firstName;
@SerializedName("lastName")
@Expose
private String lastName;
@SerializedName("email")
@Expose
private String email;
@SerializedName("imageUrl")
@Expose
private String imageUrl;
@SerializedName("activated")
@Expose
private Boolean activated;
@SerializedName("langKey")
@Expose
private Object langKey;
@SerializedName("createdBy")
@Expose
private String createdBy;
@SerializedName("createdDate")
@Expose
private String createdDate;
@SerializedName("lastModifiedBy")
@Expose
private String lastModifiedBy;
@SerializedName("lastModifiedDate")
@Expose
private String lastModifiedDate;
@SerializedName("authorities")
@Expose
private List<String> authorities = null;

public Integer getId() {
return id;
}

public void setId(Integer id) {
this.id = id;
}

public String getLogin() {
return login;
}

public void setLogin(String login) {
this.login = login;
}

public String getFirstName() {
return firstName;
}

public void setFirstName(String firstName) {
this.firstName = firstName;
}

public String getLastName() {
return lastName;
}

public void setLastName(String lastName) {
this.lastName = lastName;
}

public String getEmail() {
return email;
}

public void setEmail(String email) {
this.email = email;
}

public String getImageUrl() {
return imageUrl;
}

public void setImageUrl(String imageUrl) {
this.imageUrl = imageUrl;
}

public Boolean getActivated() {
return activated;
}

public void setActivated(Boolean activated) {
this.activated = activated;
}

public Object getLangKey() {
return langKey;
}

public void setLangKey(Object langKey) {
this.langKey = langKey;
}

public String getCreatedBy() {
return createdBy;
}

public void setCreatedBy(String createdBy) {
this.createdBy = createdBy;
}

public String getCreatedDate() {
return createdDate;
}

public void setCreatedDate(String createdDate) {
this.createdDate = createdDate;
}

public String getLastModifiedBy() {
return lastModifiedBy;
}

public void setLastModifiedBy(String lastModifiedBy) {
this.lastModifiedBy = lastModifiedBy;
}

public String getLastModifiedDate() {
return lastModifiedDate;
}

public void setLastModifiedDate(String lastModifiedDate) {
this.lastModifiedDate = lastModifiedDate;
}

public List<String> getAuthorities() {
return authorities;
}

public void setAuthorities(List<String> authorities) {
this.authorities = authorities;
}

}
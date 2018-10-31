package com.mealapp.retrofit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MenuItemResp {

@SerializedName("id")
@Expose
private Integer id;
@SerializedName("itemname")
@Expose
private String itemname;
@SerializedName("price")
@Expose
private Integer price;
@SerializedName("description")
@Expose
private String description;
@SerializedName("imageurl")
@Expose
private String imageurl;
@SerializedName("imageurlContentType")
@Expose
private Object imageurlContentType;

public Integer getId() {
return id;
}

public void setId(Integer id) {
this.id = id;
}

public String getItemname() {
return itemname;
}

public void setItemname(String itemname) {
this.itemname = itemname;
}

public Integer getPrice() {
return price;
}

public void setPrice(Integer price) {
this.price = price;
}

public String getDescription() {
return description;
}

public void setDescription(String description) {
this.description = description;
}

public String getImageurl() {
return imageurl;
}

public void setImageurl(String imageurl) {
this.imageurl = imageurl;
}

public Object getImageurlContentType() {
return imageurlContentType;
}

public void setImageurlContentType(Object imageurlContentType) {
this.imageurlContentType = imageurlContentType;
}

}
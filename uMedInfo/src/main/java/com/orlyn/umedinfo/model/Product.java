package com.orlyn.umedinfo.model;

import java.io.Serializable;

public class Product implements Serializable{
		private String splId;
		private String rid;
		private String name;
		private String form;
		private String route;
		private String manufacturer;
		private String quantity;
		private String firstImage;
		private int tmpImgID;
		public int getTmpImgID() {
			return tmpImgID;
		}
		public String getSplId() {
			return splId;
		}
		public void setSplId(String splId) {
			this.splId = splId;
		}
		public void setTmpImgID(int tmpImgID) {
			this.tmpImgID = tmpImgID;
		}
		public String getForm() {
			return form;
		}
		public void setForm(String form) {
			this.form = form;
		}
		public String getRoute() {
			return route;
		}
		public void setRoute(String route) {
			this.route = route;
		}
		public String getManufacturer() {
			return manufacturer;
		}
		public void setManufacturer(String manufacturer) {
			this.manufacturer = manufacturer;
		}
		public String getQuantity() {
			return quantity;
		}
		public void setQuantity(String quantity) {
			this.quantity = quantity;
		}
		public String getFirstImage() {
			return firstImage;
		}
		public void setFirstImage(String firstImage) {
			this.firstImage = firstImage;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getRid() {
			return rid;
		}
		public void setRid(String rid) {
			this.rid = rid;
		}
}

package com.example.marketapp.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LocationPost {

        @SerializedName("longitude")
        @Expose
        private Double longitude;
        @SerializedName("latitude")
        @Expose
        private Double latitude;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("description")
        @Expose
        private String description;

        public LocationPost(Double longitude, Double latitude, String city, String description) {
            this.longitude = longitude;
            this.latitude = latitude;
            this.city = city;
            this.description = description;
        }

        public Double getLongitude() {
            return longitude;
        }

        public void setLongitude(Double longitude) {
            this.longitude = longitude;
        }

        public Double getLatitude() {
            return latitude;
        }

        public void setLatitude(Double latitude) {
            this.latitude = latitude;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

    }

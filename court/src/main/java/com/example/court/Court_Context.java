package com.example.court;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class  Court_Context {

        private String profile;
        private String name;
        private String address;
        private String time;
        private String information;
        private ImageView imageView;

    public ImageView getImageView() {
        return imageView;
    }

    public String getProfile() {
            return profile;
        }

        public void setProfile(String profile) {
            this.profile = profile;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getInformation() {
            return information;
        }

        public void setInformation(String information) {
            this.information = information;
        }


    }


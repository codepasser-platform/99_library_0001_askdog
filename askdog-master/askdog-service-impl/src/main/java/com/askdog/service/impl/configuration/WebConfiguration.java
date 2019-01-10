package com.askdog.service.impl.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

public class WebConfiguration {


    @Configuration
    @ConfigurationProperties("askdog.web.user")
    public static class UserConfiguration {

        private String defaultAvatar;

        public String getDefaultAvatar() {
            return defaultAvatar;
        }

        public void setDefaultAvatar(String defaultAvatar) {
            this.defaultAvatar = defaultAvatar;
        }
    }

    @Configuration
    @ConfigurationProperties("askdog.web.question")
    public static class QuestionConfiguration {

        private String detailUrl;

        public String getDetailUrl() {
            return detailUrl;
        }

        public void setDetailUrl(String detailUrl) {
            this.detailUrl = detailUrl;
        }

        private QrCode qrCode;

        public QrCode getQrCode() {
            return qrCode;
        }

        public void setQrCode(QrCode qrCode) {
            this.qrCode = qrCode;
        }

        public static class QrCode {

            private int width;
            private int height;
            private String format;

            public int getWidth() {
                return width;
            }

            public void setWidth(int width) {
                this.width = width;
            }

            public int getHeight() {
                return height;
            }

            public void setHeight(int height) {
                this.height = height;
            }

            public String getFormat() {
                return format;
            }

            public void setFormat(String format) {
                this.format = format;
            }

        }

    }
}

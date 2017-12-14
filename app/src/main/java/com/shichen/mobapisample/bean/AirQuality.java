package com.shichen.mobapisample.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shichen on 2017/11/17.
 *
 * @author shichen 754314442@qq.com
 */

public class AirQuality extends BaseResult implements Serializable {

    /**
     * msg : success
     * result : [{"aqi":67,"city":"重庆","district":"重庆","fetureData":[{"aqi":80,"date":"2017-12-14","quality":"良"},{"aqi":63,"date":"2017-12-15","quality":"良"},{"aqi":59,"date":"2017-12-16","quality":"良"},{"aqi":52,"date":"2017-12-17","quality":"良"},{"aqi":77,"date":"2017-12-18","quality":"良"},{"aqi":91,"date":"2017-12-19","quality":"良"}],"hourData":[{"aqi":67,"dateTime":"2017-12-13 07:00:00"},{"aqi":65,"dateTime":"2017-12-13 06:00:00"},{"aqi":67,"dateTime":"2017-12-13 05:00:00"},{"aqi":69,"dateTime":"2017-12-13 04:00:00"},{"aqi":74,"dateTime":"2017-12-13 03:00:00"},{"aqi":80,"dateTime":"2017-12-13 02:00:00"},{"aqi":85,"dateTime":"2017-12-13 01:00:00"},{"aqi":92,"dateTime":"2017-12-13 00:00:00"},{"aqi":100,"dateTime":"2017-12-12 23:00:00"},{"aqi":109,"dateTime":"2017-12-12 22:00:00"},{"aqi":119,"dateTime":"2017-12-12 21:00:00"},{"aqi":127,"dateTime":"2017-12-12 20:00:00"},{"aqi":135,"dateTime":"2017-12-12 19:00:00"},{"aqi":144,"dateTime":"2017-12-12 18:00:00"},{"aqi":139,"dateTime":"2017-12-12 17:00:00"},{"aqi":145,"dateTime":"2017-12-12 16:00:00"},{"aqi":140,"dateTime":"2017-12-12 15:00:00"},{"aqi":147,"dateTime":"2017-12-12 14:00:00"},{"aqi":148,"dateTime":"2017-12-12 13:00:00"},{"aqi":147,"dateTime":"2017-12-12 12:00:00"},{"aqi":138,"dateTime":"2017-12-12 11:00:00"},{"aqi":135,"dateTime":"2017-12-12 10:00:00"},{"aqi":127,"dateTime":"2017-12-12 09:00:00"},{"aqi":115,"dateTime":"2017-12-12 08:00:00"}],"no2":39,"pm10":72,"pm25":48,"province":"重庆","quality":"良","so2":8,"updateTime":"2017-12-13 08:00:00"}]
     * retCode : 200
     */
    @SerializedName("msg")
    private String msg;
    @SerializedName("retCode")
    private String retCode;
    @SerializedName("result")
    private List<AirData> result;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public List<AirData> getResult() {
        return result;
    }

    public void setResult(List<AirData> result) {
        this.result = result;
    }

    public static class AirData implements Serializable {
        /**
         * aqi : 67
         * city : 重庆
         * district : 重庆
         * fetureData : [{"aqi":80,"date":"2017-12-14","quality":"良"},{"aqi":63,"date":"2017-12-15","quality":"良"},{"aqi":59,"date":"2017-12-16","quality":"良"},{"aqi":52,"date":"2017-12-17","quality":"良"},{"aqi":77,"date":"2017-12-18","quality":"良"},{"aqi":91,"date":"2017-12-19","quality":"良"}]
         * hourData : [{"aqi":67,"dateTime":"2017-12-13 07:00:00"},{"aqi":65,"dateTime":"2017-12-13 06:00:00"},{"aqi":67,"dateTime":"2017-12-13 05:00:00"},{"aqi":69,"dateTime":"2017-12-13 04:00:00"},{"aqi":74,"dateTime":"2017-12-13 03:00:00"},{"aqi":80,"dateTime":"2017-12-13 02:00:00"},{"aqi":85,"dateTime":"2017-12-13 01:00:00"},{"aqi":92,"dateTime":"2017-12-13 00:00:00"},{"aqi":100,"dateTime":"2017-12-12 23:00:00"},{"aqi":109,"dateTime":"2017-12-12 22:00:00"},{"aqi":119,"dateTime":"2017-12-12 21:00:00"},{"aqi":127,"dateTime":"2017-12-12 20:00:00"},{"aqi":135,"dateTime":"2017-12-12 19:00:00"},{"aqi":144,"dateTime":"2017-12-12 18:00:00"},{"aqi":139,"dateTime":"2017-12-12 17:00:00"},{"aqi":145,"dateTime":"2017-12-12 16:00:00"},{"aqi":140,"dateTime":"2017-12-12 15:00:00"},{"aqi":147,"dateTime":"2017-12-12 14:00:00"},{"aqi":148,"dateTime":"2017-12-12 13:00:00"},{"aqi":147,"dateTime":"2017-12-12 12:00:00"},{"aqi":138,"dateTime":"2017-12-12 11:00:00"},{"aqi":135,"dateTime":"2017-12-12 10:00:00"},{"aqi":127,"dateTime":"2017-12-12 09:00:00"},{"aqi":115,"dateTime":"2017-12-12 08:00:00"}]
         * no2 : 39
         * pm10 : 72
         * pm25 : 48
         * province : 重庆
         * quality : 良
         * so2 : 8
         * updateTime : 2017-12-13 08:00:00
         */
        @SerializedName("aqi")
        private int aqi;
        @SerializedName("city")
        private String city;
        @SerializedName("district")
        private String district;
        @SerializedName("no2")
        private int no2;
        @SerializedName("pm10")
        private int pm10;
        @SerializedName("pm25")
        private int pm25;
        @SerializedName("province")
        private String province;
        @SerializedName("quality")
        private String quality;
        @SerializedName("so2")
        private int so2;
        @SerializedName("updateTime")
        private String updateTime;
        @SerializedName("fetureData")
        private List<FetureAirData> fetureData;
        @SerializedName("hourData")
        private List<HourAirData> hourData;

        public int getAqi() {
            return aqi;
        }

        public void setAqi(int aqi) {
            this.aqi = aqi;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public int getNo2() {
            return no2;
        }

        public void setNo2(int no2) {
            this.no2 = no2;
        }

        public int getPm10() {
            return pm10;
        }

        public void setPm10(int pm10) {
            this.pm10 = pm10;
        }

        public int getPm25() {
            return pm25;
        }

        public void setPm25(int pm25) {
            this.pm25 = pm25;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getQuality() {
            return quality;
        }

        public void setQuality(String quality) {
            this.quality = quality;
        }

        public int getSo2() {
            return so2;
        }

        public void setSo2(int so2) {
            this.so2 = so2;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public List<FetureAirData> getFetureData() {
            return fetureData;
        }

        public void setFetureData(List<FetureAirData> fetureData) {
            this.fetureData = fetureData;
        }

        public List<HourAirData> getHourData() {
            return hourData;
        }

        public void setHourData(List<HourAirData> hourData) {
            this.hourData = hourData;
        }

        public static class FetureAirData implements Serializable{
            /**
             * aqi : 80
             * date : 2017-12-14
             * quality : 良
             */
            @SerializedName("aqi")
            private int aqi;
            @SerializedName("date")
            private String date;
            @SerializedName("quality")
            private String quality;

            public int getAqi() {
                return aqi;
            }

            public void setAqi(int aqi) {
                this.aqi = aqi;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getQuality() {
                return quality;
            }

            public void setQuality(String quality) {
                this.quality = quality;
            }

            public String getMsg() {
                if (date != null) {
                    if (date.length() > 9) {
                        return date.substring(5, 10);
                    } else {
                        return "";
                    }
                } else {
                    return "";
                }
            }
        }

        public static class HourAirData implements Serializable {
            /**
             * aqi : 67
             * dateTime : 2017-12-13 07:00:00
             */
            @SerializedName("aqi")
            private int aqi;
            @SerializedName("dateTime")
            private String dateTime;

            public int getAqi() {
                return aqi;
            }

            public void setAqi(int aqi) {
                this.aqi = aqi;
            }

            public String getDateTime() {
                return dateTime;
            }

            public void setDateTime(String dateTime) {
                this.dateTime = dateTime;
            }

            public String getMsg() {
                if (dateTime != null) {
                    if (dateTime.length() > 17) {
                        return dateTime.substring(10, 16);
                    } else {
                        return "";
                    }
                } else {
                    return "";
                }
            }
        }
    }
}

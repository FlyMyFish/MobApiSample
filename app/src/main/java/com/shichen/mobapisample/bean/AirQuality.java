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
     * result : [{"aqi":198,"city":"郑州","district":"郑州","fetureData":[{"aqi":66,"date":"2017-11-18","quality":"良"},{"aqi":74,"date":"2017-11-19","quality":"良"},{"aqi":73,"date":"2017-11-20","quality":"良"},{"aqi":88,"date":"2017-11-21","quality":"良"},{"aqi":66,"date":"2017-11-22","quality":"良"}],"hourData":[{"aqi":198,"dateTime":"2017-11-17 07:00:00"},{"aqi":196,"dateTime":"2017-11-17 06:00:00"},{"aqi":196,"dateTime":"2017-11-17 05:00:00"},{"aqi":195,"dateTime":"2017-11-17 04:00:00"},{"aqi":198,"dateTime":"2017-11-17 03:00:00"},{"aqi":201,"dateTime":"2017-11-17 02:00:00"},{"aqi":199,"dateTime":"2017-11-17 01:00:00"},{"aqi":198,"dateTime":"2017-11-17 00:00:00"},{"aqi":196,"dateTime":"2017-11-16 23:00:00"},{"aqi":193,"dateTime":"2017-11-16 22:00:00"},{"aqi":198,"dateTime":"2017-11-16 21:00:00"},{"aqi":202,"dateTime":"2017-11-16 20:00:00"},{"aqi":204,"dateTime":"2017-11-16 19:00:00"},{"aqi":212,"dateTime":"2017-11-16 18:00:00"},{"aqi":216,"dateTime":"2017-11-16 17:00:00"},{"aqi":218,"dateTime":"2017-11-16 16:00:00"},{"aqi":214,"dateTime":"2017-11-16 15:00:00"},{"aqi":209,"dateTime":"2017-11-16 14:00:00"},{"aqi":190,"dateTime":"2017-11-16 13:00:00"},{"aqi":163,"dateTime":"2017-11-16 12:00:00"},{"aqi":137,"dateTime":"2017-11-16 11:00:00"},{"aqi":120,"dateTime":"2017-11-16 10:00:00"},{"aqi":113,"dateTime":"2017-11-16 09:00:00"},{"aqi":110,"dateTime":"2017-11-16 08:00:00"}],"no2":72,"pm10":200,"pm25":148,"province":"河南","quality":"中度污染","so2":26,"updateTime":"2017-11-17 08:00:00"}]
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
         * aqi : 198
         * city : 郑州
         * district : 郑州
         * fetureData : [{"aqi":66,"date":"2017-11-18","quality":"良"},{"aqi":74,"date":"2017-11-19","quality":"良"},{"aqi":73,"date":"2017-11-20","quality":"良"},{"aqi":88,"date":"2017-11-21","quality":"良"},{"aqi":66,"date":"2017-11-22","quality":"良"}]
         * hourData : [{"aqi":198,"dateTime":"2017-11-17 07:00:00"},{"aqi":196,"dateTime":"2017-11-17 06:00:00"},{"aqi":196,"dateTime":"2017-11-17 05:00:00"},{"aqi":195,"dateTime":"2017-11-17 04:00:00"},{"aqi":198,"dateTime":"2017-11-17 03:00:00"},{"aqi":201,"dateTime":"2017-11-17 02:00:00"},{"aqi":199,"dateTime":"2017-11-17 01:00:00"},{"aqi":198,"dateTime":"2017-11-17 00:00:00"},{"aqi":196,"dateTime":"2017-11-16 23:00:00"},{"aqi":193,"dateTime":"2017-11-16 22:00:00"},{"aqi":198,"dateTime":"2017-11-16 21:00:00"},{"aqi":202,"dateTime":"2017-11-16 20:00:00"},{"aqi":204,"dateTime":"2017-11-16 19:00:00"},{"aqi":212,"dateTime":"2017-11-16 18:00:00"},{"aqi":216,"dateTime":"2017-11-16 17:00:00"},{"aqi":218,"dateTime":"2017-11-16 16:00:00"},{"aqi":214,"dateTime":"2017-11-16 15:00:00"},{"aqi":209,"dateTime":"2017-11-16 14:00:00"},{"aqi":190,"dateTime":"2017-11-16 13:00:00"},{"aqi":163,"dateTime":"2017-11-16 12:00:00"},{"aqi":137,"dateTime":"2017-11-16 11:00:00"},{"aqi":120,"dateTime":"2017-11-16 10:00:00"},{"aqi":113,"dateTime":"2017-11-16 09:00:00"},{"aqi":110,"dateTime":"2017-11-16 08:00:00"}]
         * no2 : 72
         * pm10 : 200
         * pm25 : 148
         * province : 河南
         * quality : 中度污染
         * so2 : 26
         * updateTime : 2017-11-17 08:00:00
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

        public static class FetureAirData implements Serializable {
            /**
             * aqi : 66
             * date : 2017-11-18
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
             * aqi : 198
             * dateTime : 2017-11-17 07:00:00
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

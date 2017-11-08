package com.shichen.mobapisample.bean;

import java.util.List;

/**
 * 天气类
 * @author shichen 754314442@qq.com
 * Created by Administrator on 2017/9/21.
 */

public class WeatherInfo extends BaseResult{

    /**
     * msg : success
     * result : [{"airCondition":"良","city":"北京","coldIndex":"低发期","updateTime":"20150908153820","date":"2015-09-08","distrct":"门头沟","dressingIndex":"短袖类","exerciseIndex":"适宜","future":[{"date":"2015-09-09","dayTime":"阵雨","night":"阴","temperature":"24°C/18°C","week":"星期三","wind":"无持续风向小于3级"},{"date":"2015-09-10","dayTime":"阵雨","night":"阵雨","temperature":"22°C/15°C","week":"星期四","wind":"无持续风向小于3级"},{"date":"2015-09-11","dayTime":"阴","night":"晴","temperature":"23°C/15°C","week":"星期五","wind":"北风3～4级无持续风向小于3级"},{"date":"2015-09-12","dayTime":"晴","night":"晴","temperature":"26°C/13°C","week":"星期六","wind":"北风3～4级无持续风向小于3级"},{"date":"2015-09-13","dayTime":"晴","night":"晴","temperature":"27°C/16°C","week":"星期日","wind":"无持续风向小于3级"},{"date":"2015-09-14","dayTime":"晴","night":"多云","temperature":"27°C/16°C","week":"星期一","wind":"无持续风向小于3级"},{"date":"2015-09-15","dayTime":"少云","night":"晴","temperature":"26°C/14°C","week":"星期二","wind":"南风3级南风2级"},{"date":"2015-09-16","dayTime":"局部多云","night":"少云","temperature":"26°C/15°C","week":"星期三","wind":"南风3级南风2级"},{"date":"2015-09-17","dayTime":"阴天","night":"局部多云","temperature":"26°C/15°C","week":"星期四","wind":"东南风2级"}],"humidity":"湿度：46%","province":"北京","sunset":"18:37","sunrise":"05:49","temperature":"25℃","time":"14:35","washIndex":"不适宜","weather":"多云","week":"周二","wind":"南风2级"}]
     * retCode : 200
     */

    /**
     * 返回说明
     */
    private String msg;
    /**
     * 返回码
     */
    private String retCode;
    /**
     * 返回结果集
     */
    private List<WeatherBean> result;

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

    public List<WeatherBean> getResult() {
        return result;
    }

    public void setResult(List<WeatherBean> result) {
        this.result = result;
    }

    public static class WeatherBean {
        /**
         * airCondition : 良
         * city : 北京
         * coldIndex : 低发期
         * updateTime : 20150908153820
         * date : 2015-09-08
         * distrct : 门头沟
         * dressingIndex : 短袖类
         * exerciseIndex : 适宜
         * future : [{"date":"2015-09-09","dayTime":"阵雨","night":"阴","temperature":"24°C/18°C","week":"星期三","wind":"无持续风向小于3级"},{"date":"2015-09-10","dayTime":"阵雨","night":"阵雨","temperature":"22°C/15°C","week":"星期四","wind":"无持续风向小于3级"},{"date":"2015-09-11","dayTime":"阴","night":"晴","temperature":"23°C/15°C","week":"星期五","wind":"北风3～4级无持续风向小于3级"},{"date":"2015-09-12","dayTime":"晴","night":"晴","temperature":"26°C/13°C","week":"星期六","wind":"北风3～4级无持续风向小于3级"},{"date":"2015-09-13","dayTime":"晴","night":"晴","temperature":"27°C/16°C","week":"星期日","wind":"无持续风向小于3级"},{"date":"2015-09-14","dayTime":"晴","night":"多云","temperature":"27°C/16°C","week":"星期一","wind":"无持续风向小于3级"},{"date":"2015-09-15","dayTime":"少云","night":"晴","temperature":"26°C/14°C","week":"星期二","wind":"南风3级南风2级"},{"date":"2015-09-16","dayTime":"局部多云","night":"少云","temperature":"26°C/15°C","week":"星期三","wind":"南风3级南风2级"},{"date":"2015-09-17","dayTime":"阴天","night":"局部多云","temperature":"26°C/15°C","week":"星期四","wind":"东南风2级"}]
         * humidity : 湿度：46%
         * province : 北京
         * sunset : 18:37
         * sunrise : 05:49
         * temperature : 25℃
         * time : 14:35
         * washIndex : 不适宜
         * weather : 多云
         * week : 周二
         * wind : 南风2级
         */

        /**
         * 空气质量
         */
        private String airCondition;
        /**
         * 城市
         */
        private String city;
        /**
         * 感冒指数
         */
        private String coldIndex;
        /**
         * 更新时间
         */
        private String updateTime;
        /**
         * 日期
         */
        private String date;
        /**
         * 区县
         */
        private String distrct;
        /**
         * 穿衣指数
         */
        private String dressingIndex;
        /**
         * 运动指数
         */
        private String exerciseIndex;
        /**
         * 湿度
         */
        private String humidity;
        /**
         * 省份
         */
        private String province;
        /**
         * 日落时间
         */
        private String sunset;
        /**
         * 日出时间
         */
        private String sunrise;
        /**
         * 温度
         */
        private String temperature;
        /**
         * 时间
         */
        private String time;
        /**
         * 洗车指数
         */
        private String washIndex;
        /**
         * 天气
         */
        private String weather;
        /**
         * 星期
         */
        private String week;
        /**
         * 风向
         */
        private String wind;
        private List<FutureBean> future;

        public String getAirCondition() {
            return airCondition;
        }

        public void setAirCondition(String airCondition) {
            this.airCondition = airCondition;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getColdIndex() {
            return coldIndex;
        }

        public void setColdIndex(String coldIndex) {
            this.coldIndex = coldIndex;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getDistrct() {
            return distrct;
        }

        public void setDistrct(String distrct) {
            this.distrct = distrct;
        }

        public String getDressingIndex() {
            return dressingIndex;
        }

        public void setDressingIndex(String dressingIndex) {
            this.dressingIndex = dressingIndex;
        }

        public String getExerciseIndex() {
            return exerciseIndex;
        }

        public void setExerciseIndex(String exerciseIndex) {
            this.exerciseIndex = exerciseIndex;
        }

        public String getHumidity() {
            return humidity;
        }

        public void setHumidity(String humidity) {
            this.humidity = humidity;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getSunset() {
            return sunset;
        }

        public void setSunset(String sunset) {
            this.sunset = sunset;
        }

        public String getSunrise() {
            return sunrise;
        }

        public void setSunrise(String sunrise) {
            this.sunrise = sunrise;
        }

        public String getTemperature() {
            return temperature;
        }

        public void setTemperature(String temperature) {
            this.temperature = temperature;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getWashIndex() {
            return washIndex;
        }

        public void setWashIndex(String washIndex) {
            this.washIndex = washIndex;
        }

        public String getWeather() {
            return weather;
        }

        public void setWeather(String weather) {
            this.weather = weather;
        }

        public String getWeek() {
            return week;
        }

        public void setWeek(String week) {
            this.week = week;
        }

        public String getWind() {
            return wind;
        }

        public void setWind(String wind) {
            this.wind = wind;
        }

        public List<FutureBean> getFuture() {
            return future;
        }

        public void setFuture(List<FutureBean> future) {
            this.future = future;
        }

        public static class FutureBean {
            /**
             * date : 2015-09-09
             * dayTime : 阵雨
             * night : 阴
             * temperature : 24°C/18°C
             * week : 星期三
             * wind : 无持续风向小于3级
             */

            /**
             * 日期
             */
            private String date;
            /**
             * 白天天气
             */
            private String dayTime;
            /**
             * 晚上天气
             */
            private String night;
            /**
             * 温度
             */
            private String temperature;
            /**
             * 星期
             */
            private String week;
            /**
             * 风向
             */
            private String wind;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getDayTime() {
                return dayTime;
            }

            public void setDayTime(String dayTime) {
                this.dayTime = dayTime;
            }

            public String getNight() {
                return night;
            }

            public void setNight(String night) {
                this.night = night;
            }

            public String getTemperature() {
                return temperature;
            }

            public void setTemperature(String temperature) {
                this.temperature = temperature;
            }

            public String getWeek() {
                return week;
            }

            public void setWeek(String week) {
                this.week = week;
            }

            public String getWind() {
                return wind;
            }

            public void setWind(String wind) {
                this.wind = wind;
            }
        }
    }
}

package com.shichen.mobapisample.weatherapi;

import java.util.List;

/**
 * 天气接口支持的城市
 * Created by Administrator on 2017/9/21.
 */

public class SupportCity {

    /**
     * msg : success
     * result : [{"city":[{"city":"合肥","district":[{"district":"合肥"},{"district":"长丰"},{"district":"肥东"},{"district":"肥西"},{"district":"巢湖"},{"district":"庐江"}]},{"city":"蚌埠","district":[{"district":"蚌埠"},{"district":"怀远"},{"district":"固镇"},{"district":"五河"}]},{"city":"芜湖","district":[{"district":"芜湖"},{"district":"繁昌"},{"district":"芜湖县"},{"district":"南陵"},{"district":"无为"}]},{"city":"淮南","district":[{"district":"淮南"},{"district":"凤台"},{"district":"潘集"}]},{"city":"马鞍山","district":[{"district":"马鞍山"},{"district":"当涂"},{"district":"含山"},{"district":"和县"}]}]}]
     */

    private String msg;
    private List<ResultBean> result;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        private List<CityBean> city;

        public List<CityBean> getCity() {
            return city;
        }

        public void setCity(List<CityBean> city) {
            this.city = city;
        }

        public static class CityBean {
            /**
             * city : 合肥
             * district : [{"district":"合肥"},{"district":"长丰"},{"district":"肥东"},{"district":"肥西"},{"district":"巢湖"},{"district":"庐江"}]
             */

            private String city;
            private List<DistrictBean> district;

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public List<DistrictBean> getDistrict() {
                return district;
            }

            public void setDistrict(List<DistrictBean> district) {
                this.district = district;
            }

            public static class DistrictBean {
                /**
                 * district : 合肥
                 */

                private String district;

                public String getDistrict() {
                    return district;
                }

                public void setDistrict(String district) {
                    this.district = district;
                }
            }
        }
    }
}

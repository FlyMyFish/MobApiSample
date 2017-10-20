package com.shichen.mobapisample.bean;

import java.util.List;

/**
 * 天气接口支持的城市
 *
 * @author shichen 754314442@qq.com
 *         Created by Administrator on 2017/9/21.
 */

public class SupportCity extends BaseResult {
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
    private List<ResultBean> result;

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

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * city : [{"city":"北京","district":[{"district":"北京"},{"district":"海淀"},{"district":"朝阳"},{"district":"顺义"},{"district":"怀柔"},{"district":"通州"},{"district":"昌平"},{"district":"延庆"},{"district":"丰台"},{"district":"石景山"},{"district":"大兴"},{"district":"房山"},{"district":"密云"},{"district":"门头沟"},{"district":"平谷"}]}]
         * province : 北京
         */
        /**
         * 省份
         */
        private String province;
        /**
         * 城市
         */
        private List<CityBean> city;

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public List<CityBean> getCity() {
            return city;
        }

        public void setCity(List<CityBean> city) {
            this.city = city;
        }

        public static class CityBean {
            /**
             * city : 北京
             * district : [{"district":"北京"},{"district":"海淀"},{"district":"朝阳"},{"district":"顺义"},{"district":"怀柔"},{"district":"通州"},{"district":"昌平"},{"district":"延庆"},{"district":"丰台"},{"district":"石景山"},{"district":"大兴"},{"district":"房山"},{"district":"密云"},{"district":"门头沟"},{"district":"平谷"}]
             */
            /**
             * 城市
             */
            private String city;
            /**
             * 区县
             */
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
                 * district : 北京 区县
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

    public List<ResultBean.CityBean> getCityListByProvince(String province) {
        for (int i = 0; i < result.size(); i++) {
            if (result.get(i).getProvince().equals(province)) {
                return result.get(i).getCity();
            }
        }
        return null;
    }

    public List<ResultBean.CityBean.DistrictBean> getDistrictListByCity(List<ResultBean.CityBean> cityList, String city) {
        for (int i = 0; i < cityList.size(); i++) {
            if (cityList.get(i).getCity().equals(city)) {
                return cityList.get(i).getDistrict();
            }
        }
        return null;
    }
}

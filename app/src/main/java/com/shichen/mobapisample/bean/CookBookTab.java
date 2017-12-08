package com.shichen.mobapisample.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shichen on 2017/12/7.
 *
 * @author shichen 754314442@qq.com
 */

public class CookBookTab extends BaseResult implements Serializable{

    /**
     * msg : success
     * result : {"categoryInfo":{"ctgId":"0010001001","name":"全部菜谱"},"childs":[{"categoryInfo":{"ctgId":"0010001002","name":"按菜品选择菜谱","parentId":"0010001001"},"childs":[{"categoryInfo":{"ctgId":"0010001007","name":"荤菜","parentId":"0010001002"}},{"categoryInfo":{"ctgId":"0010001008","name":"素菜","parentId":"0010001002"}},{"categoryInfo":{"ctgId":"0010001009","name":"汤粥","parentId":"0010001002"}},{"categoryInfo":{"ctgId":"0010001010","name":"西点","parentId":"0010001002"}},{"categoryInfo":{"ctgId":"0010001011","name":"主食","parentId":"0010001002"}},{"categoryInfo":{"ctgId":"0010001012","name":"饮品","parentId":"0010001002"}},{"categoryInfo":{"ctgId":"0010001013","name":"便当","parentId":"0010001002"}},{"categoryInfo":{"ctgId":"0010001014","name":"小吃","parentId":"0010001002"}}]},{"categoryInfo":{"ctgId":"0010001003","name":"按工艺选择菜谱","parentId":"0010001001"},"childs":[{"categoryInfo":{"ctgId":"0010001015","name":"红烧","parentId":"0010001003"}},{"categoryInfo":{"ctgId":"0010001016","name":"炒","parentId":"0010001003"}},{"categoryInfo":{"ctgId":"0010001017","name":"煎","parentId":"0010001003"}},{"categoryInfo":{"ctgId":"0010001018","name":"炸","parentId":"0010001003"}},{"categoryInfo":{"ctgId":"0010001019","name":"焖","parentId":"0010001003"}},{"categoryInfo":{"ctgId":"0010001020","name":"炖","parentId":"0010001003"}},{"categoryInfo":{"ctgId":"0010001021","name":"蒸","parentId":"0010001003"}},{"categoryInfo":{"ctgId":"0010001022","name":"烩","parentId":"0010001003"}},{"categoryInfo":{"ctgId":"0010001023","name":"熏","parentId":"0010001003"}},{"categoryInfo":{"ctgId":"0010001024","name":"腌","parentId":"0010001003"}},{"categoryInfo":{"ctgId":"0010001025","name":"煮","parentId":"0010001003"}},{"categoryInfo":{"ctgId":"0010001026","name":"炝","parentId":"0010001003"}},{"categoryInfo":{"ctgId":"0010001027","name":"卤","parentId":"0010001003"}},{"categoryInfo":{"ctgId":"0010001028","name":"拌","parentId":"0010001003"}},{"categoryInfo":{"ctgId":"0010001029","name":"烤","parentId":"0010001003"}}]},{"categoryInfo":{"ctgId":"0010001004","name":"按菜系选择菜谱","parentId":"0010001001"},"childs":[{"categoryInfo":{"ctgId":"0010001030","name":"鲁菜","parentId":"0010001004"}},{"categoryInfo":{"ctgId":"0010001031","name":"川菜","parentId":"0010001004"}},{"categoryInfo":{"ctgId":"0010001032","name":"粤菜","parentId":"0010001004"}},{"categoryInfo":{"ctgId":"0010001033","name":"闽菜","parentId":"0010001004"}},{"categoryInfo":{"ctgId":"0010001034","name":"浙菜","parentId":"0010001004"}},{"categoryInfo":{"ctgId":"0010001035","name":"湘菜","parentId":"0010001004"}},{"categoryInfo":{"ctgId":"0010001036","name":"上海菜","parentId":"0010001004"}},{"categoryInfo":{"ctgId":"0010001037","name":"徽菜","parentId":"0010001004"}},{"categoryInfo":{"ctgId":"0010001038","name":"京菜","parentId":"0010001004"}},{"categoryInfo":{"ctgId":"0010001039","name":"东北菜","parentId":"0010001004"}},{"categoryInfo":{"ctgId":"0010001040","name":"西北菜","parentId":"0010001004"}},{"categoryInfo":{"ctgId":"0010001041","name":"客家菜","parentId":"0010001004"}},{"categoryInfo":{"ctgId":"0010001042","name":"台湾美食","parentId":"0010001004"}},{"categoryInfo":{"ctgId":"0010001043","name":"泰国菜","parentId":"0010001004"}},{"categoryInfo":{"ctgId":"0010001044","name":"日本料理","parentId":"0010001004"}},{"categoryInfo":{"ctgId":"0010001045","name":"韩国料理","parentId":"0010001004"}},{"categoryInfo":{"ctgId":"0010001046","name":"西餐","parentId":"0010001004"}}]},{"categoryInfo":{"ctgId":"0010001005","name":"按人群选择菜谱","parentId":"0010001001"},"childs":[{"categoryInfo":{"ctgId":"0010001047","name":"孕妇食谱","parentId":"0010001005"}},{"categoryInfo":{"ctgId":"0010001048","name":"婴幼食谱","parentId":"0010001005"}},{"categoryInfo":{"ctgId":"0010001049","name":"儿童食谱","parentId":"0010001005"}},{"categoryInfo":{"ctgId":"0010001050","name":"懒人食谱","parentId":"0010001005"}},{"categoryInfo":{"ctgId":"0010001051","name":"宵夜","parentId":"0010001005"}},{"categoryInfo":{"ctgId":"0010001052","name":"素食","parentId":"0010001005"}},{"categoryInfo":{"ctgId":"0010001053","name":"产妇食谱","parentId":"0010001005"}},{"categoryInfo":{"ctgId":"0010001054","name":"二人世界","parentId":"0010001005"}},{"categoryInfo":{"ctgId":"0010001055","name":"下午茶","parentId":"0010001005"}}]},{"categoryInfo":{"ctgId":"0010001006","name":"按功能选择菜谱","parentId":"0010001001"},"childs":[{"categoryInfo":{"ctgId":"0010001056","name":"减肥","parentId":"0010001006"}},{"categoryInfo":{"ctgId":"0010001057","name":"便秘","parentId":"0010001006"}},{"categoryInfo":{"ctgId":"0010001058","name":"养胃","parentId":"0010001006"}},{"categoryInfo":{"ctgId":"0010001059","name":"滋阴","parentId":"0010001006"}},{"categoryInfo":{"ctgId":"0010001060","name":"补阳","parentId":"0010001006"}},{"categoryInfo":{"ctgId":"0010001061","name":"月经不调","parentId":"0010001006"}},{"categoryInfo":{"ctgId":"0010001062","name":"美容","parentId":"0010001006"}},{"categoryInfo":{"ctgId":"0010001063","name":"养生","parentId":"0010001006"}},{"categoryInfo":{"ctgId":"0010001064","name":"贫血","parentId":"0010001006"}},{"categoryInfo":{"ctgId":"0010001065","name":"润肺","parentId":"0010001006"}}]}]}
     * retCode : 200
     */

    private String msg;
    private CookBookBean result;
    private String retCode;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public CookBookBean getResult() {
        return result;
    }

    public void setResult(CookBookBean result) {
        this.result = result;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public static class CookBookBean {
        /**
         * categoryInfo : {"ctgId":"0010001001","name":"全部菜谱"}
         * childs : [{"categoryInfo":{"ctgId":"0010001002","name":"按菜品选择菜谱","parentId":"0010001001"},"childs":[{"categoryInfo":{"ctgId":"0010001007","name":"荤菜","parentId":"0010001002"}},{"categoryInfo":{"ctgId":"0010001008","name":"素菜","parentId":"0010001002"}},{"categoryInfo":{"ctgId":"0010001009","name":"汤粥","parentId":"0010001002"}},{"categoryInfo":{"ctgId":"0010001010","name":"西点","parentId":"0010001002"}},{"categoryInfo":{"ctgId":"0010001011","name":"主食","parentId":"0010001002"}},{"categoryInfo":{"ctgId":"0010001012","name":"饮品","parentId":"0010001002"}},{"categoryInfo":{"ctgId":"0010001013","name":"便当","parentId":"0010001002"}},{"categoryInfo":{"ctgId":"0010001014","name":"小吃","parentId":"0010001002"}}]},{"categoryInfo":{"ctgId":"0010001003","name":"按工艺选择菜谱","parentId":"0010001001"},"childs":[{"categoryInfo":{"ctgId":"0010001015","name":"红烧","parentId":"0010001003"}},{"categoryInfo":{"ctgId":"0010001016","name":"炒","parentId":"0010001003"}},{"categoryInfo":{"ctgId":"0010001017","name":"煎","parentId":"0010001003"}},{"categoryInfo":{"ctgId":"0010001018","name":"炸","parentId":"0010001003"}},{"categoryInfo":{"ctgId":"0010001019","name":"焖","parentId":"0010001003"}},{"categoryInfo":{"ctgId":"0010001020","name":"炖","parentId":"0010001003"}},{"categoryInfo":{"ctgId":"0010001021","name":"蒸","parentId":"0010001003"}},{"categoryInfo":{"ctgId":"0010001022","name":"烩","parentId":"0010001003"}},{"categoryInfo":{"ctgId":"0010001023","name":"熏","parentId":"0010001003"}},{"categoryInfo":{"ctgId":"0010001024","name":"腌","parentId":"0010001003"}},{"categoryInfo":{"ctgId":"0010001025","name":"煮","parentId":"0010001003"}},{"categoryInfo":{"ctgId":"0010001026","name":"炝","parentId":"0010001003"}},{"categoryInfo":{"ctgId":"0010001027","name":"卤","parentId":"0010001003"}},{"categoryInfo":{"ctgId":"0010001028","name":"拌","parentId":"0010001003"}},{"categoryInfo":{"ctgId":"0010001029","name":"烤","parentId":"0010001003"}}]},{"categoryInfo":{"ctgId":"0010001004","name":"按菜系选择菜谱","parentId":"0010001001"},"childs":[{"categoryInfo":{"ctgId":"0010001030","name":"鲁菜","parentId":"0010001004"}},{"categoryInfo":{"ctgId":"0010001031","name":"川菜","parentId":"0010001004"}},{"categoryInfo":{"ctgId":"0010001032","name":"粤菜","parentId":"0010001004"}},{"categoryInfo":{"ctgId":"0010001033","name":"闽菜","parentId":"0010001004"}},{"categoryInfo":{"ctgId":"0010001034","name":"浙菜","parentId":"0010001004"}},{"categoryInfo":{"ctgId":"0010001035","name":"湘菜","parentId":"0010001004"}},{"categoryInfo":{"ctgId":"0010001036","name":"上海菜","parentId":"0010001004"}},{"categoryInfo":{"ctgId":"0010001037","name":"徽菜","parentId":"0010001004"}},{"categoryInfo":{"ctgId":"0010001038","name":"京菜","parentId":"0010001004"}},{"categoryInfo":{"ctgId":"0010001039","name":"东北菜","parentId":"0010001004"}},{"categoryInfo":{"ctgId":"0010001040","name":"西北菜","parentId":"0010001004"}},{"categoryInfo":{"ctgId":"0010001041","name":"客家菜","parentId":"0010001004"}},{"categoryInfo":{"ctgId":"0010001042","name":"台湾美食","parentId":"0010001004"}},{"categoryInfo":{"ctgId":"0010001043","name":"泰国菜","parentId":"0010001004"}},{"categoryInfo":{"ctgId":"0010001044","name":"日本料理","parentId":"0010001004"}},{"categoryInfo":{"ctgId":"0010001045","name":"韩国料理","parentId":"0010001004"}},{"categoryInfo":{"ctgId":"0010001046","name":"西餐","parentId":"0010001004"}}]},{"categoryInfo":{"ctgId":"0010001005","name":"按人群选择菜谱","parentId":"0010001001"},"childs":[{"categoryInfo":{"ctgId":"0010001047","name":"孕妇食谱","parentId":"0010001005"}},{"categoryInfo":{"ctgId":"0010001048","name":"婴幼食谱","parentId":"0010001005"}},{"categoryInfo":{"ctgId":"0010001049","name":"儿童食谱","parentId":"0010001005"}},{"categoryInfo":{"ctgId":"0010001050","name":"懒人食谱","parentId":"0010001005"}},{"categoryInfo":{"ctgId":"0010001051","name":"宵夜","parentId":"0010001005"}},{"categoryInfo":{"ctgId":"0010001052","name":"素食","parentId":"0010001005"}},{"categoryInfo":{"ctgId":"0010001053","name":"产妇食谱","parentId":"0010001005"}},{"categoryInfo":{"ctgId":"0010001054","name":"二人世界","parentId":"0010001005"}},{"categoryInfo":{"ctgId":"0010001055","name":"下午茶","parentId":"0010001005"}}]},{"categoryInfo":{"ctgId":"0010001006","name":"按功能选择菜谱","parentId":"0010001001"},"childs":[{"categoryInfo":{"ctgId":"0010001056","name":"减肥","parentId":"0010001006"}},{"categoryInfo":{"ctgId":"0010001057","name":"便秘","parentId":"0010001006"}},{"categoryInfo":{"ctgId":"0010001058","name":"养胃","parentId":"0010001006"}},{"categoryInfo":{"ctgId":"0010001059","name":"滋阴","parentId":"0010001006"}},{"categoryInfo":{"ctgId":"0010001060","name":"补阳","parentId":"0010001006"}},{"categoryInfo":{"ctgId":"0010001061","name":"月经不调","parentId":"0010001006"}},{"categoryInfo":{"ctgId":"0010001062","name":"美容","parentId":"0010001006"}},{"categoryInfo":{"ctgId":"0010001063","name":"养生","parentId":"0010001006"}},{"categoryInfo":{"ctgId":"0010001064","name":"贫血","parentId":"0010001006"}},{"categoryInfo":{"ctgId":"0010001065","name":"润肺","parentId":"0010001006"}}]}]
         */

        private CategoryInfoBean categoryInfo;
        private List<ChildsBeanX> childs;

        public CategoryInfoBean getCategoryInfo() {
            return categoryInfo;
        }

        public void setCategoryInfo(CategoryInfoBean categoryInfo) {
            this.categoryInfo = categoryInfo;
        }

        public List<ChildsBeanX> getChilds() {
            return childs;
        }

        public void setChilds(List<ChildsBeanX> childs) {
            this.childs = childs;
        }

        public static class CategoryInfoBean {
            /**
             * ctgId : 0010001001
             * name : 全部菜谱
             */

            private String ctgId;
            private String name;

            public String getCtgId() {
                return ctgId;
            }

            public void setCtgId(String ctgId) {
                this.ctgId = ctgId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public static class ChildsBeanX extends BaseResult{
            /**
             * categoryInfo : {"ctgId":"0010001002","name":"按菜品选择菜谱","parentId":"0010001001"}
             * childs : [{"categoryInfo":{"ctgId":"0010001007","name":"荤菜","parentId":"0010001002"}},{"categoryInfo":{"ctgId":"0010001008","name":"素菜","parentId":"0010001002"}},{"categoryInfo":{"ctgId":"0010001009","name":"汤粥","parentId":"0010001002"}},{"categoryInfo":{"ctgId":"0010001010","name":"西点","parentId":"0010001002"}},{"categoryInfo":{"ctgId":"0010001011","name":"主食","parentId":"0010001002"}},{"categoryInfo":{"ctgId":"0010001012","name":"饮品","parentId":"0010001002"}},{"categoryInfo":{"ctgId":"0010001013","name":"便当","parentId":"0010001002"}},{"categoryInfo":{"ctgId":"0010001014","name":"小吃","parentId":"0010001002"}}]
             */

            private CategoryInfoBeanX categoryInfo;
            private List<ChildsBean> childs;

            public CategoryInfoBeanX getCategoryInfo() {
                return categoryInfo;
            }

            public void setCategoryInfo(CategoryInfoBeanX categoryInfo) {
                this.categoryInfo = categoryInfo;
            }

            public List<ChildsBean> getChilds() {
                return childs;
            }

            public void setChilds(List<ChildsBean> childs) {
                this.childs = childs;
            }

            public static class CategoryInfoBeanX {
                /**
                 * ctgId : 0010001002
                 * name : 按菜品选择菜谱
                 * parentId : 0010001001
                 */

                private String ctgId;
                private String name;
                private String parentId;

                public String getCtgId() {
                    return ctgId;
                }

                public void setCtgId(String ctgId) {
                    this.ctgId = ctgId;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getParentId() {
                    return parentId;
                }

                public void setParentId(String parentId) {
                    this.parentId = parentId;
                }
            }

            public static class ChildsBean extends BaseResult{
                /**
                 * categoryInfo : {"ctgId":"0010001007","name":"荤菜","parentId":"0010001002"}
                 */

                private CategoryInfoBeanXX categoryInfo;

                public CategoryInfoBeanXX getCategoryInfo() {
                    return categoryInfo;
                }

                public void setCategoryInfo(CategoryInfoBeanXX categoryInfo) {
                    this.categoryInfo = categoryInfo;
                }

                public static class CategoryInfoBeanXX {
                    /**
                     * ctgId : 0010001007
                     * name : 荤菜
                     * parentId : 0010001002
                     */

                    private String ctgId;
                    private String name;
                    private String parentId;

                    public String getCtgId() {
                        return ctgId;
                    }

                    public void setCtgId(String ctgId) {
                        this.ctgId = ctgId;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public String getParentId() {
                        return parentId;
                    }

                    public void setParentId(String parentId) {
                        this.parentId = parentId;
                    }
                }
            }
        }
    }
}

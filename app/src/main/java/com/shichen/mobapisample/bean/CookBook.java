package com.shichen.mobapisample.bean;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shichen on 2017/12/7.
 *
 * @author shichen 754314442@qq.com
 */

public class CookBook extends BaseResult implements Serializable {
    @SerializedName("msg")
    private String msg;
    @SerializedName("result")
    private ResultBean result;
    @SerializedName("retCode")
    private String retCode;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public static class ResultBean extends BaseResult {
        @SerializedName("curPage")
        private int curPage;
        @SerializedName("total")
        private int total;
        @SerializedName("list")
        private List<ListBean> list;

        public int getCurPage() {
            return curPage;
        }

        public void setCurPage(int curPage) {
            this.curPage = curPage;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean extends BaseResult implements Serializable {
            @SerializedName("ctgTitles")
            private String ctgTitles;
            @SerializedName("menuId")
            private String menuId;
            @SerializedName("name")
            private String name;
            @SerializedName("recipe")
            private RecipeBean recipe;
            @SerializedName("thumbnail")
            private String thumbnail;
            @SerializedName("ctgIds")
            private List<String> ctgIds;

            public String getCtgTitles() {
                return ctgTitles;
            }

            public void setCtgTitles(String ctgTitles) {
                this.ctgTitles = ctgTitles;
            }

            public String getMenuId() {
                return menuId;
            }

            public void setMenuId(String menuId) {
                this.menuId = menuId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public RecipeBean getRecipe() {
                return recipe;
            }

            public void setRecipe(RecipeBean recipe) {
                this.recipe = recipe;
            }

            public String getThumbnail() {
                return thumbnail;
            }

            public void setThumbnail(String thumbnail) {
                this.thumbnail = thumbnail;
            }

            public List<String> getCtgIds() {
                return ctgIds;
            }

            public void setCtgIds(List<String> ctgIds) {
                this.ctgIds = ctgIds;
            }

            public static class RecipeBean extends BaseResult {
                @SerializedName("img")
                private String img;
                @SerializedName("ingredients")
                private String ingredients;
                @SerializedName("method")
                private String method;
                @SerializedName("sumary")
                private String sumary;
                @SerializedName("title")
                private String title;

                private List<Method> step;

                public List<Method> getStep() {
                    if (step == null) {
                        step = new Gson().fromJson(method, new TypeToken<List<Method>>() {
                        }.getType());
                    }
                    return step;
                }

                public String getImg() {
                    return img;
                }

                public void setImg(String img) {
                    this.img = img;
                }

                public String getIngredients() {
                    return ingredients;
                }

                public void setIngredients(String ingredients) {
                    this.ingredients = ingredients;
                }

                public String getMethod() {
                    return method;
                }

                public void setMethod(String method) {
                    this.method = method;
                }

                public String getSumary() {
                    return sumary;
                }

                public void setSumary(String sumary) {
                    this.sumary = sumary;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }
            }

            public static class Method extends BaseResult implements Serializable {
                @SerializedName("img")
                private String img;
                @SerializedName("step")
                private String step;

                public String getImg() {
                    return img;
                }

                public void setImg(String img) {
                    this.img = img;
                }

                public String getStep() {
                    return step;
                }

                public void setStep(String step) {
                    this.step = step;
                }
            }
        }
    }
}

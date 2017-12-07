package com.shichen.mobapisample.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shichen on 2017/12/7.
 *
 * @author shichen 754314442@qq.com
 */

public class CookInfo extends BaseResult implements Serializable{

    /**
     * msg : success
     * result : {"ctgIds":["0010001007","0010001016","0010001034","0010001049","0010001063"],"ctgTitles":"荤菜,炒,浙菜,儿童食谱,养生","menuId":"00100010070000000001","name":"三色炒虾仁","recipe":{"img":"http://f2.mob.com/null/2015/08/18/1439876711867.jpg","ingredients":"[\"虾仁350g、油1大勺、盐1小勺、白酒1小勺、淀粉2小勺、黄瓜30g、胡萝卜30g、玉米粒30g\"]","method":"[{\"img\":\"http://f2.mob.com/null/2015/08/18/1439876712533.jpg\",\"step\":\"1.虾仁清洗干净，去掉虾线\"},{\"img\":\"http://f2.mob.com/null/2015/08/18/1439876713007.jpg\",\"step\":\"2.加入1小勺盐\"},{\"img\":\"http://f2.mob.com/null/2015/08/18/1439876713790.jpg\",\"step\":\"3.加入1小勺白酒拌匀，腌制15分钟。\"},{\"img\":\"http://f2.mob.com/null/2015/08/18/1439876715218.jpg\",\"step\":\"4.腌制好的虾仁加入2小勺淀粉抓拌均匀\"},{\"img\":\"http://f2.mob.com/null/2015/08/18/1439876715861.jpg\",\"step\":\"5.胡萝卜和黄瓜洗净切小丁。\"},{\"img\":\"http://f2.mob.com/null/2015/08/18/1439876716477.jpg\",\"step\":\"6.锅中加入1大勺油，油热后下锅滑炒虾仁，虾仁变色后即可捞出\"},{\"img\":\"http://f2.mob.com/null/2015/08/18/1439876717026.jpg\",\"step\":\"7.另起锅加入少许油，将胡萝卜和黄瓜丁下锅炒至断生\"},{\"img\":\"http://f2.mob.com/null/2015/08/18/1439876717929.jpg\",\"step\":\"8.加入少许盐，加入玉米粒翻炒\"},{\"img\":\"http://f2.mob.com/null/2015/08/18/1439876718385.jpg\",\"step\":\"9.然后加入事先炒好的虾仁，翻炒均匀，即可关火，一道美味的虾仁就出来啦\"}]","sumary":"鲜虾中含有丰富的钾、碘、镁、磷等矿物质及多种维生素，尤其是它特有的虾青素，是目前发现的最强的一种抗氧化剂，所以虾是对人体十分有益的食材。这是一道以虾仁为主要材料炒制的菜肴。炒虾仁因其清淡爽口，易于消化，老幼皆宜，而深受食客欢迎，它的配料可以随个人喜好而变化，做法多样，我今天就加入了玉米、胡萝卜和黄瓜，不但颜色好看，而且口感丰富，一看就让人食欲大开，还能摄取多种营养。","title":"怎样做一道好吃的三色炒虾仁"},"thumbnail":"http://f2.mob.com/null/2015/08/18/1439876700896.jpg"}
     * retCode : 200
     */

    private String msg;
    private ResultBean result;
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

    public static class ResultBean {
        /**
         * ctgIds : ["0010001007","0010001016","0010001034","0010001049","0010001063"]
         * ctgTitles : 荤菜,炒,浙菜,儿童食谱,养生
         * menuId : 00100010070000000001
         * name : 三色炒虾仁
         * recipe : {"img":"http://f2.mob.com/null/2015/08/18/1439876711867.jpg","ingredients":"[\"虾仁350g、油1大勺、盐1小勺、白酒1小勺、淀粉2小勺、黄瓜30g、胡萝卜30g、玉米粒30g\"]","method":"[{\"img\":\"http://f2.mob.com/null/2015/08/18/1439876712533.jpg\",\"step\":\"1.虾仁清洗干净，去掉虾线\"},{\"img\":\"http://f2.mob.com/null/2015/08/18/1439876713007.jpg\",\"step\":\"2.加入1小勺盐\"},{\"img\":\"http://f2.mob.com/null/2015/08/18/1439876713790.jpg\",\"step\":\"3.加入1小勺白酒拌匀，腌制15分钟。\"},{\"img\":\"http://f2.mob.com/null/2015/08/18/1439876715218.jpg\",\"step\":\"4.腌制好的虾仁加入2小勺淀粉抓拌均匀\"},{\"img\":\"http://f2.mob.com/null/2015/08/18/1439876715861.jpg\",\"step\":\"5.胡萝卜和黄瓜洗净切小丁。\"},{\"img\":\"http://f2.mob.com/null/2015/08/18/1439876716477.jpg\",\"step\":\"6.锅中加入1大勺油，油热后下锅滑炒虾仁，虾仁变色后即可捞出\"},{\"img\":\"http://f2.mob.com/null/2015/08/18/1439876717026.jpg\",\"step\":\"7.另起锅加入少许油，将胡萝卜和黄瓜丁下锅炒至断生\"},{\"img\":\"http://f2.mob.com/null/2015/08/18/1439876717929.jpg\",\"step\":\"8.加入少许盐，加入玉米粒翻炒\"},{\"img\":\"http://f2.mob.com/null/2015/08/18/1439876718385.jpg\",\"step\":\"9.然后加入事先炒好的虾仁，翻炒均匀，即可关火，一道美味的虾仁就出来啦\"}]","sumary":"鲜虾中含有丰富的钾、碘、镁、磷等矿物质及多种维生素，尤其是它特有的虾青素，是目前发现的最强的一种抗氧化剂，所以虾是对人体十分有益的食材。这是一道以虾仁为主要材料炒制的菜肴。炒虾仁因其清淡爽口，易于消化，老幼皆宜，而深受食客欢迎，它的配料可以随个人喜好而变化，做法多样，我今天就加入了玉米、胡萝卜和黄瓜，不但颜色好看，而且口感丰富，一看就让人食欲大开，还能摄取多种营养。","title":"怎样做一道好吃的三色炒虾仁"}
         * thumbnail : http://f2.mob.com/null/2015/08/18/1439876700896.jpg
         */

        private String ctgTitles;
        private String menuId;
        private String name;
        private RecipeBean recipe;
        private String thumbnail;
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

        public static class RecipeBean {
            /**
             * img : http://f2.mob.com/null/2015/08/18/1439876711867.jpg
             * ingredients : ["虾仁350g、油1大勺、盐1小勺、白酒1小勺、淀粉2小勺、黄瓜30g、胡萝卜30g、玉米粒30g"]
             * method : [{"img":"http://f2.mob.com/null/2015/08/18/1439876712533.jpg","step":"1.虾仁清洗干净，去掉虾线"},{"img":"http://f2.mob.com/null/2015/08/18/1439876713007.jpg","step":"2.加入1小勺盐"},{"img":"http://f2.mob.com/null/2015/08/18/1439876713790.jpg","step":"3.加入1小勺白酒拌匀，腌制15分钟。"},{"img":"http://f2.mob.com/null/2015/08/18/1439876715218.jpg","step":"4.腌制好的虾仁加入2小勺淀粉抓拌均匀"},{"img":"http://f2.mob.com/null/2015/08/18/1439876715861.jpg","step":"5.胡萝卜和黄瓜洗净切小丁。"},{"img":"http://f2.mob.com/null/2015/08/18/1439876716477.jpg","step":"6.锅中加入1大勺油，油热后下锅滑炒虾仁，虾仁变色后即可捞出"},{"img":"http://f2.mob.com/null/2015/08/18/1439876717026.jpg","step":"7.另起锅加入少许油，将胡萝卜和黄瓜丁下锅炒至断生"},{"img":"http://f2.mob.com/null/2015/08/18/1439876717929.jpg","step":"8.加入少许盐，加入玉米粒翻炒"},{"img":"http://f2.mob.com/null/2015/08/18/1439876718385.jpg","step":"9.然后加入事先炒好的虾仁，翻炒均匀，即可关火，一道美味的虾仁就出来啦"}]
             * sumary : 鲜虾中含有丰富的钾、碘、镁、磷等矿物质及多种维生素，尤其是它特有的虾青素，是目前发现的最强的一种抗氧化剂，所以虾是对人体十分有益的食材。这是一道以虾仁为主要材料炒制的菜肴。炒虾仁因其清淡爽口，易于消化，老幼皆宜，而深受食客欢迎，它的配料可以随个人喜好而变化，做法多样，我今天就加入了玉米、胡萝卜和黄瓜，不但颜色好看，而且口感丰富，一看就让人食欲大开，还能摄取多种营养。
             * title : 怎样做一道好吃的三色炒虾仁
             */

            private String img;
            private String ingredients;
            private String method;
            private String sumary;
            private String title;

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
    }
}

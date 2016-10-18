package com.lnyp.joke.pengfu;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * 笑话工具类
 */
public class JokeUtil {

    public List<JokeBean> getNewJokelist(Document doc) {

        //class等于list-item的div标签
        Elements list_item_elements = doc.select("div.list-item");

        List<JokeBean> jokeBeanList = new ArrayList<>();

        if (list_item_elements.size() > 0) {

            for (int i = 0; i < list_item_elements.size(); i++) {

                JokeBean jokeBean = new JokeBean();


                Element list_item_element = list_item_elements.get(i);

                Elements head_name_elements = list_item_element.select("div.head-name");

                if (head_name_elements.size() > 0) {
                    Element head_name_element = head_name_elements.first();
                    if (head_name_element != null) {
                        String userAvatar = head_name_element.select("img").first().attr("src");
                        String userName = head_name_element.select("img").first().attr("alt");
                        String title = head_name_element.select("a[href]").get(1).text(); //带有href属性的a元素
                        String lastTime = head_name_element.getElementsByClass("dp-i-b").first().text(); //带有href属性的a元素

                        String shareUrl = head_name_element.select("a[href]").get(1).attr("href");

                        jokeBean.setUserName(userName);
                        jokeBean.setUserAvatar(userAvatar);
                        jokeBean.setTitle(title);
                        jokeBean.setLastTime(lastTime);

                        jokeBean.setShareUrl(shareUrl);
                    }
                }

                Element con_img_elements = list_item_element.select("div").get(2);
                if (con_img_elements != null) {
                    if (con_img_elements.select("img") != null) {

                        Element img_element = con_img_elements.select("img").first();

                        JokeBean.DataBean dataBean = new JokeBean.DataBean();

                        if (img_element != null) {
                            String showImg = img_element.attr("src");
                            String gifsrcImg = img_element.attr("gifsrc");
                            String width = img_element.attr("width");
                            String height = img_element.attr("height");

                            dataBean.setShowImg(showImg);
                            dataBean.setGifsrcImg(gifsrcImg);
                            dataBean.setWidth(width);
                            dataBean.setHeight(height);

                        } else {
                            String content = con_img_elements.text().replaceAll(" ", "\n");
                            dataBean.setContent(content);
                        }

                        jokeBean.setDataBean(dataBean);

                    }
                }

                Element tagwrap_clearfix_elements = list_item_element.select("div").get(3);
                if (tagwrap_clearfix_elements != null) {

                    Elements clearfixs = tagwrap_clearfix_elements.select("a[href]"); //带有href属性的a元素

                    List<String> tags = new ArrayList<>();

                    for (int j = 0; j < clearfixs.size(); j++) {

                        String tag = clearfixs.get(j) != null ? clearfixs.get(j).text() : "";
                        tags.add(tag);
                    }
                    jokeBean.setTags(tags);
                }

                jokeBeanList.add(jokeBean);

//                for (JokeBean jokeBean1 : jokeBeanList) {
//                    System.out.println(jokeBean1.getDataBean());
//                }
            }
        }

        return jokeBeanList;

    }
}

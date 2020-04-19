package com.zscat.mallplus;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zscat.mallplus.utils.ValidatorUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Demo2 {

    public static void main(String[] args) {

        // 需要爬的网页的文章列表
        String url = "https://www.toutiao.com/ch/news_tech/";
        //文章详情页的前缀(由于今日头条的文章都是在group这个目录下,所以定义了前缀,而且通过请求获取到的html页面)
        String url2 = "http://www.toutiao.com/group/";
        //链接到该网站
        Connection connection = Jsoup.connect(url);
        Document content = null;
        try {
            //获取内容
            content = connection.get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //转换成字符串
        String htmlStr = content.html();
        //因为今日头条的文章展示比较奇葩,都是通过js定义成变量,所以无法使用获取dom元素的方式获取值
        String jsonStr = StringUtils.substringBetween(htmlStr, "var _data = ", ";");

        Map parse = (Map) JSONObject.parse(jsonStr);
        JSONArray parseArray = (JSONArray) parse.get("real_time_news");
        System.out.println(parseArray);
        Map map = null;
        List<Map> maps = new ArrayList<>();
        //遍历这个jsonArray,获取到每一个json对象,然后将其转换成Map对象(在这里其实只需要一个group_id,那么没必要使用map)
        for (int i = 0; i < parseArray.size(); i++) {
            map = (Map) parseArray.get(i);
            maps.add((Map) parseArray.get(i));
        }
        //遍历之前获取到的map集合,然后分别访问这些文章详情页
        for (Map map2 : maps) {
            connection = Jsoup.connect(url2 + map2.get("group_id"));
            System.out.println(url2 + map2.get("group_id"));
            try {
                Document document = connection.get();
                System.out.println(document.html());
                String detailStr = StringUtils.substringBetween(document.html(), "articleInfo: ", ";");
                if (ValidatorUtils.empty(detailStr)) {
                    continue;
                }
                System.out.println(detailStr);

                Map parse1 = (Map) net.sf.json.JSONObject.fromObject(detailStr);
                System.out.println(parse1);
                if (ValidatorUtils.empty(parse1)) {
                    continue;
                }
                JSONArray parseArray1 = (JSONArray) parse1.get("articleInfo");
                System.out.println(parseArray1);
                //获取文章标题
                Elements title = document.select("[class=article-title]");
                System.out.println("tt=" + title);
                System.out.println("tt=" + title.outerHtml());
                //获取文章来源和文章发布时间
                Elements articleInfo = document.select("[class=articleInfo]");
                Elements src = articleInfo.select("[class=src]");
                System.out.println(src.html());
                Elements time = articleInfo.select("[class=time]");
                System.out.println(time.html());
                //获取文章内容
                Elements contentEle = document.select("[class=article-content]");
                System.out.println(contentEle.html());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

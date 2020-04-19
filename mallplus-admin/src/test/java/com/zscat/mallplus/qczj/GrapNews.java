package com.zscat.mallplus.qczj;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TODO
 * 2017年5月21日上午12:25:30
 */
public class GrapNews {

    public static boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

    /**
     * 从笑话集抓取笑话
     *
     * @param size
     * @param baseUrl
     * @param domainName
     * @param newsListClassOrId
     * @param newsULIndex
     * @param newsContentClassOrId
     * @param titleTagOrClass
     * @param dateTag
     * @return
     */
    public static ArrayList<News> getNewsFromJokeji(int size, String baseUrl, String domainName,
                                                    String newsListClassOrId, int newsULIndex,
                                                    String newsContentClassOrId, String titleTagOrClass, String dateTag) {
        ArrayList<News> newsList = new ArrayList<News>();
        Document doc;
        Element element = null;
        Element title = null;
        News news = null;
        try {
            doc = Jsoup.connect(baseUrl).timeout(10000).get();
            element = (Element) doc.getElementsByClass(newsListClassOrId).first();
            System.out.println(element);
            Elements elements = element.getElementsByTag("li");
            if (elements != null && elements.size() > 0) {
                for (Element ele : elements) {
                    news = new News();
                    title = ele.select("a").first();
                    if (title == null) {
                        continue;
                    }
                    news.setTitle(title.getElementsByTag(titleTagOrClass).text());
                    if (news.getTitle() == null || news.getTitle().equals("")) {
                        continue;
                    }
                    news.setHref(domainName + title.attr("href"));
                    if (dateTag != null) {
                        String date = ele.select("i").text();
                        news.setDate(date);
                    }
                    String newsUrl = news.getHref();
                    if (isContainChinese(news.getHref())) {
                        newsUrl = URLEncoder.encode(news.getHref(), "utf-8")
                                .toLowerCase().replace("%3a", ":").replace("%2f", "/");
                    }
                    Document newsDoc = Jsoup.connect(newsUrl).timeout(10000).get();
                    String text = newsDoc.getElementById(newsContentClassOrId).html();
                    text = deleteImg(text);
                    text = deleteA(text);
                    StringBuffer textBuffer = new StringBuffer(5);
                    textBuffer.append("<!DOCTYPE html><html><head><meta name=\"content-type\" content=\"text/html; charset=UTF-8\">");
                    textBuffer.append("</head><body>");
                    textBuffer.append(deleteSource(text));
                    textBuffer.append("</body></html>");
                    news.setContent(textBuffer.toString());
                    news.setContent(textBuffer.toString());
                    System.out.println("标题=====" + news.getTitle());
                    System.out.println("href=====" + news.getHref());
                    System.out.println("content=====" + news.getContent());
                    newsList.add(news);
                    if (newsList.size() == size) {
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newsList;
    }

    /**
     * 从汽车之家抓新闻
     *
     * @param size
     * @param baseUrl
     * @param domainName
     * @param newsListId
     * @param newsContentClass
     * @param dateTag
     * @param needDeleteAlt
     * @return
     */
    public static ArrayList<News> getNewsFromCarHome(int size, String baseUrl, String domainName, String newsListId,
                                                     String newsContentClass, String titleTag, String dateTag, String needDeleteAlt) {
        ArrayList<News> newsList = new ArrayList<News>();
        Document doc;
        Elements elements = null;
        Element title = null;
        News news = null;
        try {
            doc = Jsoup.connect(baseUrl).timeout(10000).get();
            elements = (Elements) doc.getElementById(newsListId).children();
            if (elements != null && elements.size() > 0) {
                for (Element ele : elements) {
                    news = new News();
                    title = ele.select("a").first();
                    if (title == null) {
                        continue;
                    }
                    news.setTitle(title.getElementsByTag(titleTag).text());
                    if (news.getTitle() == null || news.getTitle().equals("")) {
                        continue;
                    }
                    news.setHref(domainName + title.attr("href"));
                    if (dateTag != null) {
                        String date = ele.select("i").text();
                        news.setDate(date);
                    }
                    String newsUrl = news.getHref();
                    if (isContainChinese(news.getHref())) {
                        newsUrl = URLEncoder.encode(news.getHref(), "utf-8")
                                .toLowerCase().replace("%3a", ":").replace("%2f", "/");
                    }
                    Document newsDoc = Jsoup.connect(newsUrl).timeout(10000).get();
                    String text = newsDoc.getElementsByClass(newsContentClass).html();
                    if (text.indexOf("余下全文") > 0 || text.indexOf("未经许可") > 0
                            || text.indexOf("禁止转载") > 0 || text.indexOf("公众号") > 0 || text.indexOf("公众账号") > 0) {
                        continue;
                    }
                    text = replaceImgSrcFromDataSrc(text, true, needDeleteAlt);
                    int index = text.lastIndexOf("（");
                    if (index > 0) {
                        text = text.substring(0, index);
                    }
                    StringBuffer textBuffer = new StringBuffer(5);
                    textBuffer.append("<!DOCTYPE html><html><head><meta name=\"content-type\" content=\"text/html; charset=UTF-8\">");
                    textBuffer.append("</head><body>");
                    textBuffer.append(deleteSource(text));
                    textBuffer.append("</body></html>");
                    news.setContent(textBuffer.toString());
                    news.setContent(textBuffer.toString());
                    System.out.println("标题=====" + news.getTitle());
                    System.out.println("href=====" + news.getHref());
                    System.out.println("content=====" + news.getContent());
                    newsList.add(news);
                    if (newsList.size() == size) {
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newsList;
    }


    public static String getVideoFromMiaoPai(String baseUrl) throws Exception {
        Document doc = Jsoup.connect(baseUrl).timeout(10000).get();
        String html = doc.html().trim();
        return getUrlFromMiaoPaiHtml(html);
    }

    public static String getUrlFromMiaoPaiHtml(String html) {
        int startIndex = html.indexOf("videoSrc");
        int endIndex = html.indexOf("poster");
        String videoUrl = html.substring(startIndex + 11, endIndex + 5);
        int index = videoUrl.indexOf('"');
        if (index > 0) {
            return videoUrl.substring(0, index);
        }
        return videoUrl;
    }

    public static String getVideoPhotoFromMiaoPaiHtml(String html) {
        System.out.println(html);
        int startIndex = html.indexOf("poster");
        int index = html.substring(startIndex).indexOf("jpg");
        return html.substring(startIndex + 9, startIndex + index + 3);
    }

    public static void main(String[] args) throws Exception {
        getNewsFromCarHome(2, "http://m.autohome.com.cn/channel", "http://m.autohome.com.cn", "list", "details", "h4", "time", "汽车之家");
        getNewsFromJokeji(3, "http://www.jokeji.cn/list.htm", "http://www.jokeji.cn", "list_title", 1, "text110", "a", "i");
        getNewsFromSouHu(20, "http://m.sohu.com/c/1592/", "a", null, null);
    }

    /**
     * 从秒拍抓视频
     *
     * @param size
     * @param baseUrl
     * @return
     */
    public static ArrayList<News> getVideoFromMiaopai(int size, String baseUrl) {
        ArrayList<News> newsList = new ArrayList<News>();
        try {
            News news = null;
            Element videoEmement = null;
            Document doc = null;
            String videoUrl = null;
            doc = Jsoup.connect(baseUrl).timeout(10000).get();
            Elements elements = doc.getElementsByClass("videoCont");
            String videoDetailUrl = "";
            if (elements != null && elements.size() > 0) {
                for (Element ele : elements) {
                    videoEmement = ele.getElementsByClass("MIAOPAI_player").first();
                    String videoId = videoEmement.attr("data-scid").toString();
                    String videoPhotoUrl = videoEmement.attr("data-img").toString();
                    String videoTitle = ele.getElementsByClass("viedoAbout").first().getElementsByTag("p").text();
                    System.out.println("视频id" + videoId);
                    System.out.println("视频封面url" + videoPhotoUrl);
                    System.out.println("视频标题" + videoTitle);
                    news = new News();
                    if (videoId != null) {
                        news.setTitle(videoTitle);
                        videoDetailUrl = "http://www.miaopai.com/show/" + videoId + ".html";
                        doc = Jsoup.connect("http://www.miaopai.com/show/" + videoId + ".html").timeout(10000).get();
                        System.out.println("视频详情url========" + videoDetailUrl);
                        news.setHref("http://m.miaopai.com/show/" + videoId);
                        news.setPhotoUrl(videoPhotoUrl);
                    }
                    if (doc != null) {
                        videoUrl = getUrlFromMiaoPaiHtml(doc.html());
                    }
                    if (videoUrl != null) {
                        news.setContent(createVideoHtml(videoUrl, videoPhotoUrl));
                        System.out.println("视频url=====" + videoUrl);
                        System.out.println("视频html======" + news.getContent());
                        newsList.add(news);
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newsList;
    }

    public static String createVideoHtml(String videoUrl, String videoPhotoUrl) {
        Document doc;
        StringBuffer textBuffer = new StringBuffer(5);
        textBuffer.append("<!DOCTYPE html><html><head><meta name=\"content-type\" content=\"text/html; charset=UTF-8\">");
        textBuffer.append("</head><body>");
        textBuffer.append("<div align=\"center\">");
        textBuffer.append(" <video></video> </div>");
        textBuffer.append("</body></html>");
        doc = Jsoup.parse(textBuffer.toString());
        doc.getElementsByTag("body").attr("style", "height:400px;");
        doc.getElementsByTag("video").attr("style", "width:100%;max-height:400px;")
                .attr("poster", videoPhotoUrl).attr("autoplay", "autoplay")
                .attr("controls", "controls").attr("src", videoUrl);
        return doc.toString();
    }

    /**
     * 从搜狐抓新闻
     *
     * @param size
     * @param baseUrl
     * @param dateTag
     * @param needDeleteAlt
     * @return
     */
    public static ArrayList<News> getNewsFromSouHu(int size, String baseUrl,
                                                   String titleTag, String dateTag, String needDeleteAlt) {
        ArrayList<News> newsList = new ArrayList<News>();
        Document doc;
        Element element = null;
        Element title = null;
        News news = null;
        try {
            doc = Jsoup.connect(baseUrl).timeout(10000).get();
            element = doc.getElementsByTag("section").get(2);
            element = element.getElementsByClass("headlines").get(0);
            Elements elements = element.children();
            if (elements != null && elements.size() > 0) {
                for (Element ele : elements) {
                    news = new News();
                    title = ele.select("a").first();
                    if (title == null) {
                        continue;
                    }
                    news.setTitle(title.getElementsByTag(titleTag).text());
                    if (news.getTitle() == null || news.getTitle().equals("")
                            || news.getTitle().indexOf("广告") > 0 || news.getTitle().indexOf("视频") > 0) {
                        continue;
                    }
                    news.setHref("https://m.sohu.com" + title.attr("href"));
                    if (dateTag != null) {
                        String dateStr = ele.select(dateTag).first().text();
                        news.setDate(dateStr);
                    }
                    String newsUrl = news.getHref();
                    if (isContainChinese(news.getHref())) {
                        newsUrl = URLEncoder.encode(news.getHref(), "utf-8")
                                .toLowerCase().replace("%3a", ":").replace("%2f", "/");
                    }
                    Document newsDoc = Jsoup.connect(newsUrl).timeout(10000).get();
                    String text = newsDoc.getElementsByTag("article").html();
                    if (text.indexOf("未经许可") > 0 || text.indexOf("禁止转载") > 0
                            || text.indexOf("公众号") > 0 || text.indexOf("公众账号") > 0) {
                        continue;
                    }
                    int index = text.indexOf("<p class=\"para\">");
                    int lastIndex = text.indexOf("<div class=\"expend-wp\"> ");
                    if (lastIndex > 0) {
                        text = text.substring(index, lastIndex);
                    } else if (index > 0) {
                        text = text.substring(index, text.length());
                    }
                    text = replaceImgSrcFromDataSrc(text, true, null);
                    if (text == null || text.length() == 0) {
                        continue;
                    }
                    StringBuffer textBuffer = new StringBuffer(5);
                    textBuffer.append("<!DOCTYPE html><html><head>"
                            + "<meta name=\"content-type\" content=\"text/html; charset=UTF-8\">");
                    textBuffer.append("</head><body>");
                    textBuffer.append(deleteSource(text));
                    textBuffer.append("</body></html>");
                    news.setContent(textBuffer.toString());
                    news.setContent(textBuffer.toString());
                    System.out.println("标题=====" + news.getTitle());
                    System.out.println("href=====" + news.getHref());
                    System.out.println("content=====" + news.getContent());
                    newsList.add(news);
                    if (newsList.size() == size) {
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newsList;
    }

    private static String deleteImg(String text) {
        return text.replaceAll("<img [^>]*>", "");
    }

    private static String deleteA(String text) {
        return text.replaceAll("<a[^>]*>(.*?)</a>", "");
    }

    private static String deleteSource(String text) {
        return text.replaceAll("\\(.*?\\)|\\[.*?]", "");
    }

    /**
     * 删除a标签中的href
     *
     * @param content
     * @return
     */
    public static String removeHref(String content) {
        Document document = Jsoup.parse(content);
        Elements elements = document.select("a[href]");
        for (Element el : elements) {
            el.removeAttr("href");
        }
        return document.html();
    }


    /**
     * 将htmlBody中所有img标签中的src内容替换为原data-src的内容, <br/>
     * 如果不报含data-src,则src的内容不会被替换 <br/>
     *
     * @param htmlBody                    html内容
     * @param needDeleteAlt               需要剔除的图片的alt信息
     * @param imgUrlNeedAddProtocolPrefix 图片的url是否需要添加http协议前缀
     * @return 返回替换后的内容
     */
    public static String replaceImgSrcFromDataSrc(String htmlBody,
                                                  boolean imgUrlNeedAddProtocolPrefix, String needDeleteAlt) {
        Document document = Jsoup.parseBodyFragment(htmlBody);
        List<Element> nodes = document.select("img");
        int nodeLenth = nodes.size();
        if (nodeLenth == 0) {
            return htmlBody;
        }
        for (int i = 0; i < nodeLenth; i++) {
            Element e = nodes.get(i);
            String dataSrc = e.attr("data-src");
            if (isNotBlank(dataSrc)) {
                e.attr("src", dataSrc);
                e.removeAttr("data-src");
            }
            String originalSrc = e.attr("original");
            if (isNotBlank(originalSrc)) {
                e.attr("src", "http:" + originalSrc);
                e.removeAttr("originalSrc");
            }
            String originalHiddenSrc = e.attr("original-hidden");
            if (isNotBlank(originalHiddenSrc)) {
                e.attr("src", "http:" + originalHiddenSrc);
                e.removeAttr("original-hidden");
            }
        }
        if (htmlBody.contains("<html>")) {
            if (needDeleteAlt == null && !imgUrlNeedAddProtocolPrefix) {
                return document.toString();
            } else if (needDeleteAlt == null && imgUrlNeedAddProtocolPrefix) {
                return document.toString().replace("src=\"//", "src=\"http://");
            } else if (needDeleteAlt != null && imgUrlNeedAddProtocolPrefix) {
                return document.toString().replace("src=\"//", "src=\"http://")
                        .replace("alt=" + needDeleteAlt, "");
            }
            return document.toString().replace("alt=" + needDeleteAlt, "");
        } else {
            if (needDeleteAlt == null && !imgUrlNeedAddProtocolPrefix) {
                return document.select("body>*").toString();
            } else if (needDeleteAlt == null && imgUrlNeedAddProtocolPrefix) {
                return document.select("body>*").toString().replace("src=\"//", "src=\"http://");
            } else if (needDeleteAlt != null && imgUrlNeedAddProtocolPrefix) {
                return document.select("body>*").
                        toString().replace("src=\"//", "src=\"http://").replace("alt=" + needDeleteAlt, "");
            }
            return document.select("body>*").toString().replace("alt=" + needDeleteAlt, "");
        }

    }


    private static boolean isNotBlank(String str) {
        if (str == null)
            return false;
        else if (str.trim().length() == 0)
            return false;
        else
            return true;
    }
}

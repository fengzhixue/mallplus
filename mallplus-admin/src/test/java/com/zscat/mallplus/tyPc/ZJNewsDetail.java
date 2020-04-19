package com.zscat.mallplus.tyPc;

import com.geccocrawler.gecco.annotation.Gecco;
import com.geccocrawler.gecco.annotation.HtmlField;
import com.geccocrawler.gecco.annotation.Text;
import com.geccocrawler.gecco.spider.HtmlBean;

@Gecco(matchUrl = "http://zj.zjol.com.cn/news/[code].html", pipelines = "zjNewsDetailPipeline")
public class ZJNewsDetail implements HtmlBean {

    @Text
    @HtmlField(cssPath = "#headline")
    private String title;

    @Text
    @HtmlField(cssPath = "#content > div > div.news_con > div.news-content > div:nth-child(1) > div > p.go-left.post-time.c-gray")
    private String createTime;
}

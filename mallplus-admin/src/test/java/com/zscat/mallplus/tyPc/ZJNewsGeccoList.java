package com.zscat.mallplus.tyPc;

import com.geccocrawler.gecco.annotation.Gecco;
import com.geccocrawler.gecco.annotation.HtmlField;
import com.geccocrawler.gecco.annotation.Request;
import com.geccocrawler.gecco.annotation.RequestParameter;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.spider.HrefBean;
import com.geccocrawler.gecco.spider.HtmlBean;
import lombok.Data;

import java.util.List;

@Data
@Gecco(matchUrl = "http://zj.zjol.com.cn/home.html?pageIndex={pageIndex}&pageSize={pageSize}", pipelines = "zJNewsListPipelines")
public class ZJNewsGeccoList implements HtmlBean {
    @Request
    private HttpRequest request;
    @RequestParameter
    private int pageIndex;
    @RequestParameter
    private int pageSize;
    @HtmlField(cssPath = "#content > div > div > div.con_index > div.r.main_mod > div > ul > li > dl > dt > a")
    private List<HrefBean> newList;
}

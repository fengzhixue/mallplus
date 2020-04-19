package com.zscat.mallplus.tyPc;

import com.geccocrawler.gecco.annotation.PipelineName;
import com.geccocrawler.gecco.pipeline.Pipeline;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.scheduler.SchedulerContext;
import com.geccocrawler.gecco.spider.HrefBean;

@PipelineName("zJNewsListPipelines")
public class ZJNewsListPipelines implements Pipeline<ZJNewsGeccoList> {
    public void process(ZJNewsGeccoList zjNewsGeccoList) {
        HttpRequest request = zjNewsGeccoList.getRequest();
        for (HrefBean bean : zjNewsGeccoList.getNewList()) {
            //进入祥情页面抓取
            SchedulerContext.into(request.subRequest("http://zj.zjol.com.cn" + bean.getUrl()));
        }
        int page = zjNewsGeccoList.getPageIndex() + 1;
        String nextUrl = "http://zj.zjol.com.cn/home.html?pageIndex=" + page + "&pageSize=100";
        //抓取下一页
        SchedulerContext.into(request.subRequest(nextUrl));
    }
}

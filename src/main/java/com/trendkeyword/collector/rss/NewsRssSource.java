package com.trendkeyword.collector.rss;

public enum NewsRssSource {
    YNA_ALL("연합뉴스 전체", "https://www.yna.co.kr/rss/news.xml"),
    JTBC_BREAKING("JTBC 속보", "https://news-ex.jtbc.co.kr/v1/get/rss/newsflesh"),
    JTBC_ISSUE("JTBC 이슈", "https://news-ex.jtbc.co.kr/v1/get/rss/issue"),
    SBS("SBS", "https://news.sbs.co.kr/news/SectionRssFeed.do?sectionId=01"),
    DONGA("동아일보", "https://rss.donga.com/total.xml"),
    CHOSUN("조선일보", "https://www.chosun.com/arc/outboundfeeds/rss/?outputType=xml"),
    KHAN("경향신문", "https://www.khan.co.kr/rss/rssdata/total_news.xml");

    private final String name;
    private final String url;

    NewsRssSource(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}

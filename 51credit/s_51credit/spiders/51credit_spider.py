#coding=utf-8
import scrapy
import time
from s_51credit.items import S51CreditItem

class S51creditSpider(scrapy.Spider):
    name = "51credit"

    def start_requests(self):
        
        #卡神
        #base_url ="https://bbs.51credit.com/forum.php?mod=forumdisplay&fid=137&filter=dateline&orderby=dateline&dateline=2592000&page="

        #招行
        #base_url ="https://bbs.51credit.com/forum.php?mod=forumdisplay&fid=8&filter=dateline&orderby=dateline&dateline=2592000&page="
        base_url ="https://bbs.51credit.com/forum-8-"
        #浦发
        #base_url ="https://bbs.51credit.com/forum.php?mod=forumdisplay&fid=13&filter=dateline&orderby=dateline&dateline=2592000&page="

        for i in range(1,500):
            url = base_url + str(i) + ".html"
            yield scrapy.Request(url, callback=self.parse)

    def parse(self, response):
        item = S51CreditItem()
        for content in response.xpath("//tbody[contains(@id,'normalthread')]"):
            
            title = content.xpath("tr/th/a[@class='s xst']/text()").extract()[0]
            
            url = content.xpath("tr/th/a[@class='s xst']/@href").extract()[0]

            if len(content.xpath("tr/td[@class='by']")[0].xpath("em/span/span/@title").extract()) != 0:
                post_time = content.xpath("tr/td[@class='by']")[0].xpath("em/span/span/@title").extract()[0]
            else:
                 post_time = content.xpath("tr/td[@class='by']")[0].xpath("em/span/text()").extract()[0]

            reply_cnt = content.xpath("tr/td[@class='num']/a/text()").extract()[0]

            '''time = comment.xpath("div[@class='comment_conBox']/div[@class='comment_avatar_time ']/div[@class='time']/text()").extract()[0]
            content = content.xpath("div[@class='comment_conBox']/div[@class='comment_conWrap']/div/p/span[@itemprop='description']/text()").extract()[0]
            content_src_list = content.xpath("div[@class='comment_conBox']/div[@class='blockquote_wrap']/blockquote/div[@class='comment_conWrap']/div[@class='comment_con']/p/span/text()").extract()
            author_src_list = content.xpath("div[@class='comment_conBox']/div[@class='blockquote_wrap']/blockquote/div[@class='comment_conWrap']/div[@class='comment_con']/a/span/text()").extract()
            floor = comment.xpath("div[@class='comment_avatar']/span/text()").extract()[0]
            '''

            item['title'] = title
            item['url'] = "https://bbs.51credit.com/" + url
            item['post_time'] = post_time
            item['reply_cnt'] = reply_cnt
            
            #if "AE白" in title :
            #    yield item
            
            yield item

        #url = response.xpath("//li[@class='pagedown']/a/@href").extract()[0]
        #if url :
        #    yield scrapy.Request(url, callback=self.parse)
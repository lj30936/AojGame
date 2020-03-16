#coding=utf-8
import scrapy
from smzdm.items import SmzdmItem

class smzdmSpider(scrapy.Spider):
    name = "smzdm"
    start_urls = [
        "https://post.smzdm.com/p/398266/"
    ]

    def parse(self, response):
        
        item = SmzdmItem()
        for comment in response.xpath("//div[@id='commentTabBlockNew']//li[@class='comment_list']"):

            author = comment.xpath("div[@class='comment_conBox']/div[@class='comment_avatar_time ']/a[@class='a_underline user_name']/span/text()").extract()[0]
            time = comment.xpath("div[@class='comment_conBox']/div[@class='comment_avatar_time ']/div[@class='time']/text()").extract()[0]
            content = comment.xpath("div[@class='comment_conBox']/div[@class='comment_conWrap']/div/p/span[@itemprop='description']/text()").extract()[0]
            content_src_list = comment.xpath("div[@class='comment_conBox']/div[@class='blockquote_wrap']/blockquote/div[@class='comment_conWrap']/div[@class='comment_con']/p/span/text()").extract()
            author_src_list = comment.xpath("div[@class='comment_conBox']/div[@class='blockquote_wrap']/blockquote/div[@class='comment_conWrap']/div[@class='comment_con']/a/span/text()").extract()
            floor = comment.xpath("div[@class='comment_avatar']/span/text()").extract()[0]
            
            content_src = ''
            author_src = ''

            if ( len(content_src_list) > 0):
                content_src = content_src_list[-1]
            if ( len(author_src_list) > 0):
                author_src = author_src_list[-1]

            item['author_src'] = author_src
            item['author'] = author
            item['time'] = time
            item['floor'] = floor
            item['content'] = content
            item['content_src'] = content_src
            yield item

        url = response.xpath("//li[@class='pagedown']/a/@href").extract()[0]
        if url :
            yield scrapy.Request(url, callback=self.parse)
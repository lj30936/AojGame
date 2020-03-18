# -*- coding: utf-8 -*-

# Define here the models for your scraped items
#
# See documentation in:
# https://doc.scrapy.org/en/latest/topics/items.html

import scrapy


class S51CreditItem(scrapy.Item):
    # define the fields for your item here like:
    # name = scrapy.Field()
    title       = scrapy.Field()
    url         = scrapy.Field()
    post_time   = scrapy.Field()
    reply_cnt   = scrapy.Field()
    pass

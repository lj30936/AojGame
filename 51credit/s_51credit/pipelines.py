# -*- coding: utf-8 -*-

# Define your item pipelines here
#
# Don't forget to add your pipeline to the ITEM_PIPELINES setting
# See: https://doc.scrapy.org/en/latest/topics/item-pipeline.html

import codecs

class S51CreditPipeline(object):
    def __init__(self):
        self.file = codecs.open('51credit.txt', 'w', 'utf-8')
    def process_item(self, item, spider):

        if item['title'] != "":
            line = item['reply_cnt'] + '   ' + item['post_time'] + '   ' + item['title'] + '\n'
            self.file.write(line)

        if item['url'] != "":
            self.file.write(item['url'] + '\n')
        
        return item

    def close_spider(self, spider):
        self.file.close()

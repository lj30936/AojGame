# -*- coding: utf-8 -*-

# Define your item pipelines here
#
# Don't forget to add your pipeline to the ITEM_PIPELINES setting
# See: http://doc.scrapy.org/en/latest/topics/item-pipeline.html

import codecs

class SmzdmPipeline(object):
    def __init__(self):
        self.file = codecs.open('data.txt', 'w', 'utf-8')
    def process_item(self, item, spider):

        if item['content_src'] != "":
            line = item['floor'] + '  ' + item['author_src'] + '  ' + item['time'] + '  ' + item['content_src'] + '\n'
            self.file.write(line)

        line = item['floor'] + '  ' + item['author'] + '  ' + item['time'] + '  ' + item['content'] + '\n'
        self.file.write(line)
        
        return item

    def close_spider(self, spider):
        self.file.close()

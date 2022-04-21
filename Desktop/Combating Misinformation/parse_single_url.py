from curses import wrapper
from email import header
from flask import jsonify
from lxml import etree
from urllib.request import urlopen
from lxml import html
from bs4 import BeautifulSoup as bf
from lxml.builder import E
from pip import main
import json
import re
import requests


def parse_single_url(url):
    # url = "https://www.sciencenews.org/article/orange-dwarf-stars-radiation-goldilocks-habitable-planets"
    html = urlopen(url)
    obj = bf(html.read(),'html.parser')
    title = obj.body.attrs = {'class': 'post-template-default single single-post postid-3112192 single-format-standard single-header-default'}
    body = obj.body
    mydivs = body.find_all("div", {"class": "site-wrapper"})
    for mydiv in mydivs:
        new = mydiv.find(id="page")
        content = new.find(id="content")
        post = content.article
        paragraph = post.find("div", {"class": "single__body___XFYC7"})
        if paragraph is None:
            return None
        more = paragraph.find("div", {"class": "single__content___Cm2ty"})
        if more is None:
            return None
        details = (more.find("div", {"class": "rich-text single__rich-text___BlzVF"}))
        if details is None:
            return None
        cali = details.find_all("p")
        if cali is None: #Maybe there will be better syntax for these None checking. 
            return None
        paragraphs = []
        for x in cali:
            em = x.em
            a = x.a
            to_append = str(x)
            to_append = re.sub('<.*?>', '', to_append)
            to_append = to_append.replace("</p>", "")
            to_append = to_append.replace("<p>", "")
            paragraphs.append(to_append)
            toReturn = {"paragraph" : paragraphs}
        print(toReturn)
        return toReturn
    
#parse_single_url("s")
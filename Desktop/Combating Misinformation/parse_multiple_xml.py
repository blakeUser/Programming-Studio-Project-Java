from re import U
import requests
import parse_single_url
from bs4 import BeautifulSoup as bs
from bs4 import BeautifulSoup as bf
from urllib.request import urlopen
resp = requests.get('https://www.sciencenews.org/sitemap.xml')
with open('import.xml', 'wb') as foutput:
   foutput.write(resp.content)
content = []
# Read the XML file
with open("import.xml", "r") as file:
    # Read each line in the file, readlines() returns a list of lines
    content = file.readlines()
    # Combine the lines in the list into a string
    content = "".join(content)
    bs_contents = bs(content, "lxml")


all_site_map = bs_contents.find_all("sitemap")
loc_list = []

for site_map in all_site_map:
    tag = site_map.find("loc")
    content = tag.contents
    loc_list.append(content)

inner_websites = []
content_of_all_paragraph_url = []
counter = 0
for loc in loc_list:
    # counter+=1
    # if counter == 10:
    #     break
    url_str = (str(loc[0]))
    resp = requests.get(url_str)
    inner_websites.append(resp.content)
    with open('sciencejournal.xml', 'wb') as foutput:
        foutput.write(resp.content)
    with open("sciencejournal.xml", "r") as file:
    # Read each line in the file, readlines() returns a list of lines   
        content = file.readlines()
        # Combine the lines in the list into a string
        content = "".join(content)
        bs_contents = bs(content, "lxml")
        all_loc_authentic = bs_contents.find_all("loc")
        for loc_authentic in all_loc_authentic:
            #print((loc_authentic.contents[0]))
            content_of_all_paragraph_url.append(loc_authentic.contents[0])

paragraph_list = []

for one_url in content_of_all_paragraph_url:
    print(one_url)
    if parse_single_url.parse_single_url(str(one_url)) is not None:
        #print(parse_single_url.parse_single_url(str(one_url)))
        paragraph_list.append(parse_single_url.parse_single_url(str(one_url)))

with open('a.txt', 'w') as f:
    for item in paragraph_list:
        f.write("%s\n" % item)


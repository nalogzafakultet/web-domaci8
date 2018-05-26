import urllib.request
import json      

with open('generated.json') as f:
	data = json.loads(f.read())

myurl = "http://localhost:8080/Web_Domaci7_war_exploded/api/tweets"


for tweet in data['lista']:
	req = urllib.request.Request(myurl)
	req.add_header('Content-Type', 'application/json; charset=utf-8')
	jsondata = json.dumps(tweet)
	jsondataasbytes = jsondata.encode('utf-8')   # needs to be bytes
	req.add_header('Content-Length', len(jsondataasbytes))
	print(jsondataasbytes)
	response = urllib.request.urlopen(req, jsondataasbytes)
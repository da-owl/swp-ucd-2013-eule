from django.http import HttpResponse
import json

def json_response(token, status_code, message='', error=''):
	response = json.dumps({'detail': message, 'token': token}, sort_keys=True, indent=4, separators=(',', ': '))
	return HttpResponse(response, 'application/json', status_code)
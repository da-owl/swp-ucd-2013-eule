from django.http import HttpResponse
import json

def json_response(request, status_code, message='', error=''):
	session = str(request.session.session_key);
	response = json.dumps({'detail': message, 'session': session}, sort_keys=True, indent=4, separators=(',', ': '))
	return HttpResponse(response, 'application/json', status_code)
from django.http import HttpResponse

def json_response(request, status_code, message='', error=''):
	# json = '{ status: "' + str(status_code) + ' ' + status_msg + '", message: "' + message + '", error: "' + error + '" }'
	json = '{ message: "' + message + '" session: "' + str(request.session.session_key) + '"}'
	return HttpResponse(json, 'text/json', status_code)
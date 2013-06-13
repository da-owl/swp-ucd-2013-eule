from django.http import HttpResponse
import json

def json_response(token, status_code, message='', error=''):
	response = json.dumps({'detail': message, 'token': str(token)}, sort_keys=True, indent=4, separators=(',', ': '))
    # return Response({'detail': 'Not found'}, status=status.HTTP_404_NOT_FOUND, exception=True)
	return HttpResponse(response, 'application/json', status_code)
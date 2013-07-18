"""
Basic endpoints. Used for registration and login.
"""
import json
import string
import pprint

from django.http import HttpResponse
from django.contrib.auth import authenticate
from django.contrib.auth import login as auth_login
from django.contrib.auth.models import User
from django.db import IntegrityError
from django.views.decorators.csrf import csrf_exempt    


from rest_framework import viewsets
from rest_framework.authtoken.models import Token

from core.helpers import json_response 
from core.serializers import UserSerializer, ForestSerializer
from core.models import Forest

def index(request):
    """
    Index
    """
    return HttpResponse("SWP-UCD Eule API 1.0")


def hello(request):
    """
    Test for presentation
    """
    auth = request.META['HTTP_AUTHORIZATION']
    # # if type(auth) == type(''):
    # #     # Work around django test client oddness
    # #     auth = auth.encode(HTTP_HEADER_ENCODING)
    if auth is None:
        return json_response(None, 403, 'Authentication credentials were not provided.')
    else:
        # auth_str = auth.encode('utf-8')
        if auth.find('Token') is -1:
            return json_response(None, 403, 'Authentication credentials were not provided.')
        else:
            token = auth.split(' ')[1]
            db_token = Token.objects.filter(key=token)
            if not db_token:
                return json_response(None, 403, 'Invalid Token.')
            else:
                return json_response(None, 200, 'SWP-UCD Eule API 1.0')
                
@csrf_exempt
def register(request):    
    """
    rest-like register using token-authentification
    json structure defined to match the framework structure
    expects a json document like this:
    { "username": "tester9", "password": "test", "email": "tester@test.de" }
    cross-site request forgery disabled
    """
    try:
        user_json = json.loads(request.body.decode("utf-8"))
        # for simple parameter implementation
        # username = request.POST['username']
        # email = request.POST['email']
        # password = request.POST['password']

        # get json values
        username = user_json['username']
        email = user_json['email']
        password = user_json['password']

        if password is '':
            return json_response(None, 500, 'Password must be set!')

        # Create user
        user = User.objects.create_user(username=username, email=email, password=password)
        forest = Forest(user=user, level=1, points=0)
        forest.save()
        token = Token.objects.get_or_create(user=user)
    except ValueError as e:
        return json_response(None, 500, e.args[0])
    except IntegrityError as e:
        return json_response(None, 500, 'Username already exits!')
        # return json_response(None, 500, e.args[0])
    return json_response(token, 201, 'Created')

class UserViewSet(viewsets.ModelViewSet):
    """
    API endpoint that allows users to be viewed or edited.
    """
    queryset = User.objects.all()
    serializer_class = UserSerializer



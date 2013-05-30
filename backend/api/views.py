# Create your views here.
import json

from django.http import HttpResponse
from django.contrib.auth import authenticate
from django.contrib.auth import login as auth_login
from django.contrib.auth.models import User
from django.db import IntegrityError
from django.views.decorators.csrf import csrf_exempt    


from rest_framework import viewsets
from rest_framework.authtoken.models import Token


from api.helpers import json_response 
from api.serializers import UserSerializer



def index(request):
    return HttpResponse("SWP-UCD Eule API 1.0")


def hello(request):
    return json_response(None, 200, 'SWP-UCD Eule API 1.0')

# ***************************************************************************
# rest-like register using token-authentification
# json structure defined to match the framework structure
# expects a json document like this:
# { "username": "tester9", "password": "test", "email": "tester@test.de" }
# cross-site request forgery disabled
# ***************************************************************************
@csrf_exempt
def register(request):    
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
        token = Token.objects.get_or_create(user=user)
    except ValueError as e:
        return json_response(None, 500, e.args[0])
    except IntegrityError:
        return json_response(None, 500, 'Username already exits!')
    return json_response(token, 201, 'Created')

# ***************************************************************************
# rest-like register
# login no longer required, because credentials are passed in the request
# ***************************************************************************
# @csrf_exempt
# def login(request):
#     username = request.POST['username']
#     password = request.POST['password']
#     user = authenticate(username=username, password=password)
#     if user is not None:
#         if user.is_active:
#             auth_login(request, user)
#             # Redirect to a success page.
#             return json_response(request, 200, 'Login successful')
#         else:
#             # Return a 'disabled account' error message
#             return json_response(request, 500, 'Account disabled')
#     else:
#         # Return an 'invalid login' error message.
#         return json_response(request, 500, 'Username not found')

# ***************************************************************************
# restful
# ***************************************************************************
class UserViewSet(viewsets.ModelViewSet):
    """
    API endpoint that allows users to be viewed or edited.
    """
    queryset = User.objects.all()
    serializer_class = UserSerializer
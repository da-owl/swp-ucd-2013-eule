# Create your views here.
from django.contrib.auth import authenticate
from django.contrib.auth import login as auth_login
from django.contrib.auth.models import User
from django.views.decorators.csrf import csrf_exempt
from api.helpers import json_response                                          



def index(request):
    return HttpResponse("SWP-UCD Eule API 1.0")

@csrf_exempt
def register(request):
    username = request.POST['username']
    email = request.POST['email']
    password = request.POST['password']
    # Create user
    user = User.objects.create_user(username=username, email=email, password=password)
    return json_response(request, 201, 'Created')

@csrf_exempt
def login(request):
    username = request.POST['username']
    password = request.POST['password']
    user = authenticate(username=username, password=password)
    if user is not None:
        if user.is_active:
            auth_login(request, user)
            # Redirect to a success page.
            return json_response(request, 200, 'Login successful')
        else:
            # Return a 'disabled account' error message
            return json_response(request, 500, 'Account disabled')
    else:
        # Return an 'invalid login' error message.
        return json_response(request, 500, 'Username not found')


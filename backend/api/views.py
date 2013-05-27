# Create your views here.
from django.http import HttpResponse
from django.contrib.auth import authenticate
from django.contrib.auth import login as auth_login
from django.contrib.auth.models import User
from django.views.decorators.csrf import csrf_exempt                                          



def index(request):
    return HttpResponse("SWP-UCD Eule API 1.0")

@csrf_exempt
def register(request):
    username = request.POST['username']
    email = request.POST['email']
    password = request.POST['password']
    user = User.objects.create_user(username=username, email=email, password=password)
    return HttpResponse(username)

@csrf_exempt
def login(request):
    username = request.POST['username']
    password = request.POST['password']
    user = authenticate(username=username, password=password)
    if user is not None:
        if user.is_active:
            auth_login(request, user)
            # Redirect to a success page.
            return HttpResponse("Welcome! Log in successful!")
        else:
            # Return a 'disabled account' error message
            return HttpResponse("Error! Your account is disabled!")
    else:
        # Return an 'invalid login' error message.
        return HttpResponse("Error! Username not found!")


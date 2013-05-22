# Create your views here.
from django.http import HttpResponse, authenticate, login

def index(request):
    return HttpResponse("SWP-UCD Eule API 1.0")

def register(request):
    return HttpResponse("register")

def login(request):
    username = request.POST['username']
    password = request.POST['password']
    user = authenticate(username=username, password=password)
    if user is not None:
        if user.is_active:
        	login(request, user)
            # Redirect to a success page.
            
        else:
            # Return a 'disabled account' error message
    else:
        # Return an 'invalid login' error message.

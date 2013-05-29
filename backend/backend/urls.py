from django.conf.urls import patterns, include, url

# Uncomment the next two lines to enable the admin:
from django.contrib import admin
from rest_framework import viewsets, routers

from api import views

admin.autodiscover()

# restful config
router = routers.DefaultRouter()
router.register(r'users', views.UserViewSet)

urlpatterns = patterns('',
    # ***************************************************************************
    # admin
    # ***************************************************************************
    # Uncomment the admin/doc line below to enable admin documentation:
    url(r'^admin/doc/', include('django.contrib.admindocs.urls')),

    # Uncomment the next line to enable the admin:
    url(r'^admin/', include(admin.site.urls)),

    # ***************************************************************************
    # register / login
    # ***************************************************************************
    url(r'^$', views.index, name='index'),
    url(r'^register', views.register, name='register'),
    # login no longer required, because credentials are passed in the request
    # url(r'^login', views.login, name='login'),

    # ***************************************************************************
    # rest_framework
    # ***************************************************************************
    # Wire up our API using automatic URL routing.
    # Additionally, we include login URLs for the browseable API.
    url(r'^', include(router.urls)),
    url(r'^api-auth/', include('rest_framework.urls', namespace='rest_framework'))
    
)



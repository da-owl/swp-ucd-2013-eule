from django.conf.urls import patterns, include, url

# Uncomment the next two lines to enable the admin:
from django.contrib import admin
from rest_framework import viewsets, routers

from core.views import index, register, hello
from core.views import UserViewSet
from forest.views import ForestViewSet, ForestFriendsViewSet, ForestUserForestItemViewSet, ItemViewSet, StatisticViewSet, ForestStatisticViewSet, UserForestItemViewSet

admin.autodiscover()

# restful config
# configure all viewset routes
router = routers.DefaultRouter()
router.register(r'users', UserViewSet)
router.register(r'forests', ForestViewSet)
router.register(r'items', ItemViewSet)
router.register(r'statistics', StatisticViewSet)
router.register(r'userforestitems', UserForestItemViewSet)

urlpatterns = patterns('',
    # """
    # admin
    # """
    # Uncomment the admin/doc line below to enable admin documentation:
    url(r'^admin/doc/', include('django.contrib.admindocs.urls')),

    # Uncomment the next line to enable the admin:
    url(r'^admin/', include(admin.site.urls)),

    # """
    # register / login
    # """
    url(r'^$', index, name='index'),
    url(r'^register', register, name='register'),
    url(r'^hello', hello, name='hello'),
    # login no longer required, because credentials are passed in the request
    # url(r'^login', views.login, name='login'),

    # """
    # rest_framework
    # """
    # Wire up our API using automatic URL routing.
    # Additionally, we include login URLs for the browseable API.
    url(r'^', include(router.urls)),
    url(r'^api-auth/', include('rest_framework.urls', namespace='rest_framework')),
    
    # configure endpoints for nested collections:
    # list / add / remove friends
    url(r'^forests/(?P<pk>[0-9]+)/friends/((?P<field_pk>[0-9]+)/)?$', ForestFriendsViewSet.as_view()),
    # list / add / remove userforestitems
    url(r'^forests/(?P<pk>[0-9]+)/userforestitems/((?P<field_pk>[0-9]+)/)?$', ForestUserForestItemViewSet.as_view()),
    # list / add / remove statistics
    url(r'^forests/(?P<pk>[0-9]+)/statistics/((?P<field_pk>[0-9]+)/)?$', ForestStatisticViewSet.as_view()),
    
)



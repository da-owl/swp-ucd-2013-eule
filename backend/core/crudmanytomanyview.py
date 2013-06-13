"""
        DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE 
                    Version 2, December 2004 

 Copyright (C) 2013 Eduardo Robles Elvira <edulix@wadobo.com> 

 Everyone is permitted to copy and distribute verbatim or modified 
 copies of this license document, and changing it is allowed as long 
 as the name is changed. 

            DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE 
   TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION 

  0. You just DO WHAT THE FUCK YOU WANT TO.
"""

from __future__ import unicode_literals

from django.views.generic.detail import SingleObjectMixin
from django.views.generic.list import MultipleObjectMixin
from django.core.exceptions import ImproperlyConfigured

from rest_framework import views, mixins, generics, serializers
from rest_framework.settings import api_settings
from rest_framework.response import Response
from rest_framework import status

from core.helpers import json_response


class CRUDManyToManyView(mixins.ListModelMixin, SingleObjectMixin,
                         generics.GenericAPIView):
    """
    Generic view that provide CRUD behaviour for ManyToMany fields.
    """
    model = None
    pk_url_kwarg = 'field_pk'
    field_name = ''
    model_pk = 'id'
    queryset = None
    item = None

    def get(self, request, *args, **kwargs):
        return self.list(request, *args, **kwargs)

    def post(self, request, *args, **kwargs):
        # print(self, kwargs)
        obj = self.get_object()
        item = self.get_item()
        getattr(self.get_item(), self.field_name).add(obj)
        item = self.get_item().save()
        return json_response(None, 201, 'Created')

    def delete(self, request, *args, **kwargs):
        obj = self.get_object()
        item = self.get_item()
        getattr(self.get_item(), self.field_name).remove(obj)
        item = self.get_item().save()
        return json_response(None, 204, 'No object found.')

    def get_item(self):
        if self.item:
            return self.item
        if self.model is None:
            raise ImproperlyConfigured("%(cls)s is missing a model." % {
                'cls': self.__class__.__name__
            })
        item_view = generics.MultipleObjectAPIView()
        item_view.model = self.model
        item_view.request = self.request
        item_view.args = self.args
        item_view.kwargs = self.kwargs
        self.item = item_view.get_object()
        return self.item

    def get_queryset(self):
        '''
        Returns the list of items in model_instance.field_name.all()
        '''
        if self.queryset:
            return self.queryset
        if self.field_name is None:
            raise ImproperlyConfigured("%(cls)s is missing a field_name." % {
                'cls': self.__class__.__name__
            })

        obj = self.get_item()
        self.queryset = getattr(obj, self.field_name).all()
        return self.queryset
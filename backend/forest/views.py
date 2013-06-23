# Create your views here.    
from rest_framework import viewsets

from core.serializers import ForestSerializer, ItemSerializer, StatSerializer, UserForestItemSerializer
from core.models import Forest, Item, Stat
from core.filters import ForestFilter
from core.helpers import json_response
from core.crudmanytomanyview import CRUDManyToManyView

class ForestViewSet(viewsets.ModelViewSet):
    """
    API endpoint that allows users to be viewed or edited.
    """
    queryset = Forest.objects.all()
    serializer_class = ForestSerializer
    filter_class = ForestFilter

    def pre_save(self, obj):
        stat = Stat(level=obj.level, points=obj.points)
        stat.save()
        obj.stats.add(stat)

class ItemViewSet(viewsets.ModelViewSet):
    """
    API endpoint that allows users to be viewed or edited.
    """
    queryset = Item.objects.all()
    serializer_class = ItemSerializer


class ForestFriendsViewSet(CRUDManyToManyView):
    model = Forest
    field_name = 'friends'
    serializer_class = ForestSerializer

    def put(self, request, *args, **kwargs):
        this_pk = self.kwargs.get('pk', None)
        friend_pk = self.kwargs.get('field_pk', None)

        this = Forest.objects.get(id=this_pk)
        friend = Forest.objects.get(id=friend_pk)

        this.friends.add(friend)
        friend.friends.add(this)

        this = this.save()

        return json_response(None, 201, 'Friend sucessfully added.')

    def delete(self, request, *args, **kwargs):
        this_pk = self.kwargs.get('pk', None)
        friend_pk = self.kwargs.get('field_pk', None)

        this = Forest.objects.get(id=this_pk)
        friend = Forest.objects.get(id=friend_pk)

        this.friends.remove(friend)
        friend.friends.remove(this)

        return json_response(None, 200, 'Friend sucessfully removed.')


class UserForestItemViewSet(CRUDManyToManyView):
    model = Forest
    field_name = 'userforestitems'
    serializer_class = UserForestItemSerializer

    def put(self, request, *args, **kwargs):
        forest_pk = self.kwargs.get('pk', None)
        item_pk = self.kwargs.get('field_pk', None)

        forest = Forest.objects.get(id=forest_pk)
        item = Item.objects.get(id=item_pk)

        forest.items.add(item)
        forest = forest.save()

        return json_response(None, 201, 'Item sucessfully added.')

    def delete(self, request, *args, **kwargs):
        forest_pk = self.kwargs.get('pk', None)
        item_pk = self.kwargs.get('field_pk', None)

        forest = Forest.objects.get(id=forest_pk)
        item = Item.objects.get(id=item_pk)

        forest.items.remove(item)
        forest = forest.save()

        return json_response(None, 200, 'Item sucessfully removed.')

class ForestStatViewSet(CRUDManyToManyView):
    model = Forest
    field_name = 'stats'
    serializer_class = StatSerializer
    

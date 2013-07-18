"""
Central point which defines all needed endpoints.
"""   
from rest_framework import viewsets

from core.serializers import ForestSerializer, ItemSerializer, StatisticSerializer, UserForestItemSerializer
from core.models import Forest, Item, Statistic, UserForestItem
from core.filters import ForestFilter
from core.helpers import json_response
from core.crudmanytomanyview import CRUDManyToManyView

class ForestViewSet(viewsets.ModelViewSet):
    """
    API endpoint that allows forests to be viewed or edited.
    """
    queryset = Forest.objects.all()
    serializer_class = ForestSerializer
    filter_class = ForestFilter

class ItemViewSet(viewsets.ModelViewSet):
    """
    API endpoint that allows items to be viewed or edited.
    """
    queryset = Item.objects.all()
    serializer_class = ItemSerializer

class StatisticViewSet(viewsets.ModelViewSet):
    """
    API endpoint that allows statistics to be viewed or edited.
    """
    queryset = Statistic.objects.all()
    serializer_class = StatisticSerializer

class UserForestItemViewSet(viewsets.ModelViewSet):
    """
    API endpoint that allows userforestitems to be viewed or edited.
    """
    queryset = UserForestItem.objects.all()
    serializer_class = UserForestItemSerializer

class ForestFriendsViewSet(CRUDManyToManyView):
    """
    API endpoint that allows forest friends to be added or removed.
    """
    model = Forest
    field_name = 'friends'
    serializer_class = ForestSerializer


    def put(self, request, *args, **kwargs):
        """
        Called if a existing forest is added to another as friend.
        """
        this_pk = self.kwargs.get('pk', None)
        friend_pk = self.kwargs.get('field_pk', None)

        this = Forest.objects.get(id=this_pk)
        friend = Forest.objects.get(id=friend_pk)

        this.friends.add(friend)
        friend.friends.add(this)

        this = this.save()

        return json_response(None, 201, 'Friend sucessfully added.')

    def delete(self, request, *args, **kwargs):
        """
        Called if a existing forest is removed from another.
        """
        this_pk = self.kwargs.get('pk', None)
        friend_pk = self.kwargs.get('field_pk', None)

        this = Forest.objects.get(id=this_pk)
        friend = Forest.objects.get(id=friend_pk)

        this.friends.remove(friend)
        friend.friends.remove(this)

        return json_response(None, 200, 'Friend sucessfully removed.')


class ForestUserForestItemViewSet(CRUDManyToManyView):
    """
    API endpoint that allows userforestitems to be added or removed.
    """
    model = Forest
    field_name = 'userforestitems'
    serializer_class = UserForestItemSerializer

    def put(self, request, *args, **kwargs):
        """
        Called if a existing userforestitem is added to a forest.
        """
        forest_pk = self.kwargs.get('pk', None)
        item_pk = self.kwargs.get('field_pk', None)

        forest = Forest.objects.get(id=forest_pk)
        item = UserForestItem.objects.get(id=item_pk)

        forest.userforestitems.add(item)
        forest = forest.save()

        return json_response(None, 201, 'Item sucessfully added.')

    def delete(self, request, *args, **kwargs):
        """
        Called if a existing userforestitem is removed from a forest.
        """
        forest_pk = self.kwargs.get('pk', None)
        item_pk = self.kwargs.get('field_pk', None)

        forest = Forest.objects.get(id=forest_pk)
        item = Item.objects.get(id=item_pk)

        forest.items.remove(item)
        forest = forest.save()

        return json_response(None, 200, 'Item sucessfully removed.')
    
class ForestStatisticViewSet(CRUDManyToManyView):
    """
    API endpoint that allows statistics to be added or removed.
    """
    model = Forest
    field_name = 'statistics'
    serializer_class = StatisticSerializer

    def put(self, request, *args, **kwargs):
        """
        Called if a existing statistic is added to a forest.
        """
        forest_pk = self.kwargs.get('pk', None)
        item_pk = self.kwargs.get('field_pk', None)

        forest = Forest.objects.get(id=forest_pk)
        item = Statistic.objects.get(id=item_pk)

        forest.statistics.add(item)
        forest = forest.save()

        return json_response(None, 201, 'Statistic sucessfully added.')

    def delete(self, request, *args, **kwargs):
        """
        Called if a existing statistic is removed from a forest.
        """
        forest_pk = self.kwargs.get('pk', None)
        item_pk = self.kwargs.get('field_pk', None)

        forest = Forest.objects.get(id=forest_pk)
        item = Statistic.objects.get(id=item_pk)

        forest.statistics.remove(item)
        forest = forest.save()

        return json_response(None, 200, 'Statistic sucessfully removed.')
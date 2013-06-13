# Create your views here.    
from rest_framework import viewsets

from core.serializers import ForestSerializer, ItemSerializer, StatSerializer
from core.models import Forest, Item, Stat
from core.filters import ForestFilter
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

    def pre_save(self, obj):
        print(self)
        print(obj)


class ForestItemViewSet(CRUDManyToManyView):
    model = Forest
    field_name = 'items'
    serializer_class = ItemSerializer

class ForestStatViewSet(CRUDManyToManyView):
    model = Forest
    field_name = 'stats'
    serializer_class = StatSerializer
    

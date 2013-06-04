from django.contrib.auth.models import User
from core.models import Forest
from core.models import Item
from rest_framework import serializers

class UserSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = User
        fields = ('url', 'username', 'email', 'groups')

class ItemSerializer(serializers.ModelSerializer):
    class Meta:
        model = Item
        fields = ('name', 'description')


class ForestFriendSerializer(serializers.ModelSerializer):
    items = ItemSerializer()
    user = UserSerializer()

    class Meta:
        model = Forest
        fields = ('user', 'level', 'points', 'friends', 'items')
        depth = 1

class ForestSerializer(serializers.ModelSerializer):
    friends = ForestFriendSerializer()
    items = ItemSerializer()
    user = UserSerializer()

    class Meta:
        model = Forest
        fields = ('user', 'level', 'points', 'friends', 'items')
        depth = 1
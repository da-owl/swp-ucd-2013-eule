"""
Serializers for model classes. Used by the restful-framework.
_fields_ defines which attributes are serialized.
"""
from django.contrib.auth.models import User
from core.models import Forest, Item, Statistic, UserForestItem
from rest_framework import serializers

class UserSerializer(serializers.HyperlinkedModelSerializer):
    """
    Serializes users.
    """
    class Meta:
        model = User
        fields = ('id', 'url', 'username', 'email', 'groups')

class ItemSerializer(serializers.ModelSerializer):
    """
    Serializes items.
    """
    class Meta:
        model = Item
        fields = ('id', 'name', 'description', 'price', 'level', 'amount', 'moveable', 'type', 'category', 'imageId')

class StatisticSerializer(serializers.ModelSerializer):
    """
    Serializes statistics.
    """
    class Meta:
        model = Statistic
        fields = ('id', 'gainedPoints', 'dataInterval', 'consumptions', 'tripConsumption')
        ordering = ['timestamp']

class ForestSerializer(serializers.ModelSerializer):
    """
    Serializes forests.
    """
    user = serializers.PrimaryKeyRelatedField()

    class Meta:
        model = Forest
        fields = ('id', 'user', 'level', 'points', 'levelProgessPoints', 'pointProgress')
        # depth = 1
        ordering = ['points']

class UserForestItemSerializer(serializers.ModelSerializer):
    """
    Serializes userforestitems.
    """
    item = serializers.PrimaryKeyRelatedField()
    class Meta:
        model = UserForestItem
        fields = ('id', 'tileX', 'tileY', 'offsetX', 'offsetY', 'item')
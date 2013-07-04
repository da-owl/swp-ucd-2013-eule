from django.contrib.auth.models import User
from core.models import Forest, Item, Statistic, UserForestItem
from rest_framework import serializers

class UserSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = User
        fields = ('id', 'url', 'username', 'email', 'groups')

class ItemSerializer(serializers.ModelSerializer):
    class Meta:
        model = Item
        fields = ('id', 'name', 'description', 'price', 'level', 'amount', 'moveable', 'type', 'category', 'imageId')

class StatisticSerializer(serializers.ModelSerializer):
    class Meta:
        model = Statistic
        fields = ('id', 'gainedPoints', 'dataInterval', 'consumptions', 'tripConsumption')
        ordering = ['timestamp']


# class ForestFriendSerializer(serializers.ModelSerializer):
#     items = ItemSerializer()
#     user = UserSerializer()

#     class Meta:
#         model = Forest
#         fields = ('id', 'user', 'level', 'points')
#         depth = 1

class ForestSerializer(serializers.ModelSerializer):
    # friends = ForestSerializer()
    # items = ItemSerializer()
    # user = UserSerializer()
    user = serializers.PrimaryKeyRelatedField()

    class Meta:
        model = Forest
        fields = ('id', 'user', 'level', 'points', 'levelProgessPoints', 'pointProgress')
        # depth = 1
        ordering = ['points']

class UserForestItemSerializer(serializers.ModelSerializer):
    item = serializers.PrimaryKeyRelatedField()
    class Meta:
        model = UserForestItem
        fields = ('id', 'tileX', 'tileY', 'offsetX', 'offsetY', 'item')
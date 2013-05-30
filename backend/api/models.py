from django.db import models
from django.contrib.auth.models import User

class ItemCategory(models.Model):
    name = models.CharField(max_length=50)

class ItemType(models.Model):
    name = models.CharField(max_length=50)

class Item(models.Model):
    name = models.CharField(max_length=30)
    description = models.CharField(max_length=200)
    image = models.CharField(max_length=200)
    costs = models.IntegerField()
    min_level = models.IntegerField()
    item_type = models.ForeignKey(ItemType)
    category = models.ForeignKey(ItemCategory, related_name='+')
    allowed_category = models.ForeignKey(ItemCategory, related_name='+')

class Forest(models.Model):
	user =  models.ForeignKey(User)
	level = models.IntegerField()
	points = models.IntegerField()

class ForestFriends(models.Model):
	forest_1 =  models.ForeignKey(Forest, related_name='+')
	forest_2 =  models.ForeignKey(Forest, related_name='+')

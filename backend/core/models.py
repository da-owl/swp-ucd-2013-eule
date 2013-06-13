from django.db import models
from django.contrib.auth.models import User

# Create your models here.
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

class Stat(models.Model):
    timestamp = models.DateTimeField(auto_now=True)
    level = models.IntegerField()
    points = models.IntegerField()

class Forest(models.Model):
    user = models.ForeignKey(User)
    level = models.IntegerField()
    points = models.IntegerField()
    # parent = models.ForeignKey('self', blank=True, related_name="friends")
    friends = models.ManyToManyField('Forest', blank=True)
    items = models.ManyToManyField(Item, blank=True)
    stats = models.ManyToManyField(Stat, blank=True)

    class Meta:
        ordering = ["points"]



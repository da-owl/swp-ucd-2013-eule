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

class UserForestItem(models.Model):
    item = models.ForeignKey(Item)
    tileX = models.IntegerField()
    tileY = models.IntegerField()
    offsetX = models.DecimalField(max_digits=2, decimal_places=1)
    offsetY = models.DecimalField(max_digits=2, decimal_places=1)

class Stat(models.Model):
    timestamp = models.DateTimeField(auto_now=True)
    level = models.IntegerField()
    points = models.IntegerField()

class Trip(models.Model):
    start = models.DateTimeField(auto_now=True)
    end = models.DateTimeField(auto_now=True)
    km = models.IntegerField()
    fuel = models.DecimalField(max_digits=4, decimal_places=2)
    points = models.IntegerField()

class Forest(models.Model):
    user = models.ForeignKey(User)
    level = models.IntegerField()
    points = models.IntegerField()
    # parent = models.ForeignKey('self', blank=True, related_name="friends")
    friends = models.ManyToManyField('Forest', blank=True)
    userforestitems = models.ManyToManyField(UserForestItem, blank=True)
    stats = models.ManyToManyField(Stat, blank=True)
    trips = models.ManyToManyField(Trip, blank=True)

    class Meta:
        ordering = ["points"]

    def __str__(self):
        return 'id:' + str(self.id) + ',level:' + str(self.level)



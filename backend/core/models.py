from django.db import models
from django.contrib.auth.models import User

class Item(models.Model):
    name = models.CharField(max_length=30)
    description = models.CharField(max_length=200)
    price = models.IntegerField()
    level = models.IntegerField()
    amount = models.IntegerField()
    moveable = models.BooleanField(default=False)
    type = models.CharField(max_length=30)
    category = models.CharField(max_length=30)
    imageId = models.IntegerField()

class UserForestItem(models.Model):
    item = models.ForeignKey(Item)
    tileX = models.IntegerField()
    tileY = models.IntegerField()
    offsetX = models.FloatField()
    offsetY = models.FloatField()

class Statistic(models.Model):
    timestamp = models.DateTimeField(auto_now=True)
    gainedPoints = models.IntegerField()
    dataInterval = models.IntegerField()
    consumptions = models.TextField()
    tripConsumption = models.FloatField()

class Forest(models.Model):
    user = models.ForeignKey(User)
    level = models.IntegerField()
    points = models.IntegerField()
    # parent = models.ForeignKey('self', blank=True, related_name="friends")
    levelProgessPoints = models.IntegerField()
    pointProgress = models.FloatField()
    friends = models.ManyToManyField('Forest', blank=True)
    userforestitems = models.ManyToManyField(UserForestItem, blank=True)
    statistics = models.ManyToManyField(Statistic, blank=True)

    class Meta:
        ordering = ["points"]

    def __str__(self):
        return 'id:' + str(self.id) + ',level:' + str(self.level)



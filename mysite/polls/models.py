from django.db import models


class Message(models.Model):
    time = models.CharField(max_length=200)
    address = models.CharField(max_length=200)
    plate_id = models.CharField(max_length=200)
    violation_code = models.CharField(max_length=200)
    drone_id = models.IntegerField()
    #pub_date = models.DateTimeField('date published')

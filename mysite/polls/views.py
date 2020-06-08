import json

from django.http import HttpResponse
from django.shortcuts import render
from django.views.decorators.csrf import csrf_exempt

from polls.models import Message


def index(request):
    alert_list = Message.objects.all()
    context = {'alert_list': alert_list}
    return render(request, 'index.html', context)


@csrf_exempt
def receive_msg(request):
    body_unicode = request.body.decode('utf-8')
    body = json.loads(body_unicode)
    print(body['drone_id'])
    msg = Message(time=body['time'],
                  address=body['address'],
                  plate_id=body['plate_id'],
                  violation_code=body['violation_code'],
                  drone_id=body['drone_id'])
    msg.save()
    return HttpResponse(request)

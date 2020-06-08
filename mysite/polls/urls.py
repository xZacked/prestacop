from django.urls import path

from . import views

urlpatterns = [
    path('', views.index, name='index'),
    path('receive_msg', views.receive_msg, name='receive_msg')  # views creer l'objet
]

from django.urls import path

from .views import upload_image, list_images

urlpatterns = [
    path('upload/', upload_image, name='upload_image'),
    path('list/', list_images, name='list_images'),
]

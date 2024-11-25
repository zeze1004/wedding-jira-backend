from django.db import models


# Create your models here.
class UploadedImage(models.Model):
    image = models.ImageField(upload_to='uploads/')  # 이미지 업로드 경로
    uploaded_at = models.DateTimeField(auto_now_add=True)  # 이미지 업로드 시간

    def __str__(self):
        return f"Image {self.id} uploaded at {self.uploaded_at}"

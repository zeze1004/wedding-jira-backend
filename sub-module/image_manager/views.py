from django.http import JsonResponse

from .services import create_uploaded_image, get_recent_images


def upload_image(request):
    if request.method == "POST" and request.FILES.get("image"):
        uploaded_file = request.FILES["image"]
        image = create_uploaded_image(uploaded_file)
        return JsonResponse({"message": "image가 성공적으로 업로드 됐습니다", "image_id": image.id})
    return JsonResponse({"error": "이미지 파일을 추가해주세요"}, status=400)


def list_images(request):
    recent_images = get_recent_images()
    return JsonResponse({"images": [{"id": img.id, "url": img.image.url} for img in recent_images]})

from .models import UploadedImage


def get_recent_images(limit=10):
    """최신 업로드 이미지 10개만 가져옵니다"""
    # TODO: 유저당 이미지 10개만 업로드하도록 제한
    return UploadedImage.objects.order_by('-uploaded_at')[:limit]


def create_uploaded_image(image_file):
    """이미지 업로드 데이터를 생성합니다."""
    # TODO: 일정 이미지 용량을 넘으면 resize해서 저장
    return UploadedImage.objects.create(image=image_file)

from django.conf.urls import url, include

from django.conf import settings
from django.conf.urls.static import static

from django.contrib import admin
from . import views

urlpatterns = [
    url( r'^$', views. login, name = 'login'),
    url(r'^admin/', admin.site.urls),
    url( r'^dashboard/', include( 'problemPage.urls') ),
    url( r'^register/', include( 'register.urls') ),
    url( r'^logout/', include( 'logout.urls') ),
] + static(settings.MEDIA_URL, document_root=settings.MEDIA_ROOT)

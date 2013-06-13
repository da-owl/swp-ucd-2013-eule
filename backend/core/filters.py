import django_filters

from core.models import Forest

class ForestFilter(django_filters.FilterSet):
    min_price = django_filters.NumberFilter(lookup_type='gte')
    max_price = django_filters.NumberFilter(lookup_type='lte')
    class Meta:
        model = Forest
        fields = ['level']
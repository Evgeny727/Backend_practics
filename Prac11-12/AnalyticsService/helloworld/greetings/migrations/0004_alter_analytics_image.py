# Generated by Django 4.2.7 on 2023-11-23 19:21

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('greetings', '0003_remove_analytics_file_name_analytics_image'),
    ]

    operations = [
        migrations.AlterField(
            model_name='analytics',
            name='image',
            field=models.ImageField(blank=True, upload_to='C:/Users/vladv/OneDrive/Рабочий стол/University docs/Third year/Development of server side/Practice 12/AnalyticsService/helloworld/static/diagrams/'),
        ),
    ]

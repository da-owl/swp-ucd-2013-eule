"""
This file demonstrates writing tests using the unittest module. These will pass
when you run "manage.py test".

Replace this with more appropriate tests for your application.
"""
from django.test import TestCase
import pycurl



class AuthTest(TestCase):
    def test_register(self):
        c = pycurl.Curl()
		c.setopt(c.URL, 'http://localhost:8080/register')
		c.setopt(c.POSTFIELDS, 'username=tester&password=test&email=tester@test.de')
		c.perform()
        self.assertEqual(1 + 1, 1)

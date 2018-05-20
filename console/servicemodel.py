__author__ = "John McAleer, R00169349"

import base64
import requests
import json

from http import HTTPStatus


class ServiceModel:
    """ This class models the microservices project backend. Provides functions to list products etc.
    """

    ENDPOINT_LOGIN = '/auth-svc/oauth/token'
    ENDPOINT_CATEGORIES = '/stock-svc/categories'
    ENDPOINT_PRODUCTS = '/stock-svc/categories/{CategoryId}/products'
    ENDPOINT_REVIEWS = '/review-svc/reviews/{ProductId}'
    ENDPOINT_DELETE_REVIEW = '/review-svc/reviews/{ReviewId}'

    def __init__(self, server_url):
        """Initialise instance.
        :param server_url: backend URL
        """
        self.server_url = server_url
        self.user = 'webapplication'
        self.access_token = None

    def login(self, username, password):
        """Log in to backend.
        :param username: username
        :param password: password
        :return true when login succceeds
        """
        authEndPoint = self.server_url + self.ENDPOINT_LOGIN
        basicAuthString = base64.b64encode((self.user + ':').encode('ascii')).decode()
        payload = {'grant_type': 'password',
                   'username': username,
                   'password': password}
        response = requests.post(authEndPoint,
                                 headers={'Accept': '*/*', 'Authorization': 'Basic ' + basicAuthString},
                                 data=payload)
        if response.status_code == HTTPStatus.OK:
            json_response = json.loads(response.text)
            self.access_token = json_response.get("access_token")
            return True
        return False

    def get_categories(self):
        """List categories
        :return list of category objects
        """
        categories_endpoint = self.server_url + self.ENDPOINT_CATEGORIES
        response = requests.get(categories_endpoint,
                                headers={'Authorization': 'Bearer ' + self.access_token})
        if response.status_code == HTTPStatus.OK:
            return json.loads(response.text)
        else:
            raise Exception('Operation failed: HTTP Status code %d returned.' % response.status_code)

    def add_category(self, category_name):
        """Adds a category
        :param category_name: category name
        """
        categories_endpoint = self.server_url + self.ENDPOINT_CATEGORIES
        payload = {'name': category_name}
        response = requests.post(categories_endpoint,
                                 headers={'Authorization': 'Bearer ' + self.access_token},
                                 json=payload)
        if response.status_code != HTTPStatus.CREATED:
            raise Exception('Operation failed: HTTP Status code %d returned.' % response.status_code)

    def get_products(self, category_id):
        """Returns list of products in given category
        :param category_id: category identifier
        :return list of product objects
        """
        products_endpoint = self.server_url + self.ENDPOINT_PRODUCTS
        products_endpoint = products_endpoint.format(CategoryId=category_id)
        response = requests.get(products_endpoint,
                                headers={'Authorization': 'Bearer ' + self.access_token})
        if response.status_code == HTTPStatus.OK:
            return json.loads(response.text)
        else:
            raise Exception('Operation failed: HTTP Status code %d returned.' % response.status_code)

    def add_product(self, category_id, product_name):
        """Adds a product with the give name to the given category
        :param category_id: category identifier
        :param product_name: product name
        """
        products_endpoint = self.server_url + self.ENDPOINT_PRODUCTS
        products_endpoint = products_endpoint.format(CategoryId=category_id)
        payload = {'name': product_name}
        response = requests.post(products_endpoint,
                                 headers={'Authorization': 'Bearer ' + self.access_token},
                                 json=payload)
        if response.status_code != HTTPStatus.CREATED:
            raise Exception('Operation failed: HTTP Status code %d returned.' % response.status_code)

    def get_reviews(self, product_id):
        """Returns list of all reviews for the given product id
          :param product_id: product identifier
          :return list of review objects
          """
        reviews_endpoint = self.server_url + self.ENDPOINT_REVIEWS
        reviews_endpoint = reviews_endpoint.format(ProductId=product_id)
        response = requests.get(reviews_endpoint,
                                headers={'Authorization': 'Bearer ' + self.access_token})
        if (response.status_code == HTTPStatus.OK):
            return json.loads(response.text)
        else:
            raise Exception('Operation failed: HTTP Status code %d returned.' % response.status_code)

    def add_review(self, product_id, review_text, rating, max_rating):
        """Adds a review for the given product id
          :param product_id: product identifier
          :param review_text: review text
          :param rating: rating value
          :param max_rating: max rating value
          """
        reviews_endpoint = self.server_url + self.ENDPOINT_REVIEWS
        reviews_endpoint = reviews_endpoint.format(ProductId=product_id)
        payload = {'text': review_text, 'rating': rating, 'maxRating': max_rating}
        response = requests.post(reviews_endpoint,
                                 headers={'Authorization': 'Bearer ' + self.access_token},
                                 json=payload)
        if response.status_code != HTTPStatus.CREATED:
            raise Exception('Operation failed: HTTP Status code %d returned.' % response.status_code)

    def delete_review(self, review_id):
        """Deletes the review with the given id
        :param review_id: review id
        """
        reviews_endpoint = self.server_url + self.ENDPOINT_DELETE_REVIEW
        reviews_endpoint = reviews_endpoint.format(ReviewId=review_id)
        response = requests.delete(reviews_endpoint,
                                   headers={'Authorization': 'Bearer ' + self.access_token})
        if response.status_code != HTTPStatus.ACCEPTED:
            raise Exception('Operation failed: HTTP Status code %d returned.' % response.status_code)

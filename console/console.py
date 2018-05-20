#!/usr/bin/env python3

"""
    Basic console UI demonstrating authentication/use of microservices.
"""


__author__ = "John McAleer, R00169349"

import argparse

from servicemodel import ServiceModel

""" 
    Console main entry point.    
"""
ACTION_LIST_CATEGORIES = 1
ACTION_ADD_CATEGORY = 2
ACTION_LIST_PRODUCTS = 3
ACTION_ADD_PRODUCT = 4
ACTION_LIST_REVIEWS = 5
ACTION_ADD_REVIEW = 6
ACTION_DELETE_REVIEW = 7
ACTION_EXIT = 8

MIN_RATING = 0
MAX_RATING = 10


def main():
    # main entry point

    # configure cmdline argument processing
    parser = configureparser()

    # determine cmdline arg values
    cmdlineargs = parser.parse_args()
    server_url = cmdlineargs.serverURL

    # instantiate service model
    service = ServiceModel(server_url)

    # loop until successful login
    logged_in = False
    while not logged_in:
        credentials = get_credentials()
        logged_in = service.login(credentials.get('username'), credentials.get('password'))
        if not logged_in:
            print("Login failed.")
        else:
            print("Login successful.")

    # read user input and perform actions until exit
    action = None
    while action != ACTION_EXIT:
        action = get_action()
        if action == ACTION_LIST_CATEGORIES:
            list_categories(service)
        elif action == ACTION_ADD_CATEGORY:
            add_category(service)
        elif action == ACTION_LIST_PRODUCTS:
            list_products(service)
        elif action == ACTION_ADD_PRODUCT:
            add_product(service)
        elif action == ACTION_LIST_REVIEWS:
            list_reviews(service)
        elif action == ACTION_ADD_REVIEW:
            add_review(service)
        elif action == ACTION_DELETE_REVIEW:
            delete_review(service)


def list_categories(service):
    """list all product categories.
    :param service: service instance
    """
    category_list = service.get_categories()
    if category_list:
        for category in category_list:
            print(category)
    else:
        print("Operation failed")


def add_category(service):
    """add a product category.
    :param service: service instance
    """
    category_name = None
    while not category_name:
        category_name = input("Enter category name: ")
    try:
        service.add_category(category_name)
    except Exception as error:
        print(repr(error))


def list_products(service):
    """lists all products in chosen category.
    :param service: service instance
    """
    category_id = None
    while not category_id:
        try:
            category_id = int(input("Enter category id: "))
        except:
            print("Invalid input")
    try:
        product_list = service.get_products(category_id)
        if product_list:
            for product in product_list:
                print(product)
        else:
            print("No products found.")
    except Exception as error:
        print(repr(error))


def add_product(service):
    """add a product to a chosen category.
    :param service: service instance
    """
    category_id = None
    while not category_id:
        try:
            category_id = int(input("Enter category id: "))
        except:
            print("Invalid input")
    product_name = None
    while not product_name:
        product_name = input("Enter product name: ")
    try:
        service.add_product(category_id, product_name)
    except Exception as error:
        print(repr(error))


def list_reviews(service):
    """lists all reviews for a chosen product.
    :param service: service instance
    """
    product_id = None
    while not product_id:
        try:
            product_id = int(input("Enter product id: "))
        except:
            print("Invalid input")
    try:
        review_list = service.get_reviews(product_id)
        if review_list:
            for review in review_list:
                print(review)
        else:
            print("No reviews found.")
    except Exception as error:
        print(repr(error))


def add_review(service):
    """adds a review for a chosen product.
    :param service: service instance
    """
    product_id = None
    while not product_id:
        try:
            product_id = int(input("Enter product id: "))
        except:
            print("Invalid input")
    review_text = None
    while not review_text:
        review_text = input("Enter review comment: ")
    review_rating = None
    while not review_rating:
        try:
            review_rating = int(input("Enter rating between %d and %d: " % (MIN_RATING, MAX_RATING)))
            if review_rating < MIN_RATING or review_rating > MAX_RATING:
                review_rating = None
        except:
            print("Invalid input")
    try:
        service.add_review(product_id, review_text, review_rating, MAX_RATING)
    except Exception as error:
        print(repr(error))


def delete_review(service):
    """deletes a review.
    :param service: service instance
    """
    review_id = None
    while not review_id:
        try:
            review_id = int(input("Enter review id: "))
        except:
            print("Invalid input")
    try:
        service.delete_review(review_id)
    except Exception as error:
        print(repr(error))


def get_action():
    """show a menu and loop for valid menuitem choice input
    """
    print()
    print("%d] List Product Categories" % ACTION_LIST_CATEGORIES)
    print("%d] Add Product Category" % ACTION_ADD_CATEGORY)
    print("%d] List Products" % ACTION_LIST_PRODUCTS)
    print("%d] Add Product" % ACTION_ADD_PRODUCT)
    print("%d] List Product Reviews" % ACTION_LIST_REVIEWS)
    print("%d] Add Product Review" % ACTION_ADD_REVIEW)
    print("%d] Delete Product Review" % ACTION_DELETE_REVIEW)
    print("%d] Exit" % ACTION_EXIT)
    print()
    choice = None
    while not choice:
        try:
            choice = int(input("Enter your choice: "))
            if 0 < choice < 9:
                return choice
            else:
                print("Invalid input")
        except:
            print("Invalid input")

def get_credentials():
    """gets user credentials
    """
    print("Please enter your credentials.")
    username = None
    password = None

    while not username:
        username = input("Username: ")
    while not password:
        password = input("Password: ")
    return {'username': username, 'password': password}

def configureparser():
    """Configures a command line parser.
    :return: Configured parser instance
    """
    parser = argparse.ArgumentParser(
        description='Basic console for microservice interaction')
    parser.add_argument('serverURL')
    return parser


if __name__ == "__main__":
    main()
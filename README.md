# Healthy Eating - Mayden Coding Challenge

## Structure

The application comprises two main views:

1. A _shopping list_ page that allows the shopper to add and remove products on their shopping list.

2. A _tracker_ page where the shopper can cross off items on their shopping list.

Package structure:

| package                           | purpose                                   |
| -------                           | -------                                   |
| uk.co.mayden.shopping.app         | Application configuration & security      |
| uk.co.mayden.shopping.list        | Shopping list management                  |
| uk.co.mayden.shopping.product     | Products and repository                   |
| uk.co.mayden.shopping.tracker     | Crosses off purchased products            |

## Implementation Notes

The application is implemented using Spring Boot and Thymeleaf for basic HTML template rendering.

The [HealthyEatingShoppingListApplication](https://github.com/stridecolossus/HealthyEating/blob/main/src/main/java/uk/co/mayden/shopping/HealthyEatingShoppingListApplication.java) is the application entry-point.

This is intentionally a bare bones implementation with the focus on code clarity rather than functionality, styling, etc.

In particular:

* Views are simple HTML without styling, i.e. no JavaScript, CSS, etc.

* For the sake of simplicity, the application is implemented to only support a simple web-site.

A real-world implementation would probably be implemented using REST-based services with views delivered via React or similar to support multiple, richer target devices (mobile, email, etc).

## Selected Stories

For the purposes of illustration, I have excluded two stories from those committed to:

* E-mailing a shopping list - This is not particularly difficult and generally involves a template-based 'view' to generate and style the email, usually more configuration than actual code.  This would obviously be much simpler if the business logic tier was implemented as a more general REST architecture.

* Reordering a shopping list - This seemed to me to imply some sort of drag-and-drop mechanism using JavaScript or CSS transform / animations.  As I have intentionally skirted around presentation this story would be difficult to deliver (or at least would be bad from a usability perspective).

# Healthy Eating - Mayden Coding Challenge

## Structure

The application comprises three main views:

1. A _login_ page.

2. A _shopping list_ page that allows the shopper to add and remove products on their shopping list.

3. A _tracker_ page where the shopper can cross off items on their shopping list.

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

Note that user accounts and persistent data are implemented using in-memory repositories (again for simplicity).

A single user account is hard-coded as follows:
Username:   `shopper`
Password:   `password`

## Selected Stories

For the purposes of illustration, I have excluded two stories from those committed to:

* #9 E-mailing a shopping list - This is not particularly difficult and generally involves a template-based 'view' to generate and style the email, usually more configuration than actual code.  This would obviously be much simpler if the business logic tier was implemented as a more general REST architecture.

* #6 Reordering a shopping list - This seemed to me to imply some sort of drag-and-drop mechanism using JavaScript or CSS transform / animations.  As I have intentionally skirted around presentation this story would be difficult to deliver (or at least would be bad from a usability perspective).

Story #8 "Put a spending limit in place..." appears to be a cut-and-paste duplicate (?) of the previous story, something I only noticed late in the day.  Obviously in a real-world scenario this requirement issue would be taken up with the product owner.  Intentional or not, for the purposes of this exercise I assumed that the spending limit is applied when the shopper is building a shopping list.

Finally, if the team are interested, I have several other repositories for various personal projects they are welcome to browse, in particular:  [Hardware accelerated 3D toolkit](https://github.com/stridecolossus/JOVE).  This project also has an (in progress) [blog](https://stridecolossus.github.io/jove-blog/) documenting design decisions, approaches taken, issues faced, etc.

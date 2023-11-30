# Finance Project

## CPSC 210 Project by Andy Chen

Like many other people, I find managing and budgeting my spending each month to be extremely important but a tedious
task. Online digital payments popularized online subscription services of different platforms. This is why my project
will be an application to **record budgets and personal
finances.** Users can add new subscriptions and expenses into *a list of different expenses*. They can then choose to
either *pay* or *remove* each subscription or expense. In addition, the user can also *increase the budget of each list*

**Possible X Classes (Subscription):**

- Netflix Subscription
- YouTube Premium Subscription
- Spotify Premium Subscription
- Vehicle Finance
- Amazon Prime Subscription

**Possible Y Classes (SubscriptionList):**

- Academic expenses
- Entertainment subscription
- Living expenses

## User Stories

- As a user, I want to be able to add a subscription to my subscriptions
- As a user, I want to be able to view the list of subscription in each of my subscriptions
- As a user, I want to be able to mark a subscription as *paid*
- As a user, I want to be able to remove a subscription from my subscriptions
- As a user, I want to be able to add funds to my subscriptions
- As a user, I want to be able to see how much fund is available for each Subscription list
- As a user, I want to be able to save my subscriptions and budgets to file (if I choose to)
- As a user, I want to be able to be able to load my subscriptions and budgets from file (if I choose to)

## Instructions for Grader

- You can add a subscription to subscriptions by clicking on Add Subscriptions and then following the instructions given
in the program.
- You can view the list of subscriptions by clicking on the View Subscriptions button.
- You can mark a subscription as *paid* by clicking on the Pay button and following the instructions given by program.
- You can remove a subscription from my subscriptions by Remove button and choosing the subscription you want to remove.
- You can see how much fund is available for each Subscription list by clicking on the View Subscriptions button after 
you've added at least one subscription.
- You can add funds to subscriptions by clicking on the Add Funds button and following the instructions given.
- You can locate my visual component by starting the program and entering main menu.
- You can save the state of my application by clicking on save button.
- You can reload the state of my application by clicking on load save button.

## Phase 4 Part 2

Tue Nov 28 20:21:41 PST 2023\
Entertainment Subscription Added.

Tue Nov 28 20:21:41 PST 2023\
Entertainment Subscription Added.

Tue Nov 28 20:21:41 PST 2023\
Living Expense Subscription Added.

Tue Nov 28 20:21:41 PST 2023\
Academic Expense Subscription Added.

Tue Nov 28 20:21:46 PST 2023\
Entertainment Funds added.

Tue Nov 28 20:21:52 PST 2023\
Entertainment Subscription Paid.

Tue Nov 28 20:21:59 PST 2023\
Entertainment Subscription Removed.

## Phase 4 Part 3

**If you had more time to work on the project, what refactoring might you use to improve your design?**

If I had more time to work on my project, the first thing I would do is to refactor my JPanels and JButtonListeners. 
Currently, all the JButtons are handled by a single ActionListener. This makes the single method 200+ lines long! Due to
my inexperience with the java swing library, I found that this helped me learn how to use the swing library. If I could
implement this change, I could add more clarity and cohesion to my application. 
As it stands at this moment, all of my JPanels are in a single class. If given the opportunity, I would create more 
classes and potentially an abstraction to contain the similar methods that are used throughout the different JPanels. 
This would significantly reduce the number of lines in my coding as well as add more clarity. Furthermore, making this 
change would make it much easier to add more panels in the future if I choose to as I could just reuse the methods
inherited from the abstract class.
# Fetch-Rewards-Coding-Excersise
Had Three Rest Endpoints for Creating Transaction, Spending, getting balance.

Fetch Rewards Coding Exercise - Backend Software Engineering
What do I need to submit?
Please write a web service that accepts HTTP requests and returns responses based on the conditions outlined in the next
section. You can use any language and frameworks you choose.
We must be able to run your web service; provide any documentation necessary to accomplish this as part of the repository you submit.
Please assume the reviewer has not executed an application in your language/framework before when developing your documentation.
What does it need to do?
Background
Our users have points in their accounts. Users only see a single balance in their accounts. But for reporting purposes we actually track their
points per payer/partner. In our system, each transaction record contains: payer (string), points (integer), timestamp (date).
For earning points it is easy to assign a payer, we know which actions earned the points. And thus which partner should be paying for the points.
When a user spends points, they don't know or care which payer the points come from. But, our accounting team does care how the points are
spent. There are two rules for determining what points to "spend" first:
● We want the oldest points to be spent first (oldest based on transaction timestamp, not the order they’re received)
● We want no payer's points to go negative.
We expect your web service to
Provide routes that:
● Add transactions for a specific payer and date.
● Spend points using the rules above and return a list of { "payer": <string>, "points": <integer> } for each call.
● Return all payer point balances.

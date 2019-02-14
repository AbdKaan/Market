# Market
A java assignment to learn threads.

- The supermarket is opened at 09:00:00 and customers may enter until
17:00:00. After 17:00:00 only the customers who have been waiting in the
queues will be processed and the supermarket will be closed.

- The customers will arrive at the supermarket by random time intervals
varying between 2 and 15 minutes.

- Each customer will spend random amount of time between 2 and 30 min-
utes while choosing products to buy.

- They will select from 1000 different products. Note that they may select
1 to 20 different products. From the products they choose, they buy 1-5
samples.

- Each cashier will have their own queue. Customers who are going to pay
will first look for empty queues. If there are multiple cashiers available,
they will select a random one to pay. If there is only one empty queue,
they will choose it. If there are no empty queues, half of the customers
will look for the shortest queue, the other half will choose a random queue
to wait in.

- When the items are processed by the cashier, the customer will wait for
(total quantity of the products they buy)*1 seconds, pay and exit the
supermarket.

- The list of products and prices are provided in "products prices.txt".

- BONUS: Calculate the average waiting time of the customers who select
the shortest queue. Calculate the average waiting time of the customers
who select their queue randomly.

# Office Queue Management System
## SE2

You need to develop a system to manage the queues at public authorities that provide services to the public (e.g., post offices). In the same office, various counters can handle different types of requests (e.g., shipping or accounts management).

The office consists of a set of counters. Counters are usually identified by number (e.g. Counter 1, 2 etc.)

Each counters can handle different types of requests, which are defined at configuration phase. The definition consist of a tag that identifies the request type and an estimate of the time needed to process the request (known as service time).

For every counter the setup must define the type of requests that it can serve.

Citizens that have a request that the office has to handle, specify the request type and receive a ticket with a number. Ticket numbers are unique for the whole office and are numbered. The numbers will be used to call citizens to the correct counter at the right time.

The system maintains different queues for each request type, so it is possible to know the number of waiting people for each request type.

When an operator at a counter is ready, he tell the system to call the next citizen. In practice given a counter identifier the system returns the ticket number that will be handled by that counter.

The next ticket to serve is selected with the following criterion: select the first number from the longest queue the counter can manage (remember, counters can only manage specific request types). If two queues have the same length, the queue associated with request type having the lowest service time is selected.

If all the queues the counter can manage are empty, system does nothing.

Selected tickets are considered served and removed from their queue (which is therefore shortened).

The system sends notifications concerning the length of the queues and the tickets called at the counters. When a new ticket is opened one queue changes, therefore, the following actions must be performed:

notify all the registered recipients that one of the queue lengths has changed
Each time a new ticket is served (with the serveNext() method) the following actions must be performed:

notify that a new ticket number is being served at a given counter;
notify that the length of the queue associated with the type of served ticket has been shortened
The system should also provide an estimate of the waiting (minimum) time for the holder of a given ticket number. The waiting time is evaluated by multiplying the number of tickets that precede the given ticket (in the queue associated with the ticket’s request type) by the service time (of that request type), and then dividing the result by the number of counters that can handle that request type. The result of the previous computation is then further incremented by half of the service time.

It must be possible to know how many clients have been served for each request type.

The system should provide the number of clients each counter has served, further divided by request type.

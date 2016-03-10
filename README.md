# End-To-End-Data-Processing-Task
 An end-to-end data processing task using the DBLP dataset and PostGres as the DBMS

What are the typical steps when one tries to process some data?
First you collect some raw data
Then you store the data in a data processing system in the required format -- in our case it will be a relational DBMS and we are going to use PoseGres. See below. Often you do some additional tasks in this step, like clean the data, extract new features, integrate the data with other datasets, etc.
Then you run queries on the data (and investigate the answers)
Eventually you try to make sense of the query results, possibly using some graphs and plots. Also, you may end up doing these steps multiple times, jumping from one step to another.

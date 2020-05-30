# Question-Difficulty-Calculator

A repository for calculating question difficulty

# Project Description

Calculating question difficulty is an important factor in test creation and question analysis. In order to implement this, a Database table is created for the given question parameters. The questions are read from the database table by creating connection with oracle database by using Java Database Connectivity. The question difficulty calculator consists of maximum weightage for each question parameters which adds to a total of 100. Using the difficulty is calculated with choosing the necessary parameters and question rating is returned as a percentage ratio. With the calculated question rating, the difficulty of each question is updated to the database table as Easy/Medium/Hard using batch processing to improve the performance. Thus, the end result will classify the difficulty of each question which can be retrieved for further enhancement.

# Performance Metrics

100 questions 100 attempts :
  
- Total Question Count = 100

- Record Start Index : 1

- Record End Index : 100

- Final Time Start (s) : 1590825447

- Final Time End (s) : 1590825447

- Final Time Elapsed (s) : 0


10000 question 10000 attempts :

- Total Question Count = 10000

- Final Time Start (s) : 1590825630

- Final Time End (s) : 1590825631

- Final Time Elapsed (s) : 1


100000 question for 1 million attempts :

- Total Question Count = 1000000

- Final Time Start (s) : 1590828877

- Final Time End (s) : 1590828955

- Final Time Elapsed (s) : 78

# Contributors 

 - Sneha S <snehasenthil.ss@gmail.com> 
 

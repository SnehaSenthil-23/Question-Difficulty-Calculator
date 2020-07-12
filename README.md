# Question-Difficulty-Calculator

A repository for calculating question difficulty

# Project Description

Calculating question difficulty is an important factor in test creation and question analysis. In order to implement this, a test data entry is made to a database table. The database table consists of some question parameters, for example : question type, manually assigned difficulty, number of persons answered correct, etc. The questions are read from the database table by creating connection with oracle database by using Java Database Connectivity. The question difficulty calculator consists of a maximum ratio assigned for each of the question parameters which adds to a total of 100. Using the difficulty that is calculated with choosing the necessary parameters and the final question rating is returned as a percentage ratio. With the calculated question rating, the difficulty of each question is updated to the table as Easy/Medium/Hard. This is done using batch processing to improve the performance. Thus, the end result will classify the difficulty of each question. Further enhancement can be made with reducing the performance much more.


# Software Requirements 

- Installation of oracle 11g Express Edition
- Eclipse IDE for 64 bit

# Compilation Tips

- Run TestDataEntryMethod after creating connection with the database
- Compile and run <QuestionDifficulty.java>

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
 

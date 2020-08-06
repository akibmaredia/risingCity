# risingCity 🏙
<div style="text-align: justify">

## Problem statement
   Wayne enterprises is developing a new city. They are constructing many buildings and plan to use software to keep track of all the buildings under construction.

   A building record has the following fields:

   >`buildingNum`: A unique integer identifier for each building.  
   >`executedTime`: total number of days spent so far on this building.  
   >`totalTime`: total number of days needed to complete the construction of the building.

   The needed operations are:

   1. Print (buildingNum) prints the triplet buildingNum, executed_time, total_time.
   2. Print (buildingNum1, buildingNum2) prints all triplets buildingNum, executed_time,
      total_time for which; <br /> buildingNum1 <= buildingNum <= buildingNum2.
   3. Insert (buildingNum, total_time) where buildingNum is different from existing building
      numbers and executed_time = 0.


   In order to complete the given task, Min-Heap and a Red-Black Tree (RBT) will be used.
   Also, you may assume that the number of active buildings will not exceed 2000.

   A min heap should be used to store (buildingNum, executed_time, total_time) triplets
   ordered by executed_time. A suitable mechanism is needed to handle duplicate executed 
   times. An RBT should be used store (buildingNum, executed_time, total_time) triplets
   ordered by buildingNum. You are required to maintain pointers between corresponding
   nodes in the min-heap and RBT.

   Wayne Construction works on one building at a time. When it is time to select a building
   to work on, the building with the lowest executed_time (ties are broken by selecting the
   building with the lowest buildingNum) is selected. The selected building is worked on until
   complete or for 5 days, whichever happens first. If the building completes during this
   period, its number and day of completion is output and it is removed from the data
   structures. Otherwise, the building’s executed_time is updated. In both cases, Wayne
   Construction selects the next building to work on using the selection rule. When no
   building remains, the completion date of the new city is output.

   You are required to maintain a global timer which starts at 0 and increments as the day passes. You cannot insert a building for construction to the data structures unless your global timer equals to the arrival time of the construction. All the time data are given in days.

   ## Input and output requirements

   Example input:
   ```
   0: Insert(5,25)
   2: Insert(9,30)
   7: Insert(30,3)
   9: Print(30)
   10: Insert(1345,12)
   13: Print(10,100)
   ```

   The number at the beginning of each command is the global time when the command has appeared in the system. You can assume this time is an integer in increasing order. When no input remains, construction continues on the remaining buildings until the city is built.

   `Insert(buildingNum,totalTime)` - should produce no output unless buildingNum is a duplicate in which case you should output an error and stop. <br />
   `Print(buildingNum)` - output the (buildingNum, executedTime, totalTime) triplet if the buildingNum exists. If not print (0,0,0). <br />
   `Print(buildingNum1,buildingNum2)` - output all (buildingNum, executedTime, totalTime) triplets separated by commas in a single line including buildingNum1 and buildingNum2; if they exist. If there is no building in the specified range, output (0,0,0). You should not print an additional comma at the end of the line. <br />
   Other output includes completion date of each building and completion date of city.

   Output can be found in "output_file.txt".

   ## Run locally
   * Run this in your terminal
      ```
      $ git clone https://github.com/akibmaredia/risingCity.git 
      ```
   * To enter the **src** folder
      ```
      $ cd risingCity/src
      ```
   * This command will create an executable file called **risingCity**
      ```
      $ make
      ```
   * To run the source with existing input_file.txt
      ```
      $ risingCity input_file.txt
      ```
      Provide any input in this file which matches the [input format](https://github.com/akibmaredia/risingCity#input-and-output-requirements)
   * You can find your output in `output_file.txt`
   * Run this command to clear all the binaries
      ```
      $ make clean
      ```

      
</div>

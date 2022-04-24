# Functions

---
## Main window
- [Create Program] button -> [Create Program] window
- [Load Program] button -> [Load Program] window
- [Performance Archive] button -> [Performance Archive] window
- [Exit] button

## [Create Program] window
- Program details
  - Name
  - Purpose
  - Length
  - [Add Division],[Remove Division] -> add or remove division quantity
  - Double-click on specific division number -> [Add Exercise] window
- Save button
- Exit button

## [Add Exercise] window
- Show list of exercises
- Add Exercise
  - Exercise name
  - Sets
  - Reps
  - Rest time
  - Weight
- [Save] button
- [Return] -> return to [Create Program] window

## [Load Program] window
- Select program
- [Start Program] button -> [Program Information] window
- [Edit Program] button -> [Program Edit] window
- [Delete Program] button -> delete selected program
- [Return] button -> return to main window 

## [Program Information] window
- Shows selected program detail
- [Start] button -> [Program Operation] window -> start program on current week and division
- [Exit] button -> return to [Load Program] window

## [Program operation] window
- shows exercise detail
- [Start] button -> start stopwatch -> switch to [Pause] button
- [Done] button -> [Rest] window
- [Pause] button -> [Pause] window

## [Rest] window
- Rest time in 'mm:ss' format
- When 'Time left' becomes 00:00, automatically start the next exercise

## [Pause] window
- [Resume] button
- [Save and Exit] button
- [Exit without saving] button

## [Program Edit] window
- Shows information of selected program in [Create Program] window format 
- [Save] button -> save the changes
- [Return] button -> return to [Load Program] window

## [Performance Archive] window
- Select the program archive to be shown
- [Return] button -> return to main window
- [Delete] button -> delete the selected program archive
- [View] button -> [Archive view] window

## [Archive View] window
- [Week - Division] tab
  - Exercise Name
  - Set
  - Reps
  - Weight
  - Rest
  - Time consumed
- [Return] button -> return to [Performance Archive] window

---
# Validation Checks

---
## Common validations
- No item selected validation
  - When any action button is pressed without item selection
  - [ERROR MESSAGE] No Item Selected!
- Data unsaved validations
  - When trying to exit window without saving data or window is forced to close
  - [MESSAGE] Are You Sure To Exit Without Saving?

## Program Management validations
- Input blank validation
  - When, trying to save with blank cells
  - [ERROR MESSAGE] "Cell name" is blank!
- Input mismatch validation(Number)
  - When non-numeric value is typed in text fields requiring numeric value  
  - [ERROR MESSAGE] "Cell name" must be numeric!
  - When value given is less than or equal to 0
  - [ERROR MESSAGE] "Cell name" must be bigger than 0!
- Input mismatch validation(Rest Time)
  - When, trying to save program with rest time cell equal to '00:00'
  - [ERROR MESSAGE] Rest Time must be bigger than 00:00!
- Duplicate exercises in division validation
  - When, trying to save exercise already in the list
  - [ERROR MESSAGE] Exercise already in the list!
- [Delete Division] button validation
  - When [Delete Division] is pressed with an empty division list
  - [ERROR MESSAGE] No Division Found!
- [Delete Exercise] button validation
  - When [Delete] button is pressed with an empty exercise list
  - [ERROR MESSAGE] No Exercise Found!
- No exercise found validation
  - When [Save] button is pressed with a division containing empty exercise list
  - [ERROR MESSAGE] No Exercise Found In Division "Division number"!

## Program Operation validations
- Program completion validation
  - When program progress reaches to be equal to its total length
  - [MESSAGE] Program Completed!
    - Insert new performance data of the completed program in performance archive
- Program Close Validation
  - When program operation window is forced to close
  - [MESSAGE] Program still in progress! Are you sure to Exit?

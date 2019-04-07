# How to use the Skeleton

## What is the purpose of the skeleton code?
The skeleton code provides a framework from which you can start focussing on the 
important aspects of the project at hand (as well as providing a framework with 
which we automatically test your work).

The usage of the skeleton is non-negotiable, your output should match the example output exactly.

# Update on the title

PLEASE ADD THIS IF YOU HAVE ALREADY STARTED WITH THE PROJECT.

You have to use the following code as your title:
```java
            StdOut.println("****************************************************************************");
	        StdOut.println("*  _______  _______  __    _  __    _  _______  _______  _______  _   ___  *"+
						"\n* |       ||       ||  |  | ||  |  | ||       ||       ||       || | |   | *"+
						"\n* |       ||   _   ||   |_| ||   |_| ||    ___||       ||_     _|| |_|   | *"+
						"\n* |       ||  | |  ||       ||       ||   |___ |       |  |   |  |       | *"+
						"\n* |      _||  |_|  ||  _    ||  _    ||    ___||      _|  |   |  |___    | *"+
						"\n* |     |_ |       || | |   || | |   ||   |___ |     |_   |   |      |   | *"+
						"\n* |_______||_______||_|  |__||_|  |__||_______||_______|  |___|      |___| *");
	        StdOut.println("*                                                                          *");
	        StdOut.println("****************************************************************************");
```
If you don't, you will lose marks. This has to display only once as your game starts at the very beginning.

## Step 1: Uploading the files

To update your repo (short for repository), open the terminal in the directory/folder you cloned your repo. Then type into the terminal `git pull`.

Download the `project.zip` file from SUNLearn.

Extract this into your repo directory/folder.

Now add the new files to your repo. To do this, once again open the terminal in the repo's directory/folder. Then type the following commands:

1. ``git add project/``
2. ``git commit -m "Adding project files"``
3. ``git push``

## Step 2: See whether you pass the tests

Go onto git.cs.sun.ac.za and see how you do! This should be a good indication of how well you will do in the project, though we might have some secret cases for the day of the demo.

## Step 3: Getting started

Now, in your repo directory try compiling your file with `javac connect4.java`. Then try running it with `java connect4`.

Running it now with the command `java connect4` will take input in, but it won't do anything with it.

```java
public static void main (String[] args) {
        //1. TODO: Read in a command line argument that states whether the program will be in either terminal
        //      mode (T) or in GUI mode (G) [Hint: use args and the variable below]
        
        //Sets whether the game is in terminal mode or GUI mode
        boolean gui = false;  
        //Variable that you can use for your input
        int input = 0;
        //Some code has been ommited for the sake of brevity
        
        if (!gui) {
            //Terminal mode

            //2. TODO: Display 10 line title

            while (true) {
            //Some code has been ommited for the sake of brevity
                StdOut.println("Choose your move: \n 1. Play Normal \n 2. Play Bomb ("+curppowers[0]+" left) \n 3. Play Teleportation ("+curppowers[1]+" left) \n 4. Play Colour Changer ("+curppowers[2]+" left)\n 5. Display Gameboard \n 6. Load Test File \n 0. Exit");
                //3. TODO: Read in chosen move, check that the data is numeric
                StdIn.readInt();
                 switch(input) {
                    case 0: Exit();
                            break;                            
```

Looking at the first *TODO: Read in a command line argument that states whether the program will be in either terminal mode (T) or in GUI mode (G) [Hint: use args and the variable below]*: 
1. You have to read in a single character from the command line (either a 'T' or a 'G' for the two modes). Based on that, you change the value of the boolean `gui` to either `true` (when you will run it in GUI mode) or `false` (when you run it in terminal mode). Currently, `gui` is set to `false`, so it will run in terminal mode even without you doing anything there. You have to take a 'T' or 'G' as a command line argument. **If it does not, you will fail a test case**.

*TODO: Display 10 line title*:

2.  You can create any 10 line title display, as long as each line starts with a `*`. **If it does not, you will fail a test case**.

*TODO: Read in chosen move, check that the data is numeric*:

3. StdIn.readInt() forces the program to wait for input, however it doesn't do anything with that input at the moment. https://introcs.cs.princeton.edu/java/stdlib/javadoc/StdIn.html

You will have to check that your input data is an integer between 0 and 9 to pass all the tests for this case. However, simply reading in `int`'s without catching incorrect input will still give you some passes. Look at `readInt()` if you're not interested in going deeper or want to get started with reading input: https://introcs.cs.princeton.edu/java/stdlib/javadoc/StdIn.html#readInt()

More will be added as we move through this project. Ask your demi's for help or go to the mentor sessions.# Connect4

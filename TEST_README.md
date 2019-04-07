# How to run the test script

## Step 0: Make sure that you used the correct skeleton and that you've added all changes

This is critical to make sure you don't needlessly lose marks. If you haven't started with the project, make sure you use the skeleton provided with these files. 

One of the changes include the use of a default title:

````java
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
````

(It looks better in terminal, trust me.)

## Step 1: Extract the test script and test cases

Extract the file ``project-114-test.zip`` into the same directory (or "folder") that your ``src`` directory is in (please *not inside* your ``src`` directory).

DO NOT just right click and select Extract Here. This will put your files in a folder. 

Rather copy the ``project-114-test.zip`` into the directory, open the terminal there and run the command ``unzip project-114-test.zip`` to extract the files without putting them into a folder.

## Step 2: Make sure your project is in the right place and has the right name

We've changed the naming convention for the project a little bit (sorry about that) to make life a little bit easier for the elves working in Santa's testing workshop.

*Your project has to be named SU followed by your student number (no space in between).* An example would be ``SU17522021.java``. 

Please remember to change your class name in the Java file as well.

## Step 3: Running the Test Script

You can run the test script (it's the file called ``test`` with no extension) by opening the terminal in the directory that you extracted into in Step 1.

Once you have the terminal open, type in ``chmod 777 test``.

After that type in ``./test`` followed by your student number, e.g. ``./test 17522021``. This should run the test cases and show you which ones you pass or fail.

Try to see how many of these public test cases you have passed. This will give you a good indication of what your mark will be, however there are 20 more unseen cases still left for the hand-in, most of which test variations of these cases.

If you are really lazy, like me, you don't want to type in your student number *each and every time* you run your test cases. So I've added another script, called ``make``, that you can edit to add in your student number where it says ``your_student_number``. In order to run it, you have to do ``chmod 777 make`` and then you call make with ``./make``.

## Step 4: Submission

You have to *submit your files via SUNLearn* with the given naming convention. Please follow this, or we have to give you zero. :(

## Step 5: Gitlab

Your repos will remain active and all the new test cases are on Gitlab.

You can still use Gitlab to test your files and as a version control system.  We encourage you to do so. But we also understand that a large portion of the class is unfamiliar with the platform, which is why we are using *SUNLearn as the only official submission method*.

Please note that Gitlab's testing still uses the old naming convention of ``connect4.java`` and only tests the file if it is in the ``project`` folder.

## Step 6: Code your Project!

Enjoy making your project! Should any questions arise, don't hesitate to use the forum to post questions or email me. ``17522021@sun.ac.za`` 
# AIracers
Driving application that self-corrects through machine learning. Each simulated vehicle adapts to the user's path.

*** HOW TO USE ***

Upon launching the application please ignore the green car for now.
Use the W key to move the finish line (the white bar) across the screen.
Use the E key to move the starting point (the yellow square) across the screen.

The cars will start on the yellow box, and learn to find the finish line as optimally as possible.
PLEASE NOTE: as of now, the finish line can only accept cars from the left side. Meaning if a car attempts to cross the finish line in reverse (from the right side, it will not be able to do so)

Use the R key to create a checkpoint. It is like a finish line but can accept cars from all angles. Use them to guide the cars through difficult courses until they can learn to navigate to the finish.


Use the left mouse button to draw a wall on the screen.
Use the Q key to switch between erase and and draw mode.
Use the left mouse buttom to erase walls on the screen.

You can save your tracks and open them up again later.
PLEASE NOTE: as of now, the "save path" function does not work. You will not be able to save your evolved AI.

Once you have drawn a course with a proper starting and ending point, go to EDIT>Number of Cars and specify how many AI will be competing with one another.
The simulation will start.

*** THE SIMULATION *** 

Survival of the fittest. Only one car will be cloned to the next generation, but with some mutations.

You can specify the magnitude of mutation of the clones of the next generation through EDIT>Set Threshold.
However, to encourage fierce competition, the threshold shrinks by a certain percentage every generation.
If you do not wish this to be the case, navigate to EDIT>Set Limit, which sets the minimum value the threshold can ever be.
By default, the limit is set to 0.01.

The purpose of RAND is to mutate a certain percentage of clones completely randomly, without any regard for the threshold. By default it is set to 0.05, or 5% of clones. You can also set this in the EDIT menu.

You may keep track of the best times measured in "ticks," on the bottom panel. You may also see which car number is the parent of this generation.

*** OPTIMIZATIONS ***

All the features in the VIEW panel reduce the amount that has to be drawn on the screen, and saves computational power if you are simulating thousands of cars.

Use the EDIT>Reduce Track feature to get rid of individual walls that are a certain amount of pixels close to one another. I recommend using this everytime you finish drawing a course. A reduction of 5 pixels is enough.
Use the EDIT>Remove Doubles feature to get rid of individual walls that are placed perfectly on top of each other. However, EDIT>Reduce Track will also do the same thing. So it is never necessary.

*** FUN ***

It's good to have fun too though, so I left a green car in that can be controlled by the user through the arrow keys.
Up is accelerate.
Down is decelerate.
Left is left, Right is right.

Try racing against your AI!

*** ABOUT THE PROGRAM *** 

Go ahead and move the starting point and finish lines to opposite sides of the canvas. Then, enter one car into the simulation.
The vehicle will try to navigate to the closest point to it. However, as it accelerates, tighter turns become more difficult. As a result, it is possible that the vehicle will overshoot certain points, using them like a sort of gravity-assist in their course.
Red dots mean to decelerate. Green dots mean to accelerate. Dark dots serve as the middle point between red and green, and are just neutral.

To prevent the vehicle from pacing back and forth between two points, it is not allowed to visit a point that it has already made contact with in the last 7 point contacts.
Each of the numbers above the points goes up whenever the vehicle makes contact with any of its points. Whenever the vehicle touches a point, that individual point will return to 1.
The car is not allowed to aim for a point with a value less than 7.


*** THANK YOU FOR TRYING MY PROGRAM ***
- Michael

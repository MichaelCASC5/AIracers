
# AI Racers

Driving application that self-corrects through machine learning. Each simulated vehicle adapts to the user's path.
## Installation

Install by cloning the repo, navigating into the src directory, and compiling and executing the code

```bash
  git clone https://github.com/MichaelCASC5/AIracers.git
  cd src/
  javac Driver.java
  java Driver.java
```

## Usage


- Use [W] to move the finish line (the white bar) across the screen.
- Use [E] to move the starting point (the yellow square) across the screen.
- Use [R] to create a checkpoint. It is like a finish line but can accept cars from all angles. Use them to guide cars through difficult courses.
- Use [Q] to switch between erase and and draw mode.
- Use the left mouse button to draw or erase a wall on the screen.
- The finish line can only accept cars from the left side.

Survival of the fittest. Only one car will be cloned to the next generation, but with some mutations. Specify the magnitude of mutation through:
```bash
EDIT > Set Threshold
```
To encourage competition, the threshold shrinks by a certain percentage every generation.
If you do not wish this to be the case:

```bash
EDIT > Set Limit
```
which sets the minimum value the threshold can ever be.
By default, the limit is set to 0.01.
#
RAND mutates a percentage of clones randomly, independent of threshold. By default it is set to 0.05 (5% of clones)

"Ticks" keeps track of the best time measured in ticks.
## Features

- Real-time graphical display
- User can draw obstacles on the screen
- Working tool bar to access many Features
- Specify number of cars in simulation
- Specify the minimum mutation rate
- Save and load obstacle courses
- Reduce and optimize the obstacles in the course
- Custom display options (show car intelligence, designation, and most fit)

## Optimizations

Features in the VIEW panel reduce the amount drawn on the screen, saving computational power when simulating tremendous numbers of cars.

Get rid of walls that are too close to one another. A reduction of 5 pixels is enough. The optimization is useful after drawing a course:
```bash
EDIT > Reduce Track
```
Get rid of individual walls placed directly on top of each other. 

```bash
EDIT > Remove Doubles
```

## Roadmap

- Implement the "save path" function, which does not work. You will not be able to save nor load your evolved AI
- Fix crashing when creating a new simulation after working with thousands of cars

## Lessons Learned

Machines can find solutions that humans don't usually concieve, and they find solutions that are incompatible with human nature. In this simulation, the ai cars can find frame-perfect ways to abuse the collision detection system and teleport forward by a few pixels to gain the advantage over their opponents.


## Support

For support or feedback, email michaelcalle14@gmail.com

## Related

This project has a sequel in my neural network project, which uses a machine learning genetic algorithm to train a neural network implemented totally from scratch in C++. It can be found here:

- [Neural Network](https://github.com/MichaelCASC5/NeuralNetwork)


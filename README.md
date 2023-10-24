# Description

It is a Java project which emulates an evolution of animals on a defined map. Play with the parameters and enjoy the animal evolution!
GUI was created using JavaFX. 

![image](https://github.com/DroppedHard/generator-ewolucyjny/assets/46939111/de5806ce-c1af-4953-8908-7421946e3014)

Project was a part of a object-oriented programming course at AGH UST.

# Set-up

Build - Gradle
JDK - 17.0.7 (Oracle OpenJDK)
IDE - IntelliJ IDEA 2023.1.1 Ultimate edition

# How it works?

## Config

Here you define simulation parameters. Please check the terminal for error message if the simulation does not run after pressing "Start simulation" button.
To quickly start up the simulation you can use pre-defined configs which will show you basic features of each variant (+ basic one, just for simplicity).
Additionally you can run multiple simulations, but please concider your computer power before creating large number of simulations.

### Parameters specification

Zacznę od wyjaśnienia głównego interfejsu, gdzie definiujemy parametry symulacji:
- **Width** and **height** define how many squares will a map have.
- **Map variant** defines an event happening when an animal oves on the edge of a map:
  - **Earth globe** - Eastern and western sides will change position of an animal to the other side, where northern and southern will only change the direction of an animal (as if the map was a globe)
  - **Hell portal** - Animal is teleported to the random position on a map and loses energy as if it reproduced.
- **Start grass** defines how many grass will be initially on the map
- **Grass energy** defines how many energy each tuft grants.
- **Growing grass** defines how much grass grow each round.
- **Growing variant** defines where grass prefers to grow (using Pareto rule, 80% use preferred spots, 20% the rest):
  - **Wooded equator** - grass prefers to grow on the horizontal line in the middle of the map.
  - **Acid corpses** - grass prefers to grow where least animals die.
- **Start animals** defines how much animals will be generated at the beginning
- **Start animal energy** defines how much energy will animals have initially
- **Healthy energy** defines how much energy will render an animal healthy (ready to reproduce)
- **Populate energy taken** defines how much energy will the animal use to reproduce
- **Min mutations** and **max mutations** define borders for how many mutations are available in offspring.
- **Mutation variant** defines how the mutation will work:
  - **Full random** - gene is changed to a random number
  - **Small correction** - gene is lowered or increased by 1
- **Genome length** defines how long animal's genome will be
- **Behaviour variant** defines how animals will behave:
  - **Full fore-ordination** - animal moves based on each genome from left to right
  - **A bit of chaos** - animal activates next genome with 80% chance (like above), or jumps to a random other genome with 20% chance.

## Map

Map includes jungle fields (green squares) and steppe fields (white fields). Grass is more likely to grow on green fields than white fields in 80:20 ratio respectively.
Map size can be changed, but if the growing variant is **wooded equator** the height must be divisible by 5 to fulfill the 80:20 rule (the equator will not fill 20% of available space).

## Animals

Animals wonder around the map looking for food (grass) and mates (for simplicity we assume they are monecious). We can see them in packs or wandering alone.

### Movement and genome

Animal's genome defines where and how it moves. Each day in movement phase it turns to proper direction and moves forward. 
The directions are defined as follows:
- **0** - North
- **1** - North-East
- **2** - East
- **3** - South-East
- **4** - South
- **5** - South-West
- **6** - West
- **7** - North-West

The direction is defined by current active genome, which selection is based on the *behaviour variant* selected.

### Energy

Each animal have their own energy. It is represented as a **number of days it can survive without food**. You can check the average of all *living* animals.
If the field is occupied by **at least** one animal it is represented by animal shape. If there are ore than 1 animals on the field, the most energetic one is shown. 

Each evergy level of an animal is displayed in 4 variants:
- ![image](https://github.com/DroppedHard/generator-ewolucyjny/assets/46939111/7d3b0fc4-221e-4f44-9f94-f11d34c7c13f) **Healthy** - the animal has the required energy defined previously as *healthy energy*.
- ![image](https://github.com/DroppedHard/generator-ewolucyjny/assets/46939111/6ca11293-9414-4c7b-a613-674cdc36f3c3) **Unhealthy** - the animal's energy is between *healthy energy* and *healthy energy* / 4
- ![image](https://github.com/DroppedHard/generator-ewolucyjny/assets/46939111/afbc900e-87bc-45c2-b340-d6693b23a250) **Dying** - the animal's energy is below *healthy energy* / 4, but still above 0
- ![image](https://github.com/DroppedHard/generator-ewolucyjny/assets/46939111/1e1f5fb9-3583-4c76-8b5c-f02c7e5191f7) **Dead** - the animal died this day - its energy reached 0. Dead animals are cleaned on the next day.

To live the animal needs to eat grass which will increase its energy level by *grass energy* defined in simulation configuration view. 
If more than one animal are "fighting" for the grass the conflict is resolved based on (in given order):
1. Energy
2. Superiority
3. Number of offspring

### Reproduction

If two or more **healthy** animals are on one field a reproduction is guaranteed. 
The offspring:
- Receives the energy used by parents
- The newborn genome is based on the parents' genome. Additionally the genome undergoes mutations defined previously as *mutation variant*
- It starts on the same field as its parents.

## Simulation

Each day in our simulation looks as follows:
1. Dead animals are removed from the map
2. Animals movement
3. Animals eat grass (if possible)
4. Animals reproduce
5. New grass is created

# Future of the project

It was fun to create this project and see final results, but I will leave it as it is. A greast showcase what we do at AGH UST.






# Java Optimization Algorithms Collection

*Java Optimization Algorithms Collection* is a modular and educational repository featuring implementations of popular optimization algorithms inspired by nature and mathematical heuristics. Designed with clarity and experimentation in mind, each algorithm resides in its own package, fully explained and testable.

## Purpose

This repository serves as a learning, research, and reference tool.
It demonstrates Java-based implementations of both **classic** and **nature-inspired** optimization algorithms — ideal for academic use, project prototyping, and algorithmic comparisons.

## Included Algorithms

| Package             | Algorithms Implemented            |
| ------------------- | --------------------------------- |
| `swarm.pso`         | Particle Swarm Optimization (PSO) |
| `swarm.gwo`         | Grey Wolf Optimizer (GWO)         |
| `swarm.abc`         | Artificial Bee Colony (ABC)       |
| `swarm.firefly`     | Firefly Algorithm                 |
| `swarm.aco`         | Ant Colony Optimization (ACO)     |
| `classic.hillclimb` | Hill Climbing                     |
| `classic.simanneal` | Simulated Annealing               |
| `classic.tabu`      | Tabu Search                       |
| `classic.threshold` | Threshold Accepting               |
| `classic.ils`       | Iterated Local Search             |

> Patterns are added and refined continuously as the project evolves.

## Project Structure

```
optimization-algorithms/
├── swarm/
│   ├── pso/
│   ├── gwo/
│   ├── abc/
│   └── ...
├── classic/
│   ├── hillclimb/
│   ├── simanneal/
│   └── ...
├── common/
│   └── utils/   # Math helpers, constraints, random generators
└── Main.java    # Optional interactive runner
```

Each algorithm package typically includes:

* Core algorithm logic
* Custom fitness/problem definitions
* Constraint handling and feasibility checks
* Test classes with real-world or educational use cases

## How to Run

### Requirements

* Java JDK 8 or later
* Any Java IDE (e.g., IntelliJ IDEA, Eclipse) or terminal

### Steps

```
# Compile all algorithms
javac -d out $(find . -name "*.java")

# Run a specific demo
java -cp out swarm.pso.PSODemo
```

Alternatively, open the project in your IDE and run individual test classes.

## Ideal For

* Students studying optimization, heuristics, or AI
* Developers exploring algorithmic problem-solving
* Comparative testing of optimization techniques
* Research and academic projects

## Demo Problems (Examples)

* Maximize profits under resource constraints
* Study schedule optimization
* Function minimization (e.g., Schwefel, Sphere)
* Load balancing and parameter tuning
* Job scheduling and resource allocation

> New test cases and benchmarks will be added continuously.

## Developer

Burak Saltalı
GitHub: [bsaltalii](https://github.com/bsaltalii)

## License

This project is licensed under the MIT License.
See the LICENSE file for more details.

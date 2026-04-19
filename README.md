# MontyHall
Modeling and extending the Monty Hall Problem using OOP.

## Extended Monty Hall Problem: Rules
1. Start with `N` closed doors, only `1` of which contains the prize.
2. Contestant chooses a closed door, or stays with their original choice if applicable.
3. Host opens a closed door that is not currently selected and does not contain the prize.
4. Repeat steps **2** and **3** until only `2` doors remain closed.
5. Contestant either stays with their current choice, or switches to the other closed door.
6. Host opens the last two remaining doors. The contestant wins if their currently chosen door contains the prize.

## Extended Monty Hall Problem: Strategies
- **STAY:** The contestant always stays with their original choice.
  - This strategy has a `1/N` win probability. As N increases, this strategy converges to 0%.
- **RANDOM:** The contestant selects a random closed door each round, which may include the door they have currently selected.
   - This strategy has a `1/2` win probability (50%).
- **SWITCH:** The contestant selects a random closed door each round, excluding the door they have currently selected.
  - It can be determined that, if M<sub>n</sub> denotes a game with N doors, then the win probability is a weighted average of that of the previous two games: $$ M_n = \frac{n-1}{n}M_{n-1} + \frac{1}{n}M_{n-2} $$
  - An exact formula for the win probability if you always switch is as follows: $$ M_n = 1 - \displaystyle\sum_{k=0}^{n}\frac{(-1)^k}{k!} $$
  - This strategy has a win probability slightly above 60%; as the number of doors increases, this win probability converges to exactly `1-1/e` (~63.2%).
- **SMART SWITCH:** The contestant first tries to select a random closed door they have never selected before. If this is not possible, the most recently selected door that is NOT currently selected is chosen.
  - For N < 10, this strategy is worse than or equal to **SWITCH**.
  - For large N, it performs substantially better that **SWITCH**, at around 69%.
  - This is the best strategy I have found so far, at least for very large N.
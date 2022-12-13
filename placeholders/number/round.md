[\<-- Go back to README.md](/README.md)

## Round

Rounds the provided number based on the provided precision and/or rounding behaviour.

### Placeholder pattern

`%formatter_number_round[_[precision]:[rounding_mode]]_<number>%`

### Options

<table>
  <tr>
    <td align="center" nowrap="nowrap">Option</td>
    <td align="center" nowrap="nowrap">Description</td>
  <tr>
  <tr>
    <td nowrap="nowrap"><h4><code>precision</code></h4></td>
    <td rowspan="2">
      <p>How many digits after the decimal point should be kept.</p>
  </tr>
  <tr>
    <td nowrap="nowrap"><b>Type:</b> Number <i>(0 ≤ x)</i><br><b>Required?</b> No<br><b>Default:</b> Config value (<code>expansion.formatter.rounding.precision</code>)</td>
  </tr>
  <tr>
    <td nowrap="nowrap"><h4><code>rounding_mode</code></h4></td>
    <td rowspan="2">
      <p>How the number should be rounded if any fractions are discarded.</p>
      <p>⚠️ <a href="#rounding-modes">See below</a> for available rounding modes. ⚠️</p>
    <td>
  </tr>
  <tr>
    <td nowrap="nowrap"><b>Type:</b> String<br><b>Required?</b> No</td><br><b>Default:</b> Config value (<code>expansion.formatter.rounding.mode</code>)
  </tr>
  <tr>
    <td nowrap="nowrap"><h4><code>number</code></h4></td>
    <td rowspan="2">The number to convert.<td>
  </tr>
  <tr>
    <td nowrap="nowrap"><b>Type:</b> Number<br><b>Required?</b> Yes</td>
  </tr>
</table>

### Rounding modes

- [`up`](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/math/RoundingMode.html#UP)
  - Rounds away from zero. Always increases the digit prior to a non-zero discarded fraction.
  - Examples:
    - `-1.5 -> -2` 
    - `-1.1 -> -2`
    - `-1.0 -> -1`
    - `1.0 -> 1`
    - `1.1 -> 2`
    - `1.5 -> 2`
- [`down`](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/math/RoundingMode.html#DOWN)
  - Rounds towards zero. Always decreases the digit prior to a non-zero discarded fraction.
  - Examples:
    - `-1.5 -> -1`
    - `-1.1 -> -1`
    - `-1.0 -> -1`
    - `1.0 -> 1`
    - `1.1 -> 1`
    - `1.5 -> 1`
- [`ceiling`](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/math/RoundingMode.html#CEILING)
  - Rounds towards positive infinity. Always rounds up if number is positive, otherwise rounds down.
  - Examples:
    - `-1.5 -> -1`
    - `-1.1 -> -1`
    - `-1.0 -> -1`
    - `1.0 -> 1`
    - `1.1 -> 2`
    - `1.5 -> 2`
- [`floor`](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/math/RoundingMode.html#FLOOR)
  - Rounds towards negative infinity. Always rounds down if number is positive, otherwise rounds up.
  - Examples:
    - `-1.5 -> -2`
    - `-1.1 -> -2`
    - `-1.0 -> -1`
    - `1.0 -> 1`
    - `1.1 -> 1`
    - `1.5 -> 1`
- [`half-up`](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/math/RoundingMode.html#HALF_UP)
  - Rounds to nearest neighbour unless both neighbours are equidistant (5) in which case round up. This is what you are usually taught in school.
  - Examples:
    - `-1.5 -> -2`
    - `-1.1 -> -1`
    - `-1.0 -> -1`
    - `1.0 -> 1`
    - `1.1 -> 1`
    - `1.5 -> 2`
- [`half-down`](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/math/RoundingMode.html#HALF_DOWN)
  - Rounds to nearest neighbour unless both neighbours are equidistant (5) in which case round down.
  - Examples:
    - `-1.5 -> -1`
    - `-1.1 -> -1`
    - `-1.0 -> -1`
    - `1.0 -> 1`
    - `1.1 -> 1`
    - `1.5 -> 1`
- [`half-even`](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/math/RoundingMode.html#HALF_EVEN)
  - Rounds to nearest neighbour unless both neighbours are equidistant (5), in which case, round to nearest even neighbour (Up if digit to the left is odd, otherwise down)
  - Examples:
    - `-1.5 -> -2`
    - `-1.1 -> -1`
    - `-1.0 -> -1`
    - `1.0 -> 1`
    - `1.1 -> 1`
    - `1.5 -> 2`

### Examples
```
/papi parse me %formatter_number_round_1.5%             -> 2     # Default is half-up
/papi parse me %formatter_number_round_3:_1.5%          -> 2.000 # Default is half-up
/papi parse me %formatter_number_round_:half-down_1.5%  -> 1
/papi parse me %formatter_number_round_3:half-down_1.5% -> 1.000
```
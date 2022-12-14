[\<-- Go back to README.md](/README.md)

## FromTo

Converts the provided number from one time unit to another. The time unit will also be attached to the number.  
Any remainders/fractions will be discarded.

### Placeholder pattern

`%formatter_number_from:<old_time_unit>_to:<new_time_unit>_<number>%`

### Options

<table>
  <tr>
    <td align="center" nowrap="nowrap">Option</td>
    <td align="center" nowrap="nowrap">Description</td>
  </tr>
  <tr>
    <td nowrap="nowrap"><h4><code>old_time_unit</code></h4></td>
    <td rowspan="2">
      <p>The Time unit to use as the original number's unit.</p>
      <p>⚠️ <a href="#time-units">See below</a> for available time units and their options. ⚠️</p>
    </td>
  </tr>
  <tr>
    <td nowrap="nowrap"><b>Type:</b> String<br><b>Required?</b> Yes</td>
  </tr>
  <tr>
    <td nowrap="nowrap"><h4><code>new_time_unit</code></h4></td>
    <td rowspan="2">
      <p>The Time unit to use as the number's new format.</p>
      <p>⚠️ <a href="#time-units">See below</a> for available time units and their options. ⚠️</p>
    </td>
  </tr>
  <tr>
    <td nowrap="nowrap"><b>Type:</b> String<br><b>Required?</b> Yes</td>
  </tr>
  <tr>
    <td nowrap="nowrap"><h4><code>number</code></h4></td>
    <td rowspan="2">The number to convert.</td>
  </tr>
  <tr>
    <td nowrap="nowrap">
      <b>Type:</b> Number<br>
      <b>Required?</b> Yes
    </td>
  </tr>
</table>

### Time units

| Time Unit   | Available options                             |
|-------------|-----------------------------------------------|
| Day         | `days`, `day`                                 |
| Hour        | `hours`, `hour`, `hrs`                        |
| Minute      | `minutes`, `minute`, `mins`, `min`            |
| Second      | `seconds`, `second`, `secs`, `sec`            |
| Millisecond | `milliseconds`, `millisecond`, `millis`, `ms` |

### Examples
```
/papi parse me %formatter_number_from:secs_to:mins_120% -> 2m
/papi parse me %formatter_number_from:mins_to:hrs_119%  -> 1h
```

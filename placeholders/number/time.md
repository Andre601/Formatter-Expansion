[\<-- Go back to README.md](../../README.md)

## Round

Converts the provided number into a collection of time units.  
The displayed time units depend on if any remainder can be converted into one and the used source unit.

### Placeholder patterns

- `%formatter_number_time_<number>%`
- `%formatter_number_time_<time_unit>_<number>%`

### Options

<table>
  <tr>
    <td align="center" nowrap="nowrap">
      Option
    </td>
    <td align="center" nowrap="nowrap">
      Description
    </td>
  </tr>
  <tr>
    <td nowrap="nowrap">
      <h4><code>time_unit</code></h4>
    </td>
    <td rowspan="2">
      The Time unit to treat the provided number as<br>
      <br>
      <b>Available Time units:</b>
      <ul>
        <li><code>fromMilliseconds</code> / <code>fromMs</code> - Treats the number as milliseconds</li>
        <li><code>fromSeconds</code> / <code>fromSecs</code> - Treats the number as seconds</li>
        <li><code>fromMinutes</code> / <code>fromMins</code> - Treats the number as minutes</li>
        <li><code>fromHours</code> / <code>fromHrs</code> - Treats the number as hours</li>
      </ul>
    </td>
  </tr>
  <tr>
    <td nowrap="nowrap">
      <b>Type:</b> String<br>
      <b>Required?</b> No<br>
      <b>Default:</b> <code>fromSeconds</code>
    </td>
  </tr>
  <tr>
    <td nowrap="nowrap">
      <h4><code>number</code></h4>
    </td>
    <td rowspan="2">
      The number to convert.
    </td>
  </tr>
  <tr>
    <td nowrap="nowrap">
      <b>Type:</b> Number<br>
      <b>Required?</b> Yes<br>
      <b>Condition:</b>
      <ul>
        <li><code>0 ≤ x</code></li>
      </ul>
    </td>
  </tr>
</table>

### Examples
```
/papi parse me %formatter_number_time_100%          -> 1m 40s
/papi parse me %formatter_number_time_fromMs_1200%  -> 1s 200ms
/papi parse me %formatter_number_time_fromSecs_100% -> 1m 40s
/papi parse me %formatter_number_time_fromMins_100% -> 1h 40m
/papi parse me %formatter_number_time_fromHrs_100%  -> 4d 4h
```

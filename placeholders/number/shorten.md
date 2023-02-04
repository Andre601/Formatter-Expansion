[\<-- Go back to README.md](../../README.md)

## Format

Shortens the number by dividing it through 1,000, 1,000,000, etc. and appending a letter at the end.  
As an example `10000` becomes `10k`.

### Placeholder patterns

- `%formatter_number_format_<number>`
- `%formatter_number_format_[locale]:[pattern]_<number>%`
- `%formatter_number_format_truncate_<number>%`

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
      <h4><code>number</code></h4>
    </td>
    <td rowspan="2">
      The number to shorten.
    </td>
  </tr>
  <tr>
    <td nowrap="nowrap">
      <b>Type:</b> Number<br>
      <b>Required?</b> Yes
    </td>
  </tr>
</table>

### Examples

```
/papi parse me %formatter_number_shorten_999% -> 999
/papi parse me %formatter_number_shorten_1000% -> 1K
/papi parse me %formatter_number_shorten_10000% -> 10K
/papi parse me %formatter_number_shorten_100000% -> 100K
/papi parse me %formatter_number_shorten_1000000% -> 1M
```

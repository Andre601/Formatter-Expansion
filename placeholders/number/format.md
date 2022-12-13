[\<-- Go back to README.md](/README.md)

## Format

Formats the provided number by adding separators such as `,` to it.

### Placeholder pattern

`%formatter_number_format[_[locale]:[pattern]]_<number>%`

### Options

<table>
  <tr>
    <td align="center" nowrap="nowrap">Option</td>
    <td align="center" nowrap="nowrap">Description</td>
  </tr>
  <tr>
    <td nowrap="nowrap"><h4><code>locale</code></h4></td>
    <td rowspan="2">
      <p>The country code to use for the pattern.</p>
      <p>⚠️ The country code needs to use <code>-</code> instead of <code>_</code> (i.e. <code>en-US</code> instead of <code>en_US</code>) ⚠️</p>
    </td>
  </tr>
  <tr>
    <td nowrap="nowrap"><b>Type:</b> String<br><b>Required?</b> No<br><b>Default:</b> Config value (<code>expansion.formatter.locale</code>)</td>
  </tr>
  <tr>
    <td nowrap="nowrap"><h4><code>pattern</code></h4></td>
    <td rowspan="2">
      <p>The number pattern to use.</p>
      <p>⚠️ The pattern uses the <a href="https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/text/DecimalFormat.html">DecimalFormat</a> from Java. ⚠️</p>
    </td>
  </tr>
  <tr>
    <td nowrap="nowrap"><b>Type:</b> String<br><b>Required?</b> No<br><b>Default:</b> Config value (<code>expansion.formatter.format</code>)</td>
  </tr>
  <tr>
    <td nowrap="nowrap"><h4><code>number</code></h4></td>
    <td rowspan="2">The number to format.</td>
  </tr>
  <tr>
    <td nowrap="nowrap"><b>Type:</b> Number<br><b>Required?</b> Yes</td>
  </tr>
</table>

### Examples
```
/papi parse me %formatter_number_format_1000%             -> 1,000
/papi parse me %formatter_number_format_de-CH:_1000%      -> 1'000
/papi parse me %formatter_number_format_:##,##_1000%      -> 10,00
/papi parse me %formatter_number_format_de-CH:##,##_1000% -> 10'00
```
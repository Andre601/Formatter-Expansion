[\<-- Go back to README.md](../../README.md)

## Uppercase

Returns the provided text all uppercase.

### Placeholder patterns

- `%formatter_text_uppercase_<text>%`

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
      <h4>
        <code>text</code>
      </h4>
    </td>
    <td rowspan="2">
      The text to turn uppercase.
    </td>
  </tr>
  <tr>
    <td nowrap="nowrap">
      <b>Type:</b> String<br>
      <b>Required?</b> Yes
    </td>
  </tr>
</table>

### Examples
```
/papi parse me %formatter_text_uppercase_Uppercase% -> UPPERCASE
```

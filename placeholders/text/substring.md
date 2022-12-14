[\<-- Go back to README.md](/README.md)

## Substring

Returns a specific part of the text based on the provided start and end index.

### Placeholder pattern

`%formatter_text_substring_[start]:[end]_<text>%`

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
      <h4><code>start</code></h4>
    </td>
    <td rowspan="2">
      <p>The start index to indicate the beginning of the text.</p>
      <p>⚠️ The number is 0-indexed, meaning 0=1, 1=2, etc. ⚠️</p>
    </td>
  </tr>
  <tr>
    <td nowrap="nowrap">
      <b>Type:</b> Number<br>
      <b>Required?</b> No<br>
      <b>Conditions:</b>
      <ul>
        <li><code>0 ≤ x ≤ (text.length() - 1)</code></li>
      </ul>
      <b>Default:</b> <code>0</code>
    </td>
  </tr>
  <tr>
    <td nowrap="nowrap">
      <h4><code>end</code></h4>
    </td>
    <td rowspan="2">
      The end index to indicate the end of the text.
    </td>
  </tr>
  <tr>
    <td nowrap="nowrap">
      <b>Type:</b> Number<br>
      <b>Required?</b> No<br>
      <b>Conditions:</b><br>
      <ul>
        <li><code>0 ≤ x ≤ text.length()</code></li>
        <li><code>start < x</code></li>
      </ul>
      <b>Default:</b> <code>text.length()</code>
    </td>
  </tr>
  <tr>
    <td nowrap="nowrap">
      <h4><code>text</code></h4>
    </td>
    <td rowspan="2">
      The text to get the substring from.
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
/papi parse me %formatter_text_substring_3:9_Substring% -> string
/papi parse me %formatter_text_substring_:3_Substring%  -> Sub
/papi parse me %formatter_text_substring_3:_Substring%  -> string
```

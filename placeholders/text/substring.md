[\<-- Go back to README.md](/README.md)

## Substring

Returns a specific part of the text based on the provided start and end index.

### Placeholder pattern

`%formatter_text_substring_[start]:[end]_<text>%`

### Options

<table>
  <tr>
    <td align="center" nowrap="nowrap">Option</td>
    <td align="center" nowrap="nowrap">Description</td>
  </tr>
  <tr>
    <td nowrap="nowrap"><h4><code>start</code></h4></td>
    <td rowspan="2">
      <p>The start index to indicate the beginning of the text.</p>
      <p>⚠️ The number is 0-based, meaning 0 equals 1, 1 equals 2 and so on. ⚠️</p>
    </td>
  </tr>
  <tr>
    <td nowrap="nowrap"><b>Type:</b> Number <i>(0 ≤ x ≤ (<code>text.length()</code> - 1))</i><br><b>Required?</b> No<br><b>Default:</b> 0</td>
  </tr>
  <tr>
    <td nowrap="nowrap"><h4><code>end</code></h4></td>
    <td rowspan="2">The end index to indicate the end of the text.</td>
  </tr>
  <tr>
    <td nowrap="nowrap"><b>Type:</b> Number <i>(0 ≤ x ≤ <code>text.length()</code>)</i><br><b>Required?</b> No<br><b>Default:</b> <code>text.length()</code></td>
  </tr>
  <tr>
    <td nowrap="nowrap"><h4><code>text</code></h4></td>
    <td rowspan="2">The text to get the substring from.</td>
  </tr>
  <tr>
    <td nowrap="nowrap"><b>Type:</b> String<br><b>Required?</b> Yes
  </tr>
</table>

### Examples
```
/papi parse me %formatter_text_substring_3:9_Substring% -> string
/papi parse me %formatter_text_substring_:3_Substring%  -> Sub
/papi parse me %formatter_text_substring_3:_Substring%  -> string
```
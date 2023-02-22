[\<-- Go back to README.md](../../README.md)

## Substring

Returns a specific part of the text based on the provided start and end index.

### Placeholder patterns

- `%formatter_text_substring_[start]:[end]_<text>%`

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
      The start index to indicate the beginning of the text.<br>
      When no valid number is given will the 0-indexed position of the first String matching <code>start</code> be used.<br>
      <br>
      ⚠️ The number is 0-indexed, meaning 0=1, 1=2, etc. ⚠️
    </td>
  </tr>
  <tr>
    <td nowrap="nowrap">
      <b>Type:</b> Text<br>
      <b>Required?</b> No<br>
      <b>Conditions:</b>
      <ul>
        <li>(If number) <code>0 ≤ x ≤ (text.length() - 1)</code></li>
      </ul>
      <b>Default:</b> <code>0</code>
    </td>
  </tr>
  <tr>
    <td nowrap="nowrap">
      <h4><code>end</code></h4>
    </td>
    <td rowspan="2">
      The end index to indicate the end of the text.<br>
      When no valid number is given will the 0-indexed position of the first String matching <code>end</code> be used.
    </td>
  </tr>
  <tr>
    <td nowrap="nowrap">
      <b>Type:</b> Text<br>
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
/papi parse me %formatter_text_substring_3:9_Substring%                -> string
/papi parse me %formatter_text_substring_:3_Substring%                 -> Sub
/papi parse me %formatter_text_substring_3:_Substring%                 -> string
/papi parse me %formatter_text_substring_,:_Substring,Another String%  -> ,Another String
/papi parse me %formatter_text_substring_:,_Substring,Another String%  -> Substring
```

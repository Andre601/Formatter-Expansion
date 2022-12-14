[\<-- Go back to README.md](/README.md)

## Replace

Replaces any appearance of the provided `<target>` with the provided `<replacement>` in the text.

### Placeholder pattern

`%formatter_text_replace_<target>_<replacement>_<text>%`

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
      <h4><code>target</code></h4>
    </td>
    <td rowspan="2">
      <p>The text to target and replace with <code>replacement</code>.<br>Text can be an empty String.</p>
      <p>⚠️ You have to use <code>{{u}}</code> to target <code>_</code> ⚠️</p>
    </td>
  </tr>
  <tr>
    <td nowrap="nowrap">
      <b>Type:</b> String<br>
      <b>Required?</b> Yes
    </td>
  </tr>
  <tr>
    <td nowrap="nowrap"><h4><code>replacement</code></h4></td>
    <td rowspan="2">
      <p>The text to replace the target with.<br>Text can be an empty String.</p>
      <p>⚠️ You have to use <code>{{u}}</code> to replace <code>_</code> ⚠️</p>
    </td>
  </tr>
  <tr>
    <td nowrap="nowrap">
      <b>Type:</b> String<br>
      <b>Required?</b> Yes
    </td>
  </tr>
  <tr>
    <td nowrap="nowrap">
      <h4><code>text</code></h4>
    </td>
    <td rowspan="2">
      The text in which the provided <code>target</code> should be replaced by the <code>replacement</code>.
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
/papi parse me %formatter_text_replace_ __Re pla ce%        -> Replace
/papi parse me %formatter_text_replace_{{u}}__Re_pla_ce me% -> Replace me
```

[wiki]: https://wiki.powerplugins.net/wiki/formatter-expansion
[changelog]: https://github.com/Andre601/Formatter-Expansion/blob/master/CHANGELOG.md

# Repository Archived
This repository has been archived. You can find the up-to-date source of it at codeberg:  
https://codeberg.org/Andre601/Formatter-Expansion

# Formatter Expansion

<a href="https://discord.gg/6dazXp6" target="_blank">
  <img alt="discord" src="https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@2/assets/minimal/social/discord-singular_vector.svg" height="64" align="right">
</a>
<a href="https://app.revolt.chat/invite/74TpERXA" target="_blank">
  <img alt="revolt" src="https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@2/assets/minimal/social/revolt-singular_vector.svg" height="64" align="right">
</a>

> [!IMPORTANT]  
> The Formatter expansion uses Paper 1.19.3 and therefore builds against Java 17.  
> This makes it incompatible with older Java Distributions. Older Minecraft versions *may* be supported, but it isn't guaranteed.
> 
> Please make sure to use **at least** Java 17 or else you may get a `UnsupportedClassVersionError` when it loads.

## Placeholders
The Formatter expansion currently offers the following placeholders:

- [`%formatter_number_format[_[locale]:[pattern]]_<number>%`](./placeholders/number/format.md)
- [`%formatter_number_from:<time_unit>_to:<time_unit>_<number>%`](./placeholders/number/fromto.md)
- [`%formatter_number_round[_[precision]:[rounding_mode]]_<number>%`](./placeholders/number/round.md)
- [`%formatter_number_shorten_<number>%`](./placeholders/number/shorten.md)
- [`%formatter_number_time[_<time_unit>]_<number>%`](./placeholders/number/time.md)
- [`%formatter_text_length_<text>%`](./placeholders/text/length.md)
- [`%formatter_text_lowercase_<text>%`](./placeholders/text/lowercase.md)
- [`%formatter_text_replace_[target]_[replacement]_<text>%`](./placeholders/text/replace.md)
- [`%formatter_text_substring_[start]:[end]_<text>%`](./placeholders/text/substring.md)
- [`%formatter_text_uppercase_<text>%`](./placeholders/text/uppercase.md)

An explanation of the placeholder pages is found in the [placeholders folder](./placeholders/README.md)

### External placeholder support
The expansion allows you to use placeholders from other expansions such as the Player expansion.  
To use a placeholder, make sure to use the bracket-format instead of the percent one (i.e. `{player_name}` instead of `%player_name%`).

## Config options
The expansion adds a few options to the config.yml of PlaceholderAPI.  
They can be found under `expansions.formatter`.

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
      <h4><code>formatting.pattern</code></h4>
    </td>
    <td rowspan="2">
      The default <a href="https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/text/DecimalFormat.html">DecimalFormat</a> to use for the <a href="./placeholders/number/format.md">format placeholder</a>.
    </td>
  </tr>
  <tr>
    <td nowrap="nowrap">
      <b>Type:</b> String<br>
      <b>Default:</b> <code>#,###,###.##</code>
    </td>
  </tr>
  <tr>
    <td nowrap="nowrap">
      <h4><code>formatting.locale</code></h4>
    </td>
    <td rowspan="2">
      The country code to use for the decimal format.<br>
      You need to replace <code>_</code> with <code>-</code>
    </td>
  </tr>
  <tr>
    <td nowrap="nowrap">
      <b>Type:</b> String<br>
      <b>Default:</b> <code>en-US</code>
    </td>
  </tr>
  <tr>
    <td nowrap="nowrap">
      <h4><code>shorten.thousands</code></h4>
    </td>
    <td rowspan="2">
      The letter used to indicate thousands.
    </td>
  </tr>
  <tr>
    <td nowrap="nowrap">
      <b>Type:</b> String<br>
      <b>Default:</b> <code>K</code>
    </td>
  </tr>
  <tr>
    <td nowrap="nowrap">
      <h4><code>shorten.millions</code></h4>
    </td>
    <td rowspan="2">
      The letter used to indicate millions.
    </td>
  </tr>
  <tr>
    <td nowrap="nowrap">
      <b>Type:</b> String<br>
      <b>Default:</b> <code>M</code>
    </td>
  </tr>
  <tr>
    <td nowrap="nowrap">
      <h4><code>shorten.billions</code></h4>
    </td>
    <td rowspan="2">
      The letter used to indicate billions.
    </td>
  </tr>
  <tr>
    <td nowrap="nowrap">
      <b>Type:</b> String<br>
      <b>Default:</b> <code>B</code>
    </td>
  </tr>
  <tr>
    <td nowrap="nowrap">
      <h4><code>shorten.trillions</code></h4>
    </td>
    <td rowspan="2">
      The letter used to indicate trillions.
    </td>
  </tr>
  <tr>
    <td nowrap="nowrap">
      <b>Type:</b> String<br>
      <b>Default:</b> <code>T</code>
    </td>
  </tr>
  <tr>
    <td nowrap="nowrap">
      <h4><code>shorten.quadrillions</code></h4>
    </td>
    <td rowspan="2">
      The letter used to indicate quadrillions.
    </td>
  </tr>
  <tr>
    <td nowrap="nowrap">
      <b>Type:</b> String<br>
      <b>Default:</b> <code>Q</code>
    </td>
  </tr>
  <tr>
    <td nowrap="nowrap">
      <h4><code>time.condensed</code></h4>
    </td>
    <td rowspan="2">
      Whether the time returned by the <a href="./placeholders/number/time">time placeholder</a> should not have any spaces.
    </td>
  </tr>
  <tr>
    <td nowrap="nowrap">
      <b>Type:</b> Boolean<br>
      <b>Default:</b> <code>false</code>
    </td>
  </tr>
  <tr>
    <td nowrap="nowrap">
      <h4><code>time.days</code></h4>
    </td>
    <td rowspan="2">
      The text used to indicate days.
    </td>
  </tr>
  <tr>
    <td nowrap="nowrap">
      <b>Type:</b> String<br>
      <b>Default:</b> <code>d</code>
    </td>
  </tr>
  <tr>
    <td nowrap="nowrap">
      <h4><code>time.hours</code></h4>
    </td>
    <td rowspan="2">
      The text used to indicate hours.
    </td>
  </tr>
  <tr>
    <td nowrap="nowrap">
      <b>Type:</b> String<br>
      <b>Default:</b> <code>h</code>
    </td>
  </tr>
  <tr>
    <td nowrap="nowrap">
      <h4><code>time.minutes</code></h4>
    </td>
    <td rowspan="2">
      The text used to indicate minutes.
    </td>
  </tr>
  <tr>
    <td nowrap="nowrap">
      <b>Type:</b> String<br>
      <b>Default:</b> <code>m</code>
    </td>
  </tr>
  <tr>
    <td nowrap="nowrap">
      <h4><code>time.seconds</code></h4>
    </td>
    <td rowspan="2">
      The text used to indicate seconds.
    </td>
  </tr>
  <tr>
    <td nowrap="nowrap">
      <b>Type:</b> String<br>
      <b>Default:</b> <code>s</code>
    </td>
  </tr>
  <tr>
    <td nowrap="nowrap">
      <h4><code>time.milliseconds</code></h4>
    </td>
    <td rowspan="2">
      The text used to indicate milliseconds.
    </td>
  </tr>
  <tr>
    <td nowrap="nowrap">
      <b>Type:</b> String<br>
      <b>Default:</b> <code>ms</code>
    </td>
  </tr>
  <tr>
    <td nowrap="nowrap">
      <h4><code>rounding.mode</code></h4>
    </td>
    <td rowspan="2">
      The default rounding mode to use if no mode was defined in the <a href="./placeholders/number/round.md">rounding placeholder</a>.
      <br>
      <br>
      <b>Available modes:</b>
      <ul>
        <li><code>up</code></li>
        <li><code>down</code></li>
        <li><code>ceiling</code></li>
        <li><code>floor</code></li>
        <li><code>half-up</code></li>
        <li><code>half-down</code></li>
        <li><code>half-even</code></li>
      </ul>
    </td>
  </tr>
  <tr>
    <td nowrap="nowrap">
      <b>Type:</b> String<br>
      <b>Default:</b> <code>half-up</code>
    </td>
  </tr>
  <tr>
    <td nowrap="nowrap">
      <h4><code>rounding.precision</code></h4>
    </td>
    <td rowspan="2">
      Default number of decimal points to display when no precision was defined the <a href="/placeholders/number/round.md">rounding placeholder</a>.
    </td>
  </tr>
  <tr>
    <td nowrap="nowrap">
      <b>Type:</b> Number <i>(0 ≤ x)</i><br>
      <b>Default:</b> <code>0</code>
    </td>
  </tr>
</table>

[wiki]: https://wiki.powerplugins.net/wiki/formatter-expansion
[changelog]: https://github.com/Andre601/Formatter-Expansion/blob/master/CHANGELOG.md

# Formatter Expansion
> **Note**  
> The Formatter-Expansion will only work on Servers using Java 11 or newer.  
> If your Server-host is still using an older Java Version (i.e. 1.8) ask them to update to at least Java 11.
> 
> **If you try using this Expansion in a Server with a Java version older than 11, you'll receive a `UnsupportedClassVersionError`!**

The formatter expansion allows you to format numbers and text in various ways.

## Placeholders
The formatter expansion currently offers two specific placeholder patterns:

- [`%formatter_text_<options>_<text>%`](#text)
- [`%formatter_number_<options>_<number>%`](#number)

### `text`
The text option tells the expansion to treat the provided value as a Text depending on the provided option.

#### `substring`
> `%formatter_text_substring_[start]:[end]_<text>%`

Substring gives back a specific range of the provided String.  
If the provided String is smaller than the provided value will the full text be returned.

The number before the `:` is 0-indexed, meaning that 0 = 1, 1 = 2 and so on.  
The number after the `:` is NOT 0-indexed, so 1 is the first character, 2 the second, etc.

Either number can be ommited to default to the very first and very last character respectively.

| Examples:                                  | Result:     | Notes: |
| ------------------------------------------ | ----------- | ------ |
| `%formatter_text_substring_0:5_Andre_601%` | `Andre`     |        |
| `%formatter_text_substring_:5_Andre_601%`  | `Andre`     |        |
| `%formatter_text_substring_0:_Andre_601%`  | `Andre_601` |        |
| `%formatter_text_substring_2:5_Andre_601%` | `dre`       |        |
| `%formatter_text_substring_2:_Andre_601%`  | `dre_601`   |        |

#### `uppercase`
> `%formatter_text_uppercase_<text>%`

Uppercase turns the entire text into uppercase.

| Examples:                              | Result:     | Notes: |
| -------------------------------------- | ----------- | ------ |
| `%formatter_text_uppercase_andre_601%` | `ANDRE_601` |        |

#### `lowercase`
> `%formatter_text_lowercase_<text>%`

Works similar to [uppercase](#uppercase) but instead of making anything large does it lowercase stuff.

| Examples:                              | Result:     | Notes: |
| -------------------------------------- | ----------- | ------ |
| `%formatter_text_lowercase_ANDRE_601%` | `andre_601` |        |

#### `replace`
> `%formatter_text_replace_<target>_<replacement>_<text>%`

Will replace any `<target>` with the provided `<replacement>`.  

`<replacement>` allows providing nothing (An empty String).  
When you want to use an underline for either `<target>` or `<replacement>` use `{{u}}` as placeholder.

| Examples:                                                | Result:                      | Notes:                                  |
| -------------------------------------------------------- | ---------------------------- | --------------------------------------- |
| `%formatter_text_replace_-_, _Andre_601-Funnycube-clip%` | `Andre_601, Funnycube, clip` |                                         |
| `%formatter_text_replace_{{u}}_ _Andre_601%`             | `Andre 601`                  | Use of `{{u}}` for replacing underlines |
| `%formatter_text_replace_ __Re pla ce%`                  | `Replace`                    |                                         |

#### `length`
> `%formatter_text_length_<text>%`

Will return the length of the provided text.

| Examples:                           | Result: | Notes: |
| ----------------------------------- | ------- | ------ |
| `%formatter_text_length_Andre_601%` | `9`     |        |

### `number`
The number option tells the expansion to treat the provided value as a number.  
The first argument after this option determines how the number is handled.

You can also use `num` as a shorter alias.

#### `format`
> `%formatter_number_format_<number>%`  
> `%formatter_number_format_[locale]:[format]_<number>%`

The format option will tell the expansion to format the provided number (optionally with a provided pattern).

By default, does the expansion use the [`format`](#format-1) and [`locale`](#locale) options set in the config.yml of PlaceholderAPI.

The expansion uses `#,###,###.##` as default format and `en_US` as default locale.  
You can override those however, by providing your own format and locale.

When using your own locale will you need to use `-` instead of `_`, so `en_US` becomes `en-US`.  
You can find an up to date list of all (known) and supported locales on the [wiki].

| Examples:                                      | Result:      | Notes: |
| ---------------------------------------------- | ------------ | ------ |
| `%formatter_number_format_1234567%`            | `1,234,567`  |        |
| `%formatter_number_format_:#,##_1234567%`      | `1,23,45,67` |        |
| `%formatter_number_format_de-CH:_1234567%`     | `1'234'567`  |        |
| `%formatter_number_format_de-CH:#,##_1234567%` | `1'23'45'67` |        |

#### `from:<time>_to:<time>`
> `%formatter_number_from:<time>_to:<time>_<number>%`

Converts the number from the given time unit to the other time unit.  
This is useful if you want to convert a single value to another single value.

The result will be a **whole number** (No decimals) and have one of the different `time` suffixes from the config applied, depending on the target time unit.

**Supported time units**:

| Time Unit   | Options                                       |
| ----------- | --------------------------------------------- |
| Day         | `days`, `day`                                 |
| Hour        | `hours`, `hour`, `hrs`                        |
| Minute      | `minutes`, `minute`, `mins`, `min`            |
| Second      | `seconds`, `second`, `secs`, `sec`            |
| Millisecond | `milliseconds`, `millisecond`, `millis`, `ms` |

| Examples:                                      | Result: | Notes:                                                  |
| ---------------------------------------------- | ------- | ------------------------------------------------------- |
| `%formatter_number_from:secs_to:minutes_120%`  | `2m`    | Turns 120 seconds into minutes.                         |
| `%formatter_number_from:minutes_to:hours_119%` | `1h`    | Turns 119 minutes into hours (Any remainder is ignored) |

#### `time`
> `%formatter_number_time_<number>%`

The time option will transform the provided number into a delay (i.e. `100` becomes `1m 40s`).  
The returned time will usually have spaces between each option, but you can change this using the [condensed](#timecondensed) config option.

The provided number will be treated as seconds.

| Examples:                       | Result:      | Notes:                                                                  |
| ------------------------------- | ------------ | ----------------------------------------------------------------------- |
| `%formatter_number_time_100%`   | `1m 40s`     | Displayed as `1m40s` with [`condensed`](#timecondensed) set to true.    |
| `%formatter_number_time_20454%` | `5h 40m 54s` | Displayed as `5h40m54s` with [`condensed`](#timecondensed) set to true. |

#### `time_fromMilliseconds` / `time_fromMs`
> `%formatter_number_time_fromMilliseconds_<number>%`  
> `%formatter_number_time_fromMs_<number>%`

Similar to the [`time`](#time) option, but treats the provided number as milliseconds instead of seconds.

| Examples:                              | Result:       | Notes:                                                                   |
| -------------------------------------- | ------------- | ------------------------------------------------------------------------ |
| `%formatter_number_time_fromMs_1200%`  | `1s 200ms`    | Displayed as `1s200ms` with [`condensed`](#timecondensed) set to true.   |
| `%formatter_number_time_fromMs_65200%` | `1m 5s 200ms` | Displayed as `1m5s200ms` with [`condensed`](#timecondensed) set to true. |

#### `time_fromSeconds` / `time_fromSecs`
> `%formatter_number_time_fromSeconds_<number>%`  
> `%formatter_number_time_fromSecs_<number>%`

Exactly the same as the [`time`](#time) option.

| Examples:                                | Result:      | Notes:                                                                  |
| ---------------------------------------- | ------------ | ----------------------------------------------------------------------- |
| `%formatter_number_time_fromSecs_100%`   | `1m 40s`     | Displayed as `1m40s` with [`condensed`](#timecondensed) set to true.    |
| `%formatter_number_time_fromSecs_20454%` | `5h 40m 54s` | Displayed as `5h40m54s` with [`condensed`](#timecondensed) set to true. |

#### `time_fromMinutes` / `time_fromMins`
> `%formatter_number_time_fromMinutes_<number>%`  
> `%formatter_number_time_fromMins_<number>%`

Similar to the [`time`](#time) option, but treats the provided number as minutes instead of seconds.

| Examples:                                | Result:      | Notes:                                                                  |
| ---------------------------------------- | ------------ | ----------------------------------------------------------------------- |
| `%formatter_number_time_fromMins_100%`   | `1h 40m`     | Displayed as `1h40m` with [`condensed`](#timecondensed) set to true.    |
| `%formatter_number_time_fromMins_20454%` | `14d 4h 54m` | Displayed as `14d4h54m` with [`condensed`](#timecondensed) set to true. |

#### `time_fromHours` / `time_fromHrs`
> `%formatter_number_time_fromHours_<number>%`  
> `%formatter_number_time_fromHrs_<number>%`

Similar to the [`time`](#time) option, but treats the provided number as hours instead of seconds.

| Examples:                               | Result:   | Notes:                                                                |
| --------------------------------------- | --------- | --------------------------------------------------------------------- |
| `%formatter_number_time_fromHrs_100%`   | `4d 4h`   | Displayed as `4d4h` with [`condensed`](#timecondensed) set to true.   |
| `%formatter_number_time_fromHrs_20454%` | `852d 6h` | Displayed as `852d6h` with [`condensed`](#timecondensed) set to true. |

#### `rounding` / `round`
> `%formatter_number_rounding_<number>%`  
> `%formatter_number_round_<number>%`
> 
> `%formatter_number_rounding_[precision]:[mode]_<number>%`  
> `%formatter_number_round_[precision]:[mode]_<number>%`

Rounds the provided number.  
Providing a `[precision]` and/or `[mode]` value will influence how many decimals are shown, and how the number is rounded.

`[precision]` can be any positive number starting at 0 and `[mode]` can be any of the following options:

- [`up`](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/math/RoundingMode.html#UP)
  - Rounds away from zero. Always increases the digit prior to a non-zero discarded fraction.
  - Examples:
    - `-1.5 -> -2` 
    - `-1.1 -> -2`
    - `-1.0 -> -1`
    - `1.0 -> 1`
    - `1.1 -> 2`
    - `1.5 -> 2`
- [`down`](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/math/RoundingMode.html#DOWN)
  - Rounds towards zero. Always decreases the digit prior to a non-zero discarded fraction.
  - Examples:
    - `-1.5 -> -1`
    - `-1.1 -> -1`
    - `-1.0 -> -1`
    - `1.0 -> 1`
    - `1.1 -> 1`
    - `1.5 -> 1`
- [`ceiling`](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/math/RoundingMode.html#CEILING)
  - Rounds towards positive infinity. Always rounds up if number is positive, otherwise rounds down.
  - Examples:
    - `-1.5 -> -1`
    - `-1.1 -> -1`
    - `-1.0 -> -1`
    - `1.0 -> 1`
    - `1.1 -> 2`
    - `1.5 -> 2`
- [`floor`](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/math/RoundingMode.html#FLOOR)
  - Rounds towards negative infinity. Always rounds down if number is positive, otherwise rounds up.
  - Examples:
    - `-1.5 -> -2`
    - `-1.1 -> -2`
    - `-1.0 -> -1`
    - `1.0 -> 1`
    - `1.1 -> 1`
    - `1.5 -> 1`
- [`half-up`](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/math/RoundingMode.html#HALF_UP)
  - Rounds to nearest neighbour unless both neighbours are equidistant (5) in which case round up. This is what you are usually taught in school.
  - Examples:
    - `-1.5 -> -2`
    - `-1.1 -> -1`
    - `-1.0 -> -1`
    - `1.0 -> 1`
    - `1.1 -> 1`
    - `1.5 -> 2`
- [`half-down`](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/math/RoundingMode.html#HALF_DOWN)
  - Rounds to nearest neighbour unless both neighbours are equidistant (5) in which case round down.
  - Examples:
    - `-1.5 -> -1`
    - `-1.1 -> -1`
    - `-1.0 -> -1`
    - `1.0 -> 1`
    - `1.1 -> 1`
    - `1.5 -> 1`
- [`half-even`](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/math/RoundingMode.html#HALF_EVEN)
  - Rounds to nearest neighbour unless both neighbours are equidistant (5), in which case, round to nearest even neighbour (Up if digit to the left is odd, otherwise down)
  - Examples:
    - `-1.5 -> -2`
    - `-1.1 -> -1`
    - `-1.0 -> -1`
    - `1.0 -> 1`
    - `1.1 -> 1`
    - `1.5 -> 2`

----
## Config
The expansion adds several config options to the config.yml of PlaceholderAPI, which can be altered.

### `format`
The default Format used for the numbers.  
The format is based on Java's DecimalFormat[^1].

### `locale`
This option is used for the [format](#format).  
Some formats have a different syntax, depending on the locale used.

The locale can be a simple language code (e.g. `en`), or a language code with country code (e.g. `en-US`).  
Note that you have to use `-` instead of `_` when using country codes.

A list of (known) supported locales can be found on the [wiki].

### `time.condensed`
When this option is set to `false` (default value) will the returned duration have spaces between each number.  
For example will `%formatter_number_time_100%` show as `1m40s` when this is set to `true`.

This option will only be used when the [time option](#time) is used in the placeholder.

### `time.days`
The text that is used to indicate days when the [time](#time) option is used.  
Defaults to `d`.

### `time.hours`
The text that is used to indicate hours when the [time](#time) option is used.  
Defaults to `h`.

### `time.minutes`
The text that is used to indicate minutes when the [time](#time) option is used.  
Defaults to `m`.

### `time.seconds`
The text that is used to indicate seconds when the [time](#time) option is used.  
Defaults to `s`.

### `time.milliseconds`
The text that is used to indicate milliseconds when the [time](#time) option is used.  
Defaults to `ms`.

### `rounding.mode`
Determines the rounding behaviour that should be used.  
Can be `up`, `down`, `ceiling`, `floor`, `half-up`, `half-down` or `half-even`.

Defaults to `half-up`.

### `rounding.precision`
Sets how precise the number should be. The higher the number, the more decimal fractions are displayed.  
Can be any positive number starting from 0.

Defaults to `0`.

## Changelog
Changes can be found in the [CHANGELOG.md][changelog] file.

<!-- Footnotes -->
[^1]: https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/text/DecimalFormat.html

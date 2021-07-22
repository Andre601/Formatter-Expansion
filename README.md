[wiki]: https://wiki.powerplugins.net/wiki/formatter-expansion
[changelog]: https://github.com/Andre601/Formatter-Expansion/blob/master/CHANGELOG.md

# Formatter Expansion
> ### Supported Java Version
> The Formatter Expansion will only work on Servers using Java 11 or newer.  
> If your Serverhost is still on Java 8 and you want to use this expansion should you ask them to update their Java enviroment to at least Java 11.
> 
> **Servers trying to use this expansion while not using Java 11 will receive a `UnsupportedClassVersionError`!**

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

**Examples**:  
```yaml
%formatter_text_substring_0:5_Andre_601% -> Andre
%formatter_text_substring_:5_Andre_601%  -> Andre
%formatter_text_substring_0:_Andre_601%  -> Andre_601
%formatter_text_substring_2:5_Andre_601% -> dre
%formatter_text_substring_2:_Andre_601%  -> dre_601
```

#### `uppercase`
> `%formatter_text_uppercase_<text>%`

Uppercase turns the entire text into uppercase.

**Example**:  
```yaml
%formatter_text_uppercase_Andre_601% -> ANDRE_601
```

#### `lowercase`
> `%formatter_text_lowercase_<text>%`

Works similar to [uppercase](#uppercase) but instead of making anything large does it lowercase stuff.

**Example**:  
```yaml
%formatter_text_lowercase_ANDRE_601% -> andre_601
```

#### `replace`
> `%formatter_text_replace_<target>_<replacement>_<text>%`

Will replace any `<target>` with the provided `<replacement>`.  

`<replacement>` allows providing nothing (An empty String).  
When you want to use an underline for either `<target>` or `<replacement>` use `{{u}}` as placeholder.

**Examples**:  
```yaml
%formatter_text_replace_-_, _Andre_601-Funnycube-clip% -> Andre_601, Funnycube, clip
%formatter_text_replace_{{u}}_ _Andre_601%             -> Andre 601
%formatter_text_replace_ __Repl a ce%                  -> Replace
```

### `number`
The number option tells the expansion to treat the provided value as a number.  
The first argument after this option determines how the number is handled.

#### `format`
> `%formatter_number_format_<number>%`  
> `%formatter_number_format_[locale]:[format]_<number>%`

The format option will tell the expansion to format the provided number (optionally with a provided pattern).

By default, does the expansion use the [`format`](#format-1) and [`locale`](#locale) options set in the config.yml of PlaceholderAPI.

The expansion uses `#,###,###.##` as default format and `en_US` as default locale.  
You can override those however, by providing your own format and locale.

When using your own locale will you need to use `-` instead of `_`, so `en_US` becomes `en-US`.  
You can find an up to date list of all (known) and supported locales on the [wiki].

**Examples**:  
```yaml
%formatter_number_format_1234567%            -> 1,234,567
%formatter_number_format_:#,##_1234567%      -> 1,23,45,67
%formatter_number_format_de-CH:_1234567%     -> 1'234'567
%formatter_number_format_de-CH:#,##_1234567% -> 1'23'45'67
```

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

**Examples**:  
```yaml
%formatter_number_from:secs_to:minutes_120%  -> 2m
%formatter_number_from:minutes_to:hours_119% -> 1h
```

#### `time`
> `%formatter_number_time_<number>%`

The time option will transform the provided number into a delay (i.e. `100` becomes `1m 40s`).  
The returned time will usually have spaces between each option, but you can change this using the [condensed](#timecondensed) config option.

**Examples**:  
```yaml
%formatter_number_time_100%   -> 1m 40s     (1m40s with condensed set to true)
%formatter_number_time_20454% -> 5h 40m 54s (5h40m54s with condensed set to true)
```

#### `time_fromMilliseconds` / `time_fromMs`
> `%formatter_number_time_fromMilliseconds_<number>%`  
> `%formatter_number_time_fromMs_<number>%`

Similar to the [`time`](#time) option, but treats the provided number as milliseconds instead of seconds.

**Examples**:  
```yaml
%formatter_number_time_fromMilliseconds_1200%  -> 1s 200ms (1s200ms with condensed set to true)
%formatter_number_time_fromMilliseconds_65200% -> 1m 5s 200ms (1m5s200ms with condensed set to true)
```

#### `time_fromSeconds` / `time_fromSecs`
> `%formatter_number_time_fromSeconds_<number>%`  
> `%formatter_number_time_fromSecs_<number>%`

Exactly the same as the [`time`](#time) option.

**Examples**:
```yaml
%formatter_number_time_fromSeconds_100%   -> 1m 40s     (1m40s with condensed set to true)
%formatter_number_time_fromSecs_20454%    -> 5h 40m 54s (5h40m54s with condensed set to true)
```

#### `time_minutes` / `time_mins`
> `%formatter_number_time_minutes_<number>%`  
> `%formatter_number_time_mins_<number>%`

Similar to the [`time`](#time) option, but treats the provided number as minutes instead of seconds.

**Examples**:
```yaml
%formatter_number_time_minutes_100%   -> 1h 40m     (1h40m with condensed set to true)
%formatter_number_time_minutes_20454% -> 14d 4h 54m (14d4h54m with condensed set to true)
```

#### `time_hours` / `time_hrs`
> `%formatter_number_time_hours_<number>%`  
> `%formatter_number_time_hrs_<number>%`

Similar to the [`time`](#time) option, but treats the provided number as hours instead of seconds.

**Examples**:
```yaml
%formatter_number_time_hours_100%   -> 4d 4h   (4d4h with condensed set to true)
%formatter_number_time_hours_20454% -> 852d 6h (852d6h with condensed set to true)
```

----
## Config
The expansion adds several config options to the config.yml of PlaceholderAPI, which can be altered.

### `format`
The default Format used for the numbers.  
More info on how the formatting works can be found here: https://docs.oracle.com/javase/7/docs/api/java/text/DecimalFormat.html

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

## Changelog
Changes can be found in the [CHANGELOG.md][changelog] file.

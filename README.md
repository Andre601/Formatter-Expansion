[wiki]: https://wiki.powerplugins.net/wiki/formatter-expansion

# Formatter Expansion
The formatter expansion allows you to format numbers and text in various ways.

## Placeholder
The placeholder follows specific patterns that you have to use.  

For [text placeholders](#text) is the format either `%formatter_text_<option>_<values>%` or `%formatter_text_<option1>_<option2>_<values>%`.  
For [number placeholders](#number) is the format `%formatter_number_<option>_<number>[_<options>]%`.

Note that `<>` indicates required options while `[]` indicates optional ones.

### `text`
The text option tells the expansion to treat the provided value as a Text depending on the provided option.

#### `substring`
Substring gives back a specific range of the provided String.  
If the provided String is smaller than the provided value will the full text be returned.

The number before the `:` is 0-indexed, meaning that 0 = 1, 1 = 2 and so on.  
The number after the `:` is NOT 0-indexed, so 1 is the first character, 2 the second, etc.

Either number can be ommited to default to the very first and very last character respectively.

**Examples**:  
```
%formatter_text_substring_0:5_Andre_601% -> Andre
%formatter_text_substring_:5_Andre_601%  -> Andre
%formatter_text_substring_0:_Andre_601%  -> Andre_601
%formatter_text_substring_2:5_Andre_601% -> dre
%formatter_text_substring_2:_Andre_601%  -> dre_601
```

#### `uppercase`
Uppercase turns the entire text into uppercase.

**Example**:  
```
%formatter_text_uppercase_Andre_601% -> ANDRE_601
```

#### `lowercase`
Works similar to [uppercase](#uppercase) but instead of making anything large does it lowercase stuff.

**Example**:  
```
%formatter_text_lowercase_ANDRE_601% -> andre_601
```

#### `join`
This option will combine the provided text with the specified character(s).  
The text to combine needs to be separated by spaces.

The full syntax is `%formatter_text_join_<character>_<text with spaces>%`

**Example**:  
```
%formatter_text_join_, _Andre_601 Funnycube extended_clip% -> Andre_601, Funnycube, extended_clip
```

### `number`
The number option tells the expansion to treat the provided value as a number.  
The first argument after this option determines how the number is handled.

#### `format`
The format option will tell the expansion to format the provided number (optionally with a provided pattern).

By default would the expansion use the [`format`](#format-1) config option which is `#,###,###.##` by default.  
You can override this by providing a `format:<format>` option at the end of the placeholder.

Note that the format displayed might vary depending on the location your server is hosted at.  
You can override the location by using the `locale:<locale>` option too.  
It is important to point out, that you have to provide the locale in a different format (i.e. `en-US` inestead of `en_US`)  
You can find an up to date list of all (known) and supported locales on the [wiki].

**Examples**:  
```
%formatter_number_format_1000357%                          -> 1,000,357
%formatter_number_format_1000357_format:#,##%              -> 1,00,03,57
%formatter_number_format_1000357_locale:de-CH%             -> 1'000'357
%formatter_number_format_1000357_format:#,##_locale:de-CH% -> 1'00'03'57
```

#### `time`
The time option will transform the provided number into a delay (i.e. `100` becomes `1m 40s`).  
The returned time will usually have spaces between each option, but you can change this using the [condensed](#timecondensed) config option.

**Examples**:  
```
%formatter_number_time_100%   -> 1m 40s     (1m40s with condensed set to true)
%formatter_number_time_20454% -> 5h 40m 54s (5h40m54s with condensed set to true)
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

## Changelog
- `1.0.0` First release.
- `1.0.1` Made placeholder options case-insensitive.
- `1.1.0` Added `time` option for placeholder. This converts the number to a time.
- `1.1.1` Fix time not working with large numbers
- `1.2.0` Revorked placeholder pattern to make it more structured. Added text formatting options.
- `1.3.0` Updated to support PlaceholderAPI 2.10.7 and Spigot 1.16.1
- `1.3.1` Added join option for Text to combine several Strings (separated by spaces).
- `1.3.2` Changed config option `condensed` from String to boolean and locale now uses `-` instead of `:`

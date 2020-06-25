[wiki]: https://wiki.powerplugins.net/wiki/formatter-expansion

# Formatter Expansion
The formatter expansion allows you to format numbers and text in various ways.

## Placeholder
The placeholder follows a specific pattern that you have to use.  

The pattern is either `%formatter_text_<option[_option2]>_<value>%` or `%formatter_number_<option>_<number>[_<options>]%`

### `text`
The text option tells the expansion to treat the provided value as a Text depending on the provided option.

#### `substring`
Substring gives back a specific range of the provided String.  
If the provided String is smaller than the provided value will the full text be returned.

You can also ommit the first or second value to use the start (`0`) or end of a text respectively.

Here are some examples:  
```
# Note that the end value is "n - 1" so 5 becomes index 4 (5th character)

%formatter_text_substring_0:5_Andre_601% -> Andre
%formatter_text_substring_:5_Andre_601% -> Andre
%formatter_text_substring_0:_Andre_601% -> Andre_601
%formatter_text_substring_2:5_Andre_601% -> dre
%formatter_text_substring_2:_Andre_601% -> dre_601
```

#### `uppercase`
Uppercase turns the entire text into uppercase.  
For example would `%formatter_text_uppercase_Andre_601%` return `ANDRE_601`.

#### `lowercase`
Works similar to [uppercase](#uppercase) but instead of making anything large does it lowercase stuff.  
For example would `%formatter_text_uppercase_ANDRE_601%` return `andre_601`.


### `number`
The number option tells the expansion to treat the provided value as a number.  
The first argument after this option determines how the number is handled.

The full syntax of the placeholder would be `%formatter_number_<option>_<number>[_options]%`

#### `format`
The format option will tell the expansion to format the provided number (optionally with a provided pattern).

By default would the expansion use the [`format`](#format-1) config option which is `#,###,###.##` by default.  
You can override this by providing a `format:<format>` option at the end of the placeholder.

Note that the format displayed might vary depending on the location your server is hosted at.  
You can override the location by using the `locale:<locale>` option too.  
It is important to point out, that you have to provide the locale in a different format (i.e. `en:US` inestead of `en_US`)  
You can find an up to date list of all (known) and supported locales on the [wiki].

Some examples:  
```
%formatter_number_format_1000357% -> 1,000,357
%formatter_number_format_1000357_format:#,##% -> 1,00,03,57
%formatter_number_format_1000357_locale:de:CH% -> 1'000'357
%formatter_number_format_1000357_format:#,##_locale:de:CH% -> 1'00'03'57
```

#### `time`
The time option will transform the provided number into a delay (i.e. `100` becomes `1m 40s`).  
The returned time will usually have spaces between each option, but you can change this using the [condensed](#timecondensed) config option.

Here is a quick example:  
```
%formatter_number_time_100% -> 1m 40s
%formatter_number_time_20454% -> 5h 40m 54s
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

The locale can be a simple language code (e.g. `en`), or a language code with country code (e.g. `en:US`).  
Note that you have to use `:` instead of `_` when using country codes.

A list of (known) supported locales can be found on the [wiki].

### `time.condensed`
When this option is set to `no` (default value) will the returned duration have spaces between each number.  
For example will `%formatter_number_time_100%` show as `1m40s` when this is set to anything other than `no`.

This option is only used when the [time option](#time) is used in the placeholder.

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
- `1.2.0` Revorked placeholder pattern to make it more structured. Added text formatting options.
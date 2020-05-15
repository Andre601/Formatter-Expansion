[wiki]: https://wiki.powerplugins.net/wiki/formatter-expansion

# Formatter Expansion
The formatter expansion allows you to turn a long number into a formatted String.  
For example would `123456789` become `123,456,789` using this expansion.

## Placeholder
The placeholder follows a specific pattern that you have to use.  
The format is `%formatter_<options>%` where `<options>` is one of the following options.

### `value:(<value>)`
This option is required as it contains the actual number to format.  
`<value>` can either be a full number or a placeholder using `{}` (`{placeholder}`).  
Note that the placeholder has to return a full number.

### `format:(<format>)`
This option is optional.  
It allows you to set a per-placeholder format, which overrides the default one.

More info on how the formatting works can be found here: https://docs.oracle.com/javase/7/docs/api/java/text/DecimalFormat.html

**Note**: The Format also depends on the [locale](#locale) you set!

### `locale:(<locale>)`
The locale option allows you to set the locale on a per-placeholder level.  
The locale is used to determine the format of the number, as some locales/countries have different formattings.

A list of (known) supported locales can be found on the [wiki].

### `time`
When this option is added to the placeholder will the expansion switch to time-conversion.  
In this mode will it treat the [value](#valuevalue) as seconds and try to convert it into a duration.

For example would `%formatter_time_value(3600)%` be translated to `1h` (1 hour).  
The position of this option is not important as long as it is separated from other options using a underscore.

**Note**: When using this option are both the [format](#formatformat) and [locale](#localelocale) options ignored!

----
## Config
The expansion adds two config options to the config.yml of PlaceholderAPI, which can be altered.

### `format`
The default Format used for the numbers.  
More info on how the formatting works can be found here: https://docs.oracle.com/javase/7/docs/api/java/text/DecimalFormat.html

### `locale`
This option is used for the [format](#format).  
Some formats have a different syntax, depending on the locale used.

The locale can be a simple language code (e.g. `en`), or a language code with country code (e.g. `en_US`). 

A list of (known) supported locales can be found on the [wiki].

### `time.condensed`
When this option is set to `no` (default value) will the returned duration have spaces between each number.  
For example will `%formatter_time_value:(100)%` show as `1m40s` when this is set to anything else than `no`.

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